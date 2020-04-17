package net.krows_team.flexmine.registry;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class EntryContainer<T extends IForgeRegistryEntry<T>> {
	
	private static Map<Type, List<EntryContainer<? extends IForgeRegistryEntry<?>>>> MAP = new HashMap<>();
	
	private List<T> list;
	
	public EntryContainer() {
		
		list = new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	public final static <T extends IForgeRegistryEntry<T>> void flushEntries(Class<T> clazz, Consumer<T[]> consumer) {
		
		MAP.remove(clazz).forEach(entry -> consumer.accept((T[]) entry.flushEntries()));
		
		if(MAP.isEmpty()) MAP = null;
	}
	
	public final static <T extends IForgeRegistryEntry<T>> void applyEntries(EntryContainer<T> entry) {
		
		MAP.computeIfAbsent(getEntryType(entry.getClass()), e -> new ArrayList<>()).add(entry);
	}
	
	protected abstract String getModName();
	
	protected final T load(T t, String name) {
		
		return load(t, new ResourceLocation(getModName(), name));
	}
	
	protected final T load(T t, ResourceLocation location) {
		
		list.add(t);
		
		return t.setRegistryName(location);
	}
	
	protected final T loadVanilla(T t, String name) {
		
		return load(t, new ResourceLocation(name));
	}
	
	public final T[] flushEntries() {
		
		T[] array = list.toArray(nullArray());
		
		list = null;
		
		return array;
	}
	
	@SuppressWarnings("unchecked")
	private final T[] nullArray() {
		
		return (T[]) Array.newInstance(getEntryType(getClass()), 0);
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends IForgeRegistryEntry<T>> Class<T> getEntryType(Class<?> clazz) {
		
		Type type = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
		
		if(type instanceof ParameterizedType) type = ((ParameterizedType) type).getRawType();
		
		return (Class<T>) type;
	}
}