package me.auto.eg.enchants;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class KeepInv extends Enchant{

	public KeepInv() {
		super("keepinventory");
	}

	@Override
	public void setDefaults() {
		typesAllowed.add("helmet");
		typesAllowed.add("chestplate");
		typesAllowed.add("leggings");
		typesAllowed.add("boots");
		displayName = "&6Keep Inventory {enchantlevel}";
		maxLevel = 1;
		event = "onEquip";
		crystal = new Crystal(this);
		crystal.displayName = "&6Keep Inventory {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD);
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity entity, double value, Block block) {
		ItemStack[] armor = player.getInventory().getArmorContents();
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		for(ItemStack is : armor) {
			if((is != null) && (!is.getType().equals(Material.AIR)) && (EnchantManager.hasEnchant(is, this.name)) && (value != 1.0)) {
				items.add(is);
			}
		}
		if((item != null) && (!item.getType().equals(Material.AIR)) && (EnchantManager.hasEnchant(item, this.name)) && (value != 1.0)) {
			if(!items.contains(item)) {
				items.add(item);
			}
		}
		PermissionUser user = PermissionsEx.getUser(player);
		if(items.size() == 4) {
			user.addPermission("keepitems.keep");
			user.addPermission("keepitems.keepxp");
		}else if((user.has("keepitems.keep")) && (user.has("keepitems.keepxp")) ) {
			user.removePermission("keepitems.keep");
			user.removePermission("keepitems.keepxp");
		}
	}

	@Override
	public void startup() {
		
	}

}
