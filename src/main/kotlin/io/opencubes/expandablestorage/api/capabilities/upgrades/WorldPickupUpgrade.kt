package io.opencubes.expandablestorage.api.capabilities.upgrades

import net.minecraft.item.ItemStack

open class WorldPickupUpgrade(override var mode: IWorldPickupUpgrade.Mode,
                              override var filter: List<ItemStack>) : IWorldPickupUpgrade {
  override fun action() {
    TODO("Not implemented")
  }

  constructor(mode: IWorldPickupUpgrade.Mode) : this(mode, List(9) { _ -> ItemStack.EMPTY })
  constructor() : this(IWorldPickupUpgrade.Mode.EXCLUDE)
}