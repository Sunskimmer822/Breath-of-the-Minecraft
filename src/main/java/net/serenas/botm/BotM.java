package net.serenas.botm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class BotM implements ModInitializer{

    public static final Logger LOGGER = LoggerFactory.getLogger("BotM");

    public static final Item TRAVELERS_BOW = new TravelersBow(new FabricItemSettings().maxCount(1).maxDamage(64).rarity(Rarity.COMMON));

    public static final Item MIPHAS_GRACE = new MiphasGrace(new Item.Settings().fireproof().maxCount(1).rarity(Rarity.EPIC).maxDamage(128));

    @Override
    public void onInitialize() {

        LOGGER.info("BotM began initialization.");

        Registry.register(Registries.ITEM, new Identifier("botm", "travelers_bow"), TRAVELERS_BOW);

        Registry.register(Registries.ITEM, new Identifier("botm", "miphas_grace"), MIPHAS_GRACE);





        LOGGER.info("BotM successfully initialized.");
    }
}