package github.elmartino4.thorium;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.ArrayList;
import java.util.List;

public class BetterExplosion {
    private Vec3f pos;
    private float rad;
    private Explosion.DestructionType destructionType;
    private List<BlockPos> posList = new ArrayList<>();

    public BetterExplosion(Vec3f pos, float dia, Explosion.DestructionType destructionType){
        this.pos = pos;
        this.rad = dia/2F;
        this.destructionType = destructionType;
    }

    public void collectBlocksDamageEntities(){
        for (int i = 1; i < this.rad + 2; i++) {
            for (int x = -i; x <= i; x++) {
                for (int y = -i; y <= i; y++) {
                    if(x != -i && x != i && y > -i + 1) y = i;
                    for (int z = -i; z <= i; z++) {
                        if(x != -i && x != i && z > -i + 1) z = i;

                        posList.add(new BlockPos(x + pos.getX(), y + pos.getY(), z + pos.getZ()));
                    }
                }
            }
        }
    }

    public void doBreak(World world){
        for (BlockPos blockPos : posList) {
            world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
        }
    }
}
