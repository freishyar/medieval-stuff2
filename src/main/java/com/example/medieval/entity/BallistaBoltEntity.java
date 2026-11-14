package com.example.medieval.entity;

import com.example.medieval.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Virote de balista: rapido, de mucho dano y atraviesa hasta 2 enemigos. */
public class BallistaBoltEntity extends AbstractArrow {

    public BallistaBoltEntity(EntityType<? extends BallistaBoltEntity> type, Level level) {
        super(type, level);
        this.setBaseDamage(4.0D);
        this.setPierceLevel((byte) 2);
    }

    public BallistaBoltEntity(Level level, LivingEntity shooter) {
        super(ModEntities.BALLISTA_BOLT.get(), shooter, level);
        this.setBaseDamage(4.0D);
        this.setPierceLevel((byte) 2);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.BALLISTA_BOLT.get());
    }
}
