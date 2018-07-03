package io.opencubes.expandablestorage.item.bag

import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.crafting.IShapedRecipe
import net.minecraftforge.registries.IForgeRegistryEntry

abstract class BagRecipe() : IShapedRecipe, IForgeRegistryEntry.Impl<IRecipe>() {
  constructor(resourceName: ResourceLocation) : this() {
    registryName = resourceName
  }

  abstract val minMaxHeight: IntRange
  abstract val minMaxWidth: IntRange

  override fun canFit(width: Int, height: Int) =
      width in minMaxWidth &&
      height in minMaxHeight
  override fun getRecipeHeight() = minMaxHeight.last
  override fun getRecipeWidth() = minMaxWidth.last
  override fun getRecipeOutput() = ItemStack.EMPTY
}
