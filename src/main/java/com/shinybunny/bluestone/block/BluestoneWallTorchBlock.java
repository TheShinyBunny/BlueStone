package com.shinybunny.bluestone.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.WallRedstoneTorchBlock;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class BluestoneWallTorchBlock extends WallRedstoneTorchBlock {
    public BluestoneWallTorchBlock() {
        super(Settings.of(Material.PART).noCollision().strength(0.0f,0.0f));
    }

    @Override
    public int getLuminance(BlockState blockState_1) {
        return blockState_1.get(LIT_2) ? 7 : 0;
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState blockState_1) {
        return BlockSoundGroup.WOOD;
    }

    @Override
    public void randomDisplayTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
        if (blockState_1.get(LIT_2)) {
            Direction direction_1 = blockState_1.get(FACING).getOpposite();
            double double_2 = (double)blockPos_1.getX() + 0.5D + (random_1.nextDouble() - 0.5D) * 0.2D + 0.27D * (double)direction_1.getOffsetX();
            double double_3 = (double)blockPos_1.getY() + 0.7D + (random_1.nextDouble() - 0.5D) * 0.2D + 0.22D;
            double double_4 = (double)blockPos_1.getZ() + 0.5D + (random_1.nextDouble() - 0.5D) * 0.2D + 0.27D * (double)direction_1.getOffsetZ();
            world_1.addParticle(new DustParticleEffect(0.0f,0.0f,1.0f,1.0f), double_2, double_3, double_4, 0.0D, 0.0D, 0.0D);
        }
    }
}
