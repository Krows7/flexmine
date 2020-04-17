package net.krows_team.flexmine.utils;

import java.util.function.Predicate;

public class CollectionUtils {
	
	public static <T> T get(Iterable<T> c, Predicate<T> predicate) {
		
		for(T t : c) if(predicate.test(t)) return t;
		
		return null;
	}
}