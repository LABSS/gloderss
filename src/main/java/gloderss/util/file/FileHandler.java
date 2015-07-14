package gloderss.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
	
	public static String fileReader(String filename) {
		String fileContent = new String();
		
		try {
			BufferedReader file = new BufferedReader(new FileReader(filename));
			try {
				StringBuilder fileBuffer = new StringBuilder();
				String line = file.readLine();
				
				while(line != null) {
					fileBuffer.append(line);
					fileBuffer.append(System.lineSeparator());
					line = file.readLine();
				}
				fileContent = fileBuffer.toString();
			} finally {
				file.close();
			}
		} catch(IOException e) {
		}
		
		return fileContent;
	}
	
	
	public static void fileWriter(String filename, String content) {
		
		try {
			File file = new File(filename);
			
			if(!file.exists()) {
				file.createNewFile();
			}
			
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filename));
			fileWriter.write(content);
			fileWriter.close();
		} catch(IOException e) {
		}
	}
}