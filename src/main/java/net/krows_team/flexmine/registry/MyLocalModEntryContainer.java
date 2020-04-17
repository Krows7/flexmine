package net.krows_team.flexmine.registry;

import net.krows_team.flexmine.FlexMine;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class MyLocalModEntryContainer<T extends IForgeRegistryEntry<T>> extends EntryContainer<T> {

	@Override
	protected String getModName() {
		
		return FlexMine.MOD_ID;
	}
}