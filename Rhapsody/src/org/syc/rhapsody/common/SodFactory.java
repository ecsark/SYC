package org.syc.rhapsody.common;

public class SodFactory {
	
	/*
	 * @param pitchName e.g. 4C#
	 * @param type 1(whole), 2(half), 4(fourth), ...
	 * @param extra dotted
	 * @return a note
	 */	
	public static Sod newNote(String pitchName, int type, M... extras) throws ParserException
	{
		Pitch pitch= new Pitch(pitchName);
		Tone tone = new Tone(pitch);
		Duration dur = new Duration(type);
		for(M extra:extras){
			switch(extra){
			case DOTTED: dur.addHalf(); break;
			default: break;
			}
		}
		return new Sod(tone,dur);
	}
	
	/*
	 * @param pitchNameList e.g. [4C#, 4E#, 4G#]
	 * @param type 1(whole), 2(half), 4(fourth), ...
	 * @param extra dotted	 * 
	 * @return a chord
	 */
	public static Sod newChord(String[] pitchNameList, int type, M... extras) throws ParserException{
		Tone tone = new Tone();
		for(String pitchName:pitchNameList){
			Pitch pitch= new Pitch(pitchName);			
			tone.addPitch(pitch);			
		}
		Duration dur = new Duration(type);
		
		for(M extra:extras){
			switch(extra){
			case DOTTED: dur.addHalf(); break;
			default: break;
			}
		}
		return new Sod(tone,dur);
	}
	
	/*
	 * @param type 1(whole), 2(half), 4(fourth), ...
	 * @param extra dotted
	 * @return a rest
	 */
	public static Sod newRest(int type, M... extras){
		Tone tone = new Tone();
		Duration dur = new Duration(type);
		for(M extra:extras){
			switch(extra){
			case DOTTED: dur.addHalf(); break;
			default: break;
			}
		}
		return new Sod(tone,dur);
	}
	

}
