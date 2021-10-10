package github.elmartino4.thorium.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ThoriumIngot extends Item {
    public ThoriumIngot(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        //System.out.println("tried make tooltip");
        if(stack.getOrCreateNbt().contains("quality")){
            String qualityStr = String.format("%.2f", stack.getOrCreateNbt().getDouble("quality"));
            tooltip.add(new TranslatableText("item.thorium.thorium_ingot.quality_tooltip").append(qualityStr));
        }
    }
}
