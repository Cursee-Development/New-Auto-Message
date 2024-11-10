package com.cursee.automessage;

import com.cursee.automessage.core.AutoMessageServerConfigForge;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.DEDICATED_SERVER)
public class AutoMessageServerForge {

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.DEDICATED_SERVER, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent
        public static void onServerSetup(final FMLDedicatedServerSetupEvent event) {
            event.enqueueWork(AutoMessageServerConfigForge::initialize);
        }
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.DEDICATED_SERVER, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class GameEvents {
        @SubscribeEvent
        public static void onServerTick(final TickEvent.ServerTickEvent event) {

            if (event.phase != TickEvent.Phase.START) return;

            MinecraftServer server = event.getServer();
            ServerLevel level = server.getLevel(ServerLevel.OVERWORLD);

            if (level == null) return;

            final Long tick = level.getLevelData().getGameTime();

            if (tick % 20 != 0) return;

            // Constants.LOG.info(String.valueOf(tick));

            AutoMessage.handleServerMessaging(server);
        }
    }
}
