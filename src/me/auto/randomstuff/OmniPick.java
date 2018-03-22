package me.auto.randomstuff;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.Smc.eg.utils.Configuration;
import me.Smc.eg.utils.Utils;

public class OmniPick {
	
	Configuration c;

	public void setExp(Player p, int i) {
		c = getConfig(p);
		c.writeValue("OmniPick Exp", i);
	}
	
	public int getExp(Player p) {
		c = getConfig(p);
		int i = Utils.stringToInt(c.getValue("OmniPick Exp"));
		return i;
	}
	
	public void setLevel(Player p, int i) {
		c = getConfig(p);
		c.writeValue("OmniPick Level", i);
	}
	
	public int getLevel(Player p) {
		c = getConfig(p);
		int i = Utils.stringToInt(c.getValue("OmniPick Level"));
		return i;
	}
	
	public void setMaterial(Player p, String m) {
		c = getConfig(p);
		c.writeValue("OmniPick Material", m);
	}
	
	public Material getMaterial(Player p) {
		String s = getMat(p);
		s.toLowerCase();
		switch(s) {
		case "wood": return Material.WOOD_PICKAXE;
		case "stone": return Material.STONE_PICKAXE;
		case "gold": return Material.GOLD_PICKAXE;
		case "iron": return Material.IRON_PICKAXE;
		case "diamond": return Material.DIAMOND_PICKAXE;
		default: return null;
		}
	}
	
	private Configuration getConfig(Player p) {
		Configuration c = new Configuration(new File("players/" + p.getUniqueId().toString() + ".fuck"));
		return c;
	}
	
	private String getMat(Player p) {
		c = getConfig(p);
		String s = c.getValue("OmniPick Material");
		return s;
	}
	
	public void setDefaults(Player p) {
		if(getMaterial(p) == null) {
			setExp(p, 0);
			setLevel(p, 1);
			setMaterial(p, "wood");
		}
	}
	
}
