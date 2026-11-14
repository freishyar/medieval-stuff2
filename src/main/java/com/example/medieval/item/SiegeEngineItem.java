package com.example.medieval.item;

import com.example.medieval.entity.SiegeEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

/** Item que coloca una maquina de asedio en el mundo. */
public class SiegeEngineItem extends Item {
    private final Supplier<? extends EntityType<? extends SiegeEngine>> type;

    public SiegeEngineItem(Supplier<? extends EntityType<? extends SiegeEngine>> type, Properties props) {
        super(props);
        this.type = type;
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        if (!(level instanceof ServerLevel server)) {
            return InteractionResult.SUCCESS;
        }
        Player player = ctx.getPlayer();
        BlockPos pos = ctx.getClickedPos().relative(ctx.getClickedFace());
        SiegeEngine engine = type.get().create(server);
        if (engine == null) {
            return InteractionResult.FAIL;
        }
        Vec3 at = Vec3.atBottomCenterOf(pos);
        float yaw = player != null ? player.getYRot() : 0.0F;
        engine.moveTo(at.x, at.y, at.z, yaw, 0.0F);
        engine.yBodyRot = yaw;
        engine.yHeadRot = yaw;
        if (!server.noCollision(engine)) {
            return InteractionResult.FAIL;
        }
        server.addFreshEntity(engine);
        server.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        if (player == null || !player.getAbilities().instabuild) {
            ctx.getItemInHand().shrink(1);
        }
        return InteractionResult.CONSUME;
    }
}
