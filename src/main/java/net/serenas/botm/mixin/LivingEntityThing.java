package net.serenas.botm.mixin;

import org.spongepowered.asm.mixin.Mixin;
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
import net.minecraft.world.World;
import net.serenas.botm.BotM;

@Mixin(LivingEntity.class)
public class LivingEntityThing {

    public ItemStack itemStack;

    @Inject(at = @At("RETURN"), method = "tryUseTotem", cancellable = true)
    private void tryUseTotem(DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
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
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 10, 5));
                    inventory.setStack(i, BotM.MIPHAS_GRACE_USED.getDefaultStack());
                    //World world = livingEntity.getWorld();
                    World world = playerEntity.getWorld();
                    
                    //particles [sand nuts dealer]

                    //not working? look into this
                    if (world.isClient()) {
                        for (int o=0;o<360;o++) {
                            world.addParticle(BotM.BLUE_FLAME, true, playerEntity.getX()+Math.sin(o), playerEntity.getEyeY(), playerEntity.getZ()+Math.cos(o), 0, 0, 0);
                        }
                    }

                    
                    
                    // world.addParticle(BotM.BLUE_FLAME, livingEntity.getX()+0.5, livingEntity.getY(), livingEntity.getZ(), 0, 0, 0);
                    // world.addParticle(BotM.BLUE_FLAME, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ()+0.5, 0, 0, 0);
                    // world.addParticle(BotM.BLUE_FLAME, livingEntity.getX()-0.5, livingEntity.getY(), livingEntity.getZ(), 0, 0, 0);
                    // world.addParticle(BotM.BLUE_FLAME, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ()-0.5, 0, 0, 0);
                    // world.addParticle(BotM.BLUE_FLAME, livingEntity.getX()+0.353553390593274, livingEntity.getY(), livingEntity.getZ()+0.353553390593274, 0, 0, 0);
                    // world.addParticle(BotM.BLUE_FLAME, livingEntity.getX()+0.353553390593274, livingEntity.getY(), livingEntity.getZ()-0.353553390593274, 0, 0, 0);
                    // world.addParticle(BotM.BLUE_FLAME, livingEntity.getX()-0.353553390593274, livingEntity.getY(), livingEntity.getZ()-0.353553390593274, 0, 0, 0);
                    // world.addParticle(BotM.BLUE_FLAME, livingEntity.getX()-0.353553390593274, livingEntity.getY(), livingEntity.getZ()+0.353553390593274, 0, 0, 0);
                    // world.addParticle(BotM.BLUE_FLAME, livingEntity.getX()+0.43301, livingEntity.getY(), livingEntity.getZ()+0.25, 0, 0, 0);
                    // world.addParticle(BotM.BLUE_FLAME, livingEntity.getX()+0.25, livingEntity.getY(), livingEntity.getZ()+0.43301, 0, 0, 0);
                    // world.addParticle(BotM.BLUE_FLAME, livingEntity.getX()-0.25, livingEntity.getY(), livingEntity.getZ()+0.43301, 0, 0, 0);
                    // world.addParticle(BotM.BLUE_FLAME, livingEntity.getX()-0.43301, livingEntity.getY(), livingEntity.getZ()+0.25, 0, 0, 0);
                    // world.addParticle(BotM.BLUE_FLAME, livingEntity.getX()-0.43301, livingEntity.getY(), livingEntity.getZ()-0.25, 0, 0, 0);
                    // world.addParticle(BotM.BLUE_FLAME, livingEntity.getX()-0.25, livingEntity.getY(), livingEntity.getZ()-0.43301, 0, 0, 0);
                    // world.addParticle(BotM.BLUE_FLAME, livingEntity.getX()+0.25, livingEntity.getY(), livingEntity.getZ()-0.43301, 0, 0, 0);
                    // world.addParticle(BotM.BLUE_FLAME, livingEntity.getX()+0.43301, livingEntity.getY(), livingEntity.getZ()-0.25, 0, 0, 0);



                    info.setReturnValue(true);
                break;
                } 

            }

        }

    }

}
