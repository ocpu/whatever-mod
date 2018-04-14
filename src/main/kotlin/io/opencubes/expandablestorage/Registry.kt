@file:EventBusSubscriber(modid = ExpandableStorage.ID)
@file:Suppress("unused", "UNUSED_PARAMETER")

package io.opencubes.expandablestorage

import io.opencubes.boxlin.setName
import io.opencubes.expandablestorage.block.BlockTileEntity
import io.opencubes.expandablestorage.block.ExpandableStorageBlocks
import io.opencubes.expandablestorage.item.ExpandableStorageItems
import io.opencubes.expandablestorage.item.ItemBag
import io.opencubes.expandablestorage.item.bag.ContainerBag
import io.opencubes.expandablestorage.item.bag.GuiBag
import io.opencubes.expandablestorage.tileentity.renderer.TileEntityPipeRenderer
import io.opencubes.expandablestorage.tileentity.TileEntityCache
import io.opencubes.expandablestorage.tileentity.TileEntityPipe
import net.minecraft.block.Block
import net.minecraft.client.gui.Gui
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.IGuiHandler
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.fml.client.registry.ClientRegistry

@SubscribeEvent
fun registerItems(e: RegistryEvent.Register<Item>) = e.registry.registerAll(
    *ExpandableStorageItems.all,
    *ExpandableStorageBlocks.all.map { ItemBlock(it).setName(it.registryName?.resourcePath ?: "") }.toTypedArray()
)

@SubscribeEvent
fun registerBlocks(e: RegistryEvent.Register<Block>) = e.registry.registerAll(
    *ExpandableStorageBlocks.all
).also {
  ExpandableStorageBlocks.all.filterIsInstance<BlockTileEntity<out TileEntity>>().forEach {
    GameRegistry.registerTileEntity(
        it.tileEntityClass,
        "${ExpandableStorage.ID}:${it.tileEntityClass.simpleName
            .replace("TileEntity", "")
            .replace("([A-Z])".toRegex(), "_$1")
            .toLowerCase()
            .substring(1)}"
    )
  }
}

@SubscribeEvent
fun registerModels(e: ModelRegistryEvent) {
  ModelLoader.setCustomModelResourceLocation(ExpandableStorageItems.bag)
  ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPipe::class.java, TileEntityPipeRenderer())
}

@SubscribeEvent
fun registerAtlases(e: TextureStitchEvent) {
  TileEntityPipeRenderer.sprite = e.map.registerSprite(TileEntityPipeRenderer.pipe)
}

/*



 */

@Suppress("MemberVisibilityCanBePrivate")
enum class Interface(
    val container: ((player: EntityPlayer, world: World, x: Int, y: Int, z: Int) -> Container)? = null,
    val gui: ((container: Container?, player: EntityPlayer, world: World, x: Int, y: Int, z: Int) -> Gui)? = null
) {
  BAG(
      { player, _, _, _, _ ->
        val stack = player.heldItemStackOfType<ItemBag>()
        val position =
            if (ItemStack.areItemStacksEqual(stack, player.heldItemMainhand)) player.inventory.currentItem
            else -1
        ContainerBag(player.inventory, stack, position)
      },
      { c, player, _, _, _, _ -> GuiBag(c!!, player.inventory) }
  )
  ;

  val id = ordinal

  fun openGui(player: EntityPlayer, world: World, x: Int, y: Int, z: Int) =
      player.openGui(ExpandableStorage, id, world, x, y, z)

  fun openGui(player: EntityPlayer, world: World, x: Double, y: Double, z: Double) =
      player.openGui(ExpandableStorage, id, world, x.toInt(), y.toInt(), z.toInt())

  fun openGui(player: EntityPlayer, world: World, pos: BlockPos) =
      player.openGui(ExpandableStorage, id, world, pos.x, pos.y, pos.z)

  fun openGui(player: EntityPlayer, world: World) =
      player.openGui(ExpandableStorage, id, world, 0, 0, 0)

  companion object : IGuiHandler {
    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int) = when (ID) {
      in 0 until Interface.values().size -> Interface.values()[ID].gui?.invoke(
          getServerGuiElement(ID, player, world, x, y, z),
          player, world, x, y, z
      )
      else -> null
    }

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int) = when (ID) {
      in 0 until Interface.values().size -> Interface.values()[ID].container?.invoke(player, world, x, y, z)
      else -> null
    }
  }
}
