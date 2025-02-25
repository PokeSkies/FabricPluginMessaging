package com.pokeskies.fabricpluginmessaging

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class FabricPluginMessaging : ModInitializer, ServerPlayNetworking.PlayPayloadHandler<PluginMessagePacket> {
    companion object {
        lateinit var INSTANCE: FabricPluginMessaging

        var MOD_ID = "fabricpluginmessaging"
        var MOD_NAME = "FabricPluginMessaging"

        val LOGGER: Logger = LogManager.getLogger(MOD_ID)

        val BUNGEE_CHANNEL: ResourceLocation = ResourceLocation.fromNamespaceAndPath("bungeecord", "main")
    }

    override fun onInitialize() {
        LOGGER.info("FabricPluginMessaging initialized!")

        INSTANCE = this

        PayloadTypeRegistry.playS2C().register(PluginMessagePacket.CHANNEL_ID, PluginMessagePacket.CODEC)
        PayloadTypeRegistry.playC2S().register(PluginMessagePacket.CHANNEL_ID, PluginMessagePacket.CODEC)

        ServerPlayNetworking.registerGlobalReceiver(PluginMessagePacket.CHANNEL_ID, this)
    }

    override fun receive(payload: PluginMessagePacket, context: ServerPlayNetworking.Context) {
        PluginMessageEvent.EVENT.invoker().onReceive(payload, context)
    }
}
