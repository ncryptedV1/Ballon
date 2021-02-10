package de.ncrypted.ballon.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EntityChangeBlockListener implements Listener {

	@EventHandler
	public void onBlockFall(EntityChangeBlockEvent e) {
		Entity ent = e.getEntity();

		if (ent instanceof FallingBlock) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				Ballon b = Main.ballon.get(p);

				if (b != null) {
					if (ent.getEntityId() == b.getFallingBlock().getEntityId()) {
						b.setNewBlock();
						e.setCancelled(true);
					}
				}
			}
		}
	}
}
