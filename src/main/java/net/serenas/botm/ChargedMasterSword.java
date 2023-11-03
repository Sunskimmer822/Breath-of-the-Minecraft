package net.serenas.botm;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ChargedMasterSword extends SwordItem {

    public ChargedMasterSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
	    super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        if (playerEntity.getHealth() == playerEntity.getMaxHealth()) {
            //master sword beam
            //MasterSwordBeamEntity beamEntity = new MasterSwordBeamEntity(world, owner, posvec3d)
            MasterSwordBeamEntity beamEntity = new MasterSwordBeamEntity(BotM.MASTER_SWORD_BEAM_ENTITY, world);
            beamEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), playerEntity.getRoll(), 20f, 2f);
            beamEntity.setPosition(playerEntity.getPos());
            world.spawnEntity(beamEntity);
            playerEntity.getItemCooldownManager().set(this, 40);
        }
        return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));
    }
}