package com.shinybunny.bluestone.mixin;

import com.shinybunny.bluestone.BlueStoneMain;
import com.shinybunny.bluestone.block.BluestoneWireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;

@Mixin(RedstoneWireBlock.class)
public abstract class MixinRedstoneWire extends Block {

    @Shadow private boolean wiresGivePower;
    private boolean futureBluestone;

    private static Field wgpField = RedstoneWireBlock.class.getDeclaredFields()[7];

    public MixinRedstoneWire(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    @Inject(method = "connectsTo(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z", at = @At("HEAD"),cancellable = true)
    private static void connectsTo(BlockState blockState_1, Direction direction_1, CallbackInfoReturnable<Boolean> cir) {
        if (BlueStoneMain.BLUESTONE_COMPONENTS.contains(blockState_1.getBlock())) cir.setReturnValue(false);
    }

    @Inject(method = "method_10478",at = @At("HEAD"),cancellable = true)
    private void hasConnection(BlockView blockView_1, BlockPos blockPos_1, Direction direction_1, CallbackInfoReturnable<Boolean> cir) {
        BlockState state = blockView_1.getBlockState(blockPos_1);
        if (state.getBlock() == BlueStoneMain.BLUESTONE_WIRE) {
            cir.setReturnValue(BluestoneWireBlock.hasConnection(blockView_1,blockPos_1,direction_1));
        }
    }

    @Inject(method = "getPlacementState",at = @At("HEAD"))
    private void getPlacementState(ItemPlacementContext itemPlacementContext_1, CallbackInfoReturnable<BlockState> cir) {
        if (itemPlacementContext_1.getStack().getItem() == BlueStoneMain.BLUESTONE_WIRE.asItem()) {
            this.futureBluestone = true;
        }
    }

    @Inject(method = "getPlacementState",at = @At("RETURN"))
    private void getPlacementStateReturn(ItemPlacementContext itemPlacementContext_1, CallbackInfoReturnable<BlockState> cir) {
        futureBluestone = false;
    }

    @Inject(method = "getRenderConnectionType",at = @At("HEAD"),cancellable = true)
    private void getRenderConnectionType(BlockView blockView_1, BlockPos blockPos_1, Direction direction_1, CallbackInfoReturnable<WireConnection> cir) {
        BlockState state = blockView_1.getBlockState(blockPos_1);
        if (state.getBlock() == BlueStoneMain.BLUESTONE_WIRE || futureBluestone) {
            WireConnection conn = BlueStoneMain.BLUESTONE_WIRE.getConnectionType(blockView_1,blockPos_1,direction_1);
            cir.setReturnValue(conn);
        }
    }

    @Inject(method = "increasePower",at = @At("HEAD"),cancellable = true)
    private void increasePower(int int_1, BlockState blockState_1, CallbackInfoReturnable<Integer> cir) {
        if (!BlueStoneMain.areSameRedstoneSystems(((BlockItem)asItem()).getBlock(),blockState_1.getBlock())) {
            cir.setReturnValue(int_1);
        }
    }

    @Inject(method = "updateLogic", at = @At(value = "FIELD",target = "Lnet/minecraft/block/RedstoneWireBlock;wiresGivePower:Z",opcode = Opcodes.PUTFIELD,shift = At.Shift.AFTER))
    private void onWiresGivePowerChange(World world_1, BlockPos blockPos_1, BlockState blockState_1, CallbackInfoReturnable<BlockState> cir) {
        try {
            wgpField.setAccessible(true);
            wgpField.set(BlueStoneMain.BLUESTONE_WIRE,wiresGivePower);
            wgpField.set(Blocks.REDSTONE_WIRE,wiresGivePower);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
