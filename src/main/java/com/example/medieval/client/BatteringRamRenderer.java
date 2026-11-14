package com.example.medieval.client;

import com.example.medieval.MedievalMod;
import com.example.medieval.entity.BatteringRamEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BatteringRamRenderer extends MobRenderer<BatteringRamEntity, BatteringRamModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MedievalMod.MODID, "textures/entity/battering_ram.png");

    public BatteringRamRenderer(EntityRendererProvider.Context context) {
        super(context, new BatteringRamModel(context.bakeLayer(ModModelLayers.BATTERING_RAM)), 1.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(BatteringRamEntity entity) {
        return TEXTURE;
    }
}
