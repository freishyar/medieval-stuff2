package com.example.medieval;

import com.example.medieval.entity.ModEntities;
import com.example.medieval.item.ModCreativeTabs;
import com.example.medieval.item.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MedievalMod.MODID)
public class MedievalMod {
    public static final String MODID = "medieval";

    public MedievalMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(bus);
        ModEntities.ENTITY_TYPES.register(bus);
        ModCreativeTabs.TABS.register(bus);
    }
}
