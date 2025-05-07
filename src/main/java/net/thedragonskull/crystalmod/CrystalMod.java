package net.thedragonskull.crystalmod;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.thedragonskull.crystalmod.block.ModBlocks;
import net.thedragonskull.crystalmod.block.entity.ModBlockEntities;
import net.thedragonskull.crystalmod.item.ModItems;
import net.thedragonskull.crystalmod.recipe.ModRecipes;
import net.thedragonskull.crystalmod.screen.ModMenuTypes;
import net.thedragonskull.crystalmod.sound.ModSounds;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(CrystalMod.MOD_ID)
public class CrystalMod {
    public static final String MOD_ID = "crystalmod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CrystalMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModSounds.register(modEventBus);

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

}
