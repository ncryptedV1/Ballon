package de.ncrypted.ballon.main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Ballon b = Main.ballon.get(e.getPlayer());

		if (b != null) {
			b.destroy();
		}
	}
}
