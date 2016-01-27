package converter;

import java.io.*;
import java.util.*;

public class Converter {

	BufferedReader savein;
	HashMap dynastys;
	String in;
	String date;
	String version;
	
	public static void main(String[] args) {
		Converter convert;
		try {
			convert = new Converter(args[0]);
			convert.Run();
		//} catch (ArrayIndexOutOfBoundsException e){
			//System.out.println("Error: No file specified");
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}
	
	public Converter (String savegame) throws FileNotFoundException{
		System.out.println("Opening file " + savegame);
		savein = new BufferedReader(new FileReader(savegame));
		dynastys = new HashMap<Integer, String>();
	}
	
	public void Run() throws IOException{		
		do {
			in = savein.readLine();
			if (in.contains("version")) {
				version = in.split("=")[1];
				version = version.replaceAll("\\s","");
				continue;
			}
			if (in.contains("date") && date ==null){
				date = in.split("=")[1];
				date = version.replaceAll("\\s","");
			}
			//TODO: check flags
			if (in.contains("dynasties=")){
				dynastyIn();
			}
			if (in.contains("character=")){
				charactersIn();
			}
			//Ignore delayed events
			//Ignore relations
			//Ignore ambitions
			//Ignore religion
			if (in.contains("provinces=")){
				provincesIn();
			}
			
		} while (in != null);
		
	}
	
	private void dynastyIn() throws IOException{
		System.out.println("Reading in dynasties...");
		int dynid = 0;
		String dynName;
		do {
			in = savein.readLine();
			if (in.contains("=")){
				
				String[] split = in.split("=");
				try {
					dynid = Integer.parseInt(split[0]);
				} catch (NumberFormatException e) {
					if (in.contains("name")){
						dynName = in.split("\"")[1];
						dynastys.put(dynid, dynName);
						dynName = null;
						dynid = 0;
					}
				}
			}
			
		} while (in.contains("character=")==false && in != null);		
		
	}

	private void charactersIn() throws IOException{
		System.out.println("Reading in characters");
		do {
			in = savein.readLine();
			
		}while (in.contains("delayed_event=")==false && in != null);
	
	}
	
	private void provincesIn() throws IOException{
		System.out.println("Reading in provinces");
		do {
			in = savein.readLine();
			
		}while (in.contains("delayed_event=")==false && in != null);
	}
}
