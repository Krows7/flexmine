package net.krows_team.flexmine.villagers.poi;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.krows_team.flexmine.registry.FlexMineEntryContainer;
import net.krows_team.flexmine.utils.Reflections;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.village.PointOfInterestType;

public class CustomPointOfInterestTypes extends FlexMineEntryContainer<PointOfInterestType> {
	
	public final static CustomPointOfInterestTypes INSTANCE = new CustomPointOfInterestTypes();
	
	public final static PointOfInterestType BROKER_POI = create("broker", Blocks.GOLD_BLOCK, 1, 1);
	public final static PointOfInterestType LUMBERJACK_POI = create("lumberjack", Blocks.CRAFTING_TABLE, 1, 1);
	
	private static Map<BlockState, PointOfInterestType> POI_MAP = getMap();
	
	private static PointOfInterestType create(String name, Block block, int i1, int i2) {
		
		POI_MAP = (POI_MAP == null) ? getMap() : POI_MAP;
		
		Set<BlockState> blocks = getAllStates(block);
		
		PointOfInterestType poi = Reflections.invokeConstructor(PointOfInterestType.ARMORER, new Object[] {name, blocks, i1, i2});
		
		blocks.forEach(b -> POI_MAP.put(b,poi));
		
		return INSTANCE.load(poi, name);
	}
	
	private static Set<BlockState> getAllStates(Block block) {
		
		return ImmutableSet.copyOf(block.getStateContainer().getValidStates());
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T getMap() {
		
		try {
			
			Field field_221073_u = PointOfInterestType.class.getDeclaredField("field_221073_u");
			field_221073_u.setAccessible(true);
			
			return (T) field_221073_u.get(null);
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
}