package io.opencubes.stuff.tools.energy

import net.minecraftforge.energy.EnergyStorage

class MyEnergyStorageTransmitter(capacity: Int, maxTransfer: Int) : EnergyStorage(capacity, maxTransfer) {

  fun setEnergy(energy: Int) {
    this.energy = energy
  }

  fun consumePower(energy: Int) {
    this.energy -= energy
    if (this.energy > 0)
      this.energy = 0
  }

  fun generatePower(energy: Int) {
    this.energy += energy
    if (this.energy > capacity)
      this.energy = capacity
  }
}
