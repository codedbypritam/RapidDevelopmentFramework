package com.codedbypritam.rdf.gui;

import com.codedbypritam.rdf.gui.slot.FixedSlot;
import lombok.Getter;
import lombok.NonNull;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class GUI implements InventoryHolder {
    @Getter
    private final Inventory inventory;
    private final Map<Integer, FixedSlot> slots = new ConcurrentHashMap<>();

    public GUI(@NonNull Component title, int rows) {
        if (rows < 1 || rows > 6) {
            throw new IndexOutOfBoundsException("rows must be between 1 and 6");
        }
        this.inventory = Bukkit.createInventory(this, rows * 9, title);

        for (int i = 0; i < (rows * 9) - 1; i++) {
            this.slots.put(i, FixedSlot.slot());
        }
    }

    public FixedSlot getSlotAt(int row, int column) {
        final int slotIndex = (row - 1) * 9 + column - 1;
        if (slotIndex + 1 < 1 || slotIndex > this.inventory.getSize()) {
            throw new IndexOutOfBoundsException("slot out of bounds");
        }
        return this.slots.get(slotIndex);
    }
}
