package org.syc.rhapsody.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.syc.rhapsody.common.*;

public class MeasureAnalyzer {
	
	ArrayList<Measure> measures;
	ArrayList<Chord> acchords;
	
	public MeasureAnalyzer(ArrayList<Measure> measures){
		this.measures = measures;
	}
	
	private static void mergeChord(HashMap<Chord,Integer> sum, HashMap<Chord,Integer> nm){
		Iterator<Entry<Chord,Integer>> iter = nm.entrySet().iterator(); 
		while (iter.hasNext()) {
		    Map.Entry<Chord,Integer> entry = (Map.Entry<Chord,Integer>) iter.next(); 
		    Chord key = entry.getKey();
		    int origin = sum.containsKey(key)?sum.get(key):0;				    
		    sum.put(key, origin+entry.getValue());
		} 
	}
	
	public static ArrayList<HashMap<Chord,Integer>> analyzeMeasure(Measure measure, 
			int nbeats) throws AnalyzerException, ParserException{
		if(measure.rhythm.beats%nbeats !=0 || nbeats > measure.rhythm.beats){
			throw new AnalyzerException("Invalid nbeats!");
		}
		int pace = measure.rhythm.beats/nbeats;
		ArrayList<HashMap<Chord,Integer>> chordCol = new ArrayList<HashMap<Chord,Integer>>();
		
		ArrayList<Sod> stressedSod = getStressedSod(measure);
		for(int i=0; i<nbeats; ++i){
			HashMap<Chord,Integer> chords = new HashMap<Chord,Integer>();
			for(int j=0; j<pace; ++j){
				Sod sod = stressedSod.get(i*pace+j);
				HashMap<Chord,Integer> sc = sod.getChords(ChordStrategy.MajorTriad);
				mergeChord(chords,sc);
				sc = sod.getChords(ChordStrategy.MinorTriad);
				mergeChord(chords,sc);
			}
			
			chordCol.add(chords);
		}
		
		return chordCol;
	}
	
	public static ArrayList<Sod> getStressedSod(Measure measure){
		
		ArrayList<Sod> stressedSod = new ArrayList<Sod>();
		
		Duration unit = new Duration(measure.rhythm.unit);
		Duration cum = new Duration(), d = new Duration();		
		Iterator<Sod> sodIter = measure.sods.iterator();
		Sod cur = measure.sods.get(0);
		
		for(int i=0; i<measure.rhythm.beats; ++i){	
			while(cum.noGreaterThan(d)){
				if(!sodIter.hasNext())
					break;				
				cur = sodIter.next();
				cum.add(cur.duration);
			}
			
			stressedSod.add(cur);
			
			if(!sodIter.hasNext())
				break;
			
			d.add(unit);
		}
		
		return stressedSod;
	}
	
}
