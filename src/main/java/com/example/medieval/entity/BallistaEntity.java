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
 * Balista: clic derecho con un virote para cargarla, clic derecho de nuevo
 * (con cualquier cosa) para disparar hacia donde miras.
 */
public class BallistaEntity extends SiegeEngine {
    private static final EntityDataAccessor<Boolean> LOADED =
            SynchedEntityData.defineId(BallistaEntity.class, EntityDataSerializers.BOOLEAN);

    private int cooldown;

    public BallistaEntity(EntityType<? extends BallistaEntity> type, Level level) {
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
        return ModItems.BALLISTA.get();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.cooldown > 0) {
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
                if (held.is(ModItems.BALLISTA_BOLT.get())) {
                    if (!player.getAbilities().instabuild) {
                        held.shrink(1);
                    }
                    this.setLoaded(true);
                    this.playSound(SoundEvents.CROSSBOW_LOADING_END, 1.5F, 0.8F);
                } else {
                    player.displayClientMessage(Component.translatable("message.medieval.ballista_empty"), true);
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
        BallistaBoltEntity bolt = new BallistaBoltEntity(this.level(), this);
        bolt.setPos(this.getX() + look.x * 1.6D, this.getY() + 1.1D + look.y * 1.6D, this.getZ() + look.z * 1.6D);
        bolt.shoot(look.x, look.y, look.z, 3.5F, 0.3F);
        bolt.pickup = net.minecraft.world.entity.projectile.AbstractArrow.Pickup.DISALLOWED;
        this.level().addFreshEntity(bolt);

        this.playSound(SoundEvents.CROSSBOW_SHOOT, 2.0F, 0.6F);
        this.setLoaded(false);
        this.cooldown = 30;
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
