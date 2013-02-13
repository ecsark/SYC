package org.syc.rhapsody.common;

import java.util.ArrayList;
import java.util.HashMap;

public class Note extends Sod{
	Pitch pitch;
	
	@Override
	public HashMap<Chord,Integer> getChords(String[] strategy) throws ParserException{
		HashMap<Chord,Integer> cmap = new HashMap<Chord,Integer>();
		for(int idx=0; idx<strategy.length+1; ++idx){
			ArrayList<Pitch> ps = new ArrayList<Pitch>();
			
			Pitch iter = pitch;
			for(int j=0; j<idx; ++j){
				iter = iter.getPitchByIntervalDown(strategy[idx-1-j]);
				ps.add(iter);
			}
				
			ps.add(pitch);
			iter = pitch;
			for(int j=idx; j<strategy.length; ++j){
				iter = iter.getPitchByIntervalUp(strategy[j]);
				ps.add(iter);
			}
			
			Chord chord = new Chord(ps);			
			cmap.put(chord.getNorm(), 1);
		}
		return cmap;
	}
	
	@Override
	public String toString(){
		return "( "+pitch.toText(true)+" )";
	}
}
