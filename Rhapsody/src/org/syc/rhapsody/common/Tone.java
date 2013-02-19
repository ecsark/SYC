package org.syc.rhapsody.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Map.Entry;

import org.syc.rhapsody.analyzer.AnalyzerException;

public class Tone{
	public ArrayList<Pitch> pits;

	public Tone(){
		pits = new ArrayList<>();
	}

	public Tone(Pitch pitch){
		this();
		pits.add(pitch);
	}
	
	Tone(ArrayList<Pitch> ps){
		this();
		pits = ps;
		Collections.sort(pits);
	}

	
	public void addPitch(Pitch pitch){
		pits.add(pitch);
		Collections.sort(pits);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pits == null) ? 0 : pits.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tone))
			return false;
		Tone other = (Tone) obj;
		if (pits == null) {
			if (other.pits != null)
				return false;
		} else if (!pits.equals(other.pits))
			return false;
		return true;
	}

	@Override
	public String toString(){
		String s = "(";
		for(Pitch p:pits){
			s += " "+p.toText(true)+" ";
		}
		s += ")";
		return s;
	}
	
	
	private HashMap<Tone,Integer> getChords(String[] strategy, Pitch pitch) throws ParserException{
		HashMap<Tone,Integer> cmap = new HashMap<Tone,Integer>();
		for(int idx=0; idx<strategy.length+1; ++idx){
			Tone ps = new Tone();
			
			Pitch iter = new Pitch(pitch);
			for(int j=0; j<idx; ++j){
				iter = iter.getPitchByIntervalDown(strategy[idx-1-j]);
				ps.addPitch(iter);
			}
				
			ps.addPitch(pitch);
			iter = pitch;
			for(int j=idx; j<strategy.length; ++j){
				iter = iter.getPitchByIntervalUp(strategy[j]);
				ps.addPitch(iter);
			}
			
			cmap.put(ps, 1);
		}
		return cmap;
	}
	
	
	public HashMap<Tone,Integer> getChords(String[] strategy) throws ParserException, AnalyzerException{
		if(pits.size()==0)//rest
			return new HashMap<Tone,Integer>();
		
		HashMap<Tone,Integer> chords = getChords(strategy,pits.get(0));
		
		if(pits.size()==1)//single note
			return chords;
		
		
		Set<Tone> tones = chords.keySet();
		for(int i=1; i<pits.size(); ++i){
			HashMap<Tone,Integer> ch = getChords(strategy,pits.get(i));
			Set<Tone> tn = ch.keySet();
			Iterator<Tone> it = tones.iterator();
			while(it.hasNext()){
				Tone t = it.next();
				if(!tn.contains(t))
					it.remove();
			}
		}
		
		return chords;			
	}
	
	public void toNorm(){
		for(Pitch p : pits)
			p.setBase(4);
	}
}

