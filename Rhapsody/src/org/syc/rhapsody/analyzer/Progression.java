package org.syc.rhapsody.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.syc.rhapsody.common.Tone;
import org.syc.rhapsody.common.ParserException;
import org.syc.rhapsody.common.Pitch;

public class Progression {
	
	KeyAnalyzer ka;
	HashMap<Tone, HashMap<Tone, Effect>> alternation;
	
	Progression(KeyAnalyzer ka){
		this.ka = ka;
		try {
			load();
		} catch (ParserException e) {
			System.err.println("Pitch alternation symbolic error");
			e.printStackTrace();
		}
	}
	
	void load() throws ParserException{
		alternation = new HashMap<Tone, HashMap<Tone,Effect>>();
		
		Tone tone1 = ka.getChord("I");
		Tone tone4 = ka.getChord("IV");
		Tone tone5 = ka.getChord("V");
		
		HashMap<Tone,Effect> t1 = new HashMap<Tone, Effect>();
		HashMap<Tone,Effect> t4 = new HashMap<Tone, Effect>();
		HashMap<Tone,Effect> t5 = new HashMap<Tone, Effect>();
		
		Effect eff = new Effect();
		t1.put(tone1, eff);
		t1.put(tone4, eff);
		t1.put(tone5, eff);
		t4.put(tone1, eff);
		t4.put(tone4, eff);
		t4.put(tone5, eff);
		t5.put(tone1, eff);
		t5.put(tone5, eff);
		
		alternation.put(tone1, t1);
		alternation.put(tone4, t4);
		alternation.put(tone5, t5);
	}
	
	
	HashMap<Tone,Effect> next(Tone current){
		return alternation.get(current);
	}
	
	/*
	private static ArrayList<Pair<String, Integer>> circle;
	static
	{	
		circle = new ArrayList<Pair<String,Integer>>();
		circle.add(new Pair<String,Integer>("I",3));
		circle.add(new Pair<String,Integer>("VI",2));
		circle.add(new Pair<String,Integer>("IV",3));
		circle.add(new Pair<String,Integer>("II",2));
		circle.add(new Pair<String,Integer>("V",3));
		circle.add(new Pair<String,Integer>("VI",2));
	}
	
	
	
	private void convert() throws ParserException{
		alternation = new HashMap<Tone, HashMap<Tone,Integer>>();
		int len = circle.size();
		for(int i=0; i<len; ++i){
			Tone from = ka.getChord(circle.get(i).a);
			HashMap<Tone,Integer> trans = new HashMap<Tone, Integer>();
			for(int j=0; j<len-2; ++j){
				Tone to = ka.getChord(circle.get((i+j)%len).a);
				trans.put(to, circle.get((i+j)%len).b);
			}
			alternation.put(from, trans);
		}
	}*/
	
	
}
