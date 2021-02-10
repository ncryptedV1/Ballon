package de.ncrypted.ballon.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Ballon b = Main.ballon.get(p);

			if (b != null) {
				if(b.getBat().getId() == e.getEntity().getEntityId()) {
					e.setCancelled(true);
				}
			}
		}
	}
}
