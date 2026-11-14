package com.example.medieval.client;

import com.example.medieval.entity.CatapultEntity;
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

public class CatapultModel extends EntityModel<CatapultEntity> {
    private final ModelPart root;
    private final ModelPart arm;

    public CatapultModel(ModelPart root) {
        this.root = root;
        this.arm = root.getChild("arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();

        // Chasis, ruedas, postes y viga superior (el frente es -Z)
        part.addOrReplaceChild("frame", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-10F, -8F, -14F, 20F, 4F, 28F)
                        .texOffs(0, 32)
                        .addBox(-13F, -10F, -12F, 3F, 10F, 10F)
                        .addBox(-13F, -10F, 4F, 3F, 10F, 10F)
                        .addBox(10F, -10F, -12F, 3F, 10F, 10F)
                        .addBox(10F, -10F, 4F, 3F, 10F, 10F)
                        .texOffs(0, 52)
                        .addBox(-10F, -24F, -2F, 3F, 16F, 4F)
                        .addBox(7F, -24F, -2F, 3F, 16F, 4F)
                        .texOffs(30, 52).addBox(-10F, -26F, -2F, 20F, 2F, 4F),
                PartPose.offset(0F, 24F, 0F));

        // Brazo de lanzamiento con cubo y contrapeso metalico
        part.addOrReplaceChild("arm", CubeListBuilder.create()
                        .texOffs(0, 72).addBox(-2F, -26F, -2F, 4F, 34F, 4F)
                        .texOffs(20, 72).addBox(-5F, -30F, -9F, 10F, 4F, 10F)
                        .texOffs(192, 0).addBox(-4F, 6F, -4F, 8F, 8F, 8F),
                PartPose.offset(0F, 2F, 0F));

        return LayerDefinition.create(mesh, 256, 256);
    }

    @Override
    public void setupAnim(CatapultEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float partial = ageInTicks - entity.tickCount;
        float progress = Mth.lerp(partial, entity.armProgressO, entity.armProgress);
        // 0 = brazo adelante (disparado), 1 = brazo tensado hacia atras
        this.arm.xRot = Mth.lerp(progress, 0.3F, -1.25F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int light, int overlay,
                               float r, float g, float b, float a) {
        this.root.render(poseStack, buffer, light, overlay, r, g, b, a);
    }
}
