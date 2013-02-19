package org.syc.rhapsody.analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.syc.rhapsody.common.*;

public class Texture {
	
	private ArrayList<Duration> beat; //duration backed up by sod in the measure
	private ArrayList<ArrayList<Integer>> flow; //pit backed up by sod in the measure
	protected int pitNum;
	private Rhythm rhythm;
	
	
	public Texture(Measure measure){
		pitNum = -1;
		parseBeat(measure);
		parseFlow(measure);
		rhythm = measure.rhythm;		
	}	
	
	private void parseBeat(Measure measure){
		beat = new ArrayList<Duration>();
		for(Sod sod : measure.sods)
			beat.add(sod.duration);
	}
	
	private void parseFlow(Measure measure){		
		ArrayList<Pitch> pitches = new ArrayList<Pitch>();
		for(Sod sod : measure.sods){
			for(Pitch pit : sod.tone.pits)
				if(!pitches.contains(pit))
					pitches.add(pit);
		}
		Collections.sort(pitches, new PitchAbsoluteComparator());
		pitNum = pitches.size();
		
		flow = new ArrayList<ArrayList<Integer>>();
		for(Sod sod : measure.sods){
			ArrayList<Integer> s = new ArrayList<Integer>();
			for(Pitch pit : sod.tone.pits){
				s.add(pitches.indexOf(pit));
			}
			flow.add(s);
		}
	}
	
	
	//pits will be sorted in the method and will reflect in the calling method
	public Measure compose(ArrayList<Pitch> pits) throws AnalyzerException{
		if(pitNum<1 || beat.size() != flow.size())
			throw new AnalyzerException("Texture has not been initialized properly!");
		Collections.sort(pits, new PitchAbsoluteComparator()); //here pits are sorted
		Measure m = new Measure(rhythm);
		for(int i=0; i<beat.size(); ++i){
			Tone tone = new Tone();
			for(int j=0; j<flow.get(i).size(); ++j)
				tone.addPitch(pits.get(flow.get(i).get(j)));
			m.add(new Sod(tone, beat.get(i)));
		}
		return m;
	}
	
		
	public Measure compose(Tone tone) throws AnalyzerException{
		ArrayList<Pitch> pits;
		if(tone.pits.size()>pitNum)
			pits = rearrangeOver(tone);
		else if(tone.pits.size()<pitNum)
			pits = rearrangeBelow(tone);
		else
			pits = rearrangeEqual(tone);
		return compose(pits);
	}
	
	//derived class can override this method
	public ArrayList<Pitch> rearrangeOver(Tone tone){
		ArrayList<Pitch> pits = new ArrayList<Pitch>();
		pits.add(tone.pits.get(0));
		int lidx = tone.pits.size()-1;
		for(int i=0; i<pitNum-1; ++i)
			pits.add(tone.pits.get(lidx-i));
		return pits;
	}
	
	//derived class can override this method
	public ArrayList<Pitch> rearrangeEqual(Tone tone){
		return rearrangeOver(tone);
	}
	
	//derived class can override this method
	public ArrayList<Pitch> rearrangeBelow(Tone tone){
		ArrayList<Pitch> pits = new ArrayList<Pitch>();
		int sz = tone.pits.size();
		for(int i=0; i<pitNum; ++i){
			Pitch p = tone.pits.get(pitNum%i);
			for(int k=0; k<i/sz; ++k){
				p = p.getPitchByIntervalUp(12); //octave
			}
			pits.add(p);
		}
		return pits;
	}
}


class PitchAbsoluteComparator implements Comparator<Pitch>{
	@Override
	public int compare(Pitch o1, Pitch o2) {
		return o1.pit - o2.pit;
	}	
}