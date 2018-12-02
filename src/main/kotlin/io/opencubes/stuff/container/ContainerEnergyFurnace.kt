package io.opencubes.stuff.container

import io.opencubes.stuff.container.slot.SlotReadOnlyItemHandler
import io.opencubes.stuff.tileentity.TileEntityEnergyFurnace
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.FurnaceRecipes
import net.minecraft.util.EnumFacing
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerEnergyFurnace(playerInv: InventoryPlayer, private val te: TileEntityEnergyFurnace) : Container() {
  init {
    val input = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP)
    val output = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)

    addSlotToContainer(object : SlotItemHandler(input, 0, 62, 18) {
      override fun isItemValid(stack: ItemStack) =
          !FurnaceRecipes.instance().getSmeltingResult(stack).isEmpty
    })
    addSlotToContainer(SlotReadOnlyItemHandler(output, 0, 98, 18))

    for (x in 0 until 9)
      for (y in 0 until 3)
        addSlotToContainer(Slot(playerInv, x + y * 9 + 9, x * 18 + 8, y * 18 + 50))
    for (x in 0 until 9)
      addSlotToContainer(Slot(playerInv, x, x * 18 + 8, 108))
  }

  override fun canInteractWith(playerIn: EntityPlayer) =
      playerIn.position.distanceSq(te.pos) <= 64

  override fun transferStackInSlot(playerIn: EntityPlayer, index: Int): ItemStack {
    var stack = ItemStack.EMPTY
    val slot = inventorySlots[index]

    if (slot != null && slot.hasStack) {
      val stack1 = slot.stack
      stack = stack1.copy()

      if (index < TileEntityEnergyFurnace.SIZE) {
        if (!mergeItemStack(stack1, TileEntityEnergyFurnace.SIZE, inventorySlots.size, false))
          return ItemStack.EMPTY
      } else if (!mergeItemStack(stack1, 0, TileEntityEnergyFurnace.SIZE, false))
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