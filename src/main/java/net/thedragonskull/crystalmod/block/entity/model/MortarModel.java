package net.thedragonskull.crystalmod.block.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.thedragonskull.crystalmod.CrystalMod;
import net.thedragonskull.crystalmod.block.entity.MortarBE;
import software.bernie.geckolib.model.GeoModel;

public class MortarModel extends GeoModel<MortarBE> {

    @Override
    public ResourceLocation getModelResource(MortarBE animatable) {
        return new ResourceLocation(CrystalMod.MOD_ID, "geo/mortar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MortarBE animatable) {
        return new ResourceLocation(CrystalMod.MOD_ID, "textures/block/mortar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MortarBE animatable) {
        return new ResourceLocation(CrystalMod.MOD_ID, "animations/mortar.animation.json");
    }
}
