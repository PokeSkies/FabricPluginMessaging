package com.pokeskies.fabricpluginmessaging;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FabricPluginMessaging implements ModInitializer, ServerPlayNetworking.PlayPayloadHandler<PluginMessagePacket> {

    public static final String MOD_ID = "fabricpluginmessaging";
    public static final String MOD_NAME = "FabricPluginMessaging";

    private static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    static final Identifier BUNGEE_CHANNEL = Identifier.fromNamespaceAndPath("bungeecord", "main");

    @Override
    public void onInitialize() {
        LOGGER.info("FabricPluginMessaging initialized!");

        PayloadTypeRegistry.clientboundPlay().register(PluginMessagePacket.CHANNEL_ID, PluginMessagePacket.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(PluginMessagePacket.CHANNEL_ID, PluginMessagePacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(PluginMessagePacket.CHANNEL_ID, this);
    }

    @Override
    public void receive(PluginMessagePacket payload, ServerPlayNetworking.Context context) {
        PluginMessageEvent.EVENT.invoker().onReceive(payload, context);
    }
}
