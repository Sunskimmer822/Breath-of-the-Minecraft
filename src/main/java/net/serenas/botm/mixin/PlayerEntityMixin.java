package net.serenas.botm.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    public int sneakTicks = 0;

    @Inject
    //inject into onDeath to reset sneakTicks, should I use a statistic instead? either way, reset the number of ticks the player has been crouching for.
}
