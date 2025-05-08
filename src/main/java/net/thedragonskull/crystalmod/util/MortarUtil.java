package net.thedragonskull.crystalmod.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.thedragonskull.crystalmod.block.entity.MortarBE;
import net.thedragonskull.crystalmod.recipe.MortarRecipe;

import java.util.Optional;
import java.util.stream.Stream;

public class MortarUtil {

    public static boolean tryInsertIngredientIntoMortar(Level level, BlockPos pos, Player player) {
        BlockEntity entity = level.getBlockEntity(pos);

        if (!(entity instanceof MortarBE mortarBE)) return false;

        Optional<ItemStack> matchingStack = Stream.of(player.getMainHandItem(), player.getOffhandItem())
                .filter(stack -> !stack.isEmpty())
                .filter(stack -> hasMatchingRecipeIngredient(level, stack))
                .findFirst();

        if (matchingStack.isPresent()) {
            ItemStack stack = matchingStack.get();

            if (mortarBE.getSlotItem().isEmpty()) {
                ItemStack toInsert = stack.copyWithCount(1);
                mortarBE.setItem(0, toInsert);

                if (!player.isCreative()) {
                    stack.shrink(1);
                }

                mortarBE.setChanged();
                level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 3);

                return true;
            }
        }

        return false;
    }

    private static boolean hasMatchingRecipeIngredient(Level level, ItemStack itemStack) {
        return level.getRecipeManager()
                .getAllRecipesFor(MortarRecipe.Type.INSTANCE)
                .stream()
                .anyMatch(recipe ->
                        recipe.getIngredients().stream().anyMatch(ingredient -> ingredient.test(itemStack)));
    }

}
