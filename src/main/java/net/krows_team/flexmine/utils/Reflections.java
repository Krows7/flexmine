package net.krows_team.flexmine.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import sun.reflect.ReflectionFactory;

public final class Reflections {
	
	private Reflections() {
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(Object object, String name) {
		
		try {
			
			Field field = object.getClass().getSuperclass().getField(name);
			field.setAccessible(true);
			
			return (T) field.get(null);
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T> T invoke(Object object, String name, Object... objects) {
		
		Method[] methods = object.getClass().getSuperclass().getDeclaredMethods();
		
		for(Method method : methods) {
			
			if(method.getName().equals(name)) {
				
				try {
					
					method.setAccessible(true);
					
					return (T) method.invoke(object, objects);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
//	TODO
	@SuppressWarnings("unchecked")
	public static <T> T invokeStatic(Class<?> clazz, String name, Object... args) {
		
		for(Method method : clazz.getDeclaredMethods()) {
			
			if(method.getName().equals(name)) {
				
				try {
					
					method.setAccessible(true);
					
					return (T) method.invoke(null, args);
				} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T> T invokeConstructor(Object object, Object[] objects) {
		
		Constructor<T>[] cons = (Constructor<T>[]) object.getClass().getDeclaredConstructors();
		
		for(Constructor<T> con : cons) {
			
			if(con.getParameterCount() == objects.length) {
				
				try {
					
					con.setAccessible(true);
					
					return con.newInstance(objects);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
//	TODO
	@SuppressWarnings("unchecked")
	public static <T> T invokeConstructor(Class<T> clazz, Object... args) {
		
		for(Constructor<?> c : clazz.getDeclaredConstructors()) {
			
			if(c.getParameterCount() == args.length) {
				
				try {
					
					c.setAccessible(true);
					
					return (T) c.newInstance(args);
				} catch(Exception e) {
					
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	public static void editStaticField(Class<?> clazz, String field, Object value) {
		
		editField(null, clazz, field, value);
	}
	
	public static void editField(Object obj, String field, Object value) {
		
		editField(obj, obj.getClass(), field, value);
	}
	
	public static void editField(Object obj, Class<?> clazz, String field, Object value) {
		
		try {
			
			Field f = clazz.getDeclaredField(field);
			f.setAccessible(true);
			
			if(Modifier.isFinal(f.getModifiers())) setNotStaticField(f);
			
			f.set(obj, value);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void setNotStaticField(Field field) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Field modifiers = Field.class.getDeclaredField("modifiers");
		modifiers.setAccessible(true);
		modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T create(Class<T> clazz) {
		
		try {
			
			ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
		
			Constructor<?> c = Object.class.getDeclaredConstructor();
			Constructor<?> con = rf.newConstructorForSerialization(clazz, c);
			
			return (T) con.newInstance();
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
}