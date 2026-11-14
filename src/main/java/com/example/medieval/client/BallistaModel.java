package com.example.medieval.client;

import com.example.medieval.entity.BallistaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BallistaModel extends EntityModel<BallistaEntity> {
    private final ModelPart root;
    private final ModelPart bolt;

    public BallistaModel(ModelPart root) {
        this.root = root;
        this.bolt = root.getChild("turret").getChild("bolt");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();

        // Base con tres patas y pilar central
        part.addOrReplaceChild("base", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6F, -4F, -8F, 12F, 4F, 16F)
                        .texOffs(0, 20)
                        .addBox(-10F, -5F, -12F, 4F, 5F, 4F)
                        .addBox(6F, -5F, -12F, 4F, 5F, 4F)
                        .addBox(-2F, -5F, 8F, 4F, 5F, 4F)
                        .texOffs(0, 30).addBox(-2F, -16F, -2F, 4F, 12F, 4F),
                PartPose.offset(0F, 24F, 0F));

        // Torreta giratoria: riel, arco y culata
        PartDefinition turret = part.addOrReplaceChild("turret", CubeListBuilder.create()
                        .texOffs(0, 46).addBox(-1.5F, -3F, -16F, 3F, 3F, 32F)
                        .texOffs(0, 82).addBox(-18F, -3F, -11F, 36F, 2F, 2F)
                        .texOffs(0, 88).addBox(-2F, -4F, 16F, 4F, 4F, 6F),
                PartPose.offset(0F, 8F, 0F));

        // Virote cargado (solo visible cuando esta cargada)
        turret.addOrReplaceChild("bolt", CubeListBuilder.create()
                        .texOffs(0, 100).addBox(-0.5F, -4F, -18F, 1F, 1F, 22F)
                        .texOffs(192, 0).addBox(-1.5F, -4.5F, -22F, 3F, 2F, 4F),
                PartPose.ZERO);

        return LayerDefinition.create(mesh, 256, 256);
    }

    @Override
    public void setupAnim(BallistaEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.bolt.visible = entity.isLoaded();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int light, int overlay,
                               float r, float g, float b, float a) {
        this.root.render(poseStack, buffer, light, overlay, r, g, b, a);
    }
}
