package io.opencubes.stuff.container.slot

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack

open class SlotLocked(inventory: IInventory, index: Int, x: Int, y: Int) : Slot(inventory, index, x, y) {
  override fun canTakeStack(playerIn: EntityPlayer?) = false
  override fun onTake(thePlayer: EntityPlayer, stack: ItemStack): ItemStack {
    return ItemStack.EMPTY
  }
}