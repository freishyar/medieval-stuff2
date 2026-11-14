package com.example.medieval.entity;

import com.example.medieval.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

/** Roca lanzada por la catapulta: explota al impactar. */
public class BoulderEntity extends ThrowableItemProjectile {

    public BoulderEntity(EntityType<? extends BoulderEntity> type, Level level) {
        super(type, level);
    }

    public BoulderEntity(Level level, LivingEntity shooter) {
        super(ModEntities.BOULDER.get(), shooter, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BOULDER.get();
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            // Respeta la regla mobGriefing para el dano a bloques
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2.5F, Level.ExplosionInteraction.MOB);
            this.discard();
        }
    }
}
