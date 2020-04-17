package net.krows_team.flexmine.villagers.professions;

import com.google.common.collect.ImmutableSet;

import net.krows_team.flexmine.registry.FlexMineEntryContainer;
import net.krows_team.flexmine.utils.Reflections;
import net.krows_team.flexmine.villagers.poi.CustomPointOfInterestTypes;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.village.PointOfInterestType;

public class CustomProfessions extends FlexMineEntryContainer<VillagerProfession> {
	
	public final static CustomProfessions INSTANCE = new CustomProfessions();
	
	public final static VillagerProfession BROKER = create(CustomPointOfInterestTypes.BROKER_POI, "broker");
	public final static VillagerProfession LUMBERJACK = create(CustomPointOfInterestTypes.LUMBERJACK_POI, "lumberjack");
	
	private static VillagerProfession create(PointOfInterestType type, String name) {
		
		return INSTANCE.load(Reflections.invokeConstructor(VillagerProfession.class, name, type, ImmutableSet.of(), ImmutableSet.of(), null), name);
	}
}