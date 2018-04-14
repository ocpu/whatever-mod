package io.opencubes.expandablestorage.item.bag

import io.opencubes.boxlin.name
import io.opencubes.expandablestorage.ExpandableStorage
import io.opencubes.expandablestorage.item.ExpandableStorageItems
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.util.ResourceLocation
import net.minecraft.client.renderer.GlStateManager

class GuiBag(container: Container, val playerInv: InventoryPlayer) : GuiContainer(container) {
  companion object {
    private val BG_TEXTURE = ResourceLocation(ExpandableStorage.ID, "textures/gui/bag.png")
  }

  init {
    xSize = 176
    ySize = 204
  }

  override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
    GlStateManager.color(1f, 1f, 1f, 1f)
    mc.textureManager.bindTexture(BG_TEXTURE)
    val x = (width - xSize) / 2
    val y = (height - ySize) / 2
    drawTexturedModalRect(x, y, 0, 0, xSize, ySize)
  }

  override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
    val name = ExpandableStorageItems.bag.name
    fontRenderer.drawString(name, 8, 6, 0x404040)
    fontRenderer.drawString(playerInv.displayName.unformattedText, 8, ySize - 94, 0x404040)
  }
}