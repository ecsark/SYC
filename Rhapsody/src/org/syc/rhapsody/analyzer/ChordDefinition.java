package org.syc.rhapsody.analyzer;

import java.util.HashMap;

import org.syc.rhapsody.common.M;

public class ChordDefinition {
	public static String[] 	MajorTriad = {"M3","m3"},
							MinorTriad = {"m3","M3"},
							AugmentedTriad = {"M3","M3"},
							DiminishedTriad = {"m3","m3"};
	
	public static HashMap<String,Pair<String,String[]>> TriadMap;
	public static HashMap<M,String[]> ScaleMap;
											
	
	static{
		TriadMap = new HashMap<String,Pair<String,String[]>>();
		TriadMap.put("I", new Pair<String, String[]>("P1", MajorTriad));
		TriadMap.put("IV",  new Pair<String, String[]>("P4", MajorTriad));
		TriadMap.put("V", new Pair<String, String[]>("P5", MajorTriad));
		TriadMap.put("i", new Pair<String, String[]>("P1", MinorTriad));
		TriadMap.put("iv", new Pair<String, String[]>("P4", MinorTriad));
		TriadMap.put("v", new Pair<String, String[]>("P5", MinorTriad));		

		ScaleMap = new HashMap<M,String[]>();
		ScaleMap.put(M.MAJOR, new String[]{"M2","M2","m2","M2","M2","M2"});
		ScaleMap.put(M.NATURALMINOR, new String[]{"M2","m2","M2","M2","m2","M2"});
		ScaleMap.put(M.HARMONICMINOR, new String[]{"M2","m2","M2","M2","m2","m3"});
		
	}
}
