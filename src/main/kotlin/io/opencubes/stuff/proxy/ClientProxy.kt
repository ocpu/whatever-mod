package io.opencubes.stuff.proxy

import io.opencubes.stuff.ModBlocks
import io.opencubes.stuff.ModItems
import io.opencubes.stuff.Stuff
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side


@Mod.EventBusSubscriber(Side.CLIENT, modid = Stuff.ID)
class ClientProxy : CommonProxy() {
  companion object {
    @SubscribeEvent @JvmStatic fun registerModels(event: ModelRegistryEvent) {
      ModBlocks.registerModels()
      ModItems.registerModels()
    }
  }

  override fun scheduleTask(callable: Runnable) = Minecraft.getMinecraft().addScheduledTask(callable)
  override val player get() = Minecraft.getMinecraft().player
}
