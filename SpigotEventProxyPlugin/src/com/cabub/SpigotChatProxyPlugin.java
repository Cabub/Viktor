package com.cabub;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

import java.util.logging.Logger;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;;

public class SpigotChatProxyPlugin extends JavaPlugin  implements Listener{
	FileConfiguration config = getConfig();
    Logger logger = Bukkit.getLogger();

    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	// set configurables
    	config.addDefault("proxy", true);
        config.options().copyDefaults(true);
        saveConfig();
    	
    	// register commands
        this.getCommand("toggle_chat_proxy").setExecutor(new CommandToggleChatProxy());
        
        // register listeners
        getServer().getPluginManager().registerEvents(this, this);
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
    
    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event)
    {
        String message = event.getMessage();
        if (config.getBoolean("proxy")) {
	        logger.log(Level.INFO, "proxying chat message - \"" + message + "\"");
	        // TODO fire async API call, handle result
        }
    }
    
}
