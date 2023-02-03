package com.wonkglorg.gui;

import com.wonkglorg.utilitylib.inventory.AbstractMenuUtility;
import com.wonkglorg.utilitylib.inventory.InventoryGUI;
import org.bukkit.plugin.java.JavaPlugin;

public class SelectionGui extends InventoryGUI

{
	public SelectionGui(JavaPlugin plugin, AbstractMenuUtility menuUtility)
	{
		super(64, "Selection", plugin, menuUtility);
	}
	
	@Override
	public void addComponents()
	{
	
	}
	
}