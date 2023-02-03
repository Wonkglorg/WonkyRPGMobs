package com.wonkglorg.utility;

import com.wonkglorg.mob.CustomMob;
import com.wonkglorg.selection.area.Area;
import com.wonkglorg.utilitylib.inventory.AbstractMenuUtility;
import org.bukkit.entity.Player;

public class MobMenuUtility extends AbstractMenuUtility
{
	private Area area;
	private CustomMob customMob;
	
	public MobMenuUtility(Player player)
	{
		super(player);
	}
	
	@Override
	public MobMenuUtility get(Player player)
	{
		return (MobMenuUtility) get(player,new MobMenuUtility(player));
	}
	
	@Override
	public AbstractMenuUtility create(Player player)
	{
		return new MobMenuUtility(player);
	}
	
	public Area getArea()
	{
		return area;
	}
	
	public CustomMob getCustomMob()
	{
		return customMob;
	}
	
	public void setArea(Area area)
	{
		this.area = area;
	}
	
	public void setCustomMob(CustomMob customMob)
	{
		this.customMob = customMob;
	}
}