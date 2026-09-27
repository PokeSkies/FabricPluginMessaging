package com.pokeskies.fabricpluginmessaging;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

public final class PluginMessagePacket implements CustomPacketPayload {
    private final byte[] data;

    public PluginMessagePacket(byte[] data) {
        this.data = data;
    }

    public PluginMessagePacket(FriendlyByteBuf buf) {
        this(getWrittenBytes(buf));
    }

    public static final CustomPacketPayload.Type<PluginMessagePacket> CHANNEL_ID = new CustomPacketPayload.Type<>(
            FabricPluginMessaging.BUNGEE_CHANNEL
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, PluginMessagePacket> CODEC = StreamCodec.ofMember(
            (value, buf) -> writeBytes(buf, value.data),
            PluginMessagePacket::new
    );

    private static byte[] getWrittenBytes(FriendlyByteBuf buf) {
        byte[] bs = new byte[buf.readableBytes()];
        buf.readBytes(bs);
        return bs;
    }

    private static void writeBytes(FriendlyByteBuf buf, byte[] v) {
        buf.writeBytes(v);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return CHANNEL_ID;
    }

    /**
     * Gets the message data. The array is copied to prevent multiple listeners from affecting each other.
     *
     * @return a copy of the data
     */
    public byte[] getData() {
        return data.clone();
    }

    /**
     * Gets the message data as an input stream
     *
     * @return the data as a stream
     */
    public InputStream getDataStream() {
        return new ByteArrayInputStream(data);
    }

    /**
     * Gets the message data as a data input stream
     *
     * @return the data as a data input
     */
    public DataInput getDataInput() {
        return new DataInputStream(getDataStream());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PluginMessagePacket that = (PluginMessagePacket) o;
        return Objects.deepEquals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
}
