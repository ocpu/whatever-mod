package io.opencubes.stuff.gui

import io.opencubes.boxlin.localize
import io.opencubes.stuff.ModItems
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.util.ResourceLocation

class GuiBag(container: Container, private val playerInv: InventoryPlayer) : GuiContainer(container) {
  companion object {
    private val BG_TEXTURE = ResourceLocation("stuff:textures/gui/bag.png")
  }

  init {
    xSize = 176
    ySize = 204
  }

  override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
    GlStateManager.color(1f, 1f, 1f, 1f)
    mc.textureManager.bindTexture(BG_TEXTURE)
    drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize)
  }

  override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
    fontRenderer.drawString("${ModItems.bag.translationKey}.name".localize(), 8, 6, 0x000000)
    fontRenderer.drawString(playerInv.displayName.unformattedText, 8, ySize - 94, 0x000000)
  }

  override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
    drawDefaultBackground()
    super.drawScreen(mouseX, mouseY, partialTicks)
    renderHoveredToolTip(mouseX, mouseY)
  }
}