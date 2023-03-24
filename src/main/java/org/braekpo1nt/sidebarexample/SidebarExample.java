package org.braekpo1nt.sidebarexample;

import fr.mrmicky.fastboard.FastBoard;
import it.unimi.dsi.fastutil.ints.AbstractInt2BooleanMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SidebarExample extends JavaPlugin implements Listener {
    
    private final Map<UUID, FastBoard> boards = new HashMap<>();
    
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    
        for (Player player : Bukkit.getOnlinePlayers()) {
            FastBoard board = new FastBoard(player);
            board.updateTitle(ChatColor.RED + "FastBoard");
            this.boards.put(player.getUniqueId(), board);
        }
        
        Bukkit.getLogger().info("Sidebar example loaded successfully");
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : this.boards.values()) {
                updateBoard(board);
            }
        }, 0, 1);
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.RED + "FastBoard");
        this.boards.put(player.getUniqueId(), board);
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        FastBoard board = this.boards.remove(player.getUniqueId());
        if(board != null) {
            board.delete();
        }
    }
    
    private void updateBoard(FastBoard board) {
        board.updateLines(
                "",
                "Players: " + getServer().getOnlinePlayers().size(),
                "",
                "Kills: "+board.getPlayer().getStatistic(Statistic.WALK_ONE_CM),
                ""
        );
    }
    
    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            FastBoard board = this.boards.remove(player.getUniqueId());
            if (board != null) {
                board.delete();
            }
        }
    }
}
