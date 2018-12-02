package io.opencubes.stuff.proxy

import com.google.common.util.concurrent.ListenableFuture
import io.opencubes.stuff.ModBlocks
import io.opencubes.stuff.ModItems
import io.opencubes.stuff.Stuff
import net.minecraft.block.Block
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = Stuff.ID)
open class CommonProxy {
  open fun preInit(e: FMLPreInitializationEvent) = Unit
  open fun init(e: FMLInitializationEvent) = Unit
  open fun postInit(e: FMLPostInitializationEvent) = Unit

  companion object {
    @SubscribeEvent @JvmStatic fun registerItems(e: RegistryEvent.Register<Item>) = ModItems.register(e)
    @SubscribeEvent @JvmStatic fun registerBlocks(e: RegistryEvent.Register<Block>) {
      ModBlocks.register(e)
      ModBlocks.registerEntities()
    }
  }

  open fun scheduleTask(callable: Runnable): ListenableFuture<Any> = throw RuntimeException()
  open val player get(): EntityPlayerSP = throw RuntimeException()
}
