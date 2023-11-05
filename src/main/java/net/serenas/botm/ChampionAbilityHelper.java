package net.serenas.botm;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class ChampionAbilityHelper {

    public static void forceAbilityRecharge(PlayerEntity player, Item ability) {
        List<ItemStack> stacks = getAbilityStacks(player, ability);
        stacks.forEach((stack) -> {
            stack = ability.getDefaultStack();
        });
    }

    private static List<ItemStack> getAbilityStacks(PlayerEntity player, Item ability) {
        List<ItemStack> stacks = new ArrayList<ItemStack>();
        PlayerInventory inventory = player.getInventory();
        for (int f = 0; f< inventory.size(); f++) {
            ItemStack stack = inventory.getStack(f);
            if (stack.isOf(ability)) {
                stacks.add(stack);
            }
        }
        return stacks;
    }

    public static void doRechargeAbilitiesTick(List<ServerPlayerEntity> players) {
        Item[] abilities = {BotM.MIPHAS_GRACE};
        Item[] usedAbilities = {BotM.MIPHAS_GRACE_USED};
        for (int i = 0;i<1;i++) { // CHANGE WHEN ADDING NEW ABILITIES
            final int o = i;
            players.forEach((player) -> {
                List<ItemStack> abilityStacks = getAbilityStacks((PlayerEntity)(Object)player, usedAbilities[o]);
                abilityStacks.forEach((stack) -> {

                    NbtCompound nbt = stack.getOrCreateNbt();
                    if (nbt.getInt("rechargeTicks") == getAbilityRechargeTimeInTicks(o)) {
                        //stack = abilities[o].getDefaultStack(); 
                        player.getInventory().setStack(player.getInventory().getSlotWithStack(stack), abilities[o].getDefaultStack());
                        player.playSound(getAbilityReadySound(o), SoundCategory.PLAYERS, 1f, 1f);
                        BotM.LOGGER.info(abilities[o].getName().toString()+" is now ready for "+player.getEntityName());
                        return;
                    } else if (!nbt.contains("rechargeTicks")) {
                        nbt.putInt("rechargeTicks", 0);
                    } else {
                        Integer currentTicks = nbt.getInt("rechargeTicks");
                        currentTicks++;
                        nbt.remove("rechargeTicks");
                        nbt.putInt("rechargeTicks", currentTicks);
                    }
                });
            });
        }
    }

    private static SoundEvent getAbilityReadySound(int ability) {
        switch(ability) {
            case 0:
                return BotM.MIPHAS_GRACE_READY_SOUND;
            case 1:
                return null; //revali's gale
            case 2:
                return null; //daruk's protection 
            case 3:
                return null; //urbosa's fury
            default:
                return null;
        }
    }

    private static int getAbilityRechargeTimeInTicks(int ability) {
        switch(ability) {
            case 0:
                return 24000;
            case 1:
                return 6000;
            case 2:
                return 18000;
            case 3:
                return 12000;
            default:
                return -1;
        }
    }
}