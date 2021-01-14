package me.bubbles.Links;


import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
	}

	@Override
	public void onDisable() {
		
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("links")) {
			Player player = (Player) sender;

			if (!sender.hasPermission("links.reload")) {
				sender.sendMessage(ChatColor.RED + "You cannot run this command");
				return true;
			}
			
			if (args.length == 0) {
				// /links
				ArrayList<TextComponent> messages = new ArrayList<TextComponent>();
				ArrayList<String> hover = new ArrayList<String>();
				ArrayList<String> links = new ArrayList<String>();
				ArrayList<String> prefix = new ArrayList<String>();
				ArrayList<String> suffix = new ArrayList<String>();
				

				for (String link : this.getConfig().getStringList("links")) { links.add(link); }
				for (String hov : this.getConfig().getStringList("hover")) { hover.add(hov); }
				for (String msg : this.getConfig().getStringList("prefix_messages")) { prefix.add(msg); }
				for (String msg : this.getConfig().getStringList("suffix_messages")) { suffix.add(msg); }
				
				
				int i = 0;
				String hovering = "";
				String linked_message = "";
				String msg_prefix = "";
				String msg_suffix = "";
				
				
				for (String msg : this.getConfig().getStringList("linked_message")) {
					
					hovering = ChatColor.translateAlternateColorCodes('&', hover.get(i));
					msg_prefix = ChatColor.translateAlternateColorCodes('&', prefix.get(i));
					msg_suffix = ChatColor.translateAlternateColorCodes('&', suffix.get(i));
					linked_message = ChatColor.translateAlternateColorCodes('&', msg);
					
					TextComponent display = new TextComponent(linked_message);
					TextComponent str_prefix = new TextComponent(msg_prefix);
					TextComponent str_suffix = new TextComponent(msg_suffix);
					
					messages.add(display);
					messages.get(i).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hovering).create()));
					messages.get(i).setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, links.get(i)));
					messages.set(i, display);
					player.spigot().sendMessage(str_prefix, messages.get(i), str_suffix);
					i++;
					
				}
				return true;
				
			}
			if (args.length > 0) {
				// /links reload
				if (args[0].equalsIgnoreCase("reload")) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("reload")));
					this.reloadConfig();
					
				}
			}
		}
		return false;
		
	}
}
