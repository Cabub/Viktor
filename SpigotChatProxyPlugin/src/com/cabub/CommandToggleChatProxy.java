/**
 * 
 */
package com.cabub;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.Bukkit;

/**
 * @author cabub
 *
 */
public class CommandToggleChatProxy implements CommandExecutor {
	FileConfiguration config = Bukkit.getPluginManager().getPlugin("SpigotChatProxyPlugin").getConfig();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		boolean proxy = config.getBoolean("proxy");
		        
		config.set("proxy", !proxy);
		if (!proxy) {
			Bukkit.broadcast("Chat proxying is now active", "proxymaster");
		}
		else {
			Bukkit.broadcast("Chat proxying is now disabled", "proxymaster");
		}
        return true;
	}
}
