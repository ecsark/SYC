package org.syc.rhapsody.analyzer;

import java.util.HashMap;

import org.syc.rhapsody.common.M;

public class ChordDefinition {
	public static String[] 	MajorTriad = {"M3","m3"},
							MinorTriad = {"m3","M3"},
							AugmentedTriad = {"M3","M3"},
							DiminishedTriad = {"m3","m3"},
							MajorMinorSeventh = {"M3","m3","m3"};
	
	public static HashMap<String,Pair<String,String[]>> ChordMap;
	public static HashMap<M,String[]> ScaleMap;
											
	
	static{
		ChordMap = new HashMap<String,Pair<String,String[]>>();
		
		ChordMap.put("I", new Pair<String, String[]>("P1", MajorTriad));
		ChordMap.put("II", new Pair<String, String[]>("M2", MinorTriad));
		ChordMap.put("III", new Pair<String, String[]>("M3", MinorTriad));
		ChordMap.put("IV",  new Pair<String, String[]>("P4", MajorTriad));
		ChordMap.put("V", new Pair<String, String[]>("P5", MajorTriad));
		ChordMap.put("VI", new Pair<String, String[]>("M6", MinorTriad));
		ChordMap.put("VII", new Pair<String, String[]>("M7", DiminishedTriad));
		ChordMap.put("V7", new Pair<String, String[]>("P5", MajorMinorSeventh));
		
		ChordMap.put("i", new Pair<String, String[]>("P1", MinorTriad));
		ChordMap.put("iv", new Pair<String, String[]>("P4", MinorTriad));
		ChordMap.put("v", new Pair<String, String[]>("P5", MinorTriad));		

		ScaleMap = new HashMap<M,String[]>();
		ScaleMap.put(M.MAJOR, new String[]{"M2","M2","m2","M2","M2","M2"});
		ScaleMap.put(M.NATURALMINOR, new String[]{"M2","m2","M2","M2","m2","M2"});
		ScaleMap.put(M.HARMONICMINOR, new String[]{"M2","m2","M2","M2","m2","m3"});
		
	}
}
