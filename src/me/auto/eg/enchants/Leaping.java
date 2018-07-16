package me.auto.eg.enchants;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffectType;

public class Leaping extends Enchant{
	
	public Leaping(){
		super("leaping");
	}

	@Override
	public void setDefaults(){
		typesAllowed.add("leggings");
		displayName = "&7Leaping {enchantlevel}";
		maxLevel = 20;
		event = "onLeap";
		permission = "eg.enchant.leaping.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Leaping {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD);
		setOption("velocity-divider", "5");
		setOption("default-leaping-strength", "0.5");
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity target, double value, Block block){
		if(value == 0.0) {
			if(EnchantManager.hasEnchant(item, this.name)) {
				int level = EnchantManager.getEnchantLevel(item, this);
				jump(player, level);
			}
		}
		if(value == 1.0) {
			addEffect(player, item);
		}
		if(value == 2.0) {
			player.removePotionEffect(PotionEffectType.JUMP);
		}
	}
	
	public void addEffect(Player p, ItemStack is) {
		/*int level = EnchantManager.getEnchantLevel(is, EnchantManager.getEnchant("leaping"));
		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, (int) Math.floor(level / 5)));
		new BukkitRunnable(){
			public void run(){
				if(p.getInventory().getLeggings() == null){p.removePotionEffect(PotionEffectType.JUMP); cancel(); return;}
				ItemStack leggings = p.getInventory().getLeggings();
				if(EnchantManager.getEnchants(leggings).isEmpty()){p.removePotionEffect(PotionEffectType.JUMP); cancel(); return;}
				boolean contained = false;
				for(Enchant enchant : EnchantManager.getEnchants(leggings))
					if(enchant.getName().equalsIgnoreCase(getName()))
						contained = true;
				if(!contained){p.removePotionEffect(PotionEffectType.JUMP); cancel(); return;}
			}
		}.runTaskLater(Main.plugin, 4);*/
	}
	
	private void jump(Player p, int i) {
		FallingBlock fb = p.getWorld().spawnFallingBlock(p.getEyeLocation(), new MaterialData(Material.PISTON_MOVING_PIECE));
		fb.setVelocity(p.getLocation().getDirection().multiply((getDoubleOption("default-leaping-strength")) + (i / getDoubleOption("velocity-divider"))));
		fb.addPassenger(p);
		p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
	}
	
	public static int getJumps(Entity e) {
		int i = 0;
		if(e instanceof Player) {
			Player p = (Player) e;
			if(EnchantManager.hasEnchant(p.getInventory().getLeggings(), "leaping")) {
				i = (int) Math.floor(EnchantManager.getEnchantLevel(p.getInventory().getLeggings(), EnchantManager.getEnchant("leaping")) / 5) + 1;
			}
		}
		return i;
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		
	}
	
}
