package com.example.medieval.client;

import com.example.medieval.MedievalMod;
import com.example.medieval.entity.BallistaEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BallistaRenderer extends MobRenderer<BallistaEntity, BallistaModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MedievalMod.MODID, "textures/entity/ballista.png");

    public BallistaRenderer(EntityRendererProvider.Context context) {
        super(context, new BallistaModel(context.bakeLayer(ModModelLayers.BALLISTA)), 1.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(BallistaEntity entity) {
        return TEXTURE;
    }
}
