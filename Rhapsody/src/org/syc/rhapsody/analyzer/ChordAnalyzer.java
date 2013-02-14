package org.syc.rhapsody.analyzer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.syc.rhapsody.common.ParserException;
import org.syc.rhapsody.common.Pitch;
import org.syc.rhapsody.common.Tone;

public class ChordAnalyzer {

	protected KeyAnalyzer key;
	protected HashSet<Tone> availChord;
	
	private Tone currentChord;
	
	public ChordAnalyzer(KeyAnalyzer key){
		
		this.key = key;
		availChord = new HashSet<Tone>();
		String[] chordList = {"I","IV","V"};
		for(String s : chordList)
			try {
				availChord.add(key.getChord(s));
			} catch (ParserException e) {
				System.err.println("Default chord list definition error!");
				e.printStackTrace();
			}		
	}
	
	public ChordAnalyzer(KeyAnalyzer key, String[] chordList) throws ParserException{
		this.key = key;
		availChord = new HashSet<Tone>();
		for(String s : chordList)
			availChord.add(key.getChord(s));		
	}
	
	protected void eliminateChord(HashMap<Tone, Integer> beatChords){
		Iterator<Entry<Tone,Integer>> iter = beatChords.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<Tone,Integer> entry = (Map.Entry<Tone,Integer>) iter.next();
			Tone chord = entry.getKey();
			if(!availChord.contains(chord))
				iter.remove();
		}
	}
	
	protected void eliminateOvertoneChord(HashMap<Tone, Integer> beatChords){
		Iterator<Entry<Tone,Integer>> iter = beatChords.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<Tone,Integer> entry = (Map.Entry<Tone,Integer>) iter.next();
			Tone chord = entry.getKey();
			for(Pitch p : chord.pits){
				if(!key.containsPitch(p)){
					iter.remove();
					break;
				}
			}
		}
	}
}
