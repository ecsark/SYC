package org.syc.rhapsody.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.syc.rhapsody.common.Chord;
import org.syc.rhapsody.common.ParserException;
import org.syc.rhapsody.common.Pitch;

public class Progression {
	
	protected KeySignature key;
	protected HashSet<ArrayList<Pitch>> availChord;
	
	
	public Progression(KeySignature key){
		this.key = key;
		availChord = new HashSet<ArrayList<Pitch>>();
		String[] chordList = {"I","IV","V"};
		for(String s : chordList)
			try {
				availChord.add(key.getChord(s));
			} catch (ParserException e) {
				System.err.println("Default chord list definition error!");
				e.printStackTrace();
			}		
	}
	
	public Progression(KeySignature key, String[] chordList) throws ParserException{
		this.key = key;
		availChord = new HashSet<ArrayList<Pitch>>();
		for(String s : chordList)
			availChord.add(key.getChord(s));		
	}
	
	protected void eliminateChord(HashMap<Chord, Integer> beatChords){
		Iterator<Entry<Chord,Integer>> iter = beatChords.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<Chord,Integer> entry = (Map.Entry<Chord,Integer>) iter.next();
			Chord chord = entry.getKey();
			if(!availChord.contains(chord.pitches))
				iter.remove();
		}
	}
	
	protected void eliminateOvertoneChord(HashMap<Chord, Integer> beatChords){
		Iterator<Entry<Chord,Integer>> iter = beatChords.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<Chord,Integer> entry = (Map.Entry<Chord,Integer>) iter.next();
			Chord chord = entry.getKey();
			for(Pitch p : chord.pitches){
				if(!key.containsPitch(p)){
					iter.remove();
					break;
				}
			}
		}
	}
}
