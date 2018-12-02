package io.opencubes.stuff.tileentity

import io.opencubes.boxlin.contains
import io.opencubes.boxlin.get
import io.opencubes.boxlin.isServer
import io.opencubes.boxlin.set
import io.opencubes.stuff.ModGui
import io.opencubes.stuff.config.EnergyFurnaceConfig
import io.opencubes.stuff.tools.IDrops
import io.opencubes.stuff.tools.ISaveable
import io.opencubes.stuff.tools.energy.MyEnergyStorageReciver
import io.opencubes.stuff.tools.gui.IGuiProvider
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.FurnaceRecipes
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler
import net.minecraftforge.items.wrapper.CombinedInvWrapper

class TileEntityEnergyFurnace : TileEntity(), ITickable, ISaveable, IGuiProvider, IDrops {
  companion object {
    const val INPUT_SLOTS = 1
    const val OUTPUT_SLOTS = 1
    const val SIZE = INPUT_SLOTS + OUTPUT_SLOTS
    const val MAX_PROGRESS = 30
  }

  private var progress = 0

  override val gui = ModGui.ENERGY_FURNACE

  private val inputHandler = object : ItemStackHandler(INPUT_SLOTS) {
    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
      if (FurnaceRecipes.instance().getSmeltingResult(stack).isEmpty) return stack
      return super.insertItem(slot, stack, simulate)
    }
    override fun onContentsChanged(slot: Int) = markDirty()
  }
  private val outputHandler = object : ItemStackHandler(OUTPUT_SLOTS) {
    override fun onContentsChanged(slot: Int) = markDirty()
  }
  private val combined = CombinedInvWrapper(inputHandler, outputHandler)
  private val energyStorage = MyEnergyStorageReciver(EnergyFurnaceConfig.MAX_POWER, EnergyFurnaceConfig.RECEIVING_SPEED)

  override fun update() {
    if (world.isServer) {
      if (energyStorage.energyStored < EnergyFurnaceConfig.RF_PER_TICK) {
        return
      }
      if (progress > 0) {
        progress--
        energyStorage.consumePower(EnergyFurnaceConfig.RF_PER_TICK)
        if (progress <= 0)
          attemptSmelt()
        markDirty()
      } else startSmelt()
    }
  }

  private fun startSmelt() {
    for (i in 0 until INPUT_SLOTS) {
      val result = FurnaceRecipes.instance().getSmeltingResult(inputHandler.getStackInSlot(i))
      if (!result.isEmpty) {
        if (insertOutput(result.copy(), true)) {
          progress = MAX_PROGRESS
          markDirty()
        }
      }
    }
  }

  private fun insertOutput(output: ItemStack, simulate: Boolean): Boolean {
    for (i in 0 until OUTPUT_SLOTS) {
      val remaining = outputHandler.insertItem(i, output, simulate)
      if (remaining.isEmpty)
        return true
    }
    return false
  }

  private fun attemptSmelt() {
    for (i in 0 until INPUT_SLOTS) {
      val result = FurnaceRecipes.instance().getSmeltingResult(inputHandler.getStackInSlot(i))
      if (!result.isEmpty) {
        if (insertOutput(result.copy(), false)) {
          inputHandler.extractItem(i, 1, false)
          break
        }
      }
    }
  }

  override fun getDrops(): List<ItemStack> = listOf(
      inputHandler.getStackInSlot(0),
      outputHandler.getStackInSlot(0)
  )

  override fun save(nbt: NBTTagCompound): NBTTagCompound {
    nbt["energy"] = energyStorage.energyStored
    return nbt
  }

  override fun load(nbt: NBTTagCompound) {
    if ("energy" in nbt)
      energyStorage.setEnergy(nbt["energy"])
  }

  override fun readFromNBT(compound: NBTTagCompound) {
    super.readFromNBT(compound)
    load(compound)

    if ("input" in compound)
      inputHandler.deserializeNBT(compound["input"])
    if ("output" in compound)
      outputHandler.deserializeNBT(compound["output"])
  }

  override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
    compound["input"] = inputHandler.serializeNBT()
    compound["output"] = outputHandler.serializeNBT()

    return super.writeToNBT(save(compound))
  }

  override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
    if (capability === CapabilityEnergy.ENERGY) return true
    if (capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return when (facing) {
      null, EnumFacing.UP, EnumFacing.DOWN -> true
      else -> super.hasCapability(capability, facing)
    }
    return super.hasCapability(capability, facing)
  }

  override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
    if (capability === CapabilityEnergy.ENERGY) return CapabilityEnergy.ENERGY.cast(energyStorage)
    if (capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) when (facing) {
      null -> return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combined)
      EnumFacing.UP -> return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler)
      EnumFacing.DOWN -> return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputHandler)
    }
    return super.getCapability(capability, facing)
  }
}
