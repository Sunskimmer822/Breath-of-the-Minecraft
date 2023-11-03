package net.serenas.botm;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class MasterSteelMaterial implements ToolMaterial{

    public static final MasterSteelMaterial INSTANCE = new MasterSteelMaterial();

	@Override
	public int getDurability() {
		return 1000;
	}

	@Override
	public float getMiningSpeedMultiplier() {
		return 5f;
	}

	@Override
	public float getAttackDamage() {
		return 12f;
	}

	@Override
	public int getMiningLevel() {
		return 9;
	}

	@Override
	public int getEnchantability() {
		return 40;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return null;
	}
    
}
