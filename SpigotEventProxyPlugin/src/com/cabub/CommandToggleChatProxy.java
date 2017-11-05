/**
 * 
 */
package com.cabub;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;

/**
 * @author cabub
 *
 */
public class CommandToggleChatProxy implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO Find a way to change the parent's config from here.
		Bukkit.broadcastMessage("Toggling Event Proxing.");
        return true;
	}

}
