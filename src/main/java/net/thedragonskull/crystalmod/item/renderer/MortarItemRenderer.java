package net.thedragonskull.crystalmod.item.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.thedragonskull.crystalmod.item.custom.MortarItem;
import net.thedragonskull.crystalmod.item.model.MortarItemModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class MortarItemRenderer extends GeoItemRenderer<MortarItem> {

    public MortarItemRenderer() {
        super(new MortarItemModel());
    }

    @Override
    public void renderCubesOfBone(PoseStack poseStack, GeoBone bone, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        if ("fill".equals(bone.getName())) {
            return;
        }

        super.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
