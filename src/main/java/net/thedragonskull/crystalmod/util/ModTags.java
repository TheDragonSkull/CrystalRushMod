package net.thedragonskull.crystalmod.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.thedragonskull.crystalmod.CrystalMod;

public class ModTags {

    public static class Items {
        public static final TagKey<Item> RAW_POWDERS = TagKey.create(Registries.ITEM, new ResourceLocation(CrystalMod.MOD_ID, "raw_powders"));
    }

}
