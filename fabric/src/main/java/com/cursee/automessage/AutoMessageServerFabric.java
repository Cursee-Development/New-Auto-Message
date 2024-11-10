package com.cursee.automessage;

import com.cursee.automessage.core.AutoMessageServerConfigFabric;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerLevel;

public class AutoMessageServerFabric implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {

        AutoMessageServerConfigFabric.initialize();

        ServerTickEvents.START_SERVER_TICK.register(server -> {
            ServerLevel level = server.getLevel(ServerLevel.OVERWORLD);

            if (level == null) return;

            final Long tick = level.getLevelData().getGameTime();

            if (tick % 20 != 0) return;

            // Constants.LOG.info(String.valueOf(tick));

            AutoMessage.handleServerMessaging(server);
        });
    }
}
