package io.opencubes.stuff.tools.energy

import net.minecraftforge.energy.EnergyStorage

class EnergyStorageContainer : EnergyStorage {
  constructor(capacity: Int) : super(capacity)
  constructor(capacity: Int, maxTransfer: Int) : super(capacity, maxTransfer)
  constructor(capacity: Int, maxReceive: Int, maxExtract: Int) : super(capacity, maxReceive, maxExtract)
  constructor(capacity: Int, maxReceive: Int, maxExtract: Int, energy: Int) : super(capacity, maxReceive, maxExtract, energy)

  fun setEnergy(energy: Int) {
    this.energy = energy
  }
}