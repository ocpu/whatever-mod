package io.opencubes.expandablestorage.block

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

abstract class BlockTileEntity<TE : TileEntity>(material: Material) : Block(material) {

	abstract val tileEntityClass: Class<TE>

	fun getTileEntity(world: IBlockAccess, pos: BlockPos) = world.getTileEntity(pos) as TE

	override fun hasTileEntity(state: IBlockState) = true

	abstract override fun createTileEntity(world: World, state: IBlockState): TE?
}