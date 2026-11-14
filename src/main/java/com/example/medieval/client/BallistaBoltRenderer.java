package com.example.medieval.client;

import com.example.medieval.MedievalMod;
import com.example.medieval.entity.BallistaBoltEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BallistaBoltRenderer extends ArrowRenderer<BallistaBoltEntity> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MedievalMod.MODID, "textures/entity/ballista_bolt.png");

    public BallistaBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(BallistaBoltEntity entity) {
        return TEXTURE;
    }
}
