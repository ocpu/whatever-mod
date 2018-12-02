package io.opencubes.stuff.tools.energy

import net.minecraftforge.energy.EnergyStorage

class MyEnergyStorageReciver(capacity: Int, maxReceive: Int) : EnergyStorage(capacity, maxReceive, 0) {

  fun setEnergy(energy: Int) {
    this.energy = energy
  }

  fun consumePower(energy: Int) {
    this.energy -= energy
    if (this.energy < 0)
      this.energy = 0
  }
}
