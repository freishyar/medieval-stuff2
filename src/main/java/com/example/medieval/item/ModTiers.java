package com.example.medieval.item;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModTiers implements Tier {
    // nivel de minado, durabilidad, velocidad, dano extra, encantabilidad, material de reparacion
    STEEL(2, 900, 7.0F, 2.5F, 12, () -> Ingredient.of(ModItems.STEEL_INGOT.get()));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantability;
    private final LazyLoadedValue<Ingredient> repair;

    ModTiers(int level, int uses, float speed, float damage, int enchantability, Supplier<Ingredient> repair) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantability = enchantability;
        this.repair = new LazyLoadedValue<>(repair);
    }

    @Override public int getUses() { return uses; }
    @Override public float getSpeed() { return speed; }
    @Override public float getAttackDamageBonus() { return damage; }
    @Override public int getLevel() { return level; }
    @Override public int getEnchantmentValue() { return enchantability; }
    @Override public Ingredient getRepairIngredient() { return repair.get(); }
}
