package com.wonkglorg.selection;

import com.wonkglorg.utilitylib.selection.Cuboid;
import com.wonkglorg.utilitylib.utils.random.Random;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.HashSet;

@SuppressWarnings("unsused")
public class EntitySpawner
{
	private static final HashSet<Location> usedLocations = new HashSet<>();
	
	public static Location generateSpawnLocation(Cuboid c)
	{
		boolean isLoaded = false;
		for(Chunk chunk : c.getChunks())
		{
			if(chunk.isLoaded())
			{
				isLoaded = true;
				break;
			}
		}
		
		if(!isLoaded)
		{
			return null;
		}
		
		///make it so only loaded chunks can be chosen for spawning instead of having to reroll until it works
		
		int x = Random.getNumberBetween(c.getLowerX(), c.getUpperX());
		int y = Random.getNumberBetween(c.getLowerY(), c.getUpperY());
		int z = Random.getNumberBetween(c.getLowerZ(), c.getUpperZ());
		Location location = new Location(c.getWorld(), x, y, z);
		
		if(usedLocations.contains(location))
		{
			return generateSpawnLocation(c);
		} else
		{
			if(!isSpawnable(location))
			{
				usedLocations.add(location);
				return generateSpawnLocation(c);
			}
			usedLocations.clear();
			return location;
		}
	}
	
	public Location generateSpawnLocation(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
	{
		return generateSpawnLocation(new Cuboid(world, minX, minY, minZ, maxX, maxY, maxZ));
	}
	//customize the spawnable method to allow changes in where they can spawn?
	
	//Allow mobs with smaller height to spawn in 2 high, piglins to spawn on magma etc
	private static boolean isSpawnable(Location loc)
	{
		if(!loc.getChunk().isLoaded()){
			return false;
		}
		Block feetBlock = loc.getBlock(), headBlock = loc.clone().add(0, 1, 0).getBlock(), upperBlock = loc.clone().add(0, 2, 0).getBlock();
		return feetBlock.isSolid() &&
			   !feetBlock.isLiquid() &&
			   headBlock.isPassable() &&
			   !headBlock.isLiquid() &&
			   upperBlock.isPassable() &&
			   !upperBlock.isLiquid();
	}
	
}