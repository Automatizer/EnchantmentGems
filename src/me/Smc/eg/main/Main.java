package me.Smc.eg.main;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import me.Smc.eg.commands.Executor;
import me.Smc.eg.enchants.EnchantManager;
import me.Smc.eg.listeners.BreakEvent;
import me.Smc.eg.listeners.DamageEvent;
import me.Smc.eg.listeners.DeathEvent;
import me.Smc.eg.listeners.EntityChangeBlock;
import me.Smc.eg.listeners.EntityDismount;
import me.Smc.eg.listeners.EquipEvent;
import me.Smc.eg.listeners.HungerEvent;
import me.Smc.eg.listeners.InteractEvent;
import me.Smc.eg.listeners.InvClickEvent;
import me.Smc.eg.listeners.ItemDamageEvent;
import me.Smc.eg.listeners.ItemPickup;
import me.Smc.eg.listeners.JoinEvent;
import me.Smc.eg.listeners.JumpEvent;
import me.Smc.eg.listeners.LoginEvent;
import me.Smc.eg.listeners.MoveEvent;
import me.Smc.eg.listeners.SneakEvent;
import me.Smc.eg.listeners.SwitchToItemEvent;
import me.Smc.eg.listeners.TargetEvent;
import me.Smc.eg.listeners.WeatherEvent;
import me.Smc.eg.utils.ChatUtils;
import me.Smc.eg.utils.EntityHider;
import me.Smc.eg.utils.EntityHider.Policy;
import me.Smc.eg.utils.GTL;
import me.Smc.eg.utils.Recipes;
import me.Smc.eg.utils.Settings;
import me.Smc.guiapi.GUIAPI;

/**
 * This is the main class for EnchantmentGems!
 * It handles plugin enabling/disabling.
 * 
 * @author Smc
 */

public class Main extends JavaPlugin{

	//Events valid: switchToItem, attackEntity, hitByEntity, killedEntity, onEquip, onHunger
	
	public static Plugin plugin;
	public static Settings settings = Settings.getInstance();
	public static EntityHider entityHider;
	public static ProtocolManager protocolManager;

	/**
	 * Enables the plugin
	 */
	
	//@SuppressWarnings("deprecation")
	public void onEnable(){
		plugin = this;
		settings.setup(this);
		protocolManager = ProtocolLibrary.getProtocolManager();
		for(Recipe recipe : Recipes.getRecipes()) getServer().addRecipe(recipe);
		getServer().addRecipe(new FurnaceRecipe(new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH));
		this.getCommand("eg").setExecutor(new Executor());
		this.getCommand("sudoku").setExecutor(new Executor());
		this.getCommand("map").setExecutor(new Executor());
		this.getCommand("trash").setExecutor(new Executor());
		new BreakEvent(this);
		new DamageEvent(this);
		new DeathEvent(this);
		new EquipEvent(this);
		//new FlightEvent(this);
		new GUIAPI(this);
		new HungerEvent(this);
		new InteractEvent(this);
		new InvClickEvent(this);
		new ItemDamageEvent(this);
		new JoinEvent(this);
		new MoveEvent(this);
		new SwitchToItemEvent(this);
		new LoginEvent(this);
		new WeatherEvent(this);
		new SneakEvent(this);
		new JumpEvent(this);
		new EntityDismount(this);
		new TargetEvent(this);
		new EntityChangeBlock(this);
		new ItemPickup(this);
		GTL.startLoops();
		entityHider = new EntityHider(plugin, Policy.BLACKLIST);
		EnchantManager.loadConfFiles();
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
