package io.opencubes.stuff.container.slot

import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

class SlotReadOnlyItemHandler(itemHandler: IItemHandler?, index: Int, xPosition: Int, yPosition: Int) :
    SlotItemHandler(itemHandler, index, xPosition, yPosition) {
  override fun isItemValid(stack: ItemStack) = false
}