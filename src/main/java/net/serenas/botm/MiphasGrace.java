package net.serenas.botm;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MiphasGrace extends Item{

    public MiphasGrace(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
    
}
