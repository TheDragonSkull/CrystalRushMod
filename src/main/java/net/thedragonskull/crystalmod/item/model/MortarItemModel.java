package net.thedragonskull.crystalmod.item.model;

import net.minecraft.resources.ResourceLocation;
import net.thedragonskull.crystalmod.CrystalMod;
import net.thedragonskull.crystalmod.item.custom.MortarItem;
import software.bernie.geckolib.model.GeoModel;

public class MortarItemModel extends GeoModel<MortarItem> {

    @Override
    public ResourceLocation getModelResource(MortarItem animatable) {
        return new ResourceLocation(CrystalMod.MOD_ID, "geo/mortar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MortarItem animatable) {
        return new ResourceLocation(CrystalMod.MOD_ID, "textures/block/mortar/mortar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MortarItem animatable) {
        return new ResourceLocation(CrystalMod.MOD_ID, "animations/mortar.animation.json");
    }
}
