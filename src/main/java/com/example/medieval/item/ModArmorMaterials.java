package com.example.medieval.item;

import com.example.medieval.MedievalMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    // nombre, mult. durabilidad, defensa (casco, peto, pantalones, botas), encantabilidad, sonido, dureza, resistencia retroceso, reparacion
    KNIGHT("knight", 38, new int[]{3, 8, 6, 3}, 10, SoundEvents.ARMOR_EQUIP_IRON, 2.0F, 0.1F,
            () -> Ingredient.of(ModItems.STEEL_INGOT.get())),
    GAMBESON("gambeson", 20, new int[]{2, 5, 4, 2}, 18, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F,
            () -> Ingredient.of(Items.LEATHER));

    private static final int[] BASE_DURABILITY = {11, 16, 16, 13};

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protection;
    private final int enchantability;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repair;

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protection, int enchantability,
                      SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repair) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protection = protection;
        this.enchantability = enchantability;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repair = new LazyLoadedValue<>(repair);
    }

    @Override public int getDurabilityForType(ArmorItem.Type type) { return BASE_DURABILITY[type.ordinal()] * durabilityMultiplier; }
    @Override public int getDefenseForType(ArmorItem.Type type) { return protection[type.ordinal()]; }
    @Override public int getEnchantmentValue() { return enchantability; }
    @Override public SoundEvent getEquipSound() { return sound; }
    @Override public Ingredient getRepairIngredient() { return repair.get(); }
    @Override public String getName() { return MedievalMod.MODID + ":" + name; }
    @Override public float getToughness() { return toughness; }
    @Override public float getKnockbackResistance() { return knockbackResistance; }
}
