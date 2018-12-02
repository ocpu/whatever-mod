package io.opencubes.stuff.tools

import net.minecraft.nbt.NBTTagCompound

interface ISaveable {
  fun save(nbt: NBTTagCompound): NBTTagCompound
  fun load(nbt: NBTTagCompound)
}