package net.thedragonskull.crystalmod.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.thedragonskull.crystalmod.CrystalMod;
import net.thedragonskull.crystalmod.block.entity.MortarBE;
import net.thedragonskull.crystalmod.util.MortarUtil;

@Mod.EventBusSubscriber(modid = CrystalMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        Player player = event.getEntity();
        BlockPos pos = event.getPos();

        if (!level.isClientSide && player.isCrouching()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof MortarBE) {
                if (MortarUtil.tryInsertIngredientIntoMortar(level, pos, player)) {
                    event.setCanceled(true);
                    event.setCancellationResult(InteractionResult.SUCCESS);
                }
            }
        }
    }


}
