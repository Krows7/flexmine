package net.krows_team.flexmine.items;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import net.krows_team.flexmine.blocks.CustomBlocks;
import net.krows_team.flexmine.effects.CustomEffects;
import net.krows_team.flexmine.registry.MyLocalModEntryContainer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Food.Builder;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.SoupItem;
import net.minecraft.item.TieredItem;
import net.minecraft.potion.EffectInstance;

public final class CustomItems extends MyLocalModEntryContainer<Item> {
	
	public final static CustomItems INSTANCE = new CustomItems();
	
	public final static Item PUMPKIN_SLICE = create(ItemGroup.FOOD, "pumpkin_slice");
	public final static Item BAKED_PUMPKIN_SLICE = createFood(food -> food.saturation(6.0F).hunger(5), "baked_pumpkin_slice");
	public final static Item PUMPKIN_COOKIE = createFood(food -> food.saturation(1.2F).hunger(1), "pumpkin_cookie");
	public final static Item PUMPKIN_SOUP = createSoup(food -> food.effect(new EffectInstance(CustomEffects.PURIFICATION, 1), 1).hunger(7).saturation(10.0F), "pumpkin_soup");
	public final static Item SPIKES = load(SpikesItem::new, ItemGroup.DECORATIONS, "spikes");
	public final static Item MILK_BOTTLE = load(p -> new MilkBottle(p.maxStackSize(16)), ItemGroup.BREWING, "milk_bottle");
	public final static Item VANILLA_GLASS_BOTTLE = loadVanilla(CustomGlassBottleItem::new, ItemGroup.BREWING, "glass_bottle");
	public final static Item LIFT = load(LiftItem::new, ItemGroup.REDSTONE, "lift");
	public final static Item MUSKET = load(Musket::new, ItemGroup.COMBAT, "musket");
	public final static Item FLOWER_SOUP = createSoup(food -> food.setAlwaysEdible().hunger(1).effect(new EffectInstance(CustomEffects.SMALL_INSTANT_HEALTH, 1, 1), 1.0F), "flower_soup");
	public final static Item DAMAGED_PAINTING = load(p -> new DamagedPainting(p.maxStackSize(1)), ItemGroup.DECORATIONS, "damaged_painting");
	public final static Item SPINNER = createFromBlock(p -> p.maxStackSize(1), CustomBlocks.SPINNER, ItemGroup.DECORATIONS, "spinner");
	public final static Item WIRING = load(Wiring::new, ItemGroup.MATERIALS, "wiring");
	public final static Item PRESSED_REDSTONE = load(PressedRedstone::new, ItemGroup.MATERIALS, "pressed_redstone");
	public final static Item PIZZA = load(p -> new BlockItem(CustomBlocks.PIZZA, p.maxStackSize(1)), ItemGroup.FOOD, "pizza");
	public final static Item RAW_PIZZA = create(ItemGroup.FOOD, "raw_pizza");
	public final static Item DOUGH = create(ItemGroup.FOOD, "dough");
	public final static Item MINCED_MEAT = create(ItemGroup.FOOD, "minced_meat");
	public final static Item SAUSAGE = createFood(food -> food.meat().hunger(6).saturation(1.2F), "sausage");
	public final static Item VANILLA_JUKEBOX = loadVanillaBlock(CustomBlocks.VANILLA_JUKEBOX, ItemGroup.DECORATIONS, "jukebox");
	public final static Item CHEEZE = createFood(food -> food.hunger(3).saturation(0.4F), "cheeze");
	public final static Item OIL_BOTTLE = create(p -> p.containerItem(Items.GLASS_BOTTLE).group(ItemGroup.FOOD), "oil_bottle");
	public final static Item COMPRESSOR = createFromBlock(p -> p, CustomBlocks.COMPRESSOR, ItemGroup.DECORATIONS, "compressor");
	public final static Item NEEDLE = load(p -> new TieredItem(ItemTier.GOLD, p), ItemGroup.MISC, "needle");
	public final static Item LIGHT_REFLECTOR = createFromBlock(p -> p, CustomBlocks.LIGHT_REFLECTOR, ItemGroup.REDSTONE, "light_reflector");
	public final static Item LIGHT_DETECTOR = createFromBlock(p -> p, CustomBlocks.LIGHT_DETECTOR, ItemGroup.REDSTONE, "light_detector");
	public final static Item VANILLA_FISHING_ROD = loadVanilla(p -> new CustomFishingRodItem(p.maxDamage(64)), ItemGroup.TOOLS, "fishing_rod");
	
/**
 * 
 * Closed constructor.
 * 
 */
	private CustomItems() {
		
	}
	
	private static Item loadVanillaBlock(Block block, ItemGroup group, String name) {
		
		return INSTANCE.loadVanilla(new BlockItem(block, new Properties().group(group)), name);
	}
	
	private static Item loadVanilla(Function<Properties, Item> function, ItemGroup group, String name) {
		
		return INSTANCE.loadVanilla(function.apply(new Properties()), name);
	}
	
	private static Item load(Function<Properties, Item> function, ItemGroup group, String name) {
		
		return INSTANCE.load(function.apply(new Properties().group(group)), name);
	}
	
	private static Item load(Function<Properties, Item> function, UnaryOperator<Properties> properties, ItemGroup group, String name) {
		
		return INSTANCE.load(function.apply(properties.apply(new Properties().group(group))), name);
	}
	
	private static Item createFromBlock(UnaryOperator<Properties> properties, Block block, ItemGroup group, String name) {
		
		return INSTANCE.load(new BlockItem(block, properties.apply(new Properties().group(group))), name);
	}
	
	private static Item createSoup(UnaryOperator<Builder> food, String name) {
		
		return createSoup(p -> p, food, name);
	}
	
	private static Item createSoup(UnaryOperator<Properties> properties, UnaryOperator<Food.Builder> food, String name) {
		
		return load(SoupItem::new, p -> p.maxStackSize(1).food(food.apply(new Builder()).build()), ItemGroup.FOOD, name);
	}
	
	private static Item createFood(UnaryOperator<Properties> properties, UnaryOperator<Food.Builder> food, String name) {
		
		return create(properties.apply(new Properties().food(food.apply(new Builder()).build())).group(ItemGroup.FOOD), name);
	}
	
	private static Item createFood(UnaryOperator<Food.Builder> consumer, String name) {
		
		return create(p -> p.food(consumer.apply(new Builder()).build()).group(ItemGroup.FOOD), name);
	}
	
	private static Item create(ItemGroup group, String name) {
		
		return create(p -> p.group(group), name);
	}
	
	private static Item create(UnaryOperator<Properties> consumer, String name) {
		
		return create(consumer.apply(new Properties()), name);
	}
	
	private static Item create(Properties p, String name) {
		
		return INSTANCE.load(new Item(p), name);
	}
	
	public static interface BlockItemBuilder {
		
		public BlockItem create(Block block, Properties properties);
	}
}