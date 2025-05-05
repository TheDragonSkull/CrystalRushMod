package net.thedragonskull.testmod.item.model;

import net.minecraft.resources.ResourceLocation;
import net.thedragonskull.testmod.TestMod;
import net.thedragonskull.testmod.item.custom.MortarItem;
import software.bernie.geckolib.model.GeoModel;

public class MortarItemModel extends GeoModel<MortarItem> {

    @Override
    public ResourceLocation getModelResource(MortarItem animatable) {
        return new ResourceLocation(TestMod.MOD_ID, "geo/mortar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MortarItem animatable) {
        return new ResourceLocation(TestMod.MOD_ID, "textures/block/mortar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MortarItem animatable) {
        return new ResourceLocation(TestMod.MOD_ID, "animations/mortar.animation.json");
    }
}
