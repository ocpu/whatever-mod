package io.opencubes.stuff.tools

import net.minecraft.item.ItemStack

interface IDrops {
  fun getDrops(): List<ItemStack>
}