package io.opencubes.stuff.block

import io.opencubes.stuff.tileentity.TileEntitySolarGenerator
import io.opencubes.stuff.tools.GenericBlock
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.world.World

class BlockSolarGenerator : GenericBlock("solar_generator", Material.IRON) {

  init {
    setHarvestLevel("pickaxe", 0)
    defaultState = blockState.baseState
  }

  override fun hasTileEntity(state: IBlockState) = true

  override fun createTileEntity(worldIn: World, state: IBlockState) = TileEntitySolarGenerator()
}