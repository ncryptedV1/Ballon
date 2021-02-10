package de.ncrypted.ballon.main;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R2.EntityBat;
import net.minecraft.server.v1_8_R2.MobEffect;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.World;

public class CustomBallonBat extends EntityBat {

	public CustomBallonBat(World world) {
		super(world);

		List<?> goalB = (List<?>) getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
		goalB.clear();
		List<?> goalC = (List<?>) getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
		goalC.clear();
		List<?> targetB = (List<?>) getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
		targetB.clear();
		List<?> targetC = (List<?>) getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
		targetC.clear();

		addEffect(new MobEffect(14, Integer.MAX_VALUE, -1));
		noclip = true;
	}

	public void makeSound(String s, float f, float f1) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Ballon b = Main.ballon.get(p);

			if (b != null) {
				if (b.getBat().getId() == this.getId()) {
					return;
				}
			}
		}
		
		super.makeSound(s, f, f1);
	}

	public static Object getPrivateField(String fieldName, Class<PathfinderGoalSelector> clazz, Object object) {
		Field field;
		Object o = null;

		try {
			field = clazz.getDeclaredField(fieldName);

			field.setAccessible(true);

			o = field.get(object);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return o;
	}

	protected void E() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Ballon b = Main.ballon.get(p);

			if (b != null) {
				if (b.getBat().getId() == this.getId()) {
					this.setAsleep(false);
					Vector v = p.getLocation().add(-1, 2, -1).toVector()
							.subtract(this.getBukkitEntity().getLocation().toVector());

					getBukkitEntity().setVelocity(v);
					
					return;
				}
			}
		}
		
		super.E();
	}
}
