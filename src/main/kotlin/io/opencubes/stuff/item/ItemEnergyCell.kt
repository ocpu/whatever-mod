package io.opencubes.stuff.item

import io.opencubes.stuff.Stuff
import io.opencubes.stuff.config.EnergyCellConfig
import io.opencubes.stuff.tools.energy.EnergyStorageContainer
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagInt
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.energy.CapabilityEnergy

class ItemEnergyCell : Item() {
  init {
    translationKey = "${Stuff.ID}.energy_cell"
    registryName = ResourceLocation("${Stuff.ID}:energy_cell")
    creativeTab = Stuff.creativeTab
  }

  override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
    super.addInformation(stack, worldIn, tooltip, flagIn)
  }

  override fun initCapabilities(stack: ItemStack, nbt: NBTTagCompound?): ICapabilityProvider? {
    return EnergyStorageProvider()
  }

  class EnergyStorageProvider : ICapabilitySerializable<NBTTagInt> {

    val energyStorage = EnergyStorageContainer(EnergyCellConfig.MAX_POWER)

    override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? =
        if (capability === CapabilityEnergy.ENERGY) CapabilityEnergy.ENERGY.cast(energyStorage) else null

    override fun deserializeNBT(nbt: NBTTagInt?) = energyStorage.setEnergy(nbt?.int ?: 0)

    override fun serializeNBT() = NBTTagInt(energyStorage.energyStored)

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) = capability === CapabilityEnergy.ENERGY
  }
}