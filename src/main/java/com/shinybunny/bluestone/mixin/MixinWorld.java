package com.shinybunny.bluestone.mixin;

import com.shinybunny.bluestone.BlueStoneMain;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class MixinWorld implements BlockView {

    @Shadow public abstract BlockState getBlockState(BlockPos blockPos_1);

    @Shadow public abstract int getEmittedRedstonePower(BlockPos blockPos_1, Direction direction_1);

    /**
     * Changes how redstone/bluestone components find redstone/bluestone power, making sure the are no cross-color reactions.
     * @author TheShinyBunny
     */
    @Overwrite
    public int getReceivedRedstonePower(BlockPos blockPos_1) {
        int int_1 = 0;
        Direction[] var3 = Direction.values();

        Block b = getBlockState(blockPos_1).getBlock();
        BlueStoneMain.powerAskingBlock = b;
        for (Direction direction_1 : var3) {
            BlockPos adjacent = blockPos_1.offset(direction_1);
            BlockState state = getBlockState(adjacent);
            if (BlueStoneMain.areSameRedstoneSystems(state.getBlock(),b)) {
                int int_2 = this.getEmittedRedstonePower(adjacent, direction_1);
                if (int_2 >= 15) {
                    BlueStoneMain.powerAskingBlock = null;
                    return 15;
                }

                if (int_2 > int_1) {
                    int_1 = int_2;
                }
            }
        }
        BlueStoneMain.powerAskingBlock = null;

        return int_1;
    }

    /**
     * If we are currently looking for redstone/bluestone power, prevents blocks of the other type from giving power.
     * @param blockPos_1
     * @param direction_1
     * @param cir
     */
    @Inject(method = "getEmittedRedstonePower",at=@At("HEAD"),cancellable = true)
    private void getEmittedRedstone(BlockPos blockPos_1, Direction direction_1, CallbackInfoReturnable<Integer> cir) {
        if (BlueStoneMain.powerAskingBlock != null) {

            BlockState state = getBlockState(blockPos_1);
            if (!state.isSimpleFullBlock(this,blockPos_1)) {
                if (!BlueStoneMain.areSameRedstoneSystems(BlueStoneMain.powerAskingBlock,state.getBlock())) cir.setReturnValue(0);
            }
        }
    }




}
