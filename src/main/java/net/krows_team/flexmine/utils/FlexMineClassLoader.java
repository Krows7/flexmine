package net.krows_team.flexmine.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.math.RayTraceContext;

@Deprecated
public class FlexMineClassLoader extends ClassLoader {
	
	private Map<String, Class<?>> classCache = new HashMap<>();
	
	private final String[] classPath;
	
	public FlexMineClassLoader(String[] classPath) {
		
		super(RayTraceContext.class.getClassLoader());
		
		this.classPath = classPath;
	}
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		
		return loadClass(name, true);
	}
	
	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		
		Class<?> result = findClass(name);
		
		if(resolve) resolveClass(result);
		
		return result;
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		
		Class<?> result = classCache.get(name);
		
		if(result != null) return result;
		
		File file = findFile(name.replace('.', '/'), ".class");
		
		if(file == null) return findSystemClass(name);
		
		try {
			
			byte[] classBytes = loadFileAsBytes(file);
			
			result = defineClass(name, classBytes, 0, classBytes.length);
		} catch(IOException e) {
			
			throw new ClassNotFoundException("Can't load class " + name + ": " + e);
		} catch(ClassFormatError e) {
			
			throw new ClassNotFoundException("Format of the specified class file is incorrect for class " + name + ": " + e);
		}
		
		classCache.put(name, result);
		
		return result;
	}
	
	@Override
	protected URL findResource(String name) {
		
		File file = findFile(name, "");
		
		try {
			
			return file == null ? null : file.toURL();
		} catch(MalformedURLException e) {
			
			return null;
		}
	}
	
	private File findFile(String name, String extension) {
		
		File file;
		
		for(int i = 0; i < classPath.length; i++) {
			
			file = new File(new File(classPath[i]).getPath() + File.separatorChar + name.replace('/', File.separatorChar) + extension);
			
			if(file.exists()) return file;
		}
		
		return null;
	}
	
	public static byte[] loadFileAsBytes(File file) throws IOException {
		
		byte[] result = new byte[(int) file.length()];
		
		try(FileInputStream input = new FileInputStream(file)) {
			
			input.read(result, 0, result.length);
		}
		
		return result;
	}
}