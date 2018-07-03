package io.opencubes.expandablestorage.api.capabilities.upgrades;

import io.opencubes.boxlin.get
import io.opencubes.boxlin.set
import net.minecraft.item.ItemStack
import net.minecraft.nbt.*
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.Capability.IStorage
import net.minecraftforge.common.capabilities.CapabilityInject

object CapabilityWorldPickupUpgrade {
  @CapabilityInject(IWorldPickupUpgrade::class)
  @JvmStatic
  lateinit var WORLD_PICKUP_UPGRADE: Capability<IWorldPickupUpgrade>

  @JvmStatic
  fun register() = CapabilityManager.INSTANCE.register(IWorldPickupUpgrade::class.java, object : IStorage<IWorldPickupUpgrade> {
    override fun readNBT(capability: Capability<IWorldPickupUpgrade>, instance: IWorldPickupUpgrade, side: EnumFacing, nbt: NBTBase) {
      val tag = nbt as NBTTagCompound
      val tagList = tag.getTagList("filter", 10)
      instance.mode = IWorldPickupUpgrade.Mode.values()[tag["mode"]]
      instance.filter = tagList.map { ItemStack(it as NBTTagCompound) }
    }

    override fun writeNBT(capability: Capability<IWorldPickupUpgrade>, instance: IWorldPickupUpgrade, side: EnumFacing): NBTTagCompound {
      return NBTTagCompound().apply {
        val tagList = NBTTagList()
        instance.filter.mapNotNull(ItemStack::getTagCompound).forEach(tagList::appendTag)
        this["filter"] = tagList
        this["mode"] = instance.mode.ordinal
      }
    }
  }) { WorldPickupUpgrade() }
}