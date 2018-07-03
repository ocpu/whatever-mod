package io.opencubes.expandablestorage.tileentity.renderer

import io.opencubes.expandablestorage.ExpandableStorage
import io.opencubes.expandablestorage.item.ExpandableStorageItems
import io.opencubes.expandablestorage.tileentity.TileEntityPipe
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.*
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.vertex.VertexFormat
import net.minecraft.entity.item.EntityItem
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.animation.FastTESR
import org.lwjgl.opengl.GL11

class TileEntityPipeRenderer : TileEntitySpecialRenderer<TileEntityPipe>() {

  val ITEM = EntityItem(Minecraft.getMinecraft().world, 0.0, 0.0, 0.0, ItemStack(ExpandableStorageItems.bag))

  override fun render(te: TileEntityPipe, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int, alpha: Float) {
    super.render(te, x, y, z, partialTicks, destroyStage, alpha)
    Minecraft.getMinecraft().renderManager.renderEntityStatic(ITEM, 0f, false)
  }
}

//class TileEntityPipeRenderer : FastTESR<TileEntityPipe>() {
//  override fun renderTileEntityFast(te: TileEntityPipe, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int, partial: Float, buffer: BufferBuilder) {
//    buffer.setTranslation(x, y, z)
//    drawCore(te, buffer)
//  }
//
//  companion object {
//    @JvmStatic val pipe = ResourceLocation(ExpandableStorage.ID, "model/pipe")
//    @JvmStatic val pipe2 = ExpandableStorage.location("textures/model/pipe.png")
//    lateinit var sprite: TextureAtlasSprite
//  }
//
//  private val pixel = 1f/16f
//  private val texPixel = 1f/32f
//
//  fun drawCore(te: TileEntityPipe, buffer: BufferBuilder) {
//    buffer.addPosUV(1.0-11.0*pixel/2.0, 11.0*pixel/2.0, 11.0*pixel/2.0, 5.0 * texPixel, 5.0 * texPixel)
//    buffer.addPosUV(1.0-11.0*pixel/2.0, 1.0-11.0*pixel/2.0, 11.0*pixel/2.0, 5.0 * texPixel, 0.0 * texPixel)
//    buffer.addPosUV(11.0*pixel/2.0, 1.0-11.0*pixel/2.0, 11.0*pixel/2.0, 0.0 * texPixel, 0.0 * texPixel)
//    buffer.addPosUV(11.0*pixel/2.0, 11.0*pixel/2.0, 11.0*pixel/2.0, 0.0 * texPixel, 5.0 * texPixel)
//  }
//
//  private fun BufferBuilder.addPosUV(x: Double, y: Double, z: Double, u: Double, v: Double) {
//    this
//        .pos(x, y, z)
////        .tex(sprite.minU + v, sprite.minV + v)
//        .endVertex()
//  }
//}
