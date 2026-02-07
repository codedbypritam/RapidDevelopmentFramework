package com.codedbypritam.rdf.listeners;

import com.codedbypritam.rdf.gui.GUI;
import com.codedbypritam.rdf.gui.click.ClickContext;
import com.codedbypritam.rdf.gui.slot.SlotClickType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() instanceof GUI gui) {
            final int slotIndex = event.getSlot();

            final SlotClickType slotClickType;

            switch (event.getClick()) {
                case SHIFT_LEFT -> slotClickType = SlotClickType.SHIFT_LEFT;
                case RIGHT -> slotClickType = SlotClickType.RIGHT;
                case SHIFT_RIGHT -> slotClickType = SlotClickType.SHIFT_RIGHT;
                case MIDDLE -> slotClickType = SlotClickType.MIDDLE;
                case NUMBER_KEY -> slotClickType = SlotClickType.NUMBER_KEY;
                case DOUBLE_CLICK -> slotClickType = SlotClickType.DOUBLE_CLICK;
                case DROP -> slotClickType = SlotClickType.DROP;
                case CONTROL_DROP -> slotClickType = SlotClickType.CONTROL_DROP;
                case SWAP_OFFHAND -> slotClickType = SlotClickType.SWAP_OFFHAND;
                default -> slotClickType = SlotClickType.LEFT;
            }

            final var result = gui.getSlotAt(slotIndex / 9, slotIndex % 9).handleClick(slotClickType, ClickContext.create(event.getWhoClicked(), event.getCursor()));
        }
    }
}
