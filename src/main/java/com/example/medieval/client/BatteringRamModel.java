package com.example.medieval.client;

import com.example.medieval.entity.BatteringRamEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class BatteringRamModel extends EntityModel<BatteringRamEntity> {
    private final ModelPart root;
    private final ModelPart log;

    public BatteringRamModel(ModelPart root) {
        this.root = root;
        this.log = root.getChild("log");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();

        // Estructura: base, ruedas, postes, techo y cadenas
        part.addOrReplaceChild("frame", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-9F, -8F, -18F, 18F, 3F, 36F)
                        .texOffs(0, 40)
                        .addBox(-12F, -9F, -14F, 3F, 9F, 9F)
                        .addBox(-12F, -9F, 6F, 3F, 9F, 9F)
                        .addBox(9F, -9F, -14F, 3F, 9F, 9F)
                        .addBox(9F, -9F, 6F, 3F, 9F, 9F)
                        .texOffs(0, 58)
                        .addBox(-9F, -30F, -18F, 3F, 22F, 3F)
                        .addBox(6F, -30F, -18F, 3F, 22F, 3F)
                        .addBox(-9F, -30F, 15F, 3F, 22F, 3F)
                        .addBox(6F, -30F, 15F, 3F, 22F, 3F)
                        .texOffs(0, 86).addBox(-10F, -34F, -19F, 20F, 4F, 38F)
                        .texOffs(0, 182)
                        .addBox(-1F, -30F, -8F, 2F, 16F, 2F)
                        .addBox(-1F, -30F, 6F, 2F, 16F, 2F),
                PartPose.offset(0F, 24F, 0F));

        // Tronco colgante con cabeza de hierro (parte movil)
        part.addOrReplaceChild("log", CubeListBuilder.create()
                        .texOffs(0, 132).addBox(-3F, -3F, -26F, 6F, 6F, 40F)
                        .texOffs(192, 0).addBox(-4F, -4F, -32F, 8F, 8F, 6F),
                PartPose.offset(0F, 10F, 0F));

        return LayerDefinition.create(mesh, 256, 256);
    }

    @Override
    public void setupAnim(BatteringRamEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        // El tronco se balancea adelante y atras al avanzar
        this.log.z = Mth.cos(limbSwing * 0.4F) * 7.0F * Math.min(1.0F, limbSwingAmount * 2.0F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int light, int overlay,
                               float r, float g, float b, float a) {
        this.root.render(poseStack, buffer, light, overlay, r, g, b, a);
    }
}
