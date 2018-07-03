package io.opencubes.expandablestorage.item.bag

import io.opencubes.boxlin.name
import io.opencubes.expandablestorage.ExpandableStorage
import io.opencubes.expandablestorage.gui.GuiContainerHelper
import io.opencubes.expandablestorage.item.ExpandableStorageItems
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.init.Items
import net.minecraft.item.ItemStack

class GuiBag(container: Container, private val playerInv: InventoryPlayer) : GuiContainerHelper(container, 198, 204) {
  companion object {
    private val BG_TEXTURE = ExpandableStorage.location("textures/gui/bag.png")
  }

  override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
    GlStateManager.color(1f, 1f, 1f, 1f)
    drawBackground(BG_TEXTURE)
  }

  override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
    write(ExpandableStorageItems.bag.name, 30, 6)
    write(playerInv.displayName.unformattedText, 30, ySize - 94)
//    drawItemStack(ItemStack(Items.ITEM_FRAME, 2), 0, 0)
  }
}