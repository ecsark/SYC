package org.syc.rhapsody.test;

import org.syc.rhapsody.common.*;

public class BlueElf {
	
	public Sentence sentence;
	public Rhythm rhythm;
	
	BlueElf(){
		sentence = new Sentence();
		rhythm = new Rhythm(4,2);
		try {
			m1_5();
			//m6_16();//not work on 8th measure
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}
	
	private Measure addNote(String[] pitch, int[] type) throws ParserException{
		if(pitch.length!=type.length)
			throw new ParserException("Pitch length and Type length should be equal");
		Measure m = new Measure();
		m.rhythm = rhythm;
		for(int i=0; i<pitch.length; ++i)
			m.add(SodFactory.newNote(pitch[i], type[i]));
		sentence.measures.add(m);
		return m;
	}
	
	private void m1_5() throws ParserException{
		
		addNote(new String[]{"4E","4F"}, new int[]{16,16});
		addNote(new String[]{"4G","4F","4E","4F"}, new int[]{8,8,8,8});
		addNote(new String[]{"4G","4F","4E","4F"}, new int[]{8,8,8,8});
		addNote(new String[]{"4G","4G","4F#","4G","5E"}, new int[]{8,16,16,8,8});
		Measure m5 = addNote(new String[]{"5C","4D","4E"}, new int[]{4,16,16});
		m5.sods.set(0, SodFactory.newNote("5C",4,M.DOTTED));		
	}
	
	private void m6_16() throws ParserException{
		addNote(new String[]{"4F","4E","4F","5D"}, new int[]{8,8,8,8});
		Measure m7 = addNote(new String[]{"4B","4D","4E"}, new int[]{4,16,16});
		m7.sods.set(0, SodFactory.newNote("4B",4,M.DOTTED));
		
		addNote(new String[]{"4F","4E","4F","4A"}, new int[]{8,8,8,8});
		Measure m9 = addNote(new String[]{"4G","4E","4F"}, new int[]{4,16,16});
		m9.sods.set(0, SodFactory.newNote("4G",4,M.DOTTED));
		
		addNote(new String[]{"4G","4F","4E","4F"}, new int[]{8,8,8,8});
		addNote(new String[]{"4G","4F","4E","4F"}, new int[]{8,8,8,8});
		addNote(new String[]{"4G","4G","4F#","4G","5E"}, new int[]{8,16,16,8,8});
		Measure m13 = addNote(new String[]{"5D","4D","4E"}, new int[]{4,16,16});
		m13.sods.set(0, SodFactory.newNote("5D",4,M.DOTTED));
		
		addNote(new String[]{"4F","4E","4F","5D"}, new int[]{8,8,8,8});
		addNote(new String[]{"5C","4B","4A","4B"}, new int[]{8,8,8,8});
		addNote(new String[]{"5C"}, new int[]{2});
	}
}
