package me.auto.eg.utils;

import org.bukkit.Material;

public class SmeltResult {

	public static Material get(Material input) {
		switch(input) {
		//foods
		case PORKCHOP: return Material.COOKED_PORKCHOP;
		case BEEF: return Material.COOKED_BEEF;
		case CHICKEN: return Material.COOKED_CHICKEN;
		case COD: return Material.COOKED_COD;
		case SALMON: return Material.COOKED_SALMON;
		case POTATO: return Material.BAKED_POTATO;
		case MUTTON: return Material.COOKED_MUTTON;
		case RABBIT: return Material.COOKED_RABBIT;
		case KELP: return Material.DRIED_KELP;
		//materials
		case IRON_ORE: return Material.IRON_INGOT;
		case GOLD_ORE: return Material.GOLD_ORE;
		case SAND: 
		case RED_SAND: return Material.GLASS;
		case COBBLESTONE: return Material.STONE;
		case CLAY_BALL: return Material.BRICK;
		case CLAY: return Material.TERRACOTTA;
		case NETHERRACK: return Material.NETHER_BRICK;
		case STONE_BRICKS: return Material.CRACKED_STONE_BRICKS;
		case BLACK_TERRACOTTA: return Material.BLACK_GLAZED_TERRACOTTA;
		case BLUE_TERRACOTTA: return Material.BLUE_GLAZED_TERRACOTTA;
		case BROWN_TERRACOTTA: return Material.BROWN_GLAZED_TERRACOTTA;
		case CYAN_TERRACOTTA: return Material.CYAN_GLAZED_TERRACOTTA;
		case GRAY_TERRACOTTA: return Material.GRAY_GLAZED_TERRACOTTA;
		case GREEN_TERRACOTTA: return Material.GREEN_GLAZED_TERRACOTTA;
		case LIGHT_BLUE_TERRACOTTA: return Material.LIGHT_BLUE_GLAZED_TERRACOTTA;
		case LIGHT_GRAY_TERRACOTTA: return Material.LIGHT_GRAY_GLAZED_TERRACOTTA;
		case LIME_TERRACOTTA: return Material.LIME_GLAZED_TERRACOTTA;
		case MAGENTA_TERRACOTTA: return Material.MAGENTA_GLAZED_TERRACOTTA;
		case ORANGE_TERRACOTTA: return Material.ORANGE_GLAZED_TERRACOTTA;
		case PINK_TERRACOTTA: return Material.PINK_GLAZED_TERRACOTTA;
		case PURPLE_TERRACOTTA: return Material.PURPLE_GLAZED_TERRACOTTA;
		case RED_TERRACOTTA: return Material.RED_GLAZED_TERRACOTTA;
		case WHITE_TERRACOTTA: return Material.WHITE_GLAZED_TERRACOTTA;
		case YELLOW_TERRACOTTA: return Material.YELLOW_GLAZED_TERRACOTTA;
		//other
		case CACTUS: return Material.CACTUS_GREEN;
		case OAK_LOG: 
		case SPRUCE_LOG:
		case BIRCH_LOG:
		case JUNGLE_LOG:
		case ACACIA_LOG:
		case DARK_OAK_LOG:
		case STRIPPED_OAK_LOG:
		case STRIPPED_SPRUCE_LOG:
		case STRIPPED_BIRCH_LOG:
		case STRIPPED_JUNGLE_LOG:
		case STRIPPED_ACACIA_LOG:
		case STRIPPED_DARK_OAK_LOG: return Material.CHARCOAL;
		case CHORUS_PLANT: return Material.POPPED_CHORUS_FRUIT;
		case WET_SPONGE: return Material.SPONGE;
		case SEA_PICKLE: return Material.LIME_DYE;
		default: return null;
		}
	}
	
}
