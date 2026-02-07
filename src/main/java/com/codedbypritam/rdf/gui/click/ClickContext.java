package com.codedbypritam.rdf.gui.click;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ClickContext {
    @Getter private final @NonNull HumanEntity clicker;
    @Getter private final @Nullable ItemStack cursorItem;

    @Getter @NonNull private final StringDataContainer dataContainer = new StringDataContainer();

    private ClickContext(@NonNull HumanEntity clicker, @Nullable ItemStack cursorItem) {
        this.clicker = clicker;
        this.cursorItem = cursorItem;
    }

    public static ClickContext create(HumanEntity clicker, @Nullable ItemStack cursorItem) {
        return new ClickContext(clicker, cursorItem);
    }
}
