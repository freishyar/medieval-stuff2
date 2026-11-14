package com.example.medieval.item;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.AABB;

import java.util.List;

/** Guadana de guerra: lenta y pesada, siega a los enemigos cercanos al objetivo. */
public class ScytheItem extends SwordItem {
    private static final double SWEEP_RADIUS = 2.5D;
    private static final float SWEEP_DAMAGE = 5.0F;

    public ScytheItem(Tier tier, int damage, float speed, Properties props) {
        super(tier, damage, speed, props);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);
        if (attacker instanceof Player player && !target.level().isClientSide) {
            AABB area = target.getBoundingBox().inflate(SWEEP_RADIUS, 0.5D, SWEEP_RADIUS);
            List<LivingEntity> victims = target.level().getEntitiesOfClass(LivingEntity.class, area,
                    e -> e != player && e != target && !(e instanceof ArmorStand) && !player.isAlliedTo(e));
            float yaw = player.getYRot() * ((float) Math.PI / 180F);
            for (LivingEntity victim : victims) {
                if (victim.hurt(player.damageSources().playerAttack(player), SWEEP_DAMAGE)) {
                    victim.knockback(0.4D, Mth.sin(yaw), -Mth.cos(yaw));
                }
            }
        }
        return result;
    }
}
