package io.opencubes.expandablestorage.api.capabilities.upgrades

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable

class WorldPickupUpgradeProvider : ICapabilitySerializable<NBTTagCompound> {
  private val capability get() = CapabilityWorldPickupUpgrade.WORLD_PICKUP_UPGRADE
  private val instance = capability.defaultInstance!!

  override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
    return if (capability == this.capability) this.capability.cast<T>(instance) else null
  }

  override fun deserializeNBT(nbt: NBTTagCompound) {
    capability.storage.readNBT(capability, instance, null, nbt)
  }

  override fun serializeNBT() =
      capability.storage.writeNBT(capability, instance, null) as NBTTagCompound

  override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) = capability == CapabilityWorldPickupUpgrade.WORLD_PICKUP_UPGRADE
}