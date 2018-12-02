package io.opencubes.stuff.item

import io.opencubes.stuff.Stuff
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

class ItemScrewDriver : Item() {
  init {
    translationKey = "${Stuff.ID}.screw_driver"
    registryName = ResourceLocation("${Stuff.ID}:screw_driver")
    creativeTab = Stuff.creativeTab
  }
}