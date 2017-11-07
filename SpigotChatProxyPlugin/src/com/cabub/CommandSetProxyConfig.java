package com.cabub;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;

import org.bukkit.Bukkit;


public class CommandSetProxyConfig implements CommandExecutor {
	FileConfiguration config = Bukkit.getPluginManager().getPlugin("SpigotChatProxyPlugin").getConfig();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		//setUsage(command);
		if (args.length == 2 && validConfig(args[0])) {
			// Object old_setting = config.get(args[0]);
			// TODO figure out how to correctly type these
			config.set(args[0], args[1]);
			return true;
		}
		return false;
	}
	
	private boolean validConfig(String key) {
		Set<String> keys = config.getKeys(true);
		if (keys.contains(key)) {
			return true;
		}
		return false;
	}
}
