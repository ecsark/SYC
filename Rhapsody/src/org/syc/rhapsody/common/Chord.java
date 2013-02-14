package org.syc.rhapsody.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Chord extends Sod{
	public ArrayList<Pitch> pitches;

	public Chord(){
		pitches = new ArrayList<>();
	}
	
	@Override
	public HashMap<Chord,Integer> getChords(String[] strategy) throws ParserException{
		if(pitches.size()==0)
			return super.getChords(strategy);
		else if(pitches.size()==1){
			Note n = new Note();
			n.pitch = pitches.get(0);
			return n.getChords(strategy);
		}
		else{
			HashMap<Chord,Integer> chords = new HashMap<Chord,Integer>();
			chords.put(this, 1);
			return chords;
		}
		
	}
	
	Chord(ArrayList<Pitch> ps){
		this();
		pitches = ps;
		Collections.sort(pitches);
	}
	
	public Chord getNorm(){
		Chord norm = new Chord(pitches);
		for(Pitch p: norm.pitches)
			p.setBase(4);
		norm.duration = new Duration(4);
		Collections.sort(norm.pitches);
		return norm;
	}
	
	public void addPitch(Pitch pitch){
		pitches.add(pitch);
		Collections.sort(pitches);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pitches == null) ? 0 : pitches.hashCode());
		return result;
	}


	/*@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Chord))
			return false;
		Chord other = (Chord) obj;
		if (pitches == null) {
			if (other.pitches != null)
				return false;
		} else{
			ArrayList<Pitch> minus = new ArrayList<Pitch>();
			minus.addAll(pitches);
			minus.retainAll(other.pitches);
			if(minus.size()!=pitches.size())
				return false;
		
		}
		return true;
	}*/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Chord))
			return false;
		Chord other = (Chord) obj;
		if (pitches == null) {
			if (other.pitches != null)
				return false;
		} else if (!pitches.equals(other.pitches))
			return false;
		return true;
	}

	@Override
	public String toString(){
		String s = "(";
		for(Pitch p:pitches){
			s += " "+p.toText(true)+" ";
		}
		s += ")";
		return s;
	}
}



