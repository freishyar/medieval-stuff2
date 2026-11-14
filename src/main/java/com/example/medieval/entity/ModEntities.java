package com.example.medieval.entity;

import com.example.medieval.MedievalMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MedievalMod.MODID);

    public static final RegistryObject<EntityType<CatapultEntity>> CATAPULT = ENTITY_TYPES.register("catapult",
            () -> EntityType.Builder.<CatapultEntity>of(CatapultEntity::new, MobCategory.MISC)
                    .sized(1.8F, 2.2F).clientTrackingRange(10).build("catapult"));

    public static final RegistryObject<EntityType<BallistaEntity>> BALLISTA = ENTITY_TYPES.register("ballista",
            () -> EntityType.Builder.<BallistaEntity>of(BallistaEntity::new, MobCategory.MISC)
                    .sized(1.6F, 1.4F).clientTrackingRange(10).build("ballista"));

    public static final RegistryObject<EntityType<BatteringRamEntity>> BATTERING_RAM = ENTITY_TYPES.register("battering_ram",
            () -> EntityType.Builder.<BatteringRamEntity>of(BatteringRamEntity::new, MobCategory.MISC)
                    .sized(1.7F, 2.1F).clientTrackingRange(10).build("battering_ram"));

    public static final RegistryObject<EntityType<BoulderEntity>> BOULDER = ENTITY_TYPES.register("boulder",
            () -> EntityType.Builder.<BoulderEntity>of(BoulderEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(8).updateInterval(5).build("boulder"));

    public static final RegistryObject<EntityType<BallistaBoltEntity>> BALLISTA_BOLT = ENTITY_TYPES.register("ballista_bolt",
            () -> EntityType.Builder.<BallistaBoltEntity>of(BallistaBoltEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(8).updateInterval(5).build("ballista_bolt"));
}
