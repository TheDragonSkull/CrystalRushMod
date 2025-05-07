package net.thedragonskull.crystalmod.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.crystalmod.CrystalMod;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CrystalMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<MortarRecipe>> MORTAR_GRINDING_SERIALIZER =
            SERIALIZERS.register("mortar_grinding", () -> MortarRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
