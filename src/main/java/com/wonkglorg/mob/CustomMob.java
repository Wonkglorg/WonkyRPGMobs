package com.wonkglorg.mob;

import com.wonkglorg.utilitylib.builder.equipment.EquipmentBuilder;
import com.wonkglorg.utilitylib.message.Message;
import com.wonkglorg.utilitylib.utils.random.Random;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CustomMob
{
	protected double chance;
	protected EntityType entityType;
	protected String customName;
	protected boolean showCustomName;
	protected int level;
	protected double health;
	protected double damage;
	protected boolean ai;
	protected boolean persistent;
	protected final EquipmentBuilder equipmentBuilder = new EquipmentBuilder();
	protected List<CustomDrop> drops;
	
	public CustomMob(String name,double chance, EntityType entityType, int level, double health, double damage, boolean ai, boolean persistent)
	{
		this.chance = chance;
		this.entityType = entityType;
		this.level = level;
		this.health = health;
		this.damage = damage;
		this.ai = ai;
		this.persistent = persistent;
		this.customName = name;
	}
	
	public double spawnChance()
	{
		return chance;
	}
	
	//update enemies health on taking damage to showcase healthbar? for entitydamageevent  e and using placeholders in the name to add healthbars
	public Entity summon(Location location)
	{
		Entity entity = location.getWorld().spawnEntity(location, entityType);
		if(customName != null && !customName.isEmpty())
		{
			entity.customName(Message.color(customName));
			entity.setCustomNameVisible(showCustomName);
		}
		if(entity instanceof LivingEntity livingEntity)
		{
			if(livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH) != null)
			{
				livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
				livingEntity.setHealth(health);
			}
			livingEntity.setAI(ai);
			livingEntity.setPersistent(persistent);
			
			equipmentBuilder.setLivingEntity(livingEntity);
			equipmentBuilder.build();
			
		}
		
		return entity;
	}
	
	public void onDeath(Location location)
	{
		for(ItemStack item : getItemsOnDeath())
		{
			location.getWorld().dropItemNaturally(location, item);
		}
	}
	
	public List<ItemStack> getItemsOnDeath()
	{
		List<ItemStack> items = new ArrayList<>();
		for(CustomDrop customDrop : drops)
		{
			if(customDrop.getDropchance() >= Random.getNumberBetween(0, 100))
			{
				items.add(customDrop.getItem());
			}
			
		}
		return items;
	}
	
	public void setEquipment(EquipmentSlot equipmentSlot, ItemStack itemStack, float dropChance)
	{
		equipmentBuilder.setItem(equipmentSlot, itemStack, dropChance);
	}
	
	public double getChance()
	{
		return chance;
	}
	
	public void setChance(double chance)
	{
		this.chance = chance;
	}
	
	public EntityType getEntityType()
	{
		return entityType;
	}
	
	public void setEntityType(EntityType entityType)
	{
		this.entityType = entityType;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public void setLevel(int level)
	{
		this.level = level;
	}
	
	public double getHealth()
	{
		return health;
	}
	
	public void setHealth(double health)
	{
		this.health = health;
	}
	
	public double getDamage()
	{
		return damage;
	}
	
	public void setDamage(double damage)
	{
		this.damage = damage;
	}
	
	public boolean isAi()
	{
		return ai;
	}
	
	public void setAi(boolean ai)
	{
		this.ai = ai;
	}
	
	public boolean isPersistent()
	{
		return persistent;
	}
	
	public void setPersistent(boolean persistent)
	{
		this.persistent = persistent;
	}
	
	public EquipmentBuilder getEquipmentBuilder()
	{
		return equipmentBuilder;
	}
	
	public List<CustomDrop> getDrops()
	{
		return drops;
	}
	
	public void setDrops(List<CustomDrop> drops)
	{
		this.drops = drops;
	}
	
	public void setCustomName(String customName)
	{
		this.customName = customName;
	}
	
	public void setShowCustomName(boolean showCustomName)
	{
		this.showCustomName = showCustomName;
	}
}