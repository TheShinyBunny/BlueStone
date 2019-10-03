package com.shinybunny.bluestone.block;

import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;

public class BluestoneBlock extends RedstoneBlock {
    public BluestoneBlock() {
        super(Settings.of(Material.METAL, MaterialColor.LAVA).strength(5.0F, 6.0F));
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState blockState_1) {
        return BlockSoundGroup.METAL;
    }
}
