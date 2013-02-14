package org.syc.rhapsody.analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import org.syc.rhapsody.common.*;

public class KeySignature {
	
	private Pitch tonic;
	@SuppressWarnings("unused")
	private M mode;
	
	private HashMap<String,ArrayList<Pitch>> chordPool = new HashMap<String,ArrayList<Pitch>>();
	private HashSet<Pitch> pitchPool = new HashSet<Pitch>();
		
	public KeySignature(Pitch tonic, M mode) throws ParserException{
		this.tonic = tonic;
		getScale(mode);
	}
	
	public KeySignature(String tonicName, M mode) throws ParserException{
		tonic = new Pitch("4"+tonicName);
		getScale(mode);
	}
	
	private void getScale(M mode) throws ParserException{
		if(!ChordStrategy.ScaleMap.containsKey(mode))
			throw new ParserException("Invalid mode!");
		this.mode = mode;
		pitchPool.add(tonic);
		String[] scale = ChordStrategy.ScaleMap.get(mode);
		Pitch iter = tonic;
		for(String itv : scale){
			iter = iter.getPitchByIntervalUp(itv);
			pitchPool.add(iter);
		}
	}

	//norm
	public ArrayList<Pitch> getChord(String chordName) throws ParserException{
		if(chordPool.containsKey(chordName))
			return chordPool.get(chordName);
		ArrayList<Pitch> pitches = new ArrayList<Pitch>();
		if(!ChordStrategy.TriadMap.containsKey(chordName))
			throw new ParserException(chordName+" is not defined!");
		Pair<String, String[]> chordMethod = ChordStrategy.TriadMap.get(chordName);
		Pitch root = tonic.getPitchByIntervalUp(chordMethod.a);
		pitches.add(root);
		for(String s : chordMethod.b){
			root = root.getPitchByIntervalUp(s);
			pitches.add(root);
		}
		Collections.sort(pitches);
		chordPool.put(chordName, pitches);
		return pitches;
	}
	
	public boolean containsPitch(Pitch pitch){
		if(pitchPool.contains(pitch))
			return true;
		else
			return false;
	}
}
