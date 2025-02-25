package com.pokeskies.fabricpluginmessaging;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FabricPluginMessaging implements ModInitializer, ServerPlayNetworking.PlayPayloadHandler<PluginMessagePacket> {
    public static FabricPluginMessaging INSTANCE;

    public static String MOD_ID = "fabricpluginmessaging";
    public static String MOD_NAME = "FabricPluginMessaging";

    public static Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static ResourceLocation BUNGEE_CHANNEL = ResourceLocation.fromNamespaceAndPath("bungeecord", "main");

    @Override
    public void onInitialize() {
        LOGGER.info("FabricPluginMessaging initialized!");

        INSTANCE = this;

        PayloadTypeRegistry.playS2C().register(PluginMessagePacket.CHANNEL_ID, PluginMessagePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(PluginMessagePacket.CHANNEL_ID, PluginMessagePacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(PluginMessagePacket.CHANNEL_ID, this);
    }

    @Override
    public void receive(PluginMessagePacket payload, ServerPlayNetworking.Context context) {
        PluginMessageEvent.EVENT.invoker().onReceive(payload, context);
    }
}
