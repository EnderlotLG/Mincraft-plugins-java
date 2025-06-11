package com.ferchogs.statstracker;

import org.bukkit.Bukkit; import org.bukkit.ChatColor; import org.bukkit.entity.Player; import org.bukkit.event.EventHandler; import org.bukkit.event.Listener; import org.bukkit.event.entity.EntityDeathEvent; import org.bukkit.event.player.PlayerExpChangeEvent; import org.bukkit.event.player.PlayerJoinEvent; import org.bukkit.plugin.java.JavaPlugin; import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap; import java.util.UUID;

public class StatsTracker extends JavaPlugin implements Listener { private FileConfiguration config; private final HashMap<UUID, Integer> kills = new HashMap<>(); private final HashMap<UUID, Integer> deaths = new HashMap<>(); private final HashMap<UUID, Integer> xpGained = new HashMap<>();

@Override
public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
    getCommand("estadisticas").setExecutor((sender, command, label, args) -> {
        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();
            int k = kills.getOrDefault(uuid, 0);
            int d = deaths.getOrDefault(uuid, 0);
            int xp = xpGained.getOrDefault(uuid, 0);
            player.sendMessage(ChatColor.YELLOW + "\n>> Tus Estadísticas <<");
            player.sendMessage(ChatColor.GREEN + "Muertes: " + d);
            player.sendMessage(ChatColor.GREEN + "Asesinatos: " + k);
            player.sendMessage(ChatColor.GREEN + "XP ganada: " + xp);
        }
        return true;
    });
    saveDefaultConfig();
    config = getConfig();
}

@EventHandler
public void onPlayerJoin(PlayerJoinEvent e) {
    UUID uuid = e.getPlayer().getUniqueId();
    kills.put(uuid, config.getInt(uuid + ".kills", 0));
    deaths.put(uuid, config.getInt(uuid + ".deaths", 0));
    xpGained.put(uuid, config.getInt(uuid + ".xp", 0));
}

@EventHandler
public void onPlayerDeath(EntityDeathEvent e) {
    if (e.getEntity() instanceof Player dead) {
        UUID deadId = dead.getUniqueId();
        deaths.put(deadId, deaths.getOrDefault(deadId, 0) + 1);
    }

    if (e.getEntity().getKiller() instanceof Player killer) {
        UUID killerId = killer.getUniqueId();
        kills.put(killerId, kills.getOrDefault(killerId, 0) + 1);
    }
}

@EventHandler
public void onXP(PlayerExpChangeEvent e) {
    Player p = e.getPlayer();
    UUID uuid = p.getUniqueId();
    xpGained.put(uuid, xpGained.getOrDefault(uuid, 0) + e.getAmount());
}

@Override
public void onDisable() {
    for (UUID uuid : kills.keySet()) {
        config.set(uuid + ".kills", kills.get(uuid));
        config.set(uuid + ".deaths", deaths.getOrDefault(uuid, 0));
        config.set(uuid + ".xp", xpGained.getOrDefault(uuid, 0));
    }
    saveConfig();
}

}

