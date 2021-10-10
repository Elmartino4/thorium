package github.elmartino4.thorium.entity;

import github.elmartino4.thorium.items.ThoriumItems;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class ThoriumBombEntity extends Entity {
    private float power;
    private static final TrackedData<Integer> FUSE = DataTracker.registerData(TntEntity.class, TrackedDataHandlerRegistry.INTEGER);
    @Nullable
    private LivingEntity causingEntity;

    public ThoriumBombEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter){
        this(ThoriumItems.THORIUM_BOMB_ENTITY_TYPE, world);
        this.setPosition(x, y, z);
        double d = world.random.nextDouble() * 6.2831854820251465D;
        this.setVelocity(-Math.sin(d) * 0.02D, 0.20000000298023224D, -Math.cos(d) * 0.02D);
        this.setFuse(80);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.causingEntity = igniter;
        this.power = 6;
        if (world instanceof ServerWorld){
            ((ServerWorld)world).setWeather(0, 0, true, true);
        }
    }

    public ThoriumBombEntity(EntityType entityType, World world) {
        super(entityType, world);
        this.inanimate = true;
        //this.inanimate = true;
        //new Entity(entityType, world);
    }

    //public ThoriumBombEntity(EntityType<Entity> entityEntityType, World world) {
    //}

    @Nullable
    public LivingEntity getCausingEntity() {
        return this.causingEntity;
    }

    public void setPower(float power){
        this.power = power;
    }

    public void setFuse(int fuse) {
        this.dataTracker.set(FUSE, fuse);
    }

    public int getFuse() {
        return (Integer)this.dataTracker.get(FUSE);
    }

    public void tick() {
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MovementType.SELF, this.getVelocity());
        this.setVelocity(this.getVelocity().multiply(0.98D));
        if (this.onGround) {
            this.setVelocity(this.getVelocity().multiply(0.7D, -0.5D, 0.7D));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i % 8 == 1 && i < 26){
            if (world.isSkyVisible(new BlockPos(getPos()))) {
                LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
                lightning.refreshPositionAfterTeleport(getPos());
                world.spawnEntity(lightning);
            }
        }
        if (i <= 0) {
            this.discard();
            if (!this.world.isClient) {
                this.explode();
            }
        } else {
            this.updateWaterState();
            if (this.world.isClient) {
                this.world.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    private void explode() {
        long timeStart = System.nanoTime();

        this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), this.power, Explosion.DestructionType.BREAK);

        //BetterExplosion expl = new BetterExplosion(new Vec3f((float)this.getX(), (float)this.getY(), (float)this.getZ()), this.power, Explosion.DestructionType.BREAK);

        //expl.collectBlocksDamageEntities();

        //expl.doBreak(this.world);

        //System.out.println("Took " + (System.nanoTime() - timeStart)/1000000D + "ms");
    }

    protected void initDataTracker() {
        this.dataTracker.startTracking(FUSE, 80);
    }

    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putShort("Fuse", (short)this.getFuse());
    }

    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.setFuse(nbt.getShort("Fuse"));
    }

    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
