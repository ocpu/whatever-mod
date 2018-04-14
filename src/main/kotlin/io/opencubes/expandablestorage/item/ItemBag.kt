package io.opencubes.expandablestorage.item

import io.opencubes.boxlin.setName
import io.opencubes.expandablestorage.*
import io.opencubes.expandablestorage.capabilities.InventoryProvider
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.*
import net.minecraft.world.World

class ItemBag : Item() {

  companion object {
    const val size = 45 // 5 * 9
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
