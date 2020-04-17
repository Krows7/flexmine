package net.krows_team.flexmine.utils;

import java.util.function.Predicate;

public class CollectionUtils {
	
/**
 * 
 * Iterates the specified {@link Iterable} object and returns an element if it matches with the specified predicate. 
 * If there are no elements matches the predicate then returns null.
 * 
 * @param <T> Any generic type.
 * 
 * @param c Iterable object to iterate elements.
 * 
 * @param predicate Predicate to find element.
 * 
 * @return Iterable element that matches the specified predicate. Otherwise returns null.
 * 
 */
	public static <T> T get(Iterable<T> c, Predicate<T> predicate) {
		
		for(T t : c) if(predicate.test(t)) return t;
		
		return null;
	}
}