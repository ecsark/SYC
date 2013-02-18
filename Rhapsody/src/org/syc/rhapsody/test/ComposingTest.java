package org.syc.rhapsody.test;

import java.util.*;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import org.syc.rhapsody.common.*;
import org.syc.rhapsody.analyzer.*;

public class ComposingTest {

	BlueElf be;
	Converter conv;
	
	@Before
	public void setUp(){
		be = new BlueElf();
		conv = new Converter();
	}

	
	//@Test
	public void converterTest(){
		
		try {
			conv.addMeasures(be.sentence.measures,1);
			conv.play();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		
	}
	
	//@Test
	public void test() {
		try {
			ArrayList<HashMap<Tone,Integer>> measureChords = 
					MeasureAnalyzer.analyzeMeasure(be.sentence.measures.get(1), 1);
			for(HashMap<Tone,Integer> beatChords: measureChords){
				Iterator<Entry<Tone,Integer>> iter = beatChords.entrySet().iterator(); 
				while (iter.hasNext()) {
				    Map.Entry<Tone,Integer> entry = (Map.Entry<Tone,Integer>) iter.next(); 
				    System.out.println("Chord: "+entry.getKey().toString()+" Value: "+entry.getValue());
				} 
			}
		} catch (AnalyzerException | ParserException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testTonicChord(){
		try {
			MeasureAnalyzer ma = new MeasureAnalyzer(be.sentence.measures, "C", M.MAJOR);
			ma.analyze();
			ArrayList<ArrayList<Tone>> progressions = ma.getProgressions();
			
			//print
			for(int i=0; i<progressions.size();++i){
				System.out.print(Integer.toString(i)+": ");
				for(Tone h:progressions.get(i))
					System.out.print(" "+h+" ");
				System.out.println();
			}
			
			ArrayList<Tone> prog = progressions.get(32);//only test the 14th progression
			
			ArrayList<Measure> chorus = new ArrayList<Measure>();
			Measure r = new Measure();
			r.add(SodFactory.newRest(8));
			chorus.add(r);
			for(int i=1; i<prog.size(); ++i){//skip the first measure
				/*Measure ch = new Measure();
				
				Sod chord = new Sod(prog.get(i), new Duration(2));
				ch.add(chord);
				chorus.add(ch);*/
				
				SimpleAccompaniment accpn = new SimpleAccompaniment(prog.get(i), be.rhythm);
				accpn.expand();
				chorus.add(accpn);
			}
			
			conv.addMeasures(be.sentence.measures,1);
			conv.addMeasures(chorus, 2);
			
			conv.play();
			
		} catch (ParserException | AnalyzerException e) {
			e.printStackTrace();
		}		
	}	
	
}

class SimpleAccompaniment extends Measure{
	private Tone chord;
	
	SimpleAccompaniment(Tone chord, Rhythm rhythm) throws AnalyzerException{
		super(rhythm);
		if(chord.pits.size()<3)
			throw new AnalyzerException("A chord must consist of at least three notes!");
		this.chord = chord;
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
