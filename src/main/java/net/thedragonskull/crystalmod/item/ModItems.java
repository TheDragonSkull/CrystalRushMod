package net.thedragonskull.crystalmod.item;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.crystalmod.CrystalMod;
import net.thedragonskull.crystalmod.block.ModBlocks;
import net.thedragonskull.crystalmod.effect.ModEffects;
import net.thedragonskull.crystalmod.item.custom.MortarItem;
import net.thedragonskull.crystalmod.item.custom.RawPowder;
import net.thedragonskull.crystalmod.item.custom.SulfurDioxideBottle;
import org.joml.Vector3f;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CrystalMod.MOD_ID);

    public static final RegistryObject<Item> MORTAR_ITEM = ITEMS.register("mortar",
            () -> new MortarItem(ModBlocks.MORTAR.get(), new Item.Properties()));

    // POWDERS

    public static final RegistryObject<Item> RAW_AMETHYST_POWDER = ITEMS.register("raw_amethyst_powder",
            () -> new RawPowder(new Item.Properties(), () -> new MobEffectInstance(ModEffects.REBALANCE_EFFECT.get(),
                    1, 0, false, false, false), 20,
                    new DustParticleOptions(new Vector3f(0.4549f, 0.2667f, 0.7686f), 1.0f)));

    public static final RegistryObject<Item> RAW_CAVANSITE_POWDER = ITEMS.register("raw_cavansite_powder",
            () -> new RawPowder(new Item.Properties(), () -> new MobEffectInstance(ModEffects.EXCHANGE_EFFECT.get(),
                    1, 0, false, false, false), 100,
                    new DustParticleOptions(new Vector3f(0.3608f, 0.8f, 0.9882f), 1.0f)));

    public static final RegistryObject<Item> RAW_SULFUR_POWDER = ITEMS.register("raw_sulfur_powder",
            () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> CAVANSITE_SHARD = ITEMS.register("cavansite_shard",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SULFUR_SHARD = ITEMS.register("sulfur_shard",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SULFUR_DIOXIDE_BOTTLE = ITEMS.register("sulfur_dioxide_bottle",
            () -> new SulfurDioxideBottle(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
