package com.shinybunny.bluestone.mixin;

import com.google.common.collect.ImmutableMap;
import com.shinybunny.bluestone.BlueStoneMain;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockState.class)
public abstract class MixinBlockState {

    @Shadow public abstract Block getBlock();

    /**
     * @author TheShinyBunny
     */
    @Overwrite
    public int getStrongRedstonePower(BlockView blockView_1, BlockPos blockPos_1, Direction direction_1) {
        if (BlueStoneMain.powerAskingBlock != null) {
            if (!BlueStoneMain.areSameRedstoneSystems(BlueStoneMain.powerAskingBlock,getBlock())) {
                return 0;
            }
        }
        return getBlock().getStrongRedstonePower(blockView_1.getBlockState(blockPos_1),blockView_1,blockPos_1,direction_1);
    }
}
