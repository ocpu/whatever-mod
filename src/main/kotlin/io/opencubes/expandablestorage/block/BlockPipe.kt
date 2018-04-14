package io.opencubes.expandablestorage.block

import io.opencubes.boxlin.setName
import io.opencubes.expandablestorage.tileentity.TileEntityPipe
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

class BlockPipe : BlockTileEntity<TileEntityPipe>(Material.ROCK) {

  init {
    setName("pipe")
  }

  /**@see net.minecraft.block.BlockChest.getRenderType] */
  override fun getRenderType(state: IBlockState?) = EnumBlockRenderType.ENTITYBLOCK_ANIMATED
  override fun isOpaqueCube(state: IBlockState?) = false
  override fun isFullCube(state: IBlockState?) = false

  override fun getUseNeighborBrightness(state: IBlockState?) = true

  override val tileEntityClass = TileEntityPipe::class.java

  override fun createTileEntity(world: World, state: IBlockState) = TileEntityPipe()

}