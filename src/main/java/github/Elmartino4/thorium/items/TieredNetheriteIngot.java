package github.elmartino4.thorium.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(stack.getOrCreateNbt().contains("quality")){
            String qualityStr = String.format("%.2f", stack.getOrCreateNbt().getDouble("quality"));
            tooltip.add(new TranslatableText("item.thorium.thorium_ingot.quality_tooltip").append(qualityStr));
        }
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
