package net.thedragonskull.testmod.block.entity.renderer;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.thedragonskull.testmod.block.entity.MortarBE;
import net.thedragonskull.testmod.block.entity.model.MortarModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MortarRenderer extends GeoBlockRenderer<MortarBE> {

    public MortarRenderer(BlockEntityRendererProvider.Context ctx) {
        super(new MortarModel());
    }

}
