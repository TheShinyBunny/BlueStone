package com.shinybunny.bluestone.mixin;

import com.shinybunny.bluestone.BlueStoneMain;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Prevents from repeaters and comparators from checking for power from the other redstone color.
 */
@Mixin({AbstractRedstoneGateBlock.class, ComparatorBlock.class})
public class MixinDiodeBlock {

    @Inject(method = "getPower",at = @At("HEAD"))
    private void getPower(World world_1, BlockPos blockPos_1, BlockState blockState_1, CallbackInfoReturnable<Integer> cir) {
        BlueStoneMain.powerAskingBlock = blockState_1.getBlock();
    }

    @Inject(method = "getPower",at = @At("RETURN"))
    private void getPowerReturn(World world_1, BlockPos blockPos_1, BlockState blockState_1, CallbackInfoReturnable<Integer> cir) {
        BlueStoneMain.powerAskingBlock = null;
    }

}
