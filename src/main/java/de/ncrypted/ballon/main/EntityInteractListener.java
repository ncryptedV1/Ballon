package de.ncrypted.ballon.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EntityInteractListener implements Listener {

	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent e) {
		Player clicker = e.getPlayer();
		Entity ent = e.getRightClicked();

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (Main.ballon.get(p) != null) {
				if (Main.ballon.get(p).getFallingBlock().getEntityId() == ent.getEntityId()) {
					if (!clicker.getUniqueId().equals(p.getUniqueId()))
						ent.setPassenger(clicker);
				}
			}
		}

		if (ent instanceof LeashHitch) {
			LeashHitch leash = (LeashHitch) ent;

			if (Main.ballon.get(clicker).getFallingBlock().getEntityId() == leash.getPassenger().getEntityId()) {
				e.setCancelled(true);
			}
		}
	}
}
