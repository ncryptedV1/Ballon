package de.ncrypted.ballon.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.entity.Bat;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R2.Entity;

public class Ballon {
	private Player holder;
	private Entity bat;
	private FallingBlock fallingBlock;
	private Material blockType;
	private byte data;
	private int naturalRenewSched;
	private int noPassengerRenewSched;

	@SuppressWarnings("deprecation")
	public Ballon(Player holder, Material blockType, byte data) {
		this.holder = holder;
		this.blockType = blockType;
		this.fallingBlock = this.holder.getLocation().getWorld()
				.spawnFallingBlock(this.holder.getLocation().add(-1, 2, -1), blockType, data == 0 ? 0 : data);
		this.data = data == 0 ? 0 : data;
	}

	public void spawn() {
		Location loc = holder.getLocation();
		bat = new CustomBallonBat(((CraftWorld) loc.getWorld()).getHandle());
		bat.setLocation(loc.getX() - 1, loc.getY() + 2, loc.getZ() - 1, loc.getYaw(), loc.getPitch());
		((CraftWorld) loc.getWorld()).getHandle().addEntity(bat);
		bat.getBukkitEntity().setPassenger(fallingBlock);
		fallingBlock.setDropItem(false);
		((Bat) bat.getBukkitEntity()).setLeashHolder(holder);

		naturalRenewSched = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			public void run() {
				setNewBlock();
			}

		}, 20 * 30, 20 * 30);
		
		noPassengerRenewSched = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			public void run() {
				if(bat.getBukkitEntity().getPassenger() == null) {
					setNewBlock();
				}
			}

		}, 20, 20);
	}

	public void destroy() {
		Bukkit.getScheduler().cancelTask(naturalRenewSched);
		Bukkit.getScheduler().cancelTask(noPassengerRenewSched);
		this.fallingBlock.remove();
		this.bat.getBukkitEntity().remove();
	}

	@SuppressWarnings("deprecation")
	public void setNewBlock() {
		fallingBlock.remove();
		fallingBlock = null;
		fallingBlock = this.holder.getLocation().getWorld()
				.spawnFallingBlock(this.holder.getLocation().add(-1, 2, -1), blockType, data);
		fallingBlock.setDropItem(false);
		bat.getBukkitEntity().setPassenger(fallingBlock);
	}

	public Entity getBat() {
		return this.bat;
	}

	public FallingBlock getFallingBlock() {
		return this.fallingBlock;
	}
}
