package io.opencubes.expandablestorage

import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.*
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.util.EnumFacing

class TestModel : IBakedModel {
  override fun getParticleTexture(): TextureAtlasSprite {
    TODO("Not implemented")
  }

  override fun getQuads(state: IBlockState?, side: EnumFacing?, rand: Long): MutableList<BakedQuad> {
    TODO("Not implemented")
  }

  override fun isBuiltInRenderer() = false
  override fun isAmbientOcclusion() = true
  override fun isGui3d() = true
  override fun getOverrides() = ItemOverrideList(listOf())
}