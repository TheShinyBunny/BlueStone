package com.shinybunny.bluestone.block;

import com.shinybunny.bluestone.BlueStoneMain;
import net.minecraft.block.*;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class BluestoneRepeaterBlock extends RepeaterBlock {
    public BluestoneRepeaterBlock() {
        super(Settings.of(Material.PART).strength(0.0f,0.0f));
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState blockState_1) {
        return BlockSoundGroup.WOOD;
    }

    @Override
    protected int getPower(World world_1, BlockPos blockPos_1, BlockState blockState_1) {
        Direction direction_1 = blockState_1.get(FACING);
        BlockPos blockPos_2 = blockPos_1.offset(direction_1);
        BlueStoneMain.powerAskingBlock = this;
        int int_1 = world_1.getEmittedRedstonePower(blockPos_2, direction_1);
        BlueStoneMain.powerAskingBlock = null;
        if (int_1 >= 15) {
            return int_1;
        } else {
            BlockState blockState_2 = world_1.getBlockState(blockPos_2);
            return Math.max(int_1, blockState_2.getBlock() == BlueStoneMain.BLUESTONE_WIRE ? blockState_2.get(RedstoneWireBlock.POWER) : 0);
        }
    }

    @Override
    public void randomDisplayTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
        if (blockState_1.get(POWERED)) {
            Direction direction_1 = blockState_1.get(FACING);
            double double_1 = (double)((float)blockPos_1.getX() + 0.5F) + (double)(random_1.nextFloat() - 0.5F) * 0.2D;
            double double_2 = (double)((float)blockPos_1.getY() + 0.4F) + (double)(random_1.nextFloat() - 0.5F) * 0.2D;
            double double_3 = (double)((float)blockPos_1.getZ() + 0.5F) + (double)(random_1.nextFloat() - 0.5F) * 0.2D;
            float float_1 = -5.0F;
            if (random_1.nextBoolean()) {
                float_1 = (float)(blockState_1.get(DELAY) * 2 - 1);
            }

            float_1 /= 16.0F;
            double double_4 = (double)(float_1 * (float)direction_1.getOffsetX());
            double double_5 = (double)(float_1 * (float)direction_1.getOffsetZ());
            world_1.addParticle(new DustParticleEffect(0.0f,0.0f,1.0f,1.0f), double_1 + double_4, double_2, double_3 + double_5, 0.0D, 0.0D, 0.0D);
        }
    }
}
