package me.Smc.eg.commands;

import org.bukkit.entity.Player;

import me.lyras.api.gui.permission.Permission;
import me.lyras.api.gui.permission.PermissionedPlayer;
import me.lyras.api.gui.ui.Listing;
import me.lyras.api.gui.ui.ListingManager;
import net.md_5.bungee.api.ChatColor;

public class Trash{

	public static void execute(Player p) {
		String s = ChatColor.RED + "TRASH";
		Listing l = new Listing(54, s) {
			@Override
			public void load() {}
		};
		PermissionedPlayer pp = new PermissionedPlayer(p, Permission.CLICK, Permission.CLOSE, Permission.OPEN);
		ListingManager.bind(l, pp);
		l.getOptions().setAllowingShift(true);
		l.getOptions().setClosing(true);
		l.open();
	}
	
}
