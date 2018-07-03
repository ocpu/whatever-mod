@file:Suppress("UNUSED_PARAMETER")

package io.opencubes.expandablestorage

import io.opencubes.boxlin.Boxlin
import io.opencubes.boxlin.useProxy
//import io.opencubes.expandablestorage.api.BagUpgrade
import io.opencubes.expandablestorage.api.capabilities.upgrades.CapabilityWorldPickupUpgrade
import io.opencubes.expandablestorage.block.ExpandableStorageBlocks
import io.opencubes.expandablestorage.item.ItemUpgrade
import io.opencubes.expandablestorage.proxy.ClientProxy
import io.opencubes.expandablestorage.proxy.CommonProxy
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent as PreInitEvent
import net.minecraftforge.fml.common.event.FMLInitializationEvent as InitEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent as PostInitEvent

@Mod(
    modid = ExpandableStorage.ID,
    name = ExpandableStorage.NAME,
    version = ExpandableStorage.VERSION,
    modLanguage = "kotlin",
    modLanguageAdapter = Boxlin.ADAPTER
)
object ExpandableStorage {
  const val ID = "expandablestorage"
  const val NAME = "Expandable Storage"
  const val VERSION = "1.0-SNAPSHOT"

  val proxy by useProxy(ClientProxy::class, CommonProxy::class)

  @EventHandler
  fun preInit(e: PreInitEvent) {
    CapabilityWorldPickupUpgrade.register()
    NetworkRegistry.INSTANCE.registerGuiHandler(this, Interface)
    println(ExpandableStorageBlocks.pipe.tileEntityClass.simpleName
        .replace("TileEntity", "")
        .replace("([A-Z])".toRegex(), "_$1")
        .toLowerCase()
        .substring(1))
    proxy.preInit()
  }

  @EventHandler
  fun preInit(e: InitEvent) = proxy.init()

  @EventHandler
  fun postInit(e: PostInitEvent) {
    proxy.postInit()
    //    val ret = GameRegistry.findRegistry(BagUpgrade::class.java)
    //    ret
    GameRegistry.findRegistry(Item::class.java).valuesCollection.filterIsInstance<ItemUpgrade>()
  }

  fun location(path: String) = ResourceLocation(ID, path)
  fun location(path: String, variant: String) = ModelResourceLocation(location(path), variant)
}
