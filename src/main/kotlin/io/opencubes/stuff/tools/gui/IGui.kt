package io.opencubes.stuff.tools.gui

import net.minecraft.client.gui.Gui
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

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