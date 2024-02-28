package net.serenas.botm;

import java.lang.reflect.Array;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class BotM implements ModInitializer{

    public static Integer iterator;

    public static final Logger LOGGER = LoggerFactory.getLogger("BotM");

    public static final Item TRAVELERS_BOW = new TravelersBow(new FabricItemSettings().maxCount(1).maxDamage(64).rarity(Rarity.COMMON));

    public static final Item MIPHAS_GRACE = new MiphasGrace(new Item.Settings().fireproof().maxCount(1).rarity(Rarity.EPIC));

    public static final Item MIPHAS_GRACE_USED = new MiphasGraceUsed(new Item.Settings().fireproof().maxCount(1).rarity(Rarity.EPIC));

    public static final Identifier MIPHAS_GRACE_READY_SOUND_IDENTIFIER = new Identifier("botm:miphas_grace_ready");
    public static final SoundEvent MIPHAS_GRACE_READY_SOUND = SoundEvent.of(MIPHAS_GRACE_READY_SOUND_IDENTIFIER);

    public static final DefaultParticleType BLUE_FLAME = FabricParticleTypes.simple();

    public static final Item MASTER_STEEL_INGOT = new MasterSteelIngot(new Item.Settings().maxCount(64).fireproof());

    public static final SwordItem CHARGED_MASTER_SWORD = new ChargedMasterSword(MasterSteelMaterial.INSTANCE, 5, 5f, new Item.Settings().maxDamage(2000).fireproof().rarity(Rarity.EPIC));

    public static final SwordItem UNCHARGED_MASTER_SWORD = new UnchargedMasterSword(MasterSteelMaterial.INSTANCE, -5, -3f, new Item.Settings().fireproof().rarity(Rarity.RARE));

    public static final Item ROD_OF_HYLIA = new RodOfHylia(new Item.Settings().fireproof().maxCount(64));

    static ItemStack[] botwItems = {
        BotM.TRAVELERS_BOW.getDefaultStack(),
        BotM.CHARGED_MASTER_SWORD.getDefaultStack(),
        BotM.UNCHARGED_MASTER_SWORD.getDefaultStack(),
        BotM.MIPHAS_GRACE.getDefaultStack(),
        BotM.MIPHAS_GRACE_USED.getDefaultStack()
    };
    
    public static final ItemGroup BOTW_MOD_GROUP = FabricItemGroup.builder()
        .displayName(Text.translatable("itemGroup.botm.botw_group"))
        .icon(() -> new ItemStack(BotM.CHARGED_MASTER_SWORD))
        .entries((content, entries) -> {
            for (iterator = 0; iterator < Array.getLength(botwItems); iterator++) {
                entries.add((ItemStack) Array.get(botwItems, iterator));
            }
        })
        .build();


    static ItemStack[] botwMaterials = {
        BotM.ROD_OF_HYLIA.getDefaultStack(),
        BotM.MASTER_STEEL_INGOT.getDefaultStack()
    };

    public static final ItemGroup BOTW_MATERIAL_GROUP = FabricItemGroup.builder()
        .displayName(Text.translatable("itemGroup.botm.botw_materials_group"))
        .icon(() -> new ItemStack(BotM.MASTER_STEEL_INGOT))
        .entries((content, entries) -> {
            for (iterator = 0; iterator < Array.getLength(botwMaterials); iterator++) {
                entries.add((ItemStack) Array.get(botwMaterials, iterator));
            }
        })
        .build();
    


    @Override
    public void onInitialize() {

        LOGGER.info("BotM began initialization.");

        Registry.register(Registries.ITEM_GROUP, new Identifier("botm", "botw_group"), BOTW_MOD_GROUP);

        Registry.register(Registries.ITEM_GROUP, new Identifier("botm", "botw_materials_group"), BOTW_MATERIAL_GROUP);

        Registry.register(Registries.ITEM, new Identifier("botm", "travelers_bow"), TRAVELERS_BOW);

        Registry.register(Registries.ITEM, new Identifier("botm", "miphas_grace"), MIPHAS_GRACE);

        Registry.register(Registries.ITEM, new Identifier("botm", "miphas_grace_used"), MIPHAS_GRACE_USED);

        Registry.register(Registries.PARTICLE_TYPE, new Identifier("botm", "blue_flame"), BLUE_FLAME);

        Registry.register(Registries.ITEM, new Identifier("botm", "charged_master_sword"), CHARGED_MASTER_SWORD);

        Registry.register(Registries.ITEM, new Identifier("botm", "uncharged_master_sword"), UNCHARGED_MASTER_SWORD);

        Registry.register(Registries.ITEM, new Identifier("botm", "master_steel_ingot"), MASTER_STEEL_INGOT);

        Registry.register(Registries.ITEM, new Identifier("botm", "rod_of_hylia"), ROD_OF_HYLIA);





        

        ServerTickEvents.END_WORLD_TICK.register((world) -> {

            List<ServerPlayerEntity> serverPlayers = world.getPlayers();

            serverPlayers.forEach((player) -> {

                

            });

            ChampionAbilityHelper.doRechargeAbilitiesTick(serverPlayers);

        });

        LOGGER.info("BotM successfully initialized.");
    }
}
