@file:Suppress("UNUSED_PARAMETER")

package io.opencubes.expandablestorage.item.bag

import io.opencubes.expandablestorage.ContainerBase
import io.opencubes.expandablestorage.capabilities.InventoryProvider
import io.opencubes.expandablestorage.item.ItemBag
import io.opencubes.expandablestorage.item.slot.SlotLocked
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack

class ContainerBag(playerInv: InventoryPlayer, bag: ItemStack, private val position: Int) : ContainerBase() {

  init {
    val inventory = InventoryProvider.fromStack(bag).get().get()

    buildInventoryGrid(inventory, gridHeight = 5, startX = 30, startY = 18)
    buildInventoryGrid(playerInv, indexOffset = 9, gridHeight = 3, startX = 30, startY = 122)
    buildInventoryGrid(startX = 30, startY = 180) { i, x, y ->
      if (i == position) SlotLocked(playerInv, i, x, y)
      else Slot(playerInv, i, x, y)
    }
    addSlotToContainer(Slot(playerInv, 40, 8, 180))
  }

  override fun canInteractWith(playerIn: EntityPlayer?) = true

  override fun transferStackInSlot(player: EntityPlayer, slotIndex: Int): ItemStack {
    println(slotIndex)
    val slot = this.getSlot(slotIndex)

    if (!slot.canTakeStack(player))
      return slot.stack

    if (slotIndex == position || !slot.hasStack)
      return ItemStack.EMPTY

    val stack = slot.stack
    val newStack = stack.copy()

//    if (ItemBag.isBlacklisted(slot.stack))
//      return ItemStack.EMPTY
    if (slotIndex < ItemBag.size) {
      if (!this.mergeItemStack(stack, ItemBag.size, this.inventorySlots.size, true))
        return ItemStack.EMPTY
      slot.onSlotChanged()
    } else if (!this.mergeItemStack(stack, 0, ItemBag.size, false))
      return ItemStack.EMPTY

    if (stack.isEmpty)
      slot.putStack(ItemStack.EMPTY)
    else
      slot.onSlotChanged()

    return slot.onTake(player, newStack)
  }
}