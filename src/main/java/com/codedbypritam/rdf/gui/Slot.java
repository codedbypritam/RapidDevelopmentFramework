package com.codedbypritam.rdf.gui;

import lombok.NonNull;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class Slot {
    @Setter private ItemStack viewItem;
    private final EnumMap<SlotClickType, Set<BiConsumer<HumanEntity, @Nullable ItemStack>>> clickHandlers = new EnumMap<>(SlotClickType.class);
    @Setter private BiPredicate<HumanEntity, @Nullable ItemStack> clickTest = (clicker, cursorItem) -> true;
    @Setter private BiPredicate<HumanEntity, @Nullable ItemStack> changeTest = (clicker, cursorItem) -> true;

    public Slot() {

    }

    /**
     * Handle any click on the slot.
     *
     * @param clickType the type of click from {@link SlotClickType}
     * @param onClick the consumer run when conditions are met, 1st parameter is the human entity that clicked, and the second parameter is the item that was on the cursor when it was clicked.
     */
    public Slot onClick(@NonNull SlotClickType clickType, @NonNull BiConsumer<HumanEntity, @Nullable ItemStack> onClick) {
        this.clickHandlers.put(clickType, this.);
        return this;
    }

    public boolean handleClick(@NonNull HumanEntity clicker, @NonNull SlotClickType clickType , @Nullable ItemStack item) {
        final boolean testResult = this.clickTest.test(clicker, item);
        if (testResult) {
            this.clickHandlers.getOrDefault(clickType, Set.of());
        }
        return testResult;
    }

    public Optional<ItemStack> getViewItem() {
        return Optional.ofNullable(viewItem);
    }

    public enum SlotClickType {
        LEFT,
        SHIFT_LEFT,
        RIGHT,
        SHIFT_RIGHT,
        MIDDLE,
        NUMBER_KEY,
        DOUBLE_CLICK,
        DROP,
        CONTROL_DROP,
        SWAP_OFFHAND
    }
}
