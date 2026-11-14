package com.example.medieval.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

/** Base comun de las maquinas de asedio: resistentes, persistentes y recogibles. */
public abstract class SiegeEngine extends PathfinderMob {

    protected SiegeEngine(EntityType<? extends SiegeEngine> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes(double health, double speed) {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, health)
                .add(Attributes.MOVEMENT_SPEED, speed)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ARMOR, 6.0D);
    }

    /** Item que se devuelve al recoger la maquina. */
    public abstract Item getDropItem();

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.WOOD_HIT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WOOD_BREAK;
    }

    /** Agachado + mano vacia: recoge la maquina. Devuelve null si no aplica. */
    protected InteractionResult tryPickup(Player player) {
        if (player.isSecondaryUseActive() && player.getMainHandItem().isEmpty() && !this.isVehicle()) {
            if (!this.level().isClientSide) {
                this.spawnAtLocation(this.getDropItem());
                this.discard();
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return null;
    }
}
