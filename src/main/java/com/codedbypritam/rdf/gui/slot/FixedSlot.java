package com.codedbypritam.rdf.gui.slot;

import com.codedbypritam.rdf.RDF;
import com.codedbypritam.rdf.gui.click.ClickContext;
import com.codedbypritam.rdf.gui.click.ClickHandler;
import com.codedbypritam.rdf.gui.rtn.ReturnData;
import com.codedbypritam.rdf.gui.rtn.ReturnType;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

// TODO: ADD EXECUTION CALLBACK
public class FixedSlot {
    private @Nullable ItemStack displayItem;

    private @Nullable String clickPermissionNode;
    private final EnumMap<SlotClickType, String> subClickPermissionNode = new EnumMap<>(SlotClickType.class);

    private Predicate<ClickContext> clickTest = ctx -> true;

    private final EnumMap<SlotClickType, TreeMap<Integer, ClickHandler>> clickHandlers = new EnumMap<>(SlotClickType.class);

    public static FixedSlot slot() {
        return new FixedSlot();
    }

    public CompletableFuture<ReturnData> handleClick(@NonNull SlotClickType clickType, @NonNull ClickContext ctx) {
        if (clickPermissionNode != null && !ctx.getClicker().hasPermission(clickPermissionNode)) return CompletableFuture.completedFuture(
                new ReturnData(ReturnType.NO_PERMISSION, clickPermissionNode)
        );

        final var subPermNode = this.subClickPermissionNode.get(clickType);
        if (subPermNode != null && !ctx.getClicker().hasPermission(subPermNode)) return CompletableFuture.completedFuture(
                new ReturnData(ReturnType.NO_SUB_PERMISSION, subPermNode)
            );

        final var clickTestResult = this.clickTest.test(ctx);
        if (!clickTestResult) return CompletableFuture.completedFuture(
                new ReturnData(ReturnType.TEST_FAILURE, null)
            );

        final var handlers = this.clickHandlers.get(clickType);
        if (handlers == null || handlers.isEmpty()) return CompletableFuture.completedFuture(
                    new ReturnData(ReturnType.SUCCESS, null)
            );

        CompletableFuture<Void> chain = CompletableFuture.completedFuture(null);

        for (var entry : handlers.entrySet()) {
            final var handler = entry.getValue();

            chain = chain.thenCompose(v ->
                    handler.execute(ctx).exceptionally(ex -> {
                        Throwable t = (ex instanceof java.util.concurrent.CompletionException ce && ce.getCause() != null)
                                ? ce.getCause()
                                : ex;

                        RDF.getPlugin().getLogger().log(
                                java.util.logging.Level.SEVERE,
                                "GUI click handler failed (type=" + clickType
                                        + ", priority=" + entry.getKey()
                                        + ", player=" + ctx.getClicker().getName() + ")",
                                t
                        );

                        return null;
                    })
            );
        }

        return chain.thenApply(v -> new ReturnData(ReturnType.SUCCESS, null));
    }

    public @NonNull FixedSlot displayItem(@NonNull ItemStack displayItem) {
        this.displayItem = displayItem;
        return this;
    }
    public @NonNull ItemStack displayItem() {
        if (displayItem == null) return new ItemStack(Material.AIR);
        return displayItem;
    }

    public @NonNull FixedSlot clickPermissionNode(@Nullable String clickPermissionNode) {
        this.clickPermissionNode = clickPermissionNode;
        return this;
    }
    public @Nullable String clickPermissionNode() {
        return clickPermissionNode;
    }

    public @NonNull FixedSlot subClickPermissionNode(@NonNull SlotClickType clickType , @NonNull String subClickPermissionNode) {
        this.subClickPermissionNode.put(clickType, subClickPermissionNode);
        return this;
    }
    public @Nullable String subClickPermissionNode(@NonNull SlotClickType clickType) {
        return this.subClickPermissionNode.get(clickType);
    }

    public @NonNull FixedSlot clickTest(Predicate<ClickContext> test) {
        this.clickTest = test;
        return this;
    }

    public @NonNull FixedSlot addHandler(@NonNull SlotClickType clickType, @NonNull ClickHandler clickHandler, int priority) {
        this.clickHandlers.putIfAbsent(clickType, new TreeMap<>());
        this.clickHandlers.get(clickType).put(priority, clickHandler);
        return this;
    }
}