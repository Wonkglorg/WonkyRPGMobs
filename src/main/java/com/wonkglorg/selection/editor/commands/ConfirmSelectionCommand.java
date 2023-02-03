package com.wonkglorg.selection.editor.commands;

import com.wonkglorg.selection.area.AreaManager;
import com.wonkglorg.selection.editor.SelectionCreator;
import com.wonkglorg.utilitylib.command.Command;
import com.wonkglorg.utilitylib.message.Message;
import com.wonkglorg.utilitylib.selection.Cuboid;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConfirmSelectionCommand extends Command
{
	private final SelectionCreator selManager;
	private final AreaManager areaManager;
	
	/**
	 * Instantiates a new Command.
	 *
	 * @param plugin the plugin
	 * @param name the name
	 */
	public ConfirmSelectionCommand(@NotNull JavaPlugin plugin, @NotNull String name, SelectionCreator selManager, AreaManager areaManager)
	{
		super(plugin, name);
		this.selManager = selManager;
		this.areaManager = areaManager;
	}
	
	@Override
	public boolean allowConsole()
	{
		return false;
	}
	
	@Override
	public boolean execute(Player player, String[] args)
	{
		
		if(!selManager.editorContains(player))
		{
			Message.msgPlayer(player, "You are not currently editing a selection");
			return true;
		}
		
		String name = selManager.getSelectionName(player);
		Cuboid cuboid = selManager.getSelection(player);
		if(cuboid == null){
			Message.msgPlayer(player,"No Selection specified");
			return true;
		}
		
		if(areaManager.createNewArea(cuboid, cuboid.getWorld(), name))
		{
			Message.msgPlayer(player, "Area Successfully Created!");
		} else
		{
			Message.msgPlayer(player, "Area name already exists in this world");
		}
		
		selManager.removeEditorList(player);
		selManager.removeEditSelectionMap(player);
		selManager.removeNewSelectionMap(player);
		
		return true;
	}
	
	@Override
	public List<String> tabComplete(@NotNull Player player, String[] args)
	{
		return null;
	}
}