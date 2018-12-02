package io.opencubes.stuff.tileentity

import io.opencubes.boxlin.capability
import io.opencubes.boxlin.contains
import io.opencubes.boxlin.get
import io.opencubes.boxlin.set
import io.opencubes.stuff.config.SolarGeneratorConfig
import io.opencubes.stuff.tools.energy.MyEnergyStorageTransmitter
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.energy.CapabilityEnergy

class TileEntitySolarGenerator : TileEntity(), ITickable {

  private val energyStorage = MyEnergyStorageTransmitter(SolarGeneratorConfig.MAX_POWER, SolarGeneratorConfig.TRANSFER_SPEED)

  override fun update() {
    // TODO: Check if "outside"
    energyStorage.generatePower(1)
    pushEnergy()
  }

  private fun pushEnergy() {
    val maxAmount = Math.min(energyStorage.energyStored, SolarGeneratorConfig.TRANSFER_SPEED)
    for (side in EnumFacing.VALUES) {
      val pos = pos.add(side.directionVec)
      val te = world.getTileEntity(pos) ?: continue
      val capability = te.capability(CapabilityEnergy.ENERGY, side.opposite)
      if (capability.isPresent) {
        val energy = capability.get().get()
        if (energy.canReceive()) {
          val amount = energy.receiveEnergy(maxAmount, false)
          energyStorage.consumePower(amount)
        }
      }
    }
  }

  override fun readFromNBT(compound: NBTTagCompound) {
    super.readFromNBT(compound)
    if ("energy" in compound)
      energyStorage.setEnergy(compound["energy"])
  }

  override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
    compound["energy"] = energyStorage.energyStored
    return super.writeToNBT(compound)
  }

  override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
    if (capability == CapabilityEnergy.ENERGY) return true
    return super.hasCapability(capability, facing)
  }

  override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
    if (capability == CapabilityEnergy.ENERGY) return CapabilityEnergy.ENERGY.cast(energyStorage)
    return super.getCapability(capability, facing)
  }
}
