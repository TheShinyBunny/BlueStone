package com.shinybunny.bluestone.block;

import com.shinybunny.bluestone.BlueStoneMain;
import net.minecraft.block.*;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

public class BluestoneComparatorBlock extends ComparatorBlock {
    public BluestoneComparatorBlock() {
        super(Settings.of(Material.PART).strength(0.0f,0.0f));
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState blockState_1) {
        return BlockSoundGroup.WOOD;
    }

    @Override
    protected int getInputLevel(ViewableWorld viewableWorld_1, BlockPos blockPos_1, Direction direction_1) {
        BlockState blockState_1 = viewableWorld_1.getBlockState(blockPos_1);
        Block block_1 = blockState_1.getBlock();
        if (this.isValidInput(blockState_1)) {
            if (block_1 == BlueStoneMain.BLUESTONE_BLOCK) {
                return 15;
            } else {
                BlueStoneMain.powerAskingBlock = this;
                int i = block_1 == BlueStoneMain.BLUESTONE_WIRE ? blockState_1.get(RedstoneWireBlock.POWER) : viewableWorld_1.getEmittedStrongRedstonePower(blockPos_1, direction_1);
                BlueStoneMain.powerAskingBlock = null;
                return i;
            }
        } else {
            return 0;
        }
    }

    @Override
    protected int getPower(World world_1, BlockPos blockPos_1, BlockState blockState_1) {
        if (world_1.getBlockState(blockPos_1.offset(blockState_1.get(FACING))).getBlock() == Blocks.REDSTONE_WIRE) return 0;
        BlueStoneMain.powerAskingBlock = this;
        int int_1 = super.getPower(world_1, blockPos_1, blockState_1);
        BlueStoneMain.powerAskingBlock = null;
        return int_1;
    }
}
