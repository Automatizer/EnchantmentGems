package me.auto.eg.main;

import java.lang.reflect.Field;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.auto.eg.commands.Executor;
import me.auto.eg.listeners.BlockChange;
import me.auto.eg.listeners.BreakEvent;
import me.auto.eg.listeners.DamageEvent;
import me.auto.eg.listeners.DeathEvent;
import me.auto.eg.listeners.EntityChangeBlock;
import me.auto.eg.listeners.EntityDismount;
import me.auto.eg.listeners.EquipEvent;
import me.auto.eg.listeners.HungerEvent;
import me.auto.eg.listeners.InteractEvent;
import me.auto.eg.listeners.InvClickEvent;
import me.auto.eg.listeners.ItemDamageEvent;
import me.auto.eg.listeners.ItemEnchant;
import me.auto.eg.listeners.ItemPickup;
import me.auto.eg.listeners.JoinEvent;
import me.auto.eg.listeners.JumpEvent;
import me.auto.eg.listeners.LoginEvent;
import me.auto.eg.listeners.PlayerRespawn;
import me.auto.eg.listeners.SneakEvent;
import me.auto.eg.listeners.SwitchToItemEvent;
import me.auto.eg.listeners.TargetEvent;
import me.auto.eg.listeners.WeatherEvent;
import me.auto.eg.oldenchants.EnchantManager;
import me.auto.eg.utils.ChatUtils;
import me.auto.eg.utils.Recipes;
import me.auto.eg.utils.Settings;
import me.smc.guiapi.GUIAPI;

/**
 * This is the main class for EnchantmentGems!
 * It handles plugin enabling/disabling.
 * 
 * @author Smc
 */

public class Main extends JavaPlugin{
	
	public static Plugin plugin;
	public static Settings settings = Settings.getInstance();

	/**
	 * Enables the plugin
	 */
	
	public void onEnable(){
		plugin = this;
		settings.setup(this);
		try{
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		}catch(Exception e){e.printStackTrace();}
		getServer().addRecipe(new FurnaceRecipe(NamespacedKey.minecraft("smelt_leather"), new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH, 1, 200));
		this.getCommand("eg").setExecutor(new Executor());
		this.getCommand("sudoku").setExecutor(new Executor());
		this.getCommand("trash").setExecutor(new Executor());
		new BreakEvent(this);
		new DamageEvent(this);
		new DeathEvent(this);
		new EquipEvent(this);
		new GUIAPI(this);
		new HungerEvent(this);
		new InteractEvent(this);
		new InvClickEvent(this);
		new ItemDamageEvent(this);
		new JoinEvent(this);
		new SwitchToItemEvent(this);
		new LoginEvent(this);
		new WeatherEvent(this);
		new SneakEvent(this);
		new JumpEvent(this);
		new EntityDismount(this);
		new TargetEvent(this);
		new EntityChangeBlock(this);
		new ItemPickup(this);
		new PlayerRespawn(this);
		new BlockChange(this);
		new ItemEnchant(this);
		for(Recipe recipe : Recipes.getRecipes()) getServer().addRecipe(recipe);
		EnchantManager.startup();
		ChatUtils.messageConsole(ChatUtils.addPrefix(settings.getMessage("Plugin-Enabled")));
	}
	
	/**
	 * Disables the plugin
	 */
	
	public void onDisable(){
		ChatUtils.messageConsole(ChatUtils.addPrefix(settings.getMessage("Plugin-Disabled")));
	}
	
	public Plugin getPlugin(){
		return this;
	}
	
}
