package net.thedragonskull.crystalmod.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.thedragonskull.crystalmod.block.entity.MortarBE;
import net.thedragonskull.crystalmod.block.entity.model.MortarModel;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import static net.thedragonskull.crystalmod.block.custom.Mortar.GRINDING;

public class MortarRenderer extends GeoBlockRenderer<MortarBE> {

    public MortarRenderer(BlockEntityRendererProvider.Context ctx) {
        super(new MortarModel());
    }

    @Override
    public void renderCubesOfBone(PoseStack poseStack, GeoBone bone, VertexConsumer buffer,
                                  int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        MortarBE blockEntity = this.animatable;
        boolean isGrinding = blockEntity.getBlockState().getValue(GRINDING);

        if ("fill".equals(bone.getName()) && !isGrinding) {
            return;
        }

        super.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, MortarBE animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        MortarBE blockEntity = this.animatable;
        boolean isGrinding = blockEntity.getBlockState().getValue(GRINDING);

        if (!isGrinding) { //TODO: cambiar por if shard in input slot || "cookingProgress" < 50%
            renderShard(poseStack, bufferSource, packedLight); //TODO: cambiar por lo que haya en el input slot
        }
    }

    private void renderShard(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        ItemStack shard = new ItemStack(Items.AMETHYST_SHARD);

        poseStack.pushPose();

        poseStack.translate(0, 0.1, 0);
        poseStack.scale(0.25f, 0.25f, 0.25f);
        poseStack.mulPose(Axis.XP.rotationDegrees(90));

        Minecraft.getInstance().getItemRenderer().renderStatic(
                shard,
                ItemDisplayContext.FIXED,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                bufferSource,
                null,
                0
        );

        poseStack.popPose();
    }

}
