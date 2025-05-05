package net.thedragonskull.testmod.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.thedragonskull.testmod.block.entity.MortarBE;
import net.thedragonskull.testmod.block.entity.model.MortarModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import static net.thedragonskull.testmod.block.custom.Mortar.GRINDING;

public class MortarRenderer extends GeoBlockRenderer<MortarBE> {

    public MortarRenderer(BlockEntityRendererProvider.Context ctx) {
        super(new MortarModel());
    }

    @Override
    public void renderCubesOfBone(PoseStack poseStack, GeoBone bone, VertexConsumer buffer,
                                  int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        MortarBE blockEntity = this.animatable;
        boolean isCrushing = blockEntity.getBlockState().getValue(GRINDING);

        if ("fill".equals(bone.getName()) && !isCrushing) {
            return;
        }

        super.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
