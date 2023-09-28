package io.sniffer.util.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
	public static List<String> readTextLinesFromPath(String path) {
		try {
			
			InputStream stream = new FileInputStream(path);
			InputStreamReader reader = new InputStreamReader(stream);
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			List<String> lines = new ArrayList<>();
			
			while(true) {
				
				String line = bufferedReader.readLine();
				if(line == null) break;
				
				lines.add(line);
			}
			
			bufferedReader.close();
			
			return lines;
			
		} catch(IOException exception) {
			
			String message = String.format("An error occurred while reading text lines from path: %s", exception);
			System.out.println(message);
			return null;
		}
	}
	
	public static void writeTextLinesToPath(List<String> textLines, String path) {
		try {
			
			OutputStream stream = new FileOutputStream(path);
			OutputStreamWriter writer = new OutputStreamWriter(stream);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			
			boolean firstLine = true;
			
			for(String textLine : textLines) {
				if(firstLine) firstLine = false;
				else bufferedWriter.newLine();
				
				bufferedWriter.write(textLine);
			}
			
			bufferedWriter.close();
			
		} catch(IOException exception) {
			
			String message = String.format("An error occurred while writing text lines to path: %s", exception);
			System.out.println(message);
		}
	}
	
}
