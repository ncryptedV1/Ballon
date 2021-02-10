package de.ncrypted.ballon.main;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerUnleashEntityEvent;

public class EntityUnleashListener implements Listener {

	@EventHandler
	public void onEntityUnleash(PlayerUnleashEntityEvent e) {
		Entity ent = e.getEntity();
		Player p = e.getPlayer();

		if (Main.ballon.get(p) != null) {
			if (Main.ballon.get(p).getBat().getId() == ent.getEntityId()) {
				e.setCancelled(true);
			}
		}
	}
}
