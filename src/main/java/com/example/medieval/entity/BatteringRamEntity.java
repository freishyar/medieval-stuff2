package com.example.medieval.entity;

import com.example.medieval.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * Ariete: montable. W/S avanza y retrocede, A/D gira.
 * Al embestir rompe bloques blandos (si mobGriefing esta activo) y empuja/danya enemigos.
 */
public class BatteringRamEntity extends SiegeEngine {
    private int bashCooldown;

    public BatteringRamEntity(EntityType<? extends BatteringRamEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public Item getDropItem() {
        return ModItems.BATTERING_RAM.get();
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.55D;
    }

    @Override
    public LivingEntity getControllingPassenger() {
        return this.getFirstPassenger() instanceof Player player ? player : null;
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        InteractionResult pickup = this.tryPickup(player);
        if (pickup != null) {
            return pickup;
        }
        if (!this.isVehicle() && !player.isSecondaryUseActive()) {
            if (!this.level().isClientSide) {
                player.startRiding(this);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isAlive() && this.getControllingPassenger() instanceof LivingEntity rider) {
            // A/D giran el ariete, W/S lo mueven
            this.setYRot(this.getYRot() - rider.xxa * 4.0F);
            this.yRotO = this.getYRot();
            this.setXRot(0.0F);
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.getYRot();

            float forward = rider.zza;
            if (forward <= 0.0F) {
                forward *= 0.3F;
            }
            this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
            super.travel(new Vec3(0.0D, travelVector.y, forward));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            return;
        }
        if (this.bashCooldown > 0) {
            this.bashCooldown--;
        }
        if (this.getControllingPassenger() == null) {
            return;
        }
        double speed = this.getDeltaMovement().horizontalDistance();
        if (speed < 0.06D) {
            return;
        }
        Vec3 forward = Vec3.directionFromRotation(0.0F, this.getYRot());

        // Dano y empuje a las criaturas que estan delante
        AABB hitArea = this.getBoundingBox().move(forward.x * 1.2D, 0.0D, forward.z * 1.2D).inflate(0.2D);
        List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, hitArea,
                e -> e != this && !this.hasPassenger(e));
        for (LivingEntity target : targets) {
            if (target.hurt(this.damageSources().mobAttack(this), 10.0F)) {
                target.knockback(1.4D, -forward.x, -forward.z);
            }
        }

        // Rotura de bloques blandos al chocar
        if (this.horizontalCollision && this.bashCooldown == 0
                && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            AABB box = this.getBoundingBox().move(forward.x * 0.8D, 0.0D, forward.z * 0.8D).inflate(0.1D, 0.0D, 0.1D);
            boolean broke = false;
            int broken = 0;
            for (BlockPos pos : BlockPos.betweenClosed(
                    Mth.floor(box.minX), Mth.floor(box.minY + 0.1D), Mth.floor(box.minZ),
                    Mth.floor(box.maxX), Mth.floor(box.maxY), Mth.floor(box.maxZ))) {
                if (broken >= 6) {
                    break;
                }
                BlockState state = this.level().getBlockState(pos);
                float hardness = state.getDestroySpeed(this.level(), pos);
                if (!state.isAir() && hardness >= 0.0F && hardness <= 5.0F) {
                    this.level().destroyBlock(pos.immutable(), true, this);
                    broke = true;
                    broken++;
                }
            }
            if (broke) {
                this.playSound(SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, 1.5F, 0.6F);
                this.bashCooldown = 12;
            }
        }
    }
}
