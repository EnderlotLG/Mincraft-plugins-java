package com.ferchogs.welcomegift;

import org.bukkit.Bukkit; import org.bukkit.ChatColor; import org.bukkit.Material; import org.bukkit.command.Command; import org.bukkit.command.CommandSender; import org.bukkit.configuration.file.FileConfiguration; import org.bukkit.entity.Player; import org.bukkit.event.EventHandler; import org.bukkit.event.Listener; import org.bukkit.event.player.PlayerJoinEvent; import org.bukkit.inventory.ItemStack; import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet; import java.util.UUID;

public class WelcomeGift extends JavaPlugin implements Listener { private final HashSet<UUID> rewardedPlayers = new HashSet<>(); private FileConfiguration config;

@Override
public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
    saveDefaultConfig();
    config = getConfig();
}

@Override
public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (label.equalsIgnoreCase("regalowelcome")) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            config = getConfig();
            sender.sendMessage(ChatColor.GREEN + "[WelcomeGift] Configuración recargada.");
            return true;
        }
    }
    return false;
}

@EventHandler
public void onPlayerJoin(PlayerJoinEvent e) {
    Player player = e.getPlayer();
    UUID uuid = player.getUniqueId();

    if (!config.contains("players." + uuid)) {
        // Dar ítems de bienvenida
        player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
        player.getInventory().addItem(new ItemStack(Material.BREAD, 8));
        player.getInventory().addItem(new ItemStack(Material.IRON_CHESTPLATE));
        player.giveExp(30);

        // Mensaje
        player.sendMessage(ChatColor.AQUA + "¡Bienvenido al servidor, " + player.getName() + "! Aquí tienes un regalo de bienvenida 🎁");

        // Marcar como recibido
        config.set("players." + uuid, true);
        saveConfig();
    }
}

}

