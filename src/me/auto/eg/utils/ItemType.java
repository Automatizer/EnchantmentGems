package me.auto.eg.utils;

import org.bukkit.inventory.ItemStack;

public enum ItemType {
	
	PICKAXE, SHOVEL, AXE, SWORD, HOE, BOW, SHEARS, FISHING_ROD, HELMET, CHESTPLATE, LEGGINGS, BOOTS, OTHER;
	
	public static ItemType getFromString(String name) {
		for(ItemType it : values()) {
			if(it.name() == name) {
				return it;
			}
		}
		return null;
	}
	
	public static ItemType getFromItemStack(ItemStack is) {
		String s = is.getType().name().toLowerCase();
		if(s.contains("pickaxe")) return PICKAXE;
		if(s.contains("shovel")) return SHOVEL;
		if((s.contains("axe")) && (!s.contains("pickaxe"))) return AXE;
		if(s.contains("sword")) return SWORD;
		if(s.contains("hoe")) return HOE;
		if(s.contains("bow")) return BOW;
		if(s.contains("shears")) return SHEARS;
		if(s.contains("fishing")) return FISHING_ROD;
		if(s.contains("helmet")) return HELMET;
		if(s.contains("chestplate")) return CHESTPLATE;
		if(s.contains("leggings")) return LEGGINGS;
		if(s.contains("boots")) return BOOTS;
		return OTHER;
	}

}
