package com.wonkglorg.selection.editor;

import com.wonkglorg.WonkyRPGMobs;
import com.wonkglorg.utilitylib.builder.SelectionBuilder;
import com.wonkglorg.utilitylib.message.Message;
import com.wonkglorg.utilitylib.selection.Cuboid;
import com.wonkglorg.utilitylib.selection.Cuboid.CuboidDirection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class SelectionCreator implements Listener
{
	private final Map<Player, String> editorList = new HashMap<>();
	private final Map<Player, Location> newSelectionMap = new HashMap<>();
	private final Map<Player, SelectionBuilder> editSelectionMap = new HashMap<>();
	private ItemStack wand;
	
	public SelectionCreator()
	{
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				if(editSelectionMap.isEmpty())
				{
					return;
				}
				for(SelectionBuilder builder : editSelectionMap.values())
				{
					if(builder != null && builder.build() != null)
					{
						builder.build().expand(CuboidDirection.East, 1).expand(CuboidDirection.North, 1).expand(CuboidDirection.Up, 1).outline(
								Particle.FLAME);
					}
				}
			}
		}.runTaskTimer(WonkyRPGMobs.getInstance(), 10, 20);
		
		setWand(new ItemStack(Material.STICK));
	}
	
	public void handleClick(Player player, Location location, Action click)
	{
		if(newSelectionMap.containsKey(player))
		{
			Location firstLocation = newSelectionMap.get(player);
			if(location.getWorld() != firstLocation.getWorld())
			{
				Message.msgPlayer(player, "First corner is in a different world");
				return;
			}
			if(location == firstLocation)
			{
				Message.msgPlayer(player, "First corner already set to this block");
				return;
			}
			newSelectionMap.remove(player);
			editSelectionMap.put(player, new SelectionBuilder(firstLocation, location));
			return;
		}
		
		if(editSelectionMap.containsKey(player))
		{
			SelectionBuilder builder = editSelectionMap.get(player);
			if(click == Action.LEFT_CLICK_BLOCK)
			{
				builder.setCorner1(location);
				
				Message.msgPlayer(player, "Set First Location");
			} else if(click == Action.RIGHT_CLICK_BLOCK)
			{
				builder.setCorner2(location);
				Message.msgPlayer(player, "Set Second Location");
			}
			return;
		}
		
		newSelectionMap.put(player, location);
		Message.msgPlayer(player, "Set First Location");
	}
	
	public void setWand(ItemStack wand)
	{
		this.wand = wand;
	}
	
	public void addEditorList(Player player, String name)
	{
		editorList.put(player, name);
	}
	
	public void removeEditorList(Player player)
	{
		editorList.remove(player);
	}
	
	public void removeEditSelectionMap(Player player)
	{
		editSelectionMap.remove(player);
	}
	
	public void addNewSelectionMap(Player player, Location location)
	{
		newSelectionMap.put(player, location);
	}
	
	public void removeNewSelectionMap(Player player)
	{
		newSelectionMap.remove(player);
	}
	
	public Cuboid getSelection(Player player)
	{
		return editSelectionMap.get(player).build();
	}
	
	public String getSelectionName(Player player)
	{
		return editorList.get(player);
	}
	
	public boolean editorContains(Player player)
	{
		return editorList.containsKey(player);
	}
	
	public ItemStack getWand()
	{
		return wand;
	}
}