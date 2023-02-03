package com.wonkglorg.selection.area;

import com.wonkglorg.mob.CustomMob;
import com.wonkglorg.selection.EntitySpawner;
import com.wonkglorg.utilitylib.selection.Cuboid;
import com.wonkglorg.utilitylib.utils.random.Random;
import com.wonkglorg.utilitylib.utils.random.WeightedRandomPicker;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class Area
{
	private final WeightedRandomPicker<CustomMob> weightedPicker;
	protected Cuboid cuboid;
	protected final World world;
	protected String name;
	
	protected final Set<CustomMob> spawnableEntityList = new HashSet<>();
	private final Map<Entity, CustomMob> aliveEntities = new HashMap<>();
	
	protected int minLevel;
	protected int maxLevel;
	protected int maxEntities;
	protected double spawnDelay;
	private long lastSpawn;
	protected int maxEntitiesPerSpawn;
	protected int playerSpawnRadius;
	private boolean active;
	
	public Area(Cuboid cuboid, World world, String name, int minLevel, int maxLevel, int maxEntities, boolean active)
	{
		this.cuboid = cuboid;
		this.world = world;
		this.name = name;
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.maxEntities = maxEntities;
		this.active = active;
		
		weightedPicker = new WeightedRandomPicker<>();
		for(CustomMob customMob : spawnableEntityList)
		{
			weightedPicker.addEntry(customMob, customMob.spawnChance());
		}
		
	}
	
	/**
	 * Causes updates to the custom mob abilities, without updates no abilities will be used
	 */
	public void update()
	{
		if(!active)
		{
			return;
		}
		//Stuff
	}
	
	/**
	 * Tries to spawn mobs until area mobcap is filled
	 */
	public void spawn()
	{
		if(!active)
		{
			return;
		}
		if(!(System.currentTimeMillis() - lastSpawn > spawnDelay * 1000))
		{
			return;
		}
		aliveEntities.keySet().removeIf(Predicate.not(Entity::isValid));
		
		int diff = maxEntities - aliveEntities.size();
		
		if(diff <= 0)
		{
			return;
		}
		System.out.println(diff);
		
		//randomly spawns between 1 and amount needed to fill cap of the region
		int spawnAmount = Math.min(Random.getNumberBetween(1, diff), maxEntitiesPerSpawn), count = 0;
		System.out.println(spawnAmount);
		
		while(count <= spawnAmount)
		{
			count++;
			
			//Add option to customize what blocks a mob can stand on by making it non static as a builder class?
			Location location = EntitySpawner.generateSpawnLocation(cuboid).clone().add(getRandomOffset(), 1, getRandomOffset());
			if(!location.getChunk().isLoaded())
			{
				return;
			}
			
			if(!location.getNearbyPlayers(playerSpawnRadius).isEmpty())
			{
				continue;
			}
			CustomMob typeToSpawn = weightedPicker.getRandom();
			if(typeToSpawn == null)
			{
				return;
			}
			lastSpawn = System.currentTimeMillis();
			aliveEntities.put(typeToSpawn.summon(location), typeToSpawn);
			
		}
	}
	
	public void setSpawnDelay(double spawnDelay)
	{
		this.spawnDelay = spawnDelay;
	}
	
	public void setMaxEntitiesPerSpawn(int maxEntitiesPerSpawn)
	{
		this.maxEntitiesPerSpawn = maxEntitiesPerSpawn;
	}
	
	private double getRandomOffset()
	{
		double random = Math.random();
		if(Math.random() > 0.5)
		{
			random *= -1;
		}
		return random;
	}
	
	public void addMob(CustomMob customMob)
	{
		spawnableEntityList.add(customMob);
		weightedPicker.addEntry(customMob, customMob.getChance());
	}
	
	public void addMob(List<CustomMob> customMobs)
	{
		spawnableEntityList.addAll(customMobs);
		for(CustomMob customMob : customMobs)
		{
			weightedPicker.addEntry(customMob, customMob.getChance());
		}
		
	}
	
	public Cuboid getCuboid()
	{
		return cuboid;
	}
	
	public void setCuboid(Cuboid cuboid)
	{
		this.cuboid = cuboid;
	}
	
	public World getWorld()
	{
		return world;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Set<CustomMob> getSpawnableEntityList()
	{
		return spawnableEntityList;
	}
	
	public Map<Entity, CustomMob> getAliveEntities()
	{
		return aliveEntities;
	}
	
	public int getMinLevel()
	{
		return minLevel;
	}
	
	public void setMinLevel(int minLevel)
	{
		this.minLevel = minLevel;
	}
	
	public int getMaxLevel()
	{
		return maxLevel;
	}
	
	public void setMaxLevel(int maxLevel)
	{
		this.maxLevel = maxLevel;
	}
	
	public int getMaxEntities()
	{
		return maxEntities;
	}
	
	public void setMaxEntities(int maxEntities)
	{
		this.maxEntities = maxEntities;
	}
	
	public int getPlayerSpawnRadius()
	{
		return playerSpawnRadius;
	}
	
	public void removeAliveEntity(Entity entity)
	{
		aliveEntities.remove(entity);
	}
	
	public void removeAllEntities()
	{
		for(Entity entity : aliveEntities.keySet())
		{
			entity.remove();
		}
	}
	
	public void removeAllAliveEntityType(EntityType entityType)
	{
		for(Entity entity : aliveEntities.keySet())
		{
			if(entity.getType() == entityType)
			{
				entity.remove();
			}
		}
	}
	
	public void setPlayerSpawnRadius(int playerSpawnRadius)
	{
		this.playerSpawnRadius = playerSpawnRadius;
	}
}