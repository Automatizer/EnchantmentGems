package me.auto.eg.oldenchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import me.auto.eg.utils.Utils;

public class Purify extends Enchant{

	public Purify(){
		super("purify");
	}

	@Override
	public void setDefaults(){
		typesAllowed.add("sword"); typesAllowed.add("axe"); typesAllowed.add("bow");
		displayName = "&7Purify {enchantlevel}";
		maxLevel = 3;
		event = "attackEntity";
		permission = "eg.enchant.purify.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Purify {enchantlevel}";
		crystal.material = Material.EMERALD;
		setOption("giveToOpponent", "true");
		setOption("hitPercentage", "5");
		setOption("hitPercentageGainPerEnchantLevel", "5");
		setOption("ascentMultSpeed", "0.5");
		setOption("ascentTargetYAbove", "10");
		setOption("ascentTimerPeriodTicks", "5");
		setOption("ascentDurationTicks", "30");
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity target, double value, Block block){
		if(target instanceof Zombie || target instanceof Skeleton){
			int level = EnchantManager.getEnchantLevel(item, this);
			int hitPercentage = (int) (getDoubleOption("hitPercentage") + getDoubleOption("hitPercentageGainPerEnchantLevel") * (level - 1));
			@SuppressWarnings("unused")
			boolean canFire = Utils.randomBetween(0, 100) <= hitPercentage ? true : false;
			/*if(canFire)
				if(event.equalsIgnoreCase("attackEntity"))
					if(getBooleanOption("giveToOpponent")) purify(target, player);*/
		}
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Purifies the entity
	 * 
	 * @param entity The entity to purify
	 */
	
	/*public void purify(final Entity entity, final Player player){
		((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 4));
		final Location startLoc = entity.getLocation();
		final Location targetLoc = entity.getLocation().add(0, getIntOption("ascentTargetYAbove"), 0);
		Location blockUnder = entity.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation();
		if(blockUnder.getBlock().getType() == Material.AIR || blockUnder.getBlock().getType() == Material.WATER){
			for(;;){
				blockUnder = blockUnder.getBlock().getRelative(0, -1, 0).getLocation();
				if(blockUnder.getBlock().getType() != Material.AIR && blockUnder.getBlock().getType() != Material.WATER) break;
			}
		}
		for(int x = -1; x <= 1; x++)
			for(int z = -1; z <= 1; z++){
				final Block block = blockUnder.getBlock().getRelative(x, 0, z);
				final Material prev = block.getType();
				@SuppressWarnings("deprecation")
				final byte prevByte = block.getData();
				Inventory chestInv = null;
				if(prev == Material.CHEST) chestInv = ((Chest) block).getBlockInventory();
				block.setType(Material.BEACON);
				block.getState().update();
				final Inventory cInv = chestInv;
				new BukkitRunnable(){
					@SuppressWarnings("deprecation")
					public void run(){
						block.setType(prev);
						block.setData(prevByte);
						block.getState().update();
						if(prev == Material.CHEST)
							if(cInv != null){
								((Chest) block).getInventory().clear();
								ItemStack[] list = cInv.getContents().clone();
								for(ItemStack item : list){
									((Chest) block).getInventory().setItem(cInv.first(item), item);
									cInv.remove(cInv.first(item));
								}
								block.getState().update();
							}
					}
				}.runTaskLater(Main.plugin, 100);
			}
		final BukkitTask ascent = new BukkitRunnable(){
			public void run(){
				Vector vector = targetLoc.toVector().subtract(entity.getLocation().toVector()).normalize().multiply(getDoubleOption("ascentMultSpeed"));
				entity.setVelocity(vector);	
				for(Entity nearby : Utils.getNearbyEntities(entity.getLocation(), 5)){
					if(!(nearby instanceof Player) && !(nearby instanceof Wolf) && !(nearby instanceof Ocelot) && nearby instanceof LivingEntity){
						Vector nearVector = nearby.getLocation().toVector().subtract(entity.getLocation().getBlock().getRelative(0, -1, 0).getLocation().toVector()).normalize().multiply(2);
						nearby.setVelocity(nearVector);
						((LivingEntity) nearby).damage(1.0, player);	
					}
				}
			}
		}.runTaskTimer(Main.plugin, 0, getIntOption("ascentTimerPeriodTicks"));
		new BukkitRunnable(){
			public void run(){
				ascent.cancel();
				Location loc = entity.getLocation();
				((LivingEntity) entity).damage(((LivingEntity) entity).getHealth());
				player.getWorld().strikeLightningEffect(loc);
				player.getWorld().spawnEntity(startLoc, EntityType.VILLAGER);
			}
		}.runTaskLater(Main.plugin, getIntOption("ascentDurationTicks"));
	}*/
	
}
