package io.opencubes.stuff.tools

import io.opencubes.boxlin.isClient
import io.opencubes.stuff.Stuff
import io.opencubes.stuff.tools.gui.IGuiProvider
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

open class GenericBlock(name: String, material: Material) : Block(material) {

  init {
    translationKey = "${Stuff.ID}.$name"
    registryName = ResourceLocation(Stuff.ID, name)
    creativeTab = Stuff.creativeTab
    this.setHardness(1.5f) // Default hardness
  }

  // ------------------------------ Open GUI --------------------------------

  override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
    if (worldIn.isClient)
      return true
    val te = worldIn.getTileEntity(pos) as? IGuiProvider ?: return false
    te.gui.openGui(playerIn, worldIn, pos)
    return true
  }

  // ------------- Defer harvest until getDrops has been called --------------

  override fun removedByPlayer(state: IBlockState, world: World, pos: BlockPos, player: EntityPlayer, willHarvest: Boolean): Boolean {
    return if (willHarvest) true
    else super.removedByPlayer(state, world, pos, player, willHarvest)
  }

  override fun harvestBlock(world: World, player: EntityPlayer, pos: BlockPos, state: IBlockState, te: TileEntity?, stack: ItemStack) {
    super.harvestBlock(world, player, pos, state, te, stack)
    world.setBlockToAir(pos)
  }

  // ---------- Save data and get relevant drops for the tile entity ---------

  override fun getDrops(drops: NonNullList<ItemStack>, world: IBlockAccess, pos: BlockPos, state: IBlockState, fortune: Int) {
    val te = world.getTileEntity(pos)
    if (te != null && (te is ISaveable || te is IDrops)) {
      val stack = ItemStack(Item.getItemFromBlock(this))
      if (te is ISaveable) {
        val tagCompound = NBTTagCompound()
        te.save(tagCompound)
        stack.tagCompound = tagCompound
      }
      if (te is IDrops) {
        drops.addAll(te.getDrops())
      }
      drops.add(stack)
    }
    else super.getDrops(drops, world, pos, state, fortune)
  }

  // ------------------------- Load saved data -------------------------------

  override fun onBlockPlacedBy(worldIn: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
    val te = worldIn.getTileEntity(pos)

    if (te is ISaveable) {
      val nbt = stack.tagCompound
      if (nbt != null)
        te.load(nbt)
    }
  }
}
