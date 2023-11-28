package net.serenas.botm.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.serenas.botm.BotM;
import net.minecraft.entity.damage.DamageSource;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(at = @At("TAIL"), method = "onDeath")
    public void onDeath(DamageSource damageSource, CallbackInfo cin) {

        ( (PlayerEntity) (Object) this ).resetStat(Stats.CUSTOM.getOrCreateStat(BotM.SNEAK_TICKS)); 

    }

    @Inject(at = @At("HEAD"), method = "jump")
    public void jump(CallbackInfo cin) {
        PlayerEntity player = ((PlayerEntity)(Object)this);
        if (!player.isSpectator()) {
            if (Integer.parseInt(Stats.CUSTOM.getOrCreateStat(BotM.SNEAK_TICKS).getValue().toString()) > 20) {

            }
        }
    }
}
