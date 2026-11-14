package com.example.medieval.item;

import com.example.medieval.MedievalMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MedievalMod.MODID);

    public static final RegistryObject<CreativeModeTab> MEDIEVAL_TAB = TABS.register("medieval_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("creativetab.medieval"))
                    .icon(() -> new ItemStack(ModItems.CLAYMORE.get()))
                    .displayItems((params, out) -> {
                        out.accept(ModItems.STEEL_INGOT.get());
                        out.accept(ModItems.LONGSWORD.get());
                        out.accept(ModItems.CLAYMORE.get());
                        out.accept(ModItems.RAPIER.get());
                        out.accept(ModItems.SCYTHE.get());
                        out.accept(ModItems.KNIGHT_HELMET.get());
                        out.accept(ModItems.KNIGHT_CHESTPLATE.get());
                        out.accept(ModItems.KNIGHT_LEGGINGS.get());
                        out.accept(ModItems.KNIGHT_BOOTS.get());
                        out.accept(ModItems.GAMBESON_HELMET.get());
                        out.accept(ModItems.GAMBESON_CHESTPLATE.get());
                        out.accept(ModItems.GAMBESON_LEGGINGS.get());
                        out.accept(ModItems.GAMBESON_BOOTS.get());
                        out.accept(ModItems.BOULDER.get());
                        out.accept(ModItems.BALLISTA_BOLT.get());
                        out.accept(ModItems.CATAPULT.get());
                        out.accept(ModItems.BALLISTA.get());
                        out.accept(ModItems.BATTERING_RAM.get());
                    })
                    .build());
}
