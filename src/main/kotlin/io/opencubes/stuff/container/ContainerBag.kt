package io.opencubes.stuff.container

import io.opencubes.stuff.container.slot.SlotLocked
import io.opencubes.stuff.item.ItemBag
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerBag(playerInv: InventoryPlayer, bag: ItemStack, private val position: Int) : Container() {

  init {
    val inventory = bag.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)

    for (y in 0 until 5)
      for (x in 0 until 9)
        addSlotToContainer(object : SlotItemHandler(inventory, y * 9 + x, x * 18 + 8, y * 18 + 18) {
          override fun isItemValid(stack: ItemStack) = stack != bag
        })

    for (x in 0 until 9)
      for (y in 0 until 3)
        addSlotToContainer(Slot(playerInv, x + y * 9 + 9, x * 18 + 8, y * 18 + 122))
    for (x in 0 until 9)
      addHotbarSlotToContainer(playerInv, x, x * 18 + 8, 180)
  }

  protected fun addHotbarSlotToContainer(inventory: IInventory, index: Int, x: Int, y: Int) {
    addSlotToContainer(
        if (index == position) SlotLocked(inventory, index, x, y)
        else Slot(inventory, index, x, y)
    )
  }

  override fun canInteractWith(playerIn: EntityPlayer?) = true

  override fun transferStackInSlot(playerIn: EntityPlayer, index: Int): ItemStack {
    var stack = ItemStack.EMPTY
    val slot = inventorySlots[index]

    if (slot != null && slot.hasStack) {
      val stack1 = slot.stack
      stack = stack1.copy()

      if (index < ItemBag.SIZE) {
        if (!mergeItemStack(stack1, ItemBag.SIZE, inventorySlots.size, true))
          return ItemStack.EMPTY
      } else if (!mergeItemStack(stack1, 0, ItemBag.SIZE, false))
        return ItemStack.EMPTY

      if (stack1.isEmpty) {
        slot.putStack(ItemStack.EMPTY)
      } else {
        slot.onSlotChanged()
      }
    }
    return stack
  }
}