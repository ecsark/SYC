package org.syc.rhapsody.test;

import java.util.*;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import org.syc.rhapsody.common.*;
import org.syc.rhapsody.analyzer.*;

public class ComposingTest {

	Sentence sentence;
	
	@Before
	public void setUp() throws Exception {
		sentence = new Sentence();
		Rhythm rhythm = new Rhythm(4,2);
		Measure m1 = new Measure();
		m1.rhythm = rhythm;
		m1.add(SodFactory.newNote("4E", 16));
		m1.add(SodFactory.newNote("4F", 16));
		sentence.measures.add(m1);
		Measure m2 = new Measure();
		m2.rhythm = rhythm;
		m2.add(SodFactory.newNote("4G", 8));
		m2.add(SodFactory.newNote("4F", 8));
		m2.add(SodFactory.newNote("4E", 8));
		m2.add(SodFactory.newNote("4F", 8));
		sentence.measures.add(m2);
		sentence.measures.add(m2);
		Measure m4 = new Measure();
		m4.rhythm = rhythm;
		m4.add(SodFactory.newNote("4G", 8));
		m4.add(SodFactory.newNote("4G", 16));
		m4.add(SodFactory.newNote("4F#", 16));
		m4.add(SodFactory.newNote("4G", 8));
		m4.add(SodFactory.newNote("5E",8));
		sentence.measures.add(m4);
		Measure m5 = new Measure();
		m5.rhythm = rhythm;
		m5.add(SodFactory.newNote("5C", 4, M.DOTTED));
		m5.add(SodFactory.newNote("4D", 16));
		m5.add(SodFactory.newNote("4E", 16));
		sentence.measures.add(m5);
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
			ArrayList<HashMap<Chord,Integer>> measureChords = 
					MeasureAnalyzer.analyzeMeasure(sentence.measures.get(1), 1);
			for(HashMap<Chord,Integer> beatChords: measureChords){
				Iterator<Entry<Chord,Integer>> iter = beatChords.entrySet().iterator(); 
				while (iter.hasNext()) {
				    Map.Entry<Chord,Integer> entry = (Map.Entry<Chord,Integer>) iter.next(); 
				    System.out.println("Chord: "+entry.getKey().toString()+" Value: "+entry.getValue());
				} 
			}
		} catch (AnalyzerException | ParserException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testTonicChord(){
		KeySignature ks;
		try {
			ks = new KeySignature("E",M.MAJOR);
			/*Iterator<Pitch> iter = ks.pitchPool.iterator();
			while(iter.hasNext()){
				System.out.println(iter.next().toText(true));
			}*/
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}
	
	
}
