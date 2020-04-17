package net.krows_team.flexmine.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FileUtils {
	
	public final static File PROFESSIONS_FOLDER = getOrCreate("data\\professions");
	
	public static File getOrCreate(String name) {
		
		File file = new File(name);
		
		if(!file.exists()) file.mkdirs();
		
		return file;
	}
	
	public static void consumeOrCreate(File path, Consumer<String> ifPresent) {
		
		if(path.exists()) ifPresent.accept(read(path));
		else run(() -> path.createNewFile());
	}
	
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