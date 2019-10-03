package com.shinybunny.bluestone;

import com.shinybunny.bluestone.block.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.impl.event.InteractionEventsRouter;
import net.minecraft.block.*;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ExtendedBlockView;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class BlueStoneMain implements ModInitializer, ClientModInitializer {

    public static final BluestoneWireBlock BLUESTONE_WIRE = new BluestoneWireBlock();
    public static final BluestoneRepeaterBlock REPEATER = new BluestoneRepeaterBlock();
    public static final BluestoneComparatorBlock COMPARATOR = new BluestoneComparatorBlock();
    public static final BluestoneTorchBlock BLUESTONE_TORCH = new BluestoneTorchBlock();
    public static final BluestoneWallTorchBlock BLUESTONE_TORCH_WALL = new BluestoneWallTorchBlock();
    public static final BluestoneBlock BLUESTONE_BLOCK = new BluestoneBlock();

    public static List<Block> BLUESTONE_COMPONENTS = Arrays.asList(BLUESTONE_WIRE, REPEATER, COMPARATOR, BLUESTONE_TORCH, BLUESTONE_BLOCK, BLUESTONE_TORCH_WALL);
    private static List<Block> REDSTONE_COMPONENTS = Arrays.asList(Blocks.REDSTONE_WIRE,Blocks.REPEATER,Blocks.COMPARATOR,Blocks.REDSTONE_TORCH, Blocks.REDSTONE_BLOCK, Blocks.REDSTONE_WALL_TORCH);

    public static boolean areSameRedstoneSystems(Block block1, Block block2) {
        if (block1 == block2) return true;
        if (BLUESTONE_COMPONENTS.contains(block1) && REDSTONE_COMPONENTS.contains(block2)) return false;
        if (REDSTONE_COMPONENTS.contains(block1) && BLUESTONE_COMPONENTS.contains(block2)) return false;
        return true;
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK,new Identifier("bluestone","bluestone_wire"), BLUESTONE_WIRE);
        Registry.register(Registry.ITEM,new Identifier("bluestone","bluestone"),new BlockItem(BLUESTONE_WIRE,new Item.Settings().group(ItemGroup.REDSTONE)));

        Registry.register(Registry.BLOCK,new Identifier("bluestone","repeater"), REPEATER);
        Registry.register(Registry.ITEM,new Identifier("bluestone","repeater"),new BlockItem(REPEATER,new Item.Settings().group(ItemGroup.REDSTONE)));

        Registry.register(Registry.BLOCK,new Identifier("bluestone","comparator"), COMPARATOR);
        Registry.register(Registry.ITEM,new Identifier("bluestone","comparator"),new BlockItem(COMPARATOR,new Item.Settings().group(ItemGroup.REDSTONE)));

        Registry.register(Registry.BLOCK,new Identifier("bluestone","bluestone_torch"), BLUESTONE_TORCH);
        Registry.register(Registry.BLOCK,new Identifier("bluestone","bluestone_wall_torch"), BLUESTONE_TORCH_WALL);
        Registry.register(Registry.ITEM,new Identifier("bluestone","bluestone_torch"),new WallStandingBlockItem(BLUESTONE_TORCH,BLUESTONE_TORCH_WALL,new Item.Settings().group(ItemGroup.REDSTONE)));

        Registry.register(Registry.BLOCK,new Identifier("bluestone","bluestone_block"), BLUESTONE_BLOCK);
        Registry.register(Registry.ITEM,new Identifier("bluestone","bluestone_block"),new BlockItem(BLUESTONE_BLOCK,new Item.Settings().group(ItemGroup.REDSTONE)));


        UseBlockCallback.EVENT.register(new UseBlockCallback() {
            @Override
            public ActionResult interact(PlayerEntity playerEntity, World world, Hand hand, BlockHitResult blockHitResult) {
                Block b = world.getBlockState(blockHitResult.getBlockPos()).getBlock();
                if (b == Blocks.REDSTONE_WIRE && playerEntity.getStackInHand(hand).getItem() == Items.LAPIS_LAZULI) {
                    world.setBlockState(blockHitResult.getBlockPos(),BLUESTONE_WIRE.getDefaultState(),3);
                    return ActionResult.SUCCESS;
                }
                return ActionResult.PASS;
            }
        });
    }

    public static Block powerAskingBlock;

    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register(new BlockColorProvider() {
            @Override
            public int getColor(BlockState blockState, ExtendedBlockView extendedBlockView, BlockPos blockPos, int i) {
                int power = blockState.get(BluestoneWireBlock.POWER);
                float float_1 = (float)power / 15.0F;
                float float_2 = float_1 * 0.6F + 0.4F;
                if (power == 0) {
                    float_2 = 0.3F;
                }

                float float_3 = float_1 * float_1 * 0.7F - 0.5F;
                float float_4 = float_1 * float_1 * 0.6F - 0.7F;
                if (float_3 < 0.0F) {
                    float_3 = 0.0F;
                }

                if (float_4 < 0.0F) {
                    float_4 = 0.0F;
                }

                int int_2 = MathHelper.clamp((int)(float_4 * 255.0F), 0, 255);
                int int_3 = MathHelper.clamp((int)(float_3 * 255.0F), 0, 255);
                int int_4 = MathHelper.clamp((int)(float_2 * 255.0F), 0, 255);
                return -16777216 | int_2 << 16 | int_3 << 8 | int_4;
            }
        }, BLUESTONE_WIRE);
    }
}
