package net.serenas.botm;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class UnchargedMasterSword extends SwordItem {

    public UnchargedMasterSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.botm.generic_master_sword.tooltip_one").formatted(Formatting.RED));
        tooltip.add(Text.translatable("item.botm.uncharged_master_sword.tooltip_two").formatted(Formatting.RED));
    }
    
}
