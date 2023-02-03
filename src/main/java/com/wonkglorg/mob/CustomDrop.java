package com.wonkglorg.mob;

import org.bukkit.inventory.ItemStack;

public class CustomDrop
{
	private ItemStack item;
	private double dropchance;
	private DropType dropType;
	
	public ItemStack getItem()
	{
		return item;
	}
	
	public void setItem(ItemStack item)
	{
		this.item = item;
	}
	
	public double getDropchance()
	{
		return dropchance;
	}
	
	public void setDropchance(double dropchance)
	{
		this.dropchance = dropchance;
	}
	
	public DropType getDropType()
	{
		return dropType;
	}
	
	public void setDropType(DropType dropType)
	{
		this.dropType = dropType;
	}
	
	private enum DropType
	{
		INCLUSIVE,
		EXCLUSIVE;
	}
}