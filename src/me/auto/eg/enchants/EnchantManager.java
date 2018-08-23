package me.auto.eg.enchants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.auto.eg.main.Main;

public class EnchantManager{
	
	private static EnchantManager manager = null;
	
	private List<Enchant> enchants = new ArrayList<Enchant>();
	
	public static EnchantManager manager() {
		if(manager == null) {
			manager = new EnchantManager();
		}
		return manager;
	}
	
	public void startup() {
		for(Enchant e : getAllEnchants()) {
			e.load();
			e.save();
			e.startup();
		}
	}
	
	public void registerEnchant(Enchant enchant) {
		enchants.add(enchant);
	}
	
	public void unregisterEnchant(Enchant enchant) {
		enchants.remove(enchant);
	}
	
	public List<Enchant> getAllEnchants() {
		return enchants;
	}
	
	public Enchant getEnchant(String name) {
		for(Enchant e : enchants) {
			if(e.getName() == name) {
				return e;
			}
		}
		return null;
	}
	
	public File getEnchantsFolder(){
		File enchants;
		try{
			enchants = new File(Main.plugin.getDataFolder() + File.separator + "Enchants");
			if(!enchants.exists()) enchants.mkdirs();
		}catch(SecurityException e1){
			enchants = null;
		}
		return enchants;
	}

}
