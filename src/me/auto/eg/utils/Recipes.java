package me.auto.eg.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

import me.auto.eg.enchants.EnchantManager;
import me.auto.eg.enchants.Magnet;

@SuppressWarnings("deprecation")
public class Recipes{
	
	public static List<Recipe> getRecipes(){
		List<Recipe> recipes = new ArrayList<Recipe>();
		recipes.add(getRepairGemRecipe());
		recipes.add(getSpeedGemRecipe());
		recipes.add(getLeapingRecipe());
		recipes.add(getHasteShardRecipe());
		recipes.add(getHasteRecipe());
		recipes.add(getMagnetRecipe());
		recipes.add(getMagnetTogglerRecipe());
		recipes.add(getNightvisionRecipe());		
		return recipes;
	}
	
	public static ItemStack getRepairGem(){
		ItemStack crystal = new ItemStack(Material.DIAMOND);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GOLD + "Repairs any item!");
		return EnchantManager.setItemWatermark(Utils.addToIM(crystal, ChatColor.DARK_AQUA + "Repair Gem", lore));
	}
	
	public static ItemStack getSpeedGem(){
		ItemStack crystal = new ItemStack(Material.EMERALD);
		List<String> lore = new ArrayList<String>();
		return EnchantManager.setItemWatermark(Utils.addToIM(crystal, ChatColor.DARK_AQUA + "Speed Gem", lore));
	}	
	
	public static ItemStack getLeapingGem(){
		ItemStack crystal = new ItemStack(Material.DIAMOND);
		List<String> lore = new ArrayList<String>();
		return EnchantManager.setItemWatermark(Utils.addToIM(crystal, ChatColor.GREEN + "Leaping Gem", lore));
	}
	
	public static ItemStack getHasteShard(){
		ItemStack shard = new ItemStack(Material.MONSTER_EGG);
	    List<String> lore = new ArrayList<String>();
	    lore.add(ChatColor.GOLD + "Imagine this is a shard?");
	    return EnchantManager.setItemWatermark(Utils.addToIM(shard, ChatColor.DARK_AQUA + "Haste Shard", lore));
	}
	
	public static Recipe getRepairGemRecipe(){
		ShapedRecipe recipe = new ShapedRecipe(getRepairGem());
		recipe.shape("***", "*%*", "***");
		recipe.setIngredient('*', Material.OBSIDIAN);
		recipe.setIngredient('%', Material.DIAMOND_BLOCK);
		return recipe;
	}
	
	public static Recipe getSpeedGemRecipe(){
		ShapedRecipe recipe = new ShapedRecipe(EnchantManager.getEnchant("flash").getCrystal().getItem(1));
		recipe.shape("*=*", "-%-", "*=*");
		recipe.setIngredient('%', Material.NETHER_STAR);
		recipe.setIngredient('-', Material.LAPIS_BLOCK);
		recipe.setIngredient('*', Material.GHAST_TEAR);
		recipe.setIngredient('=', Material.SUGAR);
		return recipe;
	}
	
	public static Recipe getLeapingRecipe(){
		ShapedRecipe recipe = new ShapedRecipe(EnchantManager.getEnchant("leaping").getCrystal().getItem(1));
		recipe.shape("***", "***", "***");
		recipe.setIngredient('*', Material.IRON_BLOCK);
		return recipe;
	}
	
	public static Recipe getHasteShardRecipe(){
		ShapedRecipe recipe = new ShapedRecipe(getHasteShard());
		recipe.shape("***", "***", "***");
		recipe.setIngredient('*', Material.GOLD_BLOCK);
		return recipe;
	}
	
	public static Recipe getHasteRecipe(){
		ShapedRecipe recipe = new ShapedRecipe(EnchantManager.getEnchant("haste").getCrystal().getItem(1));
		recipe.shape("***", "*%*", "***");
		recipe.setIngredient('*', new MaterialData(Material.MONSTER_EGG, (byte) 0));
		recipe.setIngredient('%', Material.NETHER_STAR);
		return recipe;
	}
	
	public static Recipe getMagnetRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(EnchantManager.getEnchant("magnet").getCrystal().getItem(1));
		recipe.shape("*%*", "%^%", "*%*");
		recipe.setIngredient('*', Material.QUARTZ);
		recipe.setIngredient('%', Material.REDSTONE);
		recipe.setIngredient('^', Material.REDSTONE_BLOCK);
		return recipe;
	}
	
	public static Recipe getMagnetTogglerRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(Magnet.getInstance().getToggle());
		recipe.shape("*%*", "*^*", "***");
		recipe.setIngredient('*', Material.REDSTONE_BLOCK);
		recipe.setIngredient('%', Material.LEVER);
		recipe.setIngredient('^', EnchantManager.getEnchant("magnet").getCrystal().getMaterial());
		return recipe;
	}
	
	public static Recipe getNightvisionRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(EnchantManager.getEnchant("nightvision").getCrystal().getItem(1));
		recipe.shape("***", "%^%", "&&&");
		recipe.setIngredient('*', Material.DAYLIGHT_DETECTOR);
		recipe.setIngredient('%', Material.BEACON);
		recipe.setIngredient('^', Material.GOLDEN_CARROT);
		recipe.setIngredient('&', Material.GOLD_BLOCK);
		return recipe;
	}
	
}
