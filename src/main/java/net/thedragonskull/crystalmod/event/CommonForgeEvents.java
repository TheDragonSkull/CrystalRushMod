package net.thedragonskull.crystalmod.event;

import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.thedragonskull.crystalmod.block.custom.SulfurGasBlock;

@Mod.EventBusSubscriber(modid = "crystalmod", value = Dist.CLIENT)
public class CommonForgeEvents {

    @SubscribeEvent
    public static void onFogRender(ViewportEvent.RenderFog event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        BlockPos headPos = BlockPos.containing(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(), mc.player.getZ());
        BlockState blockState = mc.level.getBlockState(headPos);

        if (blockState.getBlock() instanceof SulfurGasBlock) {
            event.setCanceled(true);

            event.setFogShape(FogShape.SPHERE);
            event.setFarPlaneDistance(15.0F);
            event.setNearPlaneDistance(0.1F);
        }
    }

    @SubscribeEvent
    public static void onFogColors(ViewportEvent.ComputeFogColor event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        BlockPos headPos = BlockPos.containing(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(), mc.player.getZ());
        BlockState blockState = mc.level.getBlockState(headPos);

        if (blockState.getBlock() instanceof SulfurGasBlock) {
            event.setRed(0.9F);
            event.setGreen(0.95F);
            event.setBlue(0.3F);
        }
    }

}
