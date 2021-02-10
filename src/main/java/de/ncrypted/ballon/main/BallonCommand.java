package de.ncrypted.ballon.main;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BallonCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Du bist kein Spieler!");
			return true;
		}
		Player p = (Player) sender;

		if (args.length != 2) {
			p.sendMessage("§cFalsche Nutzung! /ballon <Material> <Data>");
			return true;
		}

		if (Main.ballon.containsKey(p)) {
			Main.ballon.get(p).destroy();

			Main.ballon.remove(p);
			p.sendMessage("§6§lBallon§6 erfolgreich §cdeaktiviert§6!");
		} else if (!Main.ballon.containsKey(p)) {
			Material mat = Material.getMaterial(args[0].toUpperCase());

			Ballon ballon;

			int data = 0;
			try {
				data = Integer.valueOf(args[1]);
			} catch (IllegalArgumentException e) {
				p.sendMessage("§c" + args[2] + " ist kein gültiger Data Wert!");
			}

			try {
				ballon = new Ballon(p, mat, (byte) data);
			} catch (IllegalArgumentException e) {
				p.sendMessage("§cFalsches Material!");
				return true;
			}
			ballon.spawn();

			Main.ballon.put(p, ballon);

			p.sendMessage("§6§lBallon§6 erfolgreich §aaktiviert§6!");
		}

		return true;
	}

}
