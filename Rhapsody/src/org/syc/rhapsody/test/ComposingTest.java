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
			
			ArrayList<Tone> prog = progressions.get(8);//only test the 14th progression
			
			ArrayList<Measure> chorus = new ArrayList<Measure>();
			Measure r = new Measure();
			r.add(SodFactory.newRest(8));
			chorus.add(r);
			for(int i=1; i<prog.size(); ++i){//skip the first measure
				Measure ch = new Measure();
				Sod chord = new Sod(prog.get(i), new Duration(2));
				ch.add(chord);
				chorus.add(ch);
			}
			
			conv.addMeasures(be.sentence.measures,1);
			conv.addMeasures(chorus, 2);
			
			conv.play();
			
		} catch (ParserException | AnalyzerException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
