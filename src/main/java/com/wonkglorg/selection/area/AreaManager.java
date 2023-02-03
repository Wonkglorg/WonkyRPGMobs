package com.wonkglorg.selection.area;

import com.wonkglorg.WonkyRPGMobs;
import com.wonkglorg.mob.CustomMob;
import com.wonkglorg.utilitylib.message.ChatColor;
import com.wonkglorg.utilitylib.selection.Cuboid;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AreaManager
{
	private final List<Area> areaList = new ArrayList<>();
	
	public AreaManager()
	{
		new BukkitRunnable(){
			
			@Override
			public void run()
			{
				update();
			}
		}.runTaskTimer(WonkyRPGMobs.getInstance(),10,60);
	}
	
	public void update()
	{
		for(Area area : areaList)
		{
			area.spawn();
			area.update();
		}
	}
	
	public void removeEntities(){
		for(Area area:areaList){
			area.removeAllEntities();
		}
	}
	
	public void addArea(Area area)
	{
		areaList.add(area);
	}
	
	public void removeArea(String area)
	{
		areaList.removeIf(area1 -> Objects.equals(area1.name, area));
	}
	
	public void removeArea(Area area)
	{
		areaList.remove(area);
	}
	
	public boolean createNewArea(Cuboid cuboid, World world, String name)
	{
		for(Area area : areaList)
		{
			if(area.getName() == null || area.getName().isEmpty())
			{
				continue;
			}
			if(area.getName().equalsIgnoreCase(name) && area.getWorld() == world)
			{
				return false;
			}
		}
		Area area = new Area(cuboid, world, name, 1, 5, 50, true);
		CustomMob customMob = new CustomMob(ChatColor.RED + "Medium warm Zombie",100, EntityType.ZOMBIE,100,100.0,10,true,true);
		customMob.setShowCustomName(true);
		
		CustomMob customMob1 = new CustomMob(ChatColor.BLUE + "Deep freeze Skeleton",100, EntityType.SKELETON,100,100.0,10,true,true);
		customMob1.setShowCustomName(true);
		
		area.addMob(customMob);
		area.addMob(customMob1);
		
		areaList.add(area);

		return true;
	}
}