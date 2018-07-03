package io.opencubes.expandablestorage.api.capabilities.upgrades

import net.minecraft.item.ItemStack

interface IWorldPickupUpgrade : IUpgrade {
  var filter: List<ItemStack>
  var mode: Mode

  enum class Mode {
    EXCLUDE, INCLUDE
  }
}
