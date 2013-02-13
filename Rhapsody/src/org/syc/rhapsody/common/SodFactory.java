package org.syc.rhapsody.common;

import java.util.Collections;

public class SodFactory {

	
	/*
	 * @param pitchName e.g. 4C#
	 * @param type 1(whole), 2(half), 4(fourth), ...
	 * @param extra dotted
	 * @return a note
	 */	
	public static Note newNote(String pitchName, int type, M... extras) throws ParserException
	{
		Pitch pitch= new Pitch(pitchName);
				
		Duration dur = new Duration(type);
		for(M extra:extras){
			switch(extra){
			case DOTTED: dur.addHalf(); break;
			default: break;
			}
		}
		Note note = new Note();
		note.duration = dur;
		note.pitch = pitch;
		return note;
	}
	
	/*
	 * @param pitchNameList e.g. [4C#, 4E#, 4G#]
	 * @param type 1(whole), 2(half), 4(fourth), ...
	 * @param extra dotted	 * 
	 * @return a chord
	 */
	public static Chord newChord(String[] pitchNameList, int type, M... extras) throws ParserException{
		Chord chord = new Chord();
		for(String pitchName:pitchNameList){
			Pitch pitch= new Pitch(pitchName);			
			chord.pitches.add(pitch);			
		}
		Collections.sort(chord.pitches);
		Duration dur = new Duration(type);
		
		for(M extra:extras){
			switch(extra){
			case DOTTED: dur.addHalf(); break;
			default: break;
			}
		}
		chord.duration = dur;
		return chord;
	}
	
	/*
	 * @param type 1(whole), 2(half), 4(fourth), ...
	 * @param extra dotted
	 * @return a rest
	 */
	public static Rest newRest(int type, M... extras){
		Rest rest = new Rest();
		Duration dur = new Duration(type);
		for(M extra:extras){
			switch(extra){
			case DOTTED: dur.addHalf(); break;
			default: break;
			}
		}
		return rest;
	}
	

}
