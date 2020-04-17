package net.krows_team.flexmine.registry;

import net.krows_team.flexmine.FlexMine;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FlexMine.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class FlexMineRegistry<T> {
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> e) {
		
		EntryContainer.flushEntries(e.getRegistry().getRegistrySuperType(), e.getRegistry()::registerAll);
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> e) {
		
		EntryContainer.flushEntries(e.getRegistry().getRegistrySuperType(), e.getRegistry()::registerAll);
	}
	
	@SubscribeEvent
	public static void registerEffects(RegistryEvent.Register<Effect> e) {
		
		EntryContainer.flushEntries(e.getRegistry().getRegistrySuperType(), e.getRegistry()::registerAll);
	}
	
	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityType<?>> e) {
		
		EntryContainer.flushEntries(e.getRegistry().getRegistrySuperType(), e.getRegistry()::registerAll);
	}
	
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> e) {
		
		EntryContainer.flushEntries(e.getRegistry().getRegistrySuperType(), e.getRegistry()::registerAll);
	}
	
	@SubscribeEvent
	public static void registerProfessions(RegistryEvent.Register<VillagerProfession> e) {
		
		EntryContainer.flushEntries(e.getRegistry().getRegistrySuperType(), e.getRegistry()::registerAll);
	}
	
	@SubscribeEvent
	public static void registerPOI(RegistryEvent.Register<PointOfInterestType> e) {
		
		EntryContainer.flushEntries(e.getRegistry().getRegistrySuperType(), e.getRegistry()::registerAll);
	}
}