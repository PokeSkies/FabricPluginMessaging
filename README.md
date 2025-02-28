# Fabric Plugin Messaging
<img src="https://img.shields.io/badge/Enviroment-Server-purple" alt="Enviroment Server"> <a href="https://discord.gg/invite/cgBww275Fg" rel="noopener nofollow ugc"><img src="https://img.shields.io/discord/1158447623989116980?color=blue&amp;logo=discord&amp;label=Discord" alt="Discord"></a>

A Fabric plugin messaging library! This server-sided mod will register a Plugin Message Packet (`PluginMessagePacket.class`) and a Plugin Message Event (`PluginMessageEvent#EVENT`) for developers to use in their projects.

Without this mod, developers would need to register their own packets, which would cause conflicts if multiple are present at the same time. By using the Packet and Listener provided here, this issue is avoided.

## Developer Info
Add the mod as a dependency in your gradle build script! This will include the API in your final build.
```
dependencies {
    modImplementation(include("com.pokeskies:fabricpluginmessage:1.0.0"))
}
```

### Sending
Then you can send Plugin Messages by creating an instance of the PluginMessagePacket class and then sending it! You can either create it by passing a byte array or a FriendlyByteBuf:
```java
ByteArrayDataOutput outputStream = ByteStreams.newDataOutput();
outputStream.writeUTF("Connect");
outputStream.writeUTF("cobblemon");

ServerPlayNetworking.send(player, new PluginMessagePacket(outputStream.toByteArray()));
```

### Receiving
If you need to receive a Plugin Message from the proxy, you can subscribe to the PluginMessageEvent:
```java
PluginMessageEvent.EVENT.register((payload, context) -> {
    ByteArrayDataInput inputStream  = ByteStreams.newDataInput(payload.getData());
    String channel = inputStream.readUTF();
    if (channel.equals("GetServers")) {
        String serversList = inputStream.readUTF();
        System.out.println("Proxy Servers: " + serversList);
    }
});
```

## Support
A community support Discord has been opened up for all Skies Development related projects! Feel free to join and ask questions or leave suggestions :)

<a class="discord-widget" href="https://discord.gg/cgBww275Fg" title="Join us on Discord"><img src="https://discordapp.com/api/guilds/1158447623989116980/embed.png?style=banner2"></a>
