package io.opencubes.stuff.item

import io.opencubes.stuff.ModGui
import io.opencubes.stuff.Stuff
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.*
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

class ItemBag : Item() {

  companion object {
    const val SIZE = 45 // 5 * 9
  }

  init {
    translationKey = "${Stuff.ID}.bag"
    registryName = ResourceLocation(Stuff.ID, "bag")
    creativeTab = CreativeTabs.MISC
  }

  override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
    if (player.isSneaking)
      return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand))

    ModGui.BAG.openGui(player, world, hand.ordinal, 0, 0)
    return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand))
  }

  override fun initCapabilities(stack: ItemStack?, nbt: NBTTagCompound?) = InventoryProvider()

  class InventoryProvider : ICapabilitySerializable<NBTTagCompound> {

    val inventory = ItemStackHandler(SIZE)

    override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? =
        if (capability !== CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) null
        else CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory)

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) =
        capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY

    override fun deserializeNBT(nbt: NBTTagCompound) = inventory.deserializeNBT(nbt)

    override fun serializeNBT() = inventory.serializeNBT()!!
  }
}
