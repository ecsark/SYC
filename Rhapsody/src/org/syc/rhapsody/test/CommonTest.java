package org.syc.rhapsody.test;

import java.util.*;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import org.syc.rhapsody.common.*;
import org.syc.rhapsody.analyzer.*;

public class CommonTest {

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
					MeasureAnalyzer.analyzeChord(be.sentence.measures.get(1), 1);
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

}
