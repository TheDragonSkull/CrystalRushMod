package net.thedragonskull.crystalmod.event;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.thedragonskull.crystalmod.CrystalMod;
import net.thedragonskull.crystalmod.block.entity.ModBlockEntities;
import net.thedragonskull.crystalmod.block.entity.renderer.MortarRenderer;

@Mod.EventBusSubscriber(modid = CrystalMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(ModBlockEntities.MORTAR_BE.get(), MortarRenderer::new);
    }
}
