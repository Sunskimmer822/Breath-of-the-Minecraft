package net.serenas.botm;

import java.lang.reflect.Array;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
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

    public static final EntityType<? extends ExplosiveProjectileEntity> MASTER_SWORD_BEAM_ENTITY = Registry.register(Registries.ENTITY_TYPE, new Identifier("botm", "master_sword_beam"), FabricEntityTypeBuilder.create().entityFactory(MasterSwordBeamEntity::new).build());

    public static final DefaultParticleType BLUE_FLAME = FabricParticleTypes.simple();

    public static final Item MASTER_STEEL_INGOT = new MasterSteelIngot(new Item.Settings().maxCount(64).fireproof());

    public static final SwordItem CHARGED_MASTER_SWORD = new ChargedMasterSword(MasterSteelMaterial.INSTANCE, 5, 5f, new Item.Settings().maxDamage(2000).fireproof().rarity(Rarity.EPIC));

    public static final SwordItem UNCHARGED_MASTER_SWORD = new UnchargedMasterSword(MasterSteelMaterial.INSTANCE, -5, -3f, new Item.Settings().fireproof().rarity(Rarity.RARE));

    public static final Item ROD_OF_HYLIA = new RodOfHylia(new Item.Settings().fireproof().maxCount(64));



    static ItemStack[] botwItems = {BotM.TRAVELERS_BOW.getDefaultStack(),BotM.MIPHAS_GRACE.getDefaultStack(),BotM.MIPHAS_GRACE_USED.getDefaultStack(), BotM.MASTER_STEEL_INGOT.getDefaultStack(), BotM.CHARGED_MASTER_SWORD.getDefaultStack(), BotM.UNCHARGED_MASTER_SWORD.getDefaultStack(), BotM.ROD_OF_HYLIA.getDefaultStack()};
    
    public static final ItemGroup BOTW_MOD_GROUP = FabricItemGroup.builder()
        .displayName(Text.translatable("itemGroup.botm.botw_group"))
        .icon(() -> new ItemStack(BotM.CHARGED_MASTER_SWORD))
        .entries((content, entries) -> {
            for (iterator = 0; iterator < Array.getLength(botwItems); iterator++) {
                entries.add((ItemStack) Array.get(botwItems, iterator));
            }
        })
        .build();


    @Override
    public void onInitialize() {

        LOGGER.info("BotM began initialization.");

        Registry.register(Registries.ITEM, new Identifier("botm", "travelers_bow"), TRAVELERS_BOW);

        Registry.register(Registries.ITEM, new Identifier("botm", "miphas_grace"), MIPHAS_GRACE);

        Registry.register(Registries.ITEM, new Identifier("botm", "miphas_grace_used"), MIPHAS_GRACE_USED);

        //Registry.register(Registries.ENTITY_TYPE, new Identifier("botm", "master_sword_beam"), MASTER_SWORD_BEAM_ENTITY);

        Registry.register(Registries.PARTICLE_TYPE, new Identifier("botm", "blue_flame"), BLUE_FLAME);

        Registry.register(Registries.ITEM, new Identifier("botm", "charged_master_sword"), CHARGED_MASTER_SWORD);

        Registry.register(Registries.ITEM, new Identifier("botm", "uncharged_master_sword"), UNCHARGED_MASTER_SWORD);

        Registry.register(Registries.ITEM, new Identifier("botm", "master_steel_ingot"), MASTER_STEEL_INGOT);

        Registry.register(Registries.ITEM_GROUP, new Identifier("botm", "botw_mod_group"), BOTW_MOD_GROUP);

        Registry.register(Registries.ITEM, new Identifier("botm", "rod_of_hylia"), ROD_OF_HYLIA);

        


        ServerTickEvents.END_WORLD_TICK.register((world) -> {

            //MIPHA'S GRACE
            //Gets list of all online players, iterates over said players and iterates over every one of their slots. If an inactive 
            //Mipha's Grace is found in any inventory it will first check if the item is at the threshold for being recharged, 
            //otherwise it will ensure the tag is present and change the tag to include the new value every tick.
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



                    //recharge Mipha's grace
                    if (stack.isOf(BotM.MIPHAS_GRACE_USED)) {
                        NbtCompound nbt = stack.getOrCreateNbt();
                        if (nbt.getInt("rechargeTicks") == 12000) {
                            inventory.setStack(f, BotM.MIPHAS_GRACE.getDefaultStack());
                            serverPlayerEntity.playSound(MIPHAS_GRACE_READY_SOUND, SoundCategory.PLAYERS, 10f, 1f);
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

                    //recharge master sword



                }

            }


        });

        LOGGER.info("BotM successfully initialized.");
    }
}