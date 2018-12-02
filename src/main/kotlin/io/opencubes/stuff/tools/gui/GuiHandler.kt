package io.opencubes.stuff.tools.gui

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler
import net.minecraftforge.fml.common.network.NetworkRegistry

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

  fun register() = NetworkRegistry.INSTANCE.registerGuiHandler(values[0].mod, this)
}