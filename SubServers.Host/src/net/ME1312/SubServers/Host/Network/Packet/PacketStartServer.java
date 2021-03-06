package net.ME1312.SubServers.Host.Network.Packet;

import net.ME1312.SubServers.Host.Library.JSONCallback;
import net.ME1312.SubServers.Host.Library.Util;
import net.ME1312.SubServers.Host.Library.Version.Version;
import net.ME1312.SubServers.Host.Network.PacketIn;
import net.ME1312.SubServers.Host.Network.PacketOut;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;

/**
 * Start Server Packet
 */
public class PacketStartServer implements PacketIn, PacketOut {
    private static HashMap<String, JSONCallback[]> callbacks = new HashMap<String, JSONCallback[]>();
    private UUID player;
    private String server;
    private String id;

    /**
     * New PacketStartServer (In)
     */
    public PacketStartServer() {}

    /**
     * New PacketStartServer (Out)
     *
     * @param player Player Starting
     * @param server Server
     * @param callback Callbacks
     */
    public PacketStartServer(UUID player, String server, JSONCallback... callback) {
        if (Util.isNull(server, callback)) throw new NullPointerException();
        this.player = player;
        this.server = server;
        this.id = Util.getNew(callbacks.keySet(), UUID::randomUUID).toString();
        callbacks.put(id, callback);
    }

    @Override
    public JSONObject generate() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        if (player != null) json.put("player", player.toString());
        json.put("server", server);
        return json;
    }

    @Override
    public void execute(JSONObject data) {
        for (JSONCallback callback : callbacks.get(data.getString("id"))) callback.run(data);
        callbacks.remove(data.getString("id"));
    }

    @Override
    public Version getVersion() {
        return new Version("2.11.0a");
    }
}
