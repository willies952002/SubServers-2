package net.ME1312.SubServers.Host.Network;

import net.ME1312.SubServers.Host.Library.Version.Version;
import org.json.JSONObject;

/**
 * PacketIn Layout Class
 */
public interface PacketIn {
    /**
     * Execute Incoming Packet
     *
     * @param data Incoming Data
     */
    void execute(JSONObject data) throws Throwable;

    /**
     * Get Packet Version
     *
     * @return Packet Version
     */
    Version getVersion();
}
