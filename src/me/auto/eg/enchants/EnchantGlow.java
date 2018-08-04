package me.auto.eg.enchants;

import java.lang.reflect.Field;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

public class EnchantGlow extends EnchantmentWrapper{

	private static Enchantment glow;

	public EnchantGlow(String namespacedKey){
		super(namespacedKey);
	}

	@Override
	public boolean canEnchantItem(ItemStack item){
		return true;
	}

	@Override
	public boolean conflictsWith(Enchantment other){
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget(){
		return null;
	}

	@Override
	public int getMaxLevel(){
		return 10;
	}

	@Override
	public String getName(){
		return "Glow";
	}

	@Override
	public int getStartLevel(){
		return 1;
	}

	public static Enchantment getGlow(){
		if(glow != null) return glow;
		try{
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		}catch(Exception e){e.printStackTrace();}
		glow = new EnchantGlow("glow");
		if(Enchantment.getByKey(NamespacedKey.minecraft("glow")) == null) Enchantment.registerEnchantment(glow);
		return glow;
	}

	public static void addGlow(ItemStack item){
		Enchantment glow = getGlow();
		item.addEnchantment(glow, 1);
	}

}
