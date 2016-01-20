package de.poaceae.SPG.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {
	File file;
	FileWriter fileWritter;
	BufferedWriter bufferWritter;
	boolean active = false;
	int count = 1;
	public WriteFile(){

		

	}
	
	public void activate(){
		active = true;
	}
	
	public void open(){
		if (active == false){
			return;
		}
		String filename;
		filename = "SLOG_" + String.valueOf(count++) + ".csv";
		file = new File(filename);

		//if file doesnt exists, then create it
		if(!file.exists()){
			try{
				file.createNewFile();
				fileWritter = new FileWriter(file.getName(),true);
				bufferWritter = new BufferedWriter(fileWritter);
			}
			catch(IOException e){
				e.printStackTrace();
				System.out.println("Error");	
			}
		}
	}
	
	public void close(){
		if (active == false){
			return;
		}		
		try{
		bufferWritter.close();
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Error");
		}
		active = false;
	}
	
	public void appendLine(String line){
		if (active == false){
			return;
		}		
		try{
		bufferWritter.write(line);
		bufferWritter.write("\r\n");
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Error");
		}
	}
	
	public void appendfile(){
		try{
			String data = " This content will append to the end of the file \r\n";

			//true = append file
			//FileWriter fileWritter = new FileWriter(file.getName(),true);
			//BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(data);
			//bufferWritter.close();

			System.out.println("Done");

		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Error");
		}
	}
}
