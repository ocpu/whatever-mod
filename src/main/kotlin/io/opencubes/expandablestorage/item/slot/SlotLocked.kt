package io.opencubes.expandablestorage.item.slot

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.Slot

open class SlotLocked(inventory: IInventory, index: Int, x: Int, y: Int) : Slot(inventory, index, x, y) {
  override fun canTakeStack(playerIn: EntityPlayer?) = false
}