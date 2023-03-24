package org.braekpo1nt.sidebarexample;

import me.catcoder.sidebar.Sidebar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class SidebarExample extends JavaPlugin {
    
    private Sidebar sidebar;
    
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("Sidebar example loaded successfully");
        sidebar = new Sidebar("Title");
        sidebar.addDynamicLine(player -> player.getName());
        for (Player player : Bukkit.getOnlinePlayers()) {
            sidebar.addViewer(player);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
