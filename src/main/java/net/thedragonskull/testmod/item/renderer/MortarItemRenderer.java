package net.thedragonskull.testmod.item.renderer;

import net.thedragonskull.testmod.item.custom.MortarItem;
import net.thedragonskull.testmod.item.model.MortarItemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class MortarItemRenderer extends GeoItemRenderer<MortarItem> {

    public MortarItemRenderer() {
        super(new MortarItemModel());
    }
}
