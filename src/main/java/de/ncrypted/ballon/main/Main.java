package de.ncrypted.ballon.main;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private static Main main;
	
	public static HashMap<Player, Ballon> ballon = new HashMap<>();
	
	public void onEnable() {
		main = this;
		getCommand("ballon").setExecutor(new BallonCommand());
		getServer().getPluginManager().registerEvents(new EntityInteractListener(), this);
		getServer().getPluginManager().registerEvents(new EntityChangeBlockListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
		getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
		getServer().getPluginManager().registerEvents(new EntityUnleashListener(), this);
		
		CustomEntityType.registerEntities();
	}
	
	public void onDisable() {
		CustomEntityType.unregisterEntities();
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			Ballon b = ballon.get(p);
			
			if(b != null) {
				b.destroy();
			}
		}
	}
	
	public static Main getInstance() {
		return main;
	}
}
