package com.cursee.automessage;

import com.cursee.automessage.core.AutoMessageClientConfigForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class AutoMessageClientForge {

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event) {
            event.enqueueWork(AutoMessageClientConfigForge::initialize);
        }
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class GameEvents {
        @SubscribeEvent
        public static void onClientTick(final TickEvent.ClientTickEvent event) {

            if (event.phase != TickEvent.Phase.START) return;

            Minecraft client = Minecraft.getInstance();
            ClientLevel level = client.level;

            if (level == null) return;

            final Long tick = level.getLevelData().getGameTime();

            if (tick % 20 != 0) return;

            // Constants.LOG.info(String.valueOf(tick));

            AutoMessage.handleClientMessaging(client);
        }
    }
}
