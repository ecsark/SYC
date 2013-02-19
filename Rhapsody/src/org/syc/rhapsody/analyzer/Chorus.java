package org.syc.rhapsody.analyzer;

import org.syc.rhapsody.common.*;

public class Chorus extends Measure {
	protected Tone chord;
	
	public Chorus(Tone chord, Rhythm rhythm) throws AnalyzerException{
		super(rhythm);
		if(chord.pits.size()<3)
			throw new AnalyzerException("A chord must consist of at least three notes!");
		this.chord = chord;
		expand();
	}
	
	public void expand() throws AnalyzerException{
		Tone root = new Tone(chord.pits.get(0));
		Tone trunk = new Tone();
		for(int i=1; i<chord.pits.size(); ++i){
			trunk.addPitch(chord.pits.get(i));
		}
		if(rhythm.beats == 0 || rhythm.unit == 0)
			throw new AnalyzerException("Rhythm has to be redefined!");
		Duration dur = new Duration(rhythm.unit*2);
		for(int i=0; i<rhythm.beats; ++i){
			sods.add(new Sod(root, dur));
			sods.add(new Sod(trunk, dur));
			
		}
	}
}
