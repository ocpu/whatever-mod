package io.opencubes.stuff.block

import io.opencubes.boxlin.contains
import io.opencubes.boxlin.get
import io.opencubes.stuff.tileentity.TileEntityEnergyFurnace
import io.opencubes.stuff.tools.GenericBlock
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World


class BlockEnergyFurnace : GenericBlock("energy_furnace", Material.IRON) {

  companion object {
    val FACING = PropertyDirection.create("facing", EnumFacing.HORIZONTALS.toList())
  }

  init {
    setHarvestLevel("pickaxe", 0)
    defaultState = blockState.baseState.withProperty(FACING, EnumFacing.NORTH)
  }

  override fun hasTileEntity(state: IBlockState) = true

  override fun createTileEntity(world: World, state: IBlockState) = TileEntityEnergyFurnace()

  override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
    val nbt = stack.tagCompound ?: return
    if ("energy" in nbt) {
      tooltip += "Energy: " + nbt.get<Int>("energy")
    }
  }

  override fun getStateForPlacement(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase, hand: EnumHand) =
      defaultState.withProperty(FACING, placer.horizontalFacing.opposite)

  override fun createBlockState() = BlockStateContainer(this, FACING)

  override fun getMetaFromState(state: IBlockState): Int = state.getValue(FACING).horizontalIndex

  override fun getStateFromMeta(meta: Int) = defaultState.withProperty(FACING, EnumFacing.byHorizontalIndex(meta))
}