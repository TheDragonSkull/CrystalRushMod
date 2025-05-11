package net.thedragonskull.crystalmod.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.crystalmod.CrystalMod;
import net.thedragonskull.crystalmod.block.ModBlocks;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CrystalMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<MortarBE>> MORTAR_BE =
            BLOCK_ENTITIES.register("mortar_be", () ->
                    BlockEntityType.Builder.of(MortarBE::new,
                            ModBlocks.MORTAR.get()).build(null));

    public static final RegistryObject<BlockEntityType<SulfurGasBE>> SULFUR_GAS_BE =
            BLOCK_ENTITIES.register("sulfur_gas_be", () ->
                    BlockEntityType.Builder.of(SulfurGasBE::new,
                            ModBlocks.SULFUR_GAS.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
