package com.wonkglorg.selection.editor.commands;

import com.wonkglorg.selection.editor.SelectionCreator;
import com.wonkglorg.utilitylib.command.Command;
import com.wonkglorg.utilitylib.message.ChatColor;
import com.wonkglorg.utilitylib.message.Message;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CreateSelectionCommand extends Command
{
	private final SelectionCreator selManager;
	
	/**
	 * Instantiates a new Command.
	 *
	 * @param plugin the plugin
	 * @param name the name
	 */
	public CreateSelectionCommand(@NotNull JavaPlugin plugin, @NotNull String name, SelectionCreator selManager)
	{
		super(plugin, name);
		this.selManager = selManager;
	}
	
	@Override
	public boolean allowConsole()
	{
		return false;
	}
	
	@Override
	public boolean execute(Player player, String[] args)
	{
		if(args.length < 1)
		{
			return false;
		}
		if(selManager.editorContains(player))
		{
			Message.msgPlayer(player, "Already editing existing Area, Use /Confirm to finish editing the current selection");
			return true;
		}
		//Check if area with that name already exists in this world
		selManager.addEditorList(player, argAsString(0));
		Message.msgPlayer(player, "Creating area " + ChatColor.RED + argAsString(0));
		return true;
	}
	
	@Override
	public List<String> tabComplete(@NotNull Player player, String[] args)
	{
		return List.of("");
	}
}