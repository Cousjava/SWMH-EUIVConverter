package converter;

import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.util.*;


public class Converter {

	BufferedReader savein;
	HashMap<Integer, String> dynastys;
	CK2Prov[] CK2Provs;
	ArrayList<CK2Title> CK2Titles;
	HashMap<String, CK2Title> titles2;
	HashMap<Integer, EUIVProv> euprovs;
	String in;
	String date;
	String version;
	String player_realm;
	String outDir;
	static int noProvinces = 1954; //SMWH 2.901
	static int EUIVProvs = 3003;
	
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
		CK2Provs = new CK2Prov[noProvinces + 1];
		CK2Titles = new ArrayList<CK2Title>();
		titles2 = new HashMap<String, CK2Title>();
		euprovs = new HashMap<Integer, EUIVProv>();
		//String outDirtop = new File(savegame).getParent().getParent().getParent().getParent();		
		try {
			Files.deleteIfExists(new File(savegame.replace(".ck2", "")).toPath());
		} catch (IOException e) {
			System.out.println("Error cleaning output folder");
			e.printStackTrace();
		}
		boolean fCreate = new File(savegame.replace(".ck2", "")).mkdir();
		if (!fCreate){
			System.out.println("Error creating output folder");
			System.exit(1);
		}
	}
	
	public void Run() throws IOException{		
		do {
			in = savein.readLine();
			if (in ==null) break;
			if (in.contains("version") && version == null) {
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
			
			//Ignore delayed events
			//Ignore relations
			//Ignore ambitions
			//Ignore religion
			if (in.contains("provinces=")){
				provincesIn();
			}
			if (in.contains("title=") && CK2Provs[1] != null && CK2Titles.size() == 0){//the provs bit it to stop this section firing early
				titlesIn();
			}
			if (in.contains("character=")){
				charactersIn();
			}
			
			//combat=
		} while (in != null);
		savein.close();
		System.out.println("Finished readig in CK2 file");
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
		System.out.println("Reading in characters...");
		do {
			in = savein.readLine();
			
		}while (in != null && in.contains("delayed_event=")==false);
	
	}
	
	private void provincesIn() throws IOException{
		System.out.println("Reading in provinces...");
		int brackets = 0;
		boolean techlevel = false;
		CK2Prov prov = new CK2Prov();
		do {
			in = savein.readLine();
			if (in.contains("{")) {
				brackets++;continue;
			}
			if (in.contains("tech_levels")){
				techlevel = true;
			}
			if (in.contains("}") ==true && techlevel==true){
				String[] splitter = in.split(" ");				
				for(int i = 0; i < splitter.length - 1; i++){
					prov.techTotal += Float.parseFloat(splitter[i]);
				}
				brackets--;continue;
			}
			if (in.contains("}")) {
				brackets--;continue;
			}
			if (brackets==1){
				if (prov.id != 0){
					CK2Provs[prov.id]= (CK2Prov)prov.clone();
				}
				
				prov = new CK2Prov();
				prov.id = Integer.parseInt(in.split("=")[0].replaceAll("\\s",""));
			}
			if (in.contains("religion")){
				prov.religion = in.split("=")[1];
			} else if (in.contains("culture")){
				prov.culture = in.split("=")[1];
			} else if (in.contains("ca")){
				prov.ca_builds++;
			} else if (in.contains("ct")){
				prov.ct_builds++;
			} else if (in.contains("tp")){
				prov.tp_builds++;
			} else if (in.contains("tb_hillfort")){
				prov.tb_hillfort++;
			} else if (in.contains("tb_market_town")){
				prov.tb_market_town++;
			} else if (in.contains("tb_shipyard")){
				prov.tb_shipyard++;
			} else if (in.contains("tb_training_ground")){
				prov.tb_trainingground++;
			}else if (in.contains("tb_war_camp") || in.contains("tb_steppe_barr")){
				prov.tb_war_camp++;
			} else if (in.contains("tb_practice_range") || in.contains("tb_stable")){
				prov.tb_practice_range++;
			} else if (in.contains("arsenal")){
				prov.tb_arsenal++;
			}
			
		} while (in.contains("title=")==false && in != null);
	}

	private void titlesIn() throws IOException{
		System.out.println("Reading in titles...");
		CK2Title title = new CK2Title();
		do {
			in = savein.readLine();
			if (in.contains("combat="))break;
			if ((in.contains("c_") || in.contains("b_")||in.contains("d_")||in.contains("e_")||in.contains("k_"))&&!in.contains("title=")){
				CK2Titles.add((CK2Title) title.clone());
				titles2.put(title.name, (CK2Title) title.clone());
				title = new CK2Title();
				title.name = in.split("=")[0].replaceAll("\\s","");
			}else if (in.contains("holder") && title.iHolder != 0){
				title.iHolder = Integer.parseInt(in.split("=")[1].replaceAll("\\s",""));
			} else if (in.contains("title=")){
				title.sLeige = in.split("=")[1].replaceAll("\\s","").replaceAll("\"", "");
			}
			
		} while (in != null && in.contains("combat=")==false);
		
		
	}
	
	private void EUIVGen(){
		
		
		
	}
	
	private String topLiege(CK2Title title){
		if (title.leige == null){
			return title.name;
		} else {		
			String parent = title.sLeige;
			CK2Title parTitle = titles2.get(parent);
			return topLiege(parTitle);
		}
		
		
	}
}
