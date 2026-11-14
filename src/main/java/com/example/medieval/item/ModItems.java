package com.example.medieval.item;

import com.example.medieval.MedievalMod;
import com.example.medieval.entity.ModEntities;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MedievalMod.MODID);

    // Materiales y municion
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BOULDER = ITEMS.register("boulder", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BALLISTA_BOLT = ITEMS.register("ballista_bolt", () -> new Item(new Item.Properties()));

    // Espadas
    public static final RegistryObject<Item> LONGSWORD = ITEMS.register("longsword",
            () -> new SwordItem(ModTiers.STEEL, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> CLAYMORE = ITEMS.register("claymore",
            () -> new ClaymoreItem(ModTiers.STEEL, 5, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> SCYTHE = ITEMS.register("scythe",
            () -> new ScytheItem(ModTiers.STEEL, 4, -3.1F, new Item.Properties()));
    public static final RegistryObject<Item> RAPIER = ITEMS.register("rapier",
            () -> new SwordItem(ModTiers.STEEL, 1, -1.6F, new Item.Properties()));

    // Armadura de caballero (acero)
    public static final RegistryObject<Item> KNIGHT_HELMET = ITEMS.register("knight_helmet",
            () -> new ArmorItem(ModArmorMaterials.KNIGHT, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> KNIGHT_CHESTPLATE = ITEMS.register("knight_chestplate",
            () -> new ArmorItem(ModArmorMaterials.KNIGHT, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> KNIGHT_LEGGINGS = ITEMS.register("knight_leggings",
            () -> new ArmorItem(ModArmorMaterials.KNIGHT, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> KNIGHT_BOOTS = ITEMS.register("knight_boots",
            () -> new ArmorItem(ModArmorMaterials.KNIGHT, ArmorItem.Type.BOOTS, new Item.Properties()));

    // Armadura ligera (gambeson)
    public static final RegistryObject<Item> GAMBESON_HELMET = ITEMS.register("gambeson_helmet",
            () -> new ArmorItem(ModArmorMaterials.GAMBESON, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> GAMBESON_CHESTPLATE = ITEMS.register("gambeson_chestplate",
            () -> new ArmorItem(ModArmorMaterials.GAMBESON, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> GAMBESON_LEGGINGS = ITEMS.register("gambeson_leggings",
            () -> new ArmorItem(ModArmorMaterials.GAMBESON, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> GAMBESON_BOOTS = ITEMS.register("gambeson_boots",
            () -> new ArmorItem(ModArmorMaterials.GAMBESON, ArmorItem.Type.BOOTS, new Item.Properties()));

    // Maquinas de asedio
    public static final RegistryObject<Item> CATAPULT = ITEMS.register("catapult",
            () -> new SiegeEngineItem(ModEntities.CATAPULT, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BALLISTA = ITEMS.register("ballista",
            () -> new SiegeEngineItem(ModEntities.BALLISTA, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BATTERING_RAM = ITEMS.register("battering_ram",
            () -> new SiegeEngineItem(ModEntities.BATTERING_RAM, new Item.Properties().stacksTo(1)));
}
