package org.syc.rhapsody.common;

import java.util.ArrayList;
import java.util.Iterator;

public class Measure {
	
	public Rhythm rhythm;
	
	public ArrayList<Sod> sods;
	
	public Measure(){
		 sods = new ArrayList<Sod>();
		 rhythm = new Rhythm();
	}
	
	public Measure(Rhythm rhythm){
		sods = new ArrayList<Sod>();
		this.rhythm =rhythm;  
	}
	
	public void add(Sod sod){
		sods.add(sod);
	}
	
	public ArrayList<Tone> getStressedTones(){
		
		ArrayList<Tone> stressedTones = new ArrayList<Tone>();
		
		Duration unit = new Duration(rhythm.unit);
		Duration cum = new Duration(), d = new Duration();		
		Iterator<Sod> sodIter = sods.iterator();
		Sod cur = sods.get(0);
		
		for(int i=0; i<rhythm.beats; ++i){	
			while(cum.noGreaterThan(d)){
				if(!sodIter.hasNext())
					break;				
				cur = sodIter.next();
				cum.add(cur.duration);
			}
			
			stressedTones.add(cur.tone);
			
			if(!sodIter.hasNext())
				break;
			
			d.add(unit);
		}
		
		if(stressedTones.size()<rhythm.beats){
			Tone last = stressedTones.get(stressedTones.size()-1);
			while(stressedTones.size()<rhythm.beats)
				stressedTones.add(last);
		}
		
		return stressedTones;
	}
	
}
