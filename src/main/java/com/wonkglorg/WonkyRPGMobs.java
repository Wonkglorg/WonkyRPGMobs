package com.wonkglorg;

import com.wonkglorg.gui.SelectionGui;
import com.wonkglorg.selection.area.AreaManager;
import com.wonkglorg.selection.editor.SelectionCreator;
import com.wonkglorg.selection.editor.SelectionListener;
import com.wonkglorg.selection.editor.commands.ConfirmSelectionCommand;
import com.wonkglorg.selection.editor.commands.CreateSelectionCommand;
import com.wonkglorg.utility.MobMenuUtility;
import com.wonkglorg.utilitylib.UtilityPlugin;

public final class WonkyRPGMobs extends UtilityPlugin
{
	private static WonkyRPGMobs wonkyRPGMobs;
	private SelectionCreator selectionManager;
	private AreaManager areaManager;
	
	@Override
	public void pluginStartup()
	{
	}
	
	@Override
	public void pluginShutdown()
	{
		areaManager.removeEntities();
	}
	
	@Override
	public void loadBefore()
	{
		wonkyRPGMobs = this;
		selectionManager = new SelectionCreator();
		areaManager = new AreaManager();
		
	}
	
	@Override
	public void event()
	{
		manager.add(new SelectionListener(selectionManager));
	}
	
	@Override
	public void command()
	{
		manager.add(new ConfirmSelectionCommand(this, "Confirm", selectionManager, areaManager));
		manager.add(new CreateSelectionCommand(this, "Create", selectionManager));
	}
	
	@Override
	public void config()
	{
	
	}
	
	@Override
	public void lang()
	{
	
	}
	
	@Override
	public void recipe()
	{
	
	}
	
	@Override
	public void enchant()
	{
	
	}
	
	public static WonkyRPGMobs getInstance()
	{
		return wonkyRPGMobs;
	}
	
}
