package io.opencubes.expandablestorage.gui

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.inventory.Container
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import java.awt.Color

abstract class GuiContainerHelper(container: Container, guiWidth: Int, guiHeight: Int) : GuiContainer(container) {
  init { xSize = guiWidth; ySize = guiHeight }

  /**
   * Draw texture
   */
  fun drawTexture(texture: ResourceLocation, x: Int, y: Int, width: Int, height: Int, u: Int = 0, v: Int = 0) {
    mc.textureManager.bindTexture(texture)
    drawTexturedModalRect(x, y, u, v, width, height)
  }

  /**
   * Draw background
   */
  fun drawBackground(texture: ResourceLocation, x: Int, y: Int, u: Int = 0, v: Int = 0) =
      drawTexture(texture, x, y, xSize, ySize, u, v)

  /**
   * Draw background in the center of the screen
   */
  fun drawBackground(texture: ResourceLocation, u: Int = 0, v: Int = 0) =
      drawBackground(texture, (width - xSize) / 2, (height - ySize) / 2, u, v)

  /**
   * Draw text on screen
   */
  fun write(text: String, x: Int, y: Int, color: Int = 0x404040, dropShadow: Boolean = false) {
    fontRenderer.drawString(text, x.toFloat(), y.toFloat(), color, dropShadow)
  }

  /**
   * Draw text on screen
   */
  fun write(text: String, x: Int, y: Int, color: Color, dropShadow: Boolean = false) =
      write(text, x, y, color.rgb, dropShadow)

  /**
   * Draw wrapped text on screen
   */
  fun write(text: String, x: Int, y: Int, maxWidth: Int, color: Int = 0x404040) =
      fontRenderer.drawSplitString(text, x, y, maxWidth, color)

  fun drawItemStack(stack: ItemStack, x: Int, y: Int, altText: String = "") {
    GlStateManager.translate(0.0f, 0.0f, 32.0f)
    zLevel = 200.0f
    itemRender.zLevel = 200.0f
    itemRender.renderItemAndEffectIntoGUI(stack, x, y)
    itemRender.renderItemOverlayIntoGUI(stack.item.getFontRenderer(stack) ?: fontRenderer, stack, x, y, altText)
    zLevel = 0.0f
    itemRender.zLevel = 0.0f
  }

//  fun drawRect(x: Int, y: Int, width: Int, height: Int, color: Int, hasAlpha: Boolean = false) =
//      drawRect(x, y, width, height, Color(color, hasAlpha))
//  fun drawRect(x: Int, y: Int, width: Int, height: Int, color: Color) =
//      drawRect(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble(), color)
//  fun drawRect(x: Float, y: Float, width: Float, height: Float, color: Int, hasAlpha: Boolean = false) =
//      drawRect(x, y, width, height, Color(color, hasAlpha))
//  fun drawRect(x: Float, y: Float, width: Float, height: Float, color: Color) =
//      drawRect(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble(), color)
//
//  fun drawRect(x: Double, y: Double, width: Double, height: Double, color: Int, hasAlpha: Boolean = false) =
//      drawRect(x, y, width, height, Color(color, hasAlpha))
//  fun drawRect(x: Double, y: Double, width: Double, height: Double, color: Color) {
//    val tes = Tessellator.getInstance()
//    val buffer = tes.buffer
//    buffer.begin(7, DefaultVertexFormats.POSITION_COLOR)
//    buffer.pos(x, y, 0.0).color(color.red, color.green, color.blue, color.alpha).endVertex()
//    buffer.pos(x + width, y, 0.0).color(color.red, color.green, color.blue, color.alpha).endVertex()
//    buffer.pos(x + width, y + height, 0.0).color(color.red, color.green, color.blue, color.alpha).endVertex()
//    buffer.pos(x, y + height, 0.0).color(color.red, color.green, color.blue, color.alpha).endVertex()
//    tes.draw()
//  }

//  enum class TexturePosition {
//    TOP, TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, LEFT, TOP_LEFT, CENTER
//  }
}
