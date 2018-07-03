package io.opencubes.expandablestorage.capabilities

import io.opencubes.expandablestorage.capability
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

class InventoryProvider(private val inventory: ItemStackHandler,
                        private vararg val sides: EnumFacing) : ICapabilitySerializable<NBTTagCompound> {
  constructor(size: Int) : this(ItemStackHandler(size))

  private fun atSide(facing: EnumFacing?) = (facing == null && sides.isEmpty()) || facing in sides

  @Suppress("UNCHECKED_CAST")
  override fun <T: Any?> getCapability(capability: Capability<T>, facing: EnumFacing?) =
      if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && atSide(facing)) inventory as T? else null

  override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) =
      capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && atSide(facing)

  override fun deserializeNBT(nbt: NBTTagCompound) = inventory.deserializeNBT(nbt)

  override fun serializeNBT() = inventory.serializeNBT()!!

  companion object {
    /**
     * Get the associated inventory from [stack] at [facing].
     *
     * @param stack
     * @param facing
     */
    @JvmStatic fun fromStack(stack: ItemStack, facing: EnumFacing) =
        stack.capability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)

    /**
     * Get the associated inventory from [stack] with null facing.
     *
     * @param stack
     */
    @JvmStatic fun fromStack(stack: ItemStack) =
        stack.capability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
  }
}