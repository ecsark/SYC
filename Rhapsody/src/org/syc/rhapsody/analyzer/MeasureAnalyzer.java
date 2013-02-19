package org.syc.rhapsody.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.syc.rhapsody.common.*;

public class MeasureAnalyzer {
	
	private ChordAnalyzer ca;
	private KeyAnalyzer ka;
	private Progression pg;
	
	
	protected ArrayList<Measure> measures;
	protected ArrayList<HashMap<Tone,Integer>> chords;
	protected ArrayList<ArrayList<Tone>> progressions;
	
	public ArrayList<ArrayList<Tone>> getProgressions(){
		return progressions;
	}
	
	private static String[][] seedChords = {ChordDefinition.MajorTriad, ChordDefinition.MinorTriad,
		ChordDefinition.AugmentedTriad,ChordDefinition.DiminishedTriad, ChordDefinition.MajorMinorSeventh};
	
	private Tone start, end;
	
	public MeasureAnalyzer(ArrayList<Measure> measures, String tonicName, M mode) throws ParserException{
		this.measures = measures;
		ka = new KeyAnalyzer(tonicName, mode);		
		ca = new ChordAnalyzer(ka);
		pg = new Progression(ka);
		chords = new ArrayList<HashMap<Tone,Integer>>();
	}
	
	public void showChords(){
		for(HashMap<Tone,Integer> beatChords : chords){
			Iterator<Entry<Tone,Integer>> iter = beatChords.entrySet().iterator();
			while(iter.hasNext()){
				Map.Entry<Tone,Integer> entry = (Map.Entry<Tone,Integer>) iter.next();
				System.out.print(" "+entry.getKey().toString()+":"+Integer.toString(entry.getValue()));
			}
			System.out.println();
		}
	}
	
	public void analyze() throws AnalyzerException, ParserException{
		for(Measure m : measures)
			chords.addAll(analyzeChord(m, 1));//get one chord for each measure
		
		//for(HashMap<Tone,Integer> beatChords : chords)
			//ca.eliminateChord(beatChords);
		
		strictChord(2);//retain chords that include both stressed notes
		
		progressions = new ArrayList<ArrayList<Tone>>();
		
		start = end = ka.getChord("I");
		ArrayList<Tone> history = new ArrayList<Tone>();
		history.add(start);
		progress(history, 1, 0);
		
		for(ArrayList<Tone> tones : progressions){
			for(Tone tone : tones)
				tone.toNorm();
		}
	}
	
	
	
	//DFS
	private void progress(ArrayList<Tone> history, int index, int rate){
		if(index == chords.size()){
			//printHistory(history, rate);
			if(history.get(index-1).equals(end))
				progressions.add(history);
			return;
		}
		Tone prev = history.get(index-1);
		Set<Tone> posis = pg.next(prev).keySet();
		Set<Tone> currents = chords.get(index).keySet();
		currents.retainAll(posis);
		if(currents.size()==0){
			System.out.println("Stuck here -> "+Integer.toString(index+1));
			printHistory(history,rate);
		}
		for(Tone cur: currents){
			int r = chords.get(index).get(cur);
			@SuppressWarnings("unchecked")
			ArrayList<Tone> nhist = (ArrayList<Tone>) history.clone();
			nhist.add(cur);
			progress(nhist,index+1, rate+r);
		}			
	}
	
	

	private void printHistory(ArrayList<Tone> history, int rate){
		for(Tone h:history)
			System.out.print(" "+h+" ");
		System.out.println("RATE:"+Integer.toString(rate));
	}
	
	private static void mergeChord(HashMap<Tone,Integer> sum, HashMap<Tone,Integer> nm){
		Iterator<Entry<Tone,Integer>> iter = nm.entrySet().iterator(); 
		while (iter.hasNext()) {
		    Map.Entry<Tone,Integer> entry = (Map.Entry<Tone,Integer>) iter.next(); 
		    Tone key = entry.getKey();
		    int origin = sum.containsKey(key)?sum.get(key):0;				    
		    sum.put(key, origin+entry.getValue());
		} 
	}
	
	
	private void strictChord(int threshold){
		for(HashMap<Tone,Integer> mchords : chords){
			Iterator<Entry<Tone,Integer>> iter = mchords.entrySet().iterator();
			while(iter.hasNext()){
				Entry<Tone,Integer> entry = iter.next();
				if(entry.getValue()<threshold)
					iter.remove();
			}
		}
	}
	
	//find chords with pitches in the measure
	public static ArrayList<HashMap<Tone,Integer>> analyzeChord(Measure measure, 
			int nbeats) throws AnalyzerException, ParserException{
		if(measure.rhythm.beats%nbeats !=0 || nbeats > measure.rhythm.beats){
			throw new AnalyzerException("Invalid nbeats!");
		}
		int pace = measure.rhythm.beats/nbeats;
		ArrayList<HashMap<Tone,Integer>> chordCol = new ArrayList<HashMap<Tone,Integer>>();
		
		ArrayList<Tone> stressedTones = measure.getStressedTones();
		for(int i=0; i<nbeats; ++i){
			HashMap<Tone,Integer> chords = new HashMap<Tone,Integer>();
			for(int j=0; j<pace; ++j){
				
				Tone tone = stressedTones.get(i*pace+j);
				
				for(String[] seed : seedChords){
					HashMap<Tone,Integer> sc = tone.getChords(seed);
					mergeChord(chords,sc);
				}
			}
			if(chords.size()==0)
				throw new AnalyzerException("Cannot find a chord for this measure");
			chordCol.add(chords);
		}		
		return chordCol;
	}
}
