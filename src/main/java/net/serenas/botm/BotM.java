package net.serenas.botm;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class BotM implements ModInitializer{

    public static final Logger LOGGER = LoggerFactory.getLogger("BotM");

    public static final Item TRAVELERS_BOW = new TravelersBow(new FabricItemSettings().maxCount(1).maxDamage(64).rarity(Rarity.COMMON));

    public static final Item MIPHAS_GRACE = new MiphasGrace(new Item.Settings().fireproof().maxCount(1).rarity(Rarity.EPIC));

    public static final Item MIPHAS_GRACE_USED = new MiphasGraceUsed(new Item.Settings().fireproof().maxCount(1).rarity(Rarity.EPIC));

    @Override
    public void onInitialize() {

        LOGGER.info("BotM began initialization.");

        Registry.register(Registries.ITEM, new Identifier("botm", "travelers_bow"), TRAVELERS_BOW);

        Registry.register(Registries.ITEM, new Identifier("botm", "miphas_grace"), MIPHAS_GRACE);

        Registry.register(Registries.ITEM, new Identifier("botm", "miphas_grace_used"), MIPHAS_GRACE_USED);



        ServerTickEvents.END_WORLD_TICK.register((world) -> {
            List<ServerPlayerEntity> players = world.getPlayers();
            Integer numPlayers = players.size();
            Integer e;
            if (numPlayers == 0) return;
            for (e = 0; e < numPlayers; e++) {
                ServerPlayerEntity serverPlayerEntity = players.get(e);
                PlayerInventory inventory = serverPlayerEntity.getInventory();
                Integer inventorySize = inventory.size();
                Integer f;
                for (f = 0; f< inventorySize; f++) {
                    ItemStack stack = inventory.getStack(f);
                    if (stack.isOf(BotM.MIPHAS_GRACE_USED)) {
                        NbtCompound nbt = stack.getOrCreateNbt();
                        if (nbt.getInt("rechargeTicks") == 9000) {
                            inventory.setStack(f, BotM.MIPHAS_GRACE.getDefaultStack());
                            LOGGER.info("Mipha's Grace is now ready for "+serverPlayerEntity.getEntityName());
                            break;
                        }
                        if (!nbt.contains("rechargeTicks")) {
                            nbt.putInt("rechargeTicks", 0);
                        } else {
                            Integer currentTicks = nbt.getInt("rechargeTicks");
                            currentTicks++;
                            nbt.remove("rechargeTicks");
                            nbt.putInt("rechargeTicks", currentTicks);
                        }
                    }
                }
            }
        });

        LOGGER.info("BotM successfully initialized.");
    }
}