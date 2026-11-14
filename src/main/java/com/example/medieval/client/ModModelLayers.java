package com.example.medieval.client;

import com.example.medieval.MedievalMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation CATAPULT = layer("catapult");
    public static final ModelLayerLocation BALLISTA = layer("ballista");
    public static final ModelLayerLocation BATTERING_RAM = layer("battering_ram");

    private static ModelLayerLocation layer(String name) {
        return new ModelLayerLocation(new ResourceLocation(MedievalMod.MODID, name), "main");
    }
}
