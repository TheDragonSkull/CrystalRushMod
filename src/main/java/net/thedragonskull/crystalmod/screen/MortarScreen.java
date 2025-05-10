package net.thedragonskull.crystalmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.thedragonskull.crystalmod.CrystalMod;
import net.thedragonskull.crystalmod.item.ModItems;

public class MortarScreen extends AbstractContainerScreen<MortarMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CrystalMod.MOD_ID, "textures/gui/mortar_screen.png");

    public MortarScreen(MortarMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelY = 100000;
        this.inventoryLabelY = 100000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            ItemStack stack = menu.mortarBE.getSlotItem();
            int vOffset = 0;

            if (stack.is(ModItems.CAVANSITE_SHARD.get())) {
                vOffset = 11;

            } else if (stack.is(ModItems.SULFUR_SHARD.get())) {
                vOffset = 22;
            }

            guiGraphics.blit(TEXTURE, x + 101, y + 58, 176, vOffset, 12, menu.getScaledProgress());
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
