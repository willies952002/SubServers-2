package net.ME1312.SubServers.Bungee.Network.Packet;

import net.ME1312.SubServers.Bungee.Host.SubCreator;
import net.ME1312.SubServers.Bungee.Library.Util;
import net.ME1312.SubServers.Bungee.Library.Version.Version;
import net.ME1312.SubServers.Bungee.Network.Client;
import net.ME1312.SubServers.Bungee.Network.PacketIn;
import net.ME1312.SubServers.Bungee.Network.PacketOut;
import net.ME1312.SubServers.Bungee.SubPlugin;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Create Server Packet
 */
public class PacketCreateServer implements PacketIn, PacketOut {
    private SubPlugin plugin;
    private int response;
    private String message;
    private String id;

    /**
     * New PacketCreateServer (In)
     *
     * @param plugin SubPlugin
     */
    public PacketCreateServer(SubPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * New PacketCreateServer (Out)
     *
     * @param response Response ID
     * @param message Message
     * @param id Receiver ID
     */
    public PacketCreateServer(int response, String message, String id) {
        this.response = response;
        this.message = message;
        this.id = id;
    }

    @Override
    public JSONObject generate() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("r", response);
        json.put("m", message);
        return json;
    }

    @Override
    public void execute(Client client, JSONObject data) {
        try {
            if (data.getJSONObject("creator").getString("name").contains(" ")) {
                client.sendPacket(new PacketCreateServer(3, "server names cannot have spaces", (data.keySet().contains("id")) ? data.getString("id") : null));
            } else if (plugin.api.getSubServers().keySet().contains(data.getJSONObject("creator").getString("name").toLowerCase()) || SubCreator.isReserved(data.getJSONObject("creator").getString("name"))) {
                client.sendPacket(new PacketCreateServer(3, "There is already a subserver with that name", (data.keySet().contains("id")) ? data.getString("id") : null));
            } else if (!plugin.hosts.keySet().contains(data.getJSONObject("creator").getString("host").toLowerCase())) {
                client.sendPacket(new PacketCreateServer(4, "There is no Host with that name", (data.keySet().contains("id")) ? data.getString("id") : null));
            } else if (!plugin.hosts.get(data.getJSONObject("creator").getString("host").toLowerCase()).getCreator().getTemplates().keySet().contains(data.getJSONObject("creator").getString("template").toLowerCase()) ||
                    !plugin.hosts.get(data.getJSONObject("creator").getString("host").toLowerCase()).getCreator().getTemplate(data.getJSONObject("creator").getString("template")).isEnabled()) {
                client.sendPacket(new PacketCreateServer(6, "There is no template with that name", (data.keySet().contains("id")) ? data.getString("id") : null));
            } else if (new Version("1.8").compareTo(new Version(data.getJSONObject("creator").getString("version"))) > 0) {
                client.sendPacket(new PacketCreateServer(7, "SubCreator cannot create servers before Minecraft 1.8", (data.keySet().contains("id")) ? data.getString("id") : null));
            } else if (data.getJSONObject("creator").getInt("port") <= 0 || data.getJSONObject("creator").getInt("port") > 65535) {
                client.sendPacket(new PacketCreateServer(8, "Invalid Port Number", (data.keySet().contains("id")) ? data.getString("id") : null));
            } else {
                if (plugin.hosts.get(data.getJSONObject("creator").getString("host").toLowerCase()).getCreator().create((data.keySet().contains("player"))?UUID.fromString(data.getString("player")):null, data.getJSONObject("creator").getString("name"), plugin.hosts.get(data.getJSONObject("creator").getString("host").toLowerCase()).getCreator().getTemplate(data.getJSONObject("creator").getString("template")), new Version(data.getJSONObject("creator").getString("version")), data.getJSONObject("creator").getInt("port"))) {
                    if (data.keySet().contains("wait") && data.getBoolean("wait")) {
                        new Thread(() -> {
                            try {
                                plugin.hosts.get(data.getJSONObject("creator").getString("host").toLowerCase()).getCreator().waitFor();
                                client.sendPacket(new PacketCreateServer(0, "Created SubServer", (data.keySet().contains("id")) ? data.getString("id") : null));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    } else {
                        client.sendPacket(new PacketCreateServer(0, "Creating SubServer", (data.keySet().contains("id")) ? data.getString("id") : null));
                    }
                } else {
                    client.sendPacket(new PacketCreateServer(1, "Couldn't create SubServer", (data.keySet().contains("id")) ? data.getString("id") : null));
                }

            }
        } catch (Throwable e) {
            client.sendPacket(new PacketCreateServer(2, e.getClass().getCanonicalName() + ": " + e.getMessage(), (data.keySet().contains("id")) ? data.getString("id") : null));
            e.printStackTrace();
        }
    }

    @Override
    public Version getVersion() {
        return new Version("2.11.0a");
    }
}
