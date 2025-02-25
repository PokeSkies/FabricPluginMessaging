package com.pokeskies.fabricpluginmessaging

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking

fun interface PluginMessageEvent {
    companion object {
        @JvmField
        val EVENT: Event<PluginMessageEvent> =
            EventFactory.createArrayBacked(PluginMessageEvent::class.java) { listeners ->
                PluginMessageEvent { payload, context ->
                    for (listener in listeners) {
                        listener.onReceive(payload, context)
                    }
                }
            }
    }

    fun onReceive(payload: PluginMessagePacket, context: ServerPlayNetworking.Context)
}
