package com.example.medieval.client;

import com.example.medieval.MedievalMod;
import com.example.medieval.entity.CatapultEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CatapultRenderer extends MobRenderer<CatapultEntity, CatapultModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MedievalMod.MODID, "textures/entity/catapult.png");

    public CatapultRenderer(EntityRendererProvider.Context context) {
        super(context, new CatapultModel(context.bakeLayer(ModModelLayers.CATAPULT)), 1.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(CatapultEntity entity) {
        return TEXTURE;
    }
}
