package com.wonkglorg.selection.editor;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SelectionListener implements Listener
{
	private final SelectionCreator selManager;
	
	public SelectionListener(SelectionCreator selManager)
	{
		this.selManager = selManager;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e)
	{
		
		if(e.getItem() == null)
		{
			return;
		}
		// set itemwand from config on startup in yml
		if(!e.getItem().isSimilar(selManager.getWand()))
		{
			return;
		}
		
		if(!selManager.editorContains(e.getPlayer()))
		{
			return;
		}
		if(e.getClickedBlock() == null)
		{
			return;
		}
		e.setCancelled(true);
		Player player = e.getPlayer();
		Location location = e.getClickedBlock().getLocation();
		selManager.handleClick(player, location, e.getAction());
	}
}