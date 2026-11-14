package com.example.medieval.item;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

/** Mandoble: espada lenta y pesada que empuja al enemigo con cada golpe. */
public class ClaymoreItem extends SwordItem {
    public ClaymoreItem(Tier tier, int damage, float speed, Properties props) {
        super(tier, damage, speed, props);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);
        float yaw = attacker.getYRot() * ((float) Math.PI / 180F);
        target.knockback(0.9D, Mth.sin(yaw), -Mth.cos(yaw));
        return result;
    }
}
