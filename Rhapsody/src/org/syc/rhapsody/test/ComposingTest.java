package org.syc.rhapsody.test;

import java.util.*;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import org.syc.rhapsody.common.*;
import org.syc.rhapsody.analyzer.*;

public class ComposingTest {

	BlueElf be;
	
	@Before
	public void setUp(){
		be = new BlueElf();		
	}
	
	
	//@Test
	public void hashTest(){
		HashSet<Pitch> ps = new HashSet<Pitch>();
		
		try {
			Pitch p1 = new Pitch("4C"), p2 = new Pitch("5C");
			ps.add(p1);
			System.out.println(ps.contains(p2));
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
		} catch (ParserException | AnalyzerException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
