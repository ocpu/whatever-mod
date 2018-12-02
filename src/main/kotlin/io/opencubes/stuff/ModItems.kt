package io.opencubes.stuff

import io.opencubes.stuff.item.ItemBag
import io.opencubes.stuff.item.ItemEnergyCell
import io.opencubes.stuff.item.ItemScrewDriver
import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent

object ModItems {

  val energyCell = ItemEnergyCell()
  val screwDriver = ItemScrewDriver()
  val bag = ItemBag()

  fun register(e: RegistryEvent.Register<Item>) = e.registry.registerAll(
      energyCell,
      screwDriver,
      bag,
      simpleItemBlock(ModBlocks.solarGenerator),
      simpleItemBlock(ModBlocks.energyFurnace)
  )

  fun registerModels() {
    registerModel(energyCell)
    registerModel(screwDriver)
    registerModel(bag)
  }

  private fun registerModel(item: Item, meta: Int = 0, variant: String = "inventory") =
      ModelLoader.setCustomModelResourceLocation(item, meta, ModelResourceLocation(item.registryName!!, variant))

  fun simpleItemBlock(block: Block): Item = ItemBlock(block).setRegistryName(block.registryName)
}