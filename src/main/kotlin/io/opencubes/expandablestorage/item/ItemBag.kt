package io.opencubes.expandablestorage.item

import io.opencubes.boxlin.*
import io.opencubes.expandablestorage.ExpandableStorage
import io.opencubes.expandablestorage.Interface
//import io.opencubes.expandablestorage.api.BagUpgrade
import io.opencubes.expandablestorage.capabilities.InventoryProvider
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.*
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.GameRegistry

class ItemBag : Item() {

  companion object {
    const val size = 45 // 5 * 9

//    private val registry by lazy { GameRegistry.findRegistry(BagUpgrade::class.java) }

//    @JvmStatic
//    fun getUpgrades(stack: ItemStack): List<BagUpgrade> {
//      val tag = stack.tagCompound ?: NBTTagCompound()
//      if ("upgrades" !in tag) return emptyList()
//      val upgradesTag: NBTTagCompound = tag["upgrades"]
//      if (upgradesTag.keySet.isEmpty()) return emptyList()
//      return upgradesTag.keySet.map {
//        val upgrade = registry.getValue(ResourceLocation(it))!!
//        upgrade.readFromNBT(upgradesTag[it])
//        upgrade
//      }.toList()
//    }
//
//    @JvmStatic
//    fun addUpgrade(stack: ItemStack, ): List<BagUpgrade> {
//      val tag = stack.tagCompound ?: NBTTagCompound()
//      if ("upgrades" !in tag) return emptyList()
//      val upgradesTag: NBTTagCompound = tag["upgrades"]
//      if (upgradesTag.keySet.isEmpty()) return emptyList()
//      return upgradesTag.keySet.map {
//        val upgrade = registry.getValue(ResourceLocation(it))!!
//        upgrade.readFromNBT(upgradesTag[it])
//        upgrade
//      }.toList()
//    }

    fun getBags(player: EntityPlayer?): List<ItemStack> {
      if (player == null) return emptyList()
      return listOf(
          *player.inventory.mainInventory.filter { it.item is ItemBag }.toTypedArray(),
          *player.inventory.offHandInventory.filter { it.item is ItemBag }.toTypedArray()
      )
    }
  }

  init {
    setName("bag", ExpandableStorage.ID)
    creativeTab = CreativeTabs.MISC
  }

  override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
    if (player.isSneaking)
      return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand))

    Interface.BAG.openGui(player, world)
    return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand))
  }

  override fun initCapabilities(stack: ItemStack?, nbt: NBTTagCompound?) = InventoryProvider(size)

}
