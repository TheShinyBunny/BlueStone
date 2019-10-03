package com.shinybunny.bluestone.block;

import com.shinybunny.bluestone.BlueStoneMain;
import net.minecraft.block.*;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class BluestoneWireBlock extends RedstoneWireBlock {

    public BluestoneWireBlock() {
        super(Settings.of(Material.PART).noCollision().strength(0.0f,0.0f));
    }

    public static boolean hasConnection(BlockView world, BlockPos pos, Direction dir) {
        BlockPos pos2 = pos.offset(dir);
        BlockState state = world.getBlockState(pos2);
        boolean boolean_1 = state.isSimpleFullBlock(world, pos2);
        BlockPos pos3 = pos.up();
        boolean boolean_2 = world.getBlockState(pos3).isSimpleFullBlock(world, pos3);
        if (!boolean_2 && boolean_1 && connectsTo(world, pos2.up())) {
            return true;
        } else if (blueConnectsTo(state, dir)) {
            return true;
        } else if (state.getBlock() == BlueStoneMain.REPEATER && state.get(AbstractRedstoneGateBlock.POWERED) && state.get(AbstractRedstoneGateBlock.FACING) == dir) {
            return true;
        } else {
            return !boolean_1 && blueConnectsTo(world.getBlockState(pos.down()),null);
        }
    }

    private static boolean blueConnectsTo(BlockState state, Direction dir) {
        Block block_1 = state.getBlock();
        if (block_1 == BlueStoneMain.BLUESTONE_WIRE) {
            return true;
        } else if (!BlueStoneMain.areSameRedstoneSystems(BlueStoneMain.BLUESTONE_WIRE,block_1)) {
            return false;
        } else if (state.getBlock() == BlueStoneMain.REPEATER) {
            Direction direction_2 = state.get(RepeaterBlock.FACING);
            return direction_2 == dir || direction_2.getOpposite() == dir;
        } else if (Blocks.OBSERVER == state.getBlock()) {
            return dir == state.get(ObserverBlock.FACING);
        } else {
            return state.emitsRedstonePower() && dir != null;
        }
    }

    public WireConnection getConnectionType(BlockView blockView_1, BlockPos blockPos_1, Direction direction_1) {
        BlockPos blockPos_2 = blockPos_1.offset(direction_1);
        BlockState blockState_1 = blockView_1.getBlockState(blockPos_2);
        BlockPos blockPos_3 = blockPos_1.up();
        BlockState blockState_2 = blockView_1.getBlockState(blockPos_3);
        if (!blockState_2.isSimpleFullBlock(blockView_1, blockPos_3)) {
            boolean boolean_1 = blockState_1.isSideSolidFullSquare(blockView_1, blockPos_2, Direction.UP) || blockState_1.getBlock() == Blocks.HOPPER;
            if (boolean_1 && blueConnectsTo(blockView_1.getBlockState(blockPos_2.up()),null)) {
                if (blockState_1.method_21743(blockView_1, blockPos_2)) {
                    return WireConnection.UP;
                }

                return WireConnection.SIDE;
            }
        }

        return !blueConnectsTo(blockState_1, direction_1) && (blockState_1.isSimpleFullBlock(blockView_1, blockPos_2) || !blueConnectsTo(blockView_1.getBlockState(blockPos_2.down()),null)) ? WireConnection.NONE : WireConnection.SIDE;
    }


    @Override
    public void randomDisplayTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
        int int_1 = blockState_1.get(POWER);
        if (int_1 != 0) {
            double double_1 = (double)blockPos_1.getX() + 0.5D + ((double)random_1.nextFloat() - 0.5D) * 0.2D;
            double double_2 = (double)((float)blockPos_1.getY() + 0.0625F);
            double double_3 = (double)blockPos_1.getZ() + 0.5D + ((double)random_1.nextFloat() - 0.5D) * 0.2D;
            float float_1 = (float)int_1 / 15.0F;
            float float_2 = float_1 * 0.6F + 0.4F;
            float float_3 = Math.max(0.0F, float_1 * float_1 * 0.7F - 0.5F);
            float float_4 = Math.max(0.0F, float_1 * float_1 * 0.6F - 0.7F);
            world_1.addParticle(new DustParticleEffect(float_4, float_3, float_2, 1.0F), double_1, double_2, double_3, 0.0D, 0.0D, 0.0D);
        }
    }
}
