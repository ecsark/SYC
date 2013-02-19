package org.syc.rhapsody.analyzer;

import java.util.HashMap;
import java.util.HashSet;

import org.syc.rhapsody.common.*;

public class KeyAnalyzer {
	
	private Pitch tonic;
	@SuppressWarnings("unused")
	private M mode;
	
	private HashMap<String,Tone> chordPool = new HashMap<String,Tone>();
	private HashSet<Pitch> pitchPool = new HashSet<Pitch>();
		
	public KeyAnalyzer(Pitch tonic, M mode) throws ParserException{
		this.tonic = tonic;
		getScale(mode);
	}
	
	public KeyAnalyzer(String tonicName, M mode) throws ParserException{
		tonic = new Pitch("4"+tonicName);
		getScale(mode);
	}
	
	private void getScale(M mode) throws ParserException{
		if(!ChordDefinition.ScaleMap.containsKey(mode))
			throw new ParserException("Invalid mode!");
		this.mode = mode;
		pitchPool.add(tonic);
		String[] scale = ChordDefinition.ScaleMap.get(mode);
		Pitch iter = tonic;
		for(String itv : scale){
			iter = iter.getPitchByIntervalUp(itv);
			pitchPool.add(iter);
		}
	}

	//norm
	public Tone getChord(String chordName) throws ParserException{
		if(chordPool.containsKey(chordName))
			return chordPool.get(chordName);
		Tone chord = new Tone();
		if(!ChordDefinition.ChordMap.containsKey(chordName))
			throw new ParserException(chordName+" is not defined!");
		Pair<String, String[]> chordMethod = ChordDefinition.ChordMap.get(chordName);
		Pitch root = tonic.getPitchByIntervalUp(chordMethod.a);
		chord.addPitch(root);
		for(String s : chordMethod.b){
			root = root.getPitchByIntervalUp(s);
			chord.addPitch(root);
		}
		chord.toNorm();//convert to norm
		chordPool.put(chordName, chord);
		return chord;
	}
	
	public boolean containsPitch(Pitch pitch){
		if(pitchPool.contains(pitch))
			return true;
		else
			return false;
	}
}
