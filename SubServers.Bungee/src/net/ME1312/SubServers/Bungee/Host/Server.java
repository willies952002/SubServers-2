package net.ME1312.SubServers.Bungee.Host;

import net.ME1312.SubServers.Bungee.Library.ExtraDataHandler;
import net.ME1312.SubServers.Bungee.Network.ClientHandler;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.List;

/**
 * Server Interface
 */
public interface Server extends ServerInfo, ClientHandler, ExtraDataHandler {

    /**
     * Get the Display Name of this Server
     *
     * @return Display Name
     */
    String getDisplayName();

    /**
     * Sets the Display Name for this Server
     *
     * @param value Value (or null to reset)
     */
    void setDisplayName(String value);

    /**
     * Get this Server's Groups
     *
     * @return Group names
     */
    List<String> getGroups();

    /**
     * Add this Server to a Group
     *
     * @param value Group name
     */
    void addGroup(String value);

    /**
     * Remove this Server from a Group
     *
     * @param value value Group name
     */
    void removeGroup(String value);

    /**
     * If the server is hidden from players
     *
     * @return Hidden Status
     */
    boolean isHidden();

    /**
     * Set if the server is hidden from players
     *
     * @param value Value
     */
    void setHidden(boolean value);

    /**
     * Gets the MOTD of the Server
     *
     * @return Server MOTD
     */
    String getMotd();

    /**
     * Sets the MOTD of the Server
     *
     * @param value Value
     */
    void setMotd(String value);

    /**
     * Gets if the Server is Restricted
     *
     * @return Restricted Status
     */
    boolean isRestricted();

    /**
     * Sets if the Server is Restricted
     *
     * @param value Value
     */
    void setRestricted(boolean value);
}
