package io.opencubes.stuff.gui

import io.opencubes.stuff.Stuff
import io.opencubes.stuff.container.ContainerEnergyFurnace
import io.opencubes.stuff.tileentity.TileEntityEnergyFurnace
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.ResourceLocation

class GuiEnergyFurnace(val playerInv: InventoryPlayer, val te: TileEntityEnergyFurnace) : GuiContainer(ContainerEnergyFurnace(playerInv, te)) {

  companion object {
    val BG_TEXTURE = ResourceLocation(Stuff.ID, "textures/gui/energy_furnace.png")
  }

  init {
    xSize = 176
    ySize = 132
  }

  override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
    mc.textureManager.bindTexture(BG_TEXTURE)
    drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize)
  }

  override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
    super.drawGuiContainerForegroundLayer(mouseX, mouseY)
  }

  override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
    drawDefaultBackground()
    super.drawScreen(mouseX, mouseY, partialTicks)
    renderHoveredToolTip(mouseX, mouseY)
  }
}