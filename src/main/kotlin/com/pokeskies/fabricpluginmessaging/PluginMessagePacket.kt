package com.pokeskies.fabricpluginmessaging

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload

data class PluginMessagePacket(val data: ByteArray) : CustomPacketPayload {
    companion object {
        val CHANNEL_ID: CustomPacketPayload.Type<PluginMessagePacket> = CustomPacketPayload.Type(
            FabricPluginMessaging.BUNGEE_CHANNEL
        )

        val CODEC: StreamCodec<RegistryFriendlyByteBuf, PluginMessagePacket> = StreamCodec.ofMember(
            { value: PluginMessagePacket, buf: RegistryFriendlyByteBuf ->
                writeBytes(buf, value.data)
            }, ::PluginMessagePacket
        )

        private fun writeBytes(buf: FriendlyByteBuf, v: ByteArray) {
            buf.writeBytes(v)
        }

        private fun getWrittenBytes(buf: FriendlyByteBuf): ByteArray {
            val bs = ByteArray(buf.readableBytes())
            buf.readBytes(bs)
            return bs
        }
    }

    constructor(buf: FriendlyByteBuf) : this(getWrittenBytes(buf))

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        return CHANNEL_ID
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PluginMessagePacket

        return data.contentEquals(other.data)
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}
