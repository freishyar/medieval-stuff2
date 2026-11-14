package com.example.medieval;

import com.example.medieval.entity.ModEntities;
import com.example.medieval.entity.SiegeEngine;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MedievalMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onAttributes(EntityAttributeCreationEvent event) {
        // vida, velocidad
        event.put(ModEntities.CATAPULT.get(), SiegeEngine.createAttributes(60.0D, 0.0D).build());
        event.put(ModEntities.BALLISTA.get(), SiegeEngine.createAttributes(40.0D, 0.0D).build());
        event.put(ModEntities.BATTERING_RAM.get(), SiegeEngine.createAttributes(90.0D, 0.16D).build());
    }
}
