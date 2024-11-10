package com.cursee.automessage;

import com.cursee.automessage.core.AutoMessageClientConfigFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.multiplayer.ClientLevel;

public class AutoMessageClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AutoMessageClientConfigFabric.initialize();

        ClientTickEvents.START_CLIENT_TICK.register(client -> {

            ClientLevel level = client.level;

            if (level == null) return;

            final Long tick = level.getLevelData().getGameTime();

            if (tick % 20 != 0) return;

            // Constants.LOG.info(String.valueOf(tick));

            AutoMessage.handleClientMessaging(client);
        });
    }
}
