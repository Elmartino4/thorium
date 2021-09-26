package github.Elmartino4.thorium.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;

public class TieredNetheriteIngot extends Item {
    public TieredNetheriteIngot(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack){
        return tier(stack) >= 2;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        switch (tier(stack)){
            case 2:
                return Rarity.RARE;
            case 3:
                return Rarity.EPIC;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public boolean isFireproof(){
        return true;
    }

    public byte tier(ItemStack stack){
        byte tier = stack.getOrCreateNbt().getByte("tier");
        return tier;
    }

    public static ItemStack putQuality(ItemStack stack, double quality){
        byte tier = 0;
        if(quality < 120){
            tier = 1;
        }else if(quality < 240){
            tier = 2;
        }else if(quality < 480){
            tier = 3;
        }else{
            tier = 4;
        }

        stack.getOrCreateNbt().putByte("tier", tier);
        //stack.getOrCreateNbt().putInt("custom_model_data", tier);
        stack.getOrCreateNbt().putDouble("quality", quality);
        return stack;
    }
}
