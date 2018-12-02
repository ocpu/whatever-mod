package io.opencubes.stuff

import io.opencubes.stuff.container.ContainerBag
import io.opencubes.stuff.container.ContainerEnergyFurnace
import io.opencubes.stuff.gui.GuiBag
import io.opencubes.stuff.gui.GuiEnergyFurnace
import io.opencubes.stuff.tileentity.TileEntityEnergyFurnace
import io.opencubes.stuff.tools.gui.GuiHandler
import io.opencubes.stuff.tools.gui.IGui
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

enum class ModGui : IGui {
  ENERGY_FURNACE {
    override fun container(player: EntityPlayer, world: World, x: Int, y: Int, z: Int) =
        ContainerEnergyFurnace(player.inventory, world.getTileEntity(BlockPos(x, y, z)) as TileEntityEnergyFurnace)

    override fun gui(player: EntityPlayer, world: World, x: Int, y: Int, z: Int) =
        GuiEnergyFurnace(player.inventory, world.getTileEntity(BlockPos(x, y, z)) as TileEntityEnergyFurnace)
  },
  BAG {
    override fun container(player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Container {
      val stack = player.getHeldItem(EnumHand.values()[x])
      val position = when (EnumHand.values()[x]) {
        EnumHand.OFF_HAND -> -1
        EnumHand.MAIN_HAND -> player.inventory.currentItem
      }
      return ContainerBag(player.inventory, stack, position)
    }
    override fun gui(player: EntityPlayer, world: World, x: Int, y: Int, z: Int) =
        GuiBag(container(player, world, x, y, z), player.inventory)
  },
  ;

  override val id = ordinal
  override val mod = Stuff
  companion object : GuiHandler(ModGui.values())
}
