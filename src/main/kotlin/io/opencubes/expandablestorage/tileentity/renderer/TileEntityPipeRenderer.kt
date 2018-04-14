package io.opencubes.expandablestorage.tileentity.renderer

import io.opencubes.expandablestorage.ExpandableStorage
import io.opencubes.expandablestorage.tileentity.TileEntityPipe
import net.minecraft.client.renderer.*
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.animation.FastTESR
import org.lwjgl.opengl.GL11

class TileEntityPipeRenderer : FastTESR<TileEntityPipe>() {
  override fun renderTileEntityFast(te: TileEntityPipe, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int, partial: Float, buffer: BufferBuilder) {
    buffer.setTranslation(x, y, z)
    bindTexture(pipe)
    drawCore(te, buffer)
  }

  companion object {
    @JvmStatic val pipe = ResourceLocation(ExpandableStorage.ID, "textures/model/pipe.png")
    lateinit var sprite: TextureAtlasSprite
  }

  private val pixel = 1f/16f
  private val texPixel = 1f/32f

  fun drawCore(te: TileEntityPipe, buffer: BufferBuilder) {
    buffer.pos(1.0-11.0*pixel/2.0, 11.0*pixel/2.0, 11.0*pixel/2.0).tex(5.0 * texPixel, 5.0 * texPixel)
    buffer.pos(1.0-11.0*pixel/2.0, 1.0-11.0*pixel/2.0, 11.0*pixel/2.0).tex(5.0 * texPixel, 0.0 * texPixel)
    buffer.pos(11.0*pixel/2.0, 1.0-11.0*pixel/2.0, 11.0*pixel/2.0).tex(0.0 * texPixel, 0.0 * texPixel)
    buffer.pos(11.0*pixel/2.0, 11.0*pixel/2.0, 11.0*pixel/2.0).tex(0.0 * texPixel, 5.0 * texPixel)
    buffer
  }
}
