package net.thedragonskull.testmod.block.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.thedragonskull.testmod.TestMod;
import net.thedragonskull.testmod.block.entity.MortarBE;
import software.bernie.geckolib.model.GeoModel;

public class MortarModel extends GeoModel<MortarBE> {

    @Override
    public ResourceLocation getModelResource(MortarBE animatable) {
        return new ResourceLocation(TestMod.MOD_ID, "geo/mortar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MortarBE animatable) {
        return new ResourceLocation(TestMod.MOD_ID, "textures/block/mortar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MortarBE animatable) {
        return new ResourceLocation(TestMod.MOD_ID, "animations/mortar.animation.json");
    }
}
