package net.thedragonskull.crystalmod.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.thedragonskull.crystalmod.block.entity.MortarBE;
import net.thedragonskull.crystalmod.block.entity.model.MortarModel;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MortarRenderer extends GeoBlockRenderer<MortarBE> {

    public MortarRenderer(BlockEntityRendererProvider.Context ctx) {
        super(new MortarModel());
    }

    @Override
    public void renderCubesOfBone(PoseStack poseStack, GeoBone bone, VertexConsumer buffer,
                                  int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        ItemStack slotStack = animatable.getSlotItem();

        if ("fill".equals(bone.getName())) {
            if (!slotStack.is(Items.GLOWSTONE_DUST)) { //todo: cambiar
                return;
            }
        }

        super.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, MortarBE animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        ItemStack slotStack = animatable.getSlotItem();
        Level level = animatable.getLevel();
        BlockPos pos = animatable.getBlockPos();

        // Render slot item
        if (!slotStack.isEmpty() && !animatable.hasProgressFinished()) {
            renderStack(poseStack, bufferSource, packedLight, slotStack);
        }

        // Render leather
        if (level != null && level.getBlockState(pos.below()).isSolidRender(level, pos.below())) {
            renderLeather(poseStack, bufferSource, packedLight, Items.LEATHER.getDefaultInstance());
        }
    }

    private void renderStack(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, ItemStack stack) {
        poseStack.pushPose();

        poseStack.translate(0, 0.1, 0);
        poseStack.scale(0.25f, 0.25f, 0.25f);
        poseStack.mulPose(Axis.XP.rotationDegrees(90));

        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack,
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

    private void renderLeather(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, ItemStack stack) {
        poseStack.pushPose();

        poseStack.scale(1.2f, 0.25f, 1f);
        poseStack.mulPose(Axis.XP.rotationDegrees(90));

        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack,
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
