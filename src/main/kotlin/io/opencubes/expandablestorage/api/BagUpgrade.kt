package io.opencubes.expandablestorage.api

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.registries.IForgeRegistryEntry

abstract class ItemBagUpgrade : Item(), IBagUpgrade {
  fun icon() = ItemStack.EMPTY
}

interface IBagUpgrade : IForgeRegistryEntry<Item> {
  fun writeToNBT(): NBTTagCompound
  fun readFromNBT(nbt: NBTTagCompound)
}

interface IGuiBagUpgrade : IBagUpgrade {
  fun icon(): ItemStack
  fun drawGui()
}
