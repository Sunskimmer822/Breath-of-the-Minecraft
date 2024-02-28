package net.serenas.botm.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.PlayerEntity;
import net.serenas.botm.BotM;
import net.minecraft.entity.damage.DamageSource;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(at = @At("TAIL"), method = "onDeath")
    public void onDeath(DamageSource damageSource, CallbackInfo cin) {

        ( (PlayerEntity) (Object) this ).addCommandTag("hi"); 

    }

    @Inject(at = @At("HEAD"), method = "jump")
    public void jump(CallbackInfo cin) {
        PlayerEntity player = ((PlayerEntity)(Object)this);
        if (!player.isSpectator()) {
            if (BotM.onlinePlayerSneakTicksMap.get(player) >= 80) {
                //TODO: Apply levitation and test until it feels right. after levitation, apply slow fall for a couple of seconds to allow easier paraglider/elytra activation
            }
        }
    }
}
