package net.serenas.botm.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.serenas.botm.BotM;

@Mixin(LivingEntity.class)
public class LivingEntityThing {

    public ItemStack itemStack;

    @Inject(at = @At("RETURN"), method = "tryUseTotem")
    private void tryUseTotem(DamageSource damageSource, CallbackInfoReturnable info) {
        LivingEntity livingEntity = ((LivingEntity) (Object) this);
        float maxHP = livingEntity.getMaxHealth();
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerEntity = ((PlayerEntity) (Object) this);
            PlayerInventory inventory = playerEntity.getInventory();
            int i;
            for (i = 0; i<inventory.size();i++) {
                itemStack = inventory.getStack(i);
                if (itemStack.isOf(BotM.MIPHAS_GRACE)) {
                    ServerPlayerEntity lv4 = (ServerPlayerEntity)livingEntity;
                    lv4.incrementStat(Stats.USED.getOrCreateStat(BotM.MIPHAS_GRACE));
                    livingEntity.setHealth(maxHP);
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 300, 3));
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 800, 1));
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 300, 1));
                    itemStack.damage(1, playerEntity.getRandom(), (ServerPlayerEntity)playerEntity);
                break;
                } 
                else itemStack = null;
            }

        }

    }

}
