package io.opencubes.expandablestorage.data

import io.opencubes.boxlin.get
import io.opencubes.boxlin.set
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

data class Stack(private val stack: ItemStack, var amount: Int) {
  fun getOne(): ItemStack {
    val stack = ItemStack(
        stack.item,
        if (amount > stack.maxStackSize) stack.maxStackSize else amount,
        stack.itemDamage
    )
    amount -= stack.count
    return stack
  }

  fun serializeNBT(): NBTTagCompound {
    val tag = NBTTagCompound()
    tag["stack"] = stack.serializeNBT()
    tag["amount"] = amount
    return tag
  }

  companion object {
    val EMPTY = Stack(ItemStack.EMPTY, 0)
    fun fromNBT(compound: NBTTagCompound) = Stack(ItemStack(compound.getCompoundTag("stack")), compound["amount"])
  }
}