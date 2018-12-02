package io.opencubes.stuff

import io.opencubes.boxlin.Boxlin
import io.opencubes.boxlin.useProxy
import io.opencubes.stuff.proxy.ClientProxy
import io.opencubes.stuff.proxy.ServerProxy
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

@Mod(modid = Stuff.ID, name = Stuff.NAME, version = Stuff.VERSION, modLanguage = "kotlin", modLanguageAdapter = Boxlin.ADAPTER)
object Stuff {
  const val ID = "stuff"
  const val NAME = "Stuff"
  const val VERSION = "0.0.0"

  val proxy by useProxy(ClientProxy::class, ServerProxy::class)
  val creativeTab = object : CreativeTabs(ID) {
    override fun createIcon() = ItemStack.EMPTY
  }

  @Mod.EventHandler fun preInit(e: FMLPreInitializationEvent) {
    proxy.preInit(e)
    ModGui.register()
  }
  @Mod.EventHandler fun init(e: FMLInitializationEvent) = proxy.init(e)
  @Mod.EventHandler fun postInit(e: FMLPostInitializationEvent) = proxy.postInit(e)
}