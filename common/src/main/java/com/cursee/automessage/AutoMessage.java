package com.cursee.automessage;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;

public class AutoMessage {

    public static void init() {}

    public static void handleClientMessaging(Minecraft client) {

        // operating once per second

        client.getChatListener().handleSystemMessage(Component.literal("client message 1/s?"), false);
    }

    public static void handleServerMessaging(MinecraftServer server) {

        // operating once per second

        server.getPlayerList().getPlayers().forEach(player -> {
            player.sendSystemMessage(Component.literal("client message 1/s?"));
        });
    }
}