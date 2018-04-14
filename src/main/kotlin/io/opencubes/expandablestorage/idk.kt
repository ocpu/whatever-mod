package io.opencubes.expandablestorage

import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraftforge.client.model.ModelLoader


inline fun <reified T : Item> EntityPlayer.heldItemStackOfTypeOrEmpty() =
    try {
      heldItemStackOfType<T>()
    } catch (_: Exception) {
      ItemStack.EMPTY
    }

inline fun <reified T : Item> EntityPlayer.heldItemStackOfType() = when {
  heldItemMainhand.item is T -> heldItemMainhand
  heldItemOffhand.item is T -> heldItemOffhand
  else -> throw Exception("Player does not have a items of type ${T::class.java.name}")
}

inline fun <reified T : Item> EntityPlayer.heldItemOfTypeOrNull() =
    try {
      heldItemOfType<T>()
    } catch (_: Exception) {
      null
    }

inline fun <reified T : Item> EntityPlayer.heldItemOfType() = heldItemStackOfType<T>().item as T

inline fun <reified T : Item> EntityPlayer.handWithItemOfTypeOrNull() =
    try {
      handWithItemOfType<T>()
    } catch (_: Exception) {
      null
    }

inline fun <reified T : Item> EntityPlayer.handWithItemOfType() = when {
  heldItemMainhand.item is T -> EnumHand.MAIN_HAND
  heldItemOffhand.item is T -> EnumHand.OFF_HAND
  else -> throw Exception("Player does not have a items of type ${T::class.java.name}")
}

@Suppress("MemberVisibilityCanBePrivate", "unused")
object ModelLoader {
  fun setCustomModelResourceLocation(item: Item, meta: Int, model: ModelResourceLocation) =
      ModelLoader.setCustomModelResourceLocation(item, meta, model)

  fun setCustomModelResourceLocation(item: Item, meta: Int, variant: String) =
      setCustomModelResourceLocation(item, meta, ModelResourceLocation(item.registryName.toString(), variant))

  fun setCustomModelResourceLocation(item: Item, meta: Int) =
      setCustomModelResourceLocation(item, meta, "inventory")

  fun setCustomModelResourceLocation(item: Item, variant: String) =
      setCustomModelResourceLocation(item, 0, variant)

  fun setCustomModelResourceLocation(item: Item) =
      setCustomModelResourceLocation(item, 0)

  fun setCustomModelResourceLocation(block: Block, meta: Int, model: ModelResourceLocation) =
      setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, model)

  fun setCustomModelResourceLocation(block: Block, meta: Int, variant: String) =
      setCustomModelResourceLocation(block, meta, ModelResourceLocation(block.registryName.toString(), variant))

  fun setCustomModelResourceLocation(block: Block, meta: Int) =
      setCustomModelResourceLocation(block, meta, "inventory")

  fun setCustomModelResourceLocation(block: Block, variant: String) =
      setCustomModelResourceLocation(block, 0, variant)

  fun setCustomModelResourceLocation(block: Block) =
      setCustomModelResourceLocation(block, 0)
}

