package io.opencubes.expandablestorage.api

import net.minecraft.item.ItemStack

interface ICapabilityBagUpgrade {
  fun moveStack(stack: ItemStack)
}