package io.opencubes.expandablestorage.block

import io.opencubes.boxlin.setName
import io.opencubes.expandablestorage.data.Stack
import io.opencubes.expandablestorage.tileentity.TileEntityCache
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.item.EntityItem
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World


class Cache : BlockTileEntity<TileEntityCache>(Material.ROCK) {
  override val tileEntityClass: Class<TileEntityCache>
    get() = TileEntityCache::class.java

  override fun createTileEntity(world: World, state: IBlockState) = TileEntityCache()

  init {
    setName("cache")
  }

  override fun onBlockAdded(worldIn: World, pos: BlockPos, state: IBlockState) {
    val tile = worldIn.getTileEntity(pos)
    (tile as? TileEntityCache)?.add()
    super.onBlockAdded(worldIn, pos, state)
  }

  override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
    val tileEntity = world.getTileEntity(pos)
    if (tileEntity is TileEntityCache) {
      val stack = tileEntity.remove()

      if (stack != Stack.EMPTY) {
        while (stack.amount > 0) {
          val entityItem = EntityItem(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), stack.getOne())
          world.spawnEntity(entityItem)
        }
      }
    }
  }
}
