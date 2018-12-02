package io.opencubes.stuff

import io.opencubes.stuff.block.BlockEnergyFurnace
import io.opencubes.stuff.block.BlockSolarGenerator
import io.opencubes.stuff.tileentity.TileEntityEnergyFurnace
import io.opencubes.stuff.tileentity.TileEntitySolarGenerator
import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.registry.GameRegistry

object ModBlocks {

  val solarGenerator = BlockSolarGenerator()
  val energyFurnace = BlockEnergyFurnace()

  fun register(e: RegistryEvent.Register<Block>) {
    e.registry.registerAll(
        solarGenerator,
        energyFurnace
    )
  }

  fun registerEntities() {
    GameRegistry.registerTileEntity(TileEntitySolarGenerator::class.java, "${Stuff.ID}:te_solar_generator")
    GameRegistry.registerTileEntity(TileEntityEnergyFurnace::class.java, "${Stuff.ID}:te_energy_furnace")
  }

  fun registerModels() {
    registerModel(solarGenerator)
    registerModel(energyFurnace)
  }

  private fun registerModel(block: Block, meta: Int = 0, variant: String = "normal") =
      ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, ModelResourceLocation(block.registryName!!, variant))
}
