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
			load1642561();
		} catch (ParserException e) {
			System.err.println("Pitch alternation symbolic error");
			e.printStackTrace();
		}
	}
	
	void load1642561() throws ParserException{
		alternation = new HashMap<Tone, HashMap<Tone,Effect>>();
		String[] loop = {"I","VI","IV","II","V","VI","I"};
		Effect eff = new Effect();
		for(int i=0; i<loop.length; ++i){
			Tone fr = ka.getChord(loop[i]);
			HashMap<Tone,Effect> t = new HashMap<Tone, Effect>();
			for(int j=i; j<loop.length; ++j){
				Tone to = ka.getChord(loop[j]);
				t.put(to, eff);
			}
			if(!alternation.containsKey(fr))
				alternation.put(fr, t);
			else{
				HashMap<Tone,Effect> tt = alternation.get(fr);
				tt.putAll(t);
			}
		}
	}
	
	void load145() throws ParserException{
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
	
	
}
