package com.example.medieval.entity;

import com.example.medieval.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Catapulta: clic derecho con una roca para cargarla, clic derecho con cualquier
 * otra cosa (o la mano vacia) para disparar hacia donde miras.
 */
public class CatapultEntity extends SiegeEngine {
    private static final EntityDataAccessor<Boolean> LOADED =
            SynchedEntityData.defineId(CatapultEntity.class, EntityDataSerializers.BOOLEAN);

    /** Progreso del brazo para la animacion (0 = suelto, 1 = tensado). Solo cliente. */
    public float armProgress;
    public float armProgressO;
    private int cooldown;

    public CatapultEntity(EntityType<? extends CatapultEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LOADED, false);
    }

    public boolean isLoaded() {
        return this.entityData.get(LOADED);
    }

    private void setLoaded(boolean loaded) {
        this.entityData.set(LOADED, loaded);
    }

    @Override
    public Item getDropItem() {
        return ModItems.CATAPULT.get();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.armProgressO = this.armProgress;
            if (this.isLoaded()) {
                this.armProgress = Math.min(1.0F, this.armProgress + 0.04F);
            } else {
                this.armProgress = Math.max(0.0F, this.armProgress - 0.35F);
            }
        } else if (this.cooldown > 0) {
            this.cooldown--;
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        InteractionResult pickup = this.tryPickup(player);
        if (pickup != null) {
            return pickup;
        }
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }
        boolean client = this.level().isClientSide;
        if (!client) {
            ItemStack held = player.getItemInHand(hand);
            if (this.cooldown > 0) {
                return InteractionResult.sidedSuccess(client);
            }
            if (!this.isLoaded()) {
                if (held.is(ModItems.BOULDER.get())) {
                    if (!player.getAbilities().instabuild) {
                        held.shrink(1);
                    }
                    this.setLoaded(true);
                    this.playSound(SoundEvents.CROSSBOW_LOADING_MIDDLE, 1.5F, 0.6F);
                } else {
                    player.displayClientMessage(Component.translatable("message.medieval.catapult_empty"), true);
                }
            } else {
                this.fire(player);
            }
        }
        return InteractionResult.sidedSuccess(client);
    }

    private void fire(Player shooter) {
        float yaw = shooter.getYRot();
        this.setYRot(yaw);
        this.yRotO = yaw;
        this.yBodyRot = yaw;
        this.yHeadRot = yaw;

        Vec3 look = shooter.getLookAngle();
        BoulderEntity boulder = new BoulderEntity(this.level(), this);
        boulder.setPos(this.getX() + look.x * 1.5D, this.getY() + 2.6D, this.getZ() + look.z * 1.5D);
        boulder.shoot(look.x, look.y + 0.25D, look.z, 1.8F, 1.0F);
        this.level().addFreshEntity(boulder);

        this.playSound(SoundEvents.CROSSBOW_SHOOT, 2.0F, 0.5F);
        this.playSound(SoundEvents.PISTON_EXTEND, 1.5F, 0.6F);
        this.setLoaded(false);
        this.cooldown = 40;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Loaded", this.isLoaded());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setLoaded(tag.getBoolean("Loaded"));
    }
}
