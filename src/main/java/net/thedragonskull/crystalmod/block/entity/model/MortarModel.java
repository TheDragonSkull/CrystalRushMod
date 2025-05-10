package net.thedragonskull.crystalmod.block.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.thedragonskull.crystalmod.CrystalMod;
import net.thedragonskull.crystalmod.block.entity.MortarBE;
import net.thedragonskull.crystalmod.item.ModItems;
import software.bernie.geckolib.model.GeoModel;

public class MortarModel extends GeoModel<MortarBE> {

    @Override
    public ResourceLocation getModelResource(MortarBE animatable) {
        return new ResourceLocation(CrystalMod.MOD_ID, "geo/mortar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MortarBE animatable) {
        ItemStack slotStack = animatable.getSlotItem();

        if (slotStack.is(ModItems.RAW_AMETHYST_POWDER.get())) {
            return new ResourceLocation(CrystalMod.MOD_ID, "textures/block/mortar/mortar.png");

        } else if (slotStack.is(ModItems.RAW_CAVANSITE_POWDER.get())) {
            return new ResourceLocation(CrystalMod.MOD_ID, "textures/block/mortar/mortar_cavansite.png");

        } else if (slotStack.is(ModItems.RAW_SULFUR_POWDER.get())) {
            return new ResourceLocation(CrystalMod.MOD_ID, "textures/block/mortar/mortar_sulfur.png");

        } else {
            return new ResourceLocation(CrystalMod.MOD_ID, "textures/block/mortar/mortar.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(MortarBE animatable) {
        return new ResourceLocation(CrystalMod.MOD_ID, "animations/mortar.animation.json");
    }
}
