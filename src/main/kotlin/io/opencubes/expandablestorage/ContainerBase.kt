package io.opencubes.expandablestorage

import net.minecraft.inventory.*
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

abstract class ContainerBase : Container() {
  protected fun buildInventoryGrid(
      gridHeight: Int = 1,
      gridWidth: Int = 9,
      indexOffset: Int = 0,
      startX: Int = 0,
      startY: Int = 0,
      xOffset: Int = 2,
      yOffset: Int = 2,
      createSlot: (index: Int, x: Int, y: Int) -> Slot): Pair<Int, Int> {
    val slotSize = 16
    for (y in 0 until gridHeight) for (x in 0 until gridWidth) this.addSlotToContainer(createSlot(
        x + (y * gridWidth) + indexOffset,
        startX + x * (slotSize + xOffset),
        startY + y * (slotSize + yOffset)
    ))
    return ((slotSize + xOffset) * gridWidth) to ((slotSize + yOffset) * gridHeight)
  }

  protected fun <T : IInventory> buildInventoryGrid(inventory: T,
                                                    gridHeight: Int = 1,
                                                    gridWidth: Int = 9,
                                                    indexOffset: Int = 0,
                                                    startX: Int = 0,
                                                    startY: Int = 0,
                                                    xOffset: Int = 2,
                                                    yOffset: Int = 2) =
      buildInventoryGrid(gridHeight, gridWidth, indexOffset, startX, startY, xOffset, yOffset) { i, x, y ->
        Slot(inventory, i, x, y)
      }

  protected fun buildInventoryGrid(inventory: IItemHandler,
                                   gridHeight: Int = 1,
                                   gridWidth: Int = 9,
                                   indexOffset: Int = 0,
                                   startX: Int = 0,
                                   startY: Int = 0,
                                   xOffset: Int = 2,
                                   yOffset: Int = 2) =
      buildInventoryGrid(gridHeight, gridWidth, indexOffset, startX, startY, xOffset, yOffset) { i, x, y ->
        SlotItemHandler(inventory, i, x, y)
      }
}