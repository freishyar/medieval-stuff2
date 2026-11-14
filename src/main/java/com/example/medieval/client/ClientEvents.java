package com.example.medieval.client;

import com.example.medieval.MedievalMod;
import com.example.medieval.entity.ModEntities;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MedievalMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.CATAPULT, CatapultModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BALLISTA, BallistaModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BATTERING_RAM, BatteringRamModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.CATAPULT.get(), CatapultRenderer::new);
        event.registerEntityRenderer(ModEntities.BALLISTA.get(), BallistaRenderer::new);
        event.registerEntityRenderer(ModEntities.BATTERING_RAM.get(), BatteringRamRenderer::new);
        event.registerEntityRenderer(ModEntities.BOULDER.get(), ctx -> new ThrownItemRenderer<>(ctx, 2.0F, true));
        event.registerEntityRenderer(ModEntities.BALLISTA_BOLT.get(), BallistaBoltRenderer::new);
    }
}
