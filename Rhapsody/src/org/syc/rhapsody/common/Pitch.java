package org.syc.rhapsody.common;

import java.util.HashMap;

public class Pitch implements Comparable<Pitch>{	

	private int pit;
	
	private static HashMap<String, Integer> interval;
	
	static{
		interval = new HashMap<String,Integer>();
		String[] itvn = {"P1","m2","M2","m3","M3","P4","A4","d5","P5","m6","M6","m7","M7","P8"};
		for(int i=0; i<itvn.length; ++i)
			interval.put(itvn[i], i);		
	}
	
	private Pitch(int pit){
		this.pit = pit;
	}
	
 	public Pitch(int alpha, int base){
		pit = base*12+alpha;
	}
	
	//C#,4
	public Pitch(String pitchName, int base) throws ParserException{
		this(Integer.toString(base)+pitchName);
	}
		
	//4C#
	public Pitch(String pitchName) throws ParserException{
		char[] pn = pitchName.toCharArray();
		int base = Integer.parseInt(String.valueOf(pn[0]));
		pit = base*12;
				
		switch(pn[1]){
		case 'C': case 'c': pit += 0; break;
		case 'D': case 'd': pit += 2; break;
		case 'E': case 'e': pit += 4; break;
		case 'F': case 'f': pit += 5; break;
		case 'G': case 'g': pit += 7; break;
		case 'A': case 'a': pit += 9; break;
		case 'B': case 'b': pit += 11; break;
		default: 
			ParserException e = new ParserException("Invalid key name in "+ pitchName);
			throw e;
		}
		
		if(pn.length>2){
			switch(pn[2]){
			case '#': pit += 1; break;
			case 'b': pit -= 1; break;
			case 'T': case 't': pit -= 2; break; //bb
			case 'S': case 's': pit += 2; break; //##
			}
		}
	}
	
	
	public int getBase(){
		return pit/12;
	}
	
	public int getAlpha(){
		return pit%12;
	}
	
	public boolean setBase(int base){
		if(base<0)
			return false;		
		pit = pit%12 + base*12;
		return true;
	}
	
	public boolean setAlpha(int alpha){
		if(alpha<0 || alpha>11)
			return false;
		pit = pit - pit%12 + alpha;
		return true;
	}
	
	public Pitch getPitchByIntervalUp(int itv){
		Pitch n = new Pitch(pit);
		n.pit += itv;
		return n;
	}
	
	public Pitch getPitchByIntervalUp(String itv) throws ParserException{
		Pitch n = new Pitch(pit);
		if(!interval.containsKey(itv))
			throw new ParserException("Invalid interval "+itv);
		n.pit += interval.get(itv);
		return n;
	}
	
	public Pitch getPitchByIntervalDown(int itv){
		Pitch n = new Pitch(pit);
		n.pit -= itv;
		return n;
	}
	
	public Pitch getPitchByIntervalDown(String itv) throws ParserException{
		Pitch n = new Pitch(pit);
		if(!interval.containsKey(itv))
			throw new ParserException("Invalid interval "+itv);
		n.pit -= interval.get(itv);
		return n;
	}
	
	

	@Override
	public int hashCode() {
		return pit%12;
	}


	//Be extremely careful because two octave pitches are regarded identical!
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pitch))
			return false;
		Pitch other = (Pitch) obj;
		if (pit%12 != other.pit%12)
			return false;
		return true;
	}
	

	@Override
	public int compareTo(Pitch p) {
		return this.pit - p.pit;
	}
	
	@Override
	public String toString(){
		return Integer.toString(pit%12);
	}
	
	public String toText(boolean sharp){
		String text = "";
		switch(pit%12){
		case 0: text = "C"; break;
		case 2: text = "D"; break;
		case 4: text = "E"; break;
		case 5: text = "F"; break;
		case 7: text = "G"; break;
		case 9: text = "A"; break;
		case 11: text = "B"; break;
		}
		if(sharp==true){
			switch(pit%12){
			case 1: text = "#C"; break;
			case 3: text = "#D"; break;
			case 6: text = "#F"; break;
			case 8: text = "#G"; break;
			case 10: text = "#A"; break;
			}
		}
		else{
			switch(pit%12){
			case 1: text = "bD"; break;
			case 3: text = "bE"; break;
			case 6: text = "bG"; break;
			case 8: text = "bA"; break;
			case 10: text = "bB"; break;
			}
		}
		return text;
	}
}

