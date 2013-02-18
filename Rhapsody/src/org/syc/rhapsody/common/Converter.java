package org.syc.rhapsody.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jfugue.*;


public class Converter {
	
	private Player player;
	private Pattern pattern;
	private HashMap<Integer,Pattern> voices;
	
	public Converter(){
		player = new Player();
		voices = new HashMap<Integer, Pattern>();
	}
	
	public void saveMidi(File file) throws IOException{
		//sync();
		beTweak();
		player.saveMidi(pattern, file);
	}
	
	public void play(){
		//sync();
		beTweak();
		player.play(pattern);
	}
	
	private void sync(){
		pattern = new Pattern();
		Iterator<Entry<Integer,Pattern>> eiter = voices.entrySet().iterator();
		while(eiter.hasNext()){
			Entry<Integer,Pattern> entry = (Entry<Integer,Pattern>) eiter.next(); 
			pattern.add(addVoiceHead(entry.getKey(), entry.getValue(), "Piano"));
		}
	}
	
	private String addVoiceHead(int part, Pattern pattern, String instrument){
		return "V"+Integer.toString(part)+" I["+instrument+"] "+pattern.getMusicString();
	}
	
	private void beTweak(){
		pattern = new Pattern();
		pattern.add(addVoiceHead(1,voices.get(1), "Piano"));
		
		OctaveTransformer ot = new OctaveTransformer(false);
		pattern.add(addVoiceHead(2,ot.transform(voices.get(2)), "Piano"));
		//pattern.add(addVoiceHead(2,voices.get(2), "Piano"));
        
	}
	
	public void addMeasure(Measure measure, int part) throws ParserException{
		Pattern p;
		if(voices.containsKey(part))
			p = voices.get(part);
		else
		{
			if(part<0)
				throw new ParserException("Part number must be greater than zero!");
			p = new Pattern();
		}
		
		p.add(toMusicString(measure));
		voices.put(part, p);		
		
	}
	
	public void addMeasures(ArrayList<Measure> measures, int part) throws ParserException{
		for(Measure m : measures)
			addMeasure(m,part);
	}

	public static String toMusicString(Measure measure) throws ParserException{
		String pt = "";
		for(Sod sod : measure.sods)
			pt += " "+sod2String(sod);
		return pt;
	}
	
	private static String sod2String(Sod sod) throws ParserException{
		String dur = Float.toString(sod.duration.time/4);
		Tone t = sod.tone;
		if(t.pits.size()==0)//case of a rest
			return "R/" + dur;

		int pit = t.pits.get(0).pit+12;
		if(pit>127 || pit<0)
			throw new ParserException("Pit"+ Integer.toString(pit-12) +"out of range!");
		String s = "["+Integer.toString(pit) +"]/"+dur;
		for(int i=1; i<t.pits.size(); ++i){
			pit = t.pits.get(i).pit+12;
			if(pit>127 || pit<0)
				throw new ParserException("Pit"+ Integer.toString(pit-12) +"out of range!");
			s += "+["+Integer.toString(pit)+"]/"+dur;
		}
		return s;
	}
	
}

class OctaveTransformer extends PatternTransformer{
	
	private int interval;
	
	OctaveTransformer(boolean upwards){
		this(upwards,12);
	}
	
	OctaveTransformer(boolean upwards, int interval){
		super();
		if(upwards)
			this.interval = interval;
		else
			this.interval = 0 - interval;
		
	}
	
	@Override
	public void noteEvent(Note note){
        byte currentValue = note.getValue();
        if(currentValue!=0)
        	note.setValue((byte)(currentValue + interval));
        getReturnPattern().addElement(note);
    }
	
	@Override
	public void sequentialNoteEvent(Note note){
		noteEvent(note);
	}
	
	@Override
	public void parallelNoteEvent(Note note){
		noteEvent(note);
	}
}
