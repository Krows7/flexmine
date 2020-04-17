package net.krows_team.flexmine.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FileUtils {
	
	public final static File PROFESSIONS_FOLDER = getOrMKDIR("data\\professions");
	
/**
 * 
 * Returns directory with specified path and it doesn't exist then creates it.
 * 
 * @param name Path to directory.
 * 
 * @return {@link File} directory with specified path.
 * 
 */
	public static File getOrMKDIR(String name) {
		
		File file = new File(name);
		
		if(!file.exists()) file.mkdirs();
		
		return file;
	}
	
/**
 * 
 * If specified file exists then runs specified {@link Consumer} with file text. Otherwise creates it.
 * 
 * @param path File to extract text.
 * 
 * @param ifPresent {@link Consumer} object of file text.
 * 
 */
//	TODO
	public static void consumeOrCreate(File path, Consumer<String> ifPresent) {
		
		if(path.exists()) ifPresent.accept(read(path));
		else run(() -> path.createNewFile());
	}
	
/**
 * 
 * Returns String data from specified file.
 * 
 * @param path File to extract text.
 * 
 * @return Text from specified file.
 * 
 */
	private static String read(File path) {
		
		StringBuilder builder = new StringBuilder();
		
		try(Stream<String> stream = Files.lines(path.toPath())) {
			
			stream.forEach(s -> builder.append(s).append('\n'));
		} catch(IOException e) {
			
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	private static <T> void run(Callable<T> script) {
		
		try {
			
			script.call();
		} catch(Exception e) {
			
			e.printStackTrace();
		}
	}
}