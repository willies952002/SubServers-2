package net.ME1312.SubServers.Client.Bukkit.Network.Packet;

import net.ME1312.SubServers.Client.Bukkit.Library.Config.YAMLSection;
import net.ME1312.SubServers.Client.Bukkit.Library.Util;
import net.ME1312.SubServers.Client.Bukkit.Library.Version.Version;
import net.ME1312.SubServers.Client.Bukkit.Network.PacketIn;
import net.ME1312.SubServers.Client.Bukkit.Network.PacketOut;
import net.ME1312.SubServers.Client.Bukkit.SubPlugin;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Download Lang Packet
 */
public class PacketDownloadLang implements PacketIn, PacketOut {
    private SubPlugin plugin;

    /**
     * New PacketDownloadLang (In)
     */
    public PacketDownloadLang() {}

    /**
     * New PacketDownloadLang (Out)
     *
     * @param plugin SubServers.Client
     */
    public PacketDownloadLang(SubPlugin plugin) {
        if (Util.isNull(plugin)) throw new NullPointerException();
        this.plugin = plugin;
    }

    @Override
    public JSONObject generate() {
        return null;
    }

    @Override
    public void execute(JSONObject data) {
        data.put("Updated", Calendar.getInstance().getTime().getTime());
        plugin.lang = new YAMLSection(data);
        Bukkit.getLogger().info("SubData > Lang Settings Downloaded");
    }

    @Override
    public Version getVersion() {
        return new Version("2.11.0a");
    }
}
