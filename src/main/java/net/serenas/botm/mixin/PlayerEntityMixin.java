package net.serenas.botm.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(at = @At("TAIL"), method = "onDeath")
    public void onDeath() {

        ( (PlayerEntity) (Object) this ).resetStat(BotM.SNEAK_TICKS); 

    }
}
