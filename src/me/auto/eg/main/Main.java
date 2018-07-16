package me.auto.eg.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import me.auto.eg.commands.Executor;
import me.auto.eg.enchants.EnchantManager;
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
import me.auto.eg.listeners.ItemPickup;
import me.auto.eg.listeners.JoinEvent;
import me.auto.eg.listeners.JumpEvent;
import me.auto.eg.listeners.LoginEvent;
import me.auto.eg.listeners.MoveEvent;
import me.auto.eg.listeners.SneakEvent;
import me.auto.eg.listeners.SwitchToItemEvent;
import me.auto.eg.listeners.TargetEvent;
import me.auto.eg.listeners.WeatherEvent;
import me.auto.eg.utils.ChatUtils;
import me.auto.eg.utils.EntityHider;
import me.auto.eg.utils.GTL;
import me.auto.eg.utils.Recipes;
import me.auto.eg.utils.Settings;
import me.auto.eg.utils.EntityHider.Policy;
import me.smc.guiapi.GUIAPI;

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
	public static boolean mcMMO;

	/**
	 * Enables the plugin
	 */
	
	//@SuppressWarnings("deprecation")
	public void onEnable(){
		plugin = this;
		settings.setup(this);
		protocolManager = ProtocolLibrary.getProtocolManager();
		getServer().addRecipe(new FurnaceRecipe(new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH));
		if(Bukkit.getPluginManager().getPlugin("mcMMO") != null && Bukkit.getPluginManager().getPlugin("mcMMO").isEnabled()) {
			mcMMO = true;
		}else {mcMMO = false;}
		this.getCommand("eg").setExecutor(new Executor());
		this.getCommand("sudoku").setExecutor(new Executor());
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
		//new BlockChange(this);
		GTL.startLoops();
		entityHider = new EntityHider(plugin, Policy.BLACKLIST);
		EnchantManager.startup();
		for(Recipe recipe : Recipes.getRecipes()) getServer().addRecipe(recipe);
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
