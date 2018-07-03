@file:EventBusSubscriber(modid = ExpandableStorage.ID)
@file:Suppress("unused", "UNUSED_PARAMETER")

package io.opencubes.expandablestorage

import io.opencubes.boxlin.logger
import io.opencubes.boxlin.setName
//import io.opencubes.expandablestorage.api.BagUpgrade
//import io.opencubes.expandablestorage.api.BagUpgradeRegistry
import io.opencubes.expandablestorage.api.capabilities.upgrades.CapabilityWorldPickupUpgrade
import io.opencubes.expandablestorage.block.BlockTileEntity
import io.opencubes.expandablestorage.block.ExpandableStorageBlocks
import io.opencubes.expandablestorage.item.ExpandableStorageItems
import io.opencubes.expandablestorage.item.ItemBag
import io.opencubes.expandablestorage.item.bag.*
import io.opencubes.expandablestorage.tileentity.renderer.TileEntityPipeRenderer
import io.opencubes.expandablestorage.tileentity.TileEntityPipe
import net.minecraft.block.Block
import net.minecraft.client.gui.Gui
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.*
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
import net.minecraft.item.crafting.IRecipe
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.common.crafting.IShapedRecipe
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.registries.*

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
fun registerRecipes(e: RegistryEvent<IRecipe>) {
  object : BagRecipe(ResourceLocation("")) {
    override val minMaxHeight = 2..3
    override val minMaxWidth = 2..3

    override fun getCraftingResult(inv: InventoryCrafting): ItemStack {
      TODO("Not implemented")
    }

    override fun matches(inv: InventoryCrafting, world: World): Boolean {
      TODO("Not implemented")
    }

  }
}

//@SubscribeEvent
//fun onItemCrafted(e: WorldEvent) {
//  if (e.harvester != null && !e.harvester.isCreative) {
//    val bagItemStack = ItemStack(ExpandableStorageItems.bag)
//    if (e.harvester.inventory.hasItemStack(bagItemStack)) {
//      val bags = e.harvester.inventory.toList()
//          .filter { it.item == ExpandableStorageItems.bag }
//          .filter { it.hasCapability(CapabilityWorldPickupUpgrade.WORLD_PICKUP_UPGRADE, null) }
//      logger.info(bags.toString())
//    }
//  }
//}

//@SubscribeEvent
//fun newRegistries(e: RegistryEvent.NewRegistry) {
//
//  RegistryBuilder<BagUpgrade>()
//      .setName(ExpandableStorage.location("bag_upgrades_registry"))
//      .setType(BagUpgrade::class.java)
//      .add(IForgeRegistry.CreateCallback<BagUpgrade> { owner, stage ->
//        println("Created")
//      })
//      .add({ owner, stage, id, obj, oldObj ->
//        println("Added")
//      })
//      .add(IForgeRegistry.ClearCallback { owner, stage ->
//        println("Clearing")
//      })
//      .create()
//}
//
//class WorldPickupUpgrade: BagUpgrade() {
//  override fun writeToNBT(): NBTTagCompound {
//    TODO("Not implemented")
//  }
//
//  override fun readFromNBT(nbt: NBTTagCompound) {
//    TODO("Not implemented")
//  }
//
//  init {
//    registryName = ExpandableStorage.location("world_pickup_upgrade")
//  }
//}
//
//@SubscribeEvent
//fun registerUpgrades(e: RegistryEvent.Register<BagUpgrade>) {
//
//  e.registry.registerAll(WorldPickupUpgrade())
//}

@SubscribeEvent
fun onItemPickup(e: net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent) {
val bagItemStack = ItemStack(ExpandableStorageItems.bag)
  if (e.player.inventory.hasItemStack(bagItemStack)) {
    val bags = e.player.inventory.toList()
        .filter { it.item == ExpandableStorageItems.bag }
        .filter { it.hasCapability(CapabilityWorldPickupUpgrade.WORLD_PICKUP_UPGRADE, null) }
    logger.info(bags.toString())
  }
}

fun IInventory.toList() = List(sizeInventory) { i -> getStackInSlot(i) }

@SubscribeEvent
fun registerAtlases(e: TextureStitchEvent) {
//  TileEntityPipeRenderer.sprite = e.map.registerSprite(TileEntityPipeRenderer.pipe)
}

/*



 */

interface IGui {
  fun container(player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Container? = null
  fun gui(player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Gui? = null

  val id: Int
  val mod: Any

  fun openGui(player: EntityPlayer, world: World, x: Int, y: Int, z: Int) =
      player.openGui(mod, id, world, x, y, z)

  fun openGui(player: EntityPlayer, world: World, x: Double, y: Double, z: Double) =
      player.openGui(mod, id, world, x.toInt(), y.toInt(), z.toInt())

  fun openGui(player: EntityPlayer, world: World, pos: BlockPos) =
      player.openGui(mod, id, world, pos.x, pos.y, pos.z)

  fun openGui(player: EntityPlayer, world: World) =
      player.openGui(mod, id, world, 0, 0, 0)
}
open class GuiHandler(private val values: Array<out IGui>) : IGuiHandler {
  override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int) = when (ID) {
    in 0 until values.size -> values[ID].gui(
        player, world, x, y, z
    )
    else -> null
  }

  override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int) = when (ID) {
    in 0 until values.size -> values[ID].container(player, world, x, y, z)
    else -> null
  }
}

enum class Interface : IGui {
  BAG() {
    override fun container(player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Container {
      val stack = player.heldItemStackOfType<ItemBag>()
      val position =
          if (ItemStack.areItemStacksEqual(stack, player.heldItemMainhand)) player.inventory.currentItem
          else -1
      return ContainerBag(player.inventory, stack, position)
    }
    override fun gui(player: EntityPlayer, world: World, x: Int, y: Int, z: Int) =
        GuiBag(container(player, world, x, y, z), player.inventory)
  }
  ;

  override val id = ordinal
  override val mod = ExpandableStorage
  companion object : GuiHandler(Interface.values())
}
