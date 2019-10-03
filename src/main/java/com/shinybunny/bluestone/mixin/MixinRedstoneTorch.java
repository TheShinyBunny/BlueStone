package com.shinybunny.bluestone.mixin;

import com.shinybunny.bluestone.BlueStoneMain;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.WallRedstoneTorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Prevents redstone torches and bluestone torches from powering off from the other color.
 */
@Mixin({RedstoneTorchBlock.class, WallRedstoneTorchBlock.class})
public class MixinRedstoneTorch {

    @Inject(method = "shouldUnpower",at = @At("HEAD"))
    private void getPower(World world_1, BlockPos blockPos_1, BlockState blockState_1, CallbackInfoReturnable<Integer> cir) {
        BlueStoneMain.powerAskingBlock = blockState_1.getBlock();
    }

    @Inject(method = "shouldUnpower",at = @At("RETURN"))
    private void getPowerReturn(World world_1, BlockPos blockPos_1, BlockState blockState_1, CallbackInfoReturnable<Integer> cir) {
        BlueStoneMain.powerAskingBlock = null;
    }

}
