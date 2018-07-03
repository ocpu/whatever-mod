package io.opencubes.expandablestorage.item.bag

import io.opencubes.expandablestorage.api.capabilities.upgrades.IUpgrade
import io.opencubes.expandablestorage.item.ExpandableStorageItems as Items
import io.opencubes.expandablestorage.toList
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.world.World
import net.minecraftforge.common.crafting.IShapedRecipe
import net.minecraftforge.registries.IForgeRegistryEntry

class UpgradeRecipe(val upgrade: Item) : IForgeRegistryEntry.Impl<IRecipe>(), IRecipe {
  override fun canFit(width: Int, height: Int) = true

  override fun matches(inv: InventoryCrafting, world: World): Boolean {
    val items = inv.toList().filterNot(ItemStack::isEmpty).map(ItemStack::getItem)
    if (items.size != 2) return false
    val (i1, i2) = items
    return i1 == upgrade && i2 == Items.bag || i1 == Items.bag && i2 == upgrade
  }

  override fun getRecipeOutput() = ItemStack(Items.bag)

  override fun getCraftingResult(inv: InventoryCrafting): ItemStack {
    TODO("Not implemented")
  }
}