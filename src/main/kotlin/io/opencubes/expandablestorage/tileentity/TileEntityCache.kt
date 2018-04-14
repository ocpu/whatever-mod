package io.opencubes.expandablestorage.tileentity

import io.opencubes.boxlin.set
import io.opencubes.expandablestorage.data.Stack
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler
import kotlin.collections.MutableList
import kotlin.collections.any
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.contains
import kotlin.collections.filterIsInstance
import kotlin.collections.filterNot
import kotlin.collections.find
import kotlin.collections.first
import kotlin.collections.forEach
import kotlin.collections.isNotEmpty
import kotlin.collections.iterator
import kotlin.collections.listOf
import kotlin.collections.listOfNotNull
import kotlin.collections.map
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.collections.plusAssign
import kotlin.collections.set
import kotlin.collections.toSet
import kotlin.collections.toTypedArray

class TileEntityCache : TileEntity() {
  lateinit var master: TileEntityCache
  val isMaster get() = master == this
  var tileToStack = mutableMapOf<TileEntityCache, Stack>()
  private val inventory = object : ItemStackHandler(1) {
    override fun getSlotLimit(slot: Int) = Int.MAX_VALUE
    override fun setSize(size: Int) {
      super.setSize(size)
    }
  }

  fun add() {

    val masters = listOf(
        world.getTileEntity(pos.add(+1, 0, 0)),
        world.getTileEntity(pos.add(-1, 0, 0)),
        world.getTileEntity(pos.add(0, +1, 0)),
        world.getTileEntity(pos.add(0, -1, 0)),
        world.getTileEntity(pos.add(0, 0, +1)),
        world.getTileEntity(pos.add(0, 0, -1))
    )
        .filterIsInstance<TileEntityCache>()
        .map(TileEntityCache::master)
        .toSet()

    when (masters.size) {
      0 -> master = this
      1 -> master = masters.first()
      else -> {
        master = this
        for (master in masters) {
          for ((tile, stack) in master.tileToStack) {
            tileToStack[tile] = stack
            tile.master = this
          }
          master.tileToStack = mutableMapOf()
          master.master = this
        }
      }
    }

    master.tileToStack[this] = Stack.EMPTY
  }

  fun remove(): Stack {
    val adjacentCaches = listOfNotNull(
        world.getTileEntity(pos.add(+1, 0, 0)),
        world.getTileEntity(pos.add(-1, 0, 0)),
        world.getTileEntity(pos.add(0, +1, 0)),
        world.getTileEntity(pos.add(0, -1, 0)),
        world.getTileEntity(pos.add(0, 0, +1)),
        world.getTileEntity(pos.add(0, 0, -1))
    ).filterIsInstance<TileEntityCache>()

    if (adjacentCaches.isEmpty()) {
//      val items = EntityItem(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), tileToStack[this]!!)
//      items.setNoPickupDelay()
//      world.spawnEntity(items)
      return tileToStack[this]!!
    }

    val stack = master.tileToStack.remove(this)!!

    if (adjacentCaches.size == 1) {
      val cache = adjacentCaches.first()
      if (isMaster) {
        for ((tile, s) in tileToStack) {
          cache.tileToStack[tile] = s
          tile.master = cache
        }
        master = cache
        cache.master = cache
      }
//      val items = EntityItem(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), stack)
//      items.setNoPickupDelay()
//      world.spawnEntity(items)
      return stack
    }

    val foundMaster = mutableMapOf<TileEntityCache, TileEntityCache>()
    val foundAdjacent = mutableMapOf(*adjacentCaches.map { it to mutableListOf<TileEntityCache>() }.toTypedArray())

    for (start in adjacentCaches) {
      val traversed = mutableSetOf(this)
      val toTraverse = mutableListOf(start)
      while (toTraverse.isNotEmpty()) {
        val traverse = toTraverse.removeAt(0)

        if (traverse.isMaster) foundMaster[start] = traverse
        if (traverse != start && traverse in adjacentCaches) foundAdjacent[start]!!.add(traverse)

        traversed += traverse

        listOfNotNull(
            world.getTileEntity(traverse.pos.add(+1, +0, +0)),
            world.getTileEntity(traverse.pos.add(-1, +0, +0)),
            world.getTileEntity(traverse.pos.add(+0, +1, +0)),
            world.getTileEntity(traverse.pos.add(+0, -1, +0)),
            world.getTileEntity(traverse.pos.add(+0, +0, +1)),
            world.getTileEntity(traverse.pos.add(+0, +0, -1))
        )
            .filterIsInstance<TileEntityCache>()
            .filterNot(traversed::contains)
            .filterNot(toTraverse::contains)
            .forEach { toTraverse.add(it) }
      }
    }

    val groups = mutableListOf<MutableList<TileEntityCache>>()
    foundAdjacent.forEach { key, value ->
      if (groups.any { it.contains(key) }) return@forEach
      val list = mutableListOf(key)
      list += value
      groups.add(list)
    }

    for (group in groups) {
      var master = group.find { it in foundMaster }
      if (master == null) {
        val cache = group.first()
        for ((tile, s) in this.master.tileToStack) {
          cache.tileToStack[tile] = s
          tile.master = cache
        }
        master = cache
        cache.master = cache
      }
      master.tileToStack.keys.retainAll(group)
    }

//    val items = EntityItem(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), stack)
//    items.setNoPickupDelay()
//    world.spawnEntity(items)
    return stack
  }

  override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
    compound["inventory"] = inventory.serializeNBT()

    return super.writeToNBT(compound)
  }

  override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) =
      capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing)

  override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?) =
      if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) inventory as T else super.getCapability(capability, facing)
}