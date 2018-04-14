@file:Suppress("UNUSED_PARAMETER")

package io.opencubes.expandablestorage

import io.opencubes.boxlin.Boxlin
import io.opencubes.boxlin.useProxy
import io.opencubes.expandablestorage.block.ExpandableStorageBlocks
import io.opencubes.expandablestorage.proxy.ClientProxy
import io.opencubes.expandablestorage.proxy.CommonProxy
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent as PreInitEvent
import net.minecraftforge.fml.common.event.FMLInitializationEvent as InitEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent as PostInitEvent

@Mod(
    modid = ExpandableStorage.ID,
    name = ExpandableStorage.NAME,
    version = ExpandableStorage.VERSION,
    modLanguage = "Kotlin",
    modLanguageAdapter = Boxlin.ADAPTER
)
object ExpandableStorage {
  const val ID = "expandablestorage"
  const val NAME = "Expandable Storage"
  const val VERSION = "1.0-SNAPSHOT"

  val proxy by useProxy(ClientProxy::class, CommonProxy::class)

  @EventHandler
  fun preInit(e: PreInitEvent) {
    NetworkRegistry.INSTANCE.registerGuiHandler(this, Interface)
    println(ExpandableStorageBlocks.pipe.tileEntityClass.simpleName
        .replace("TileEntity", "")
        .replace("([A-Z])".toRegex(), "_$1")
        .toLowerCase()
        .substring(1))
    proxy.preInit()
  }

  @EventHandler
  fun preInit(e: InitEvent) {
    proxy.init()
  }

  @EventHandler
  fun postInit(e: PostInitEvent) {
    proxy.postInit()
  }
}