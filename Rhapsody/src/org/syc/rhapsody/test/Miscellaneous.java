package org.syc.rhapsody.test;

import java.io.File;
import java.io.IOException;

import org.jfugue.*;
import org.jfugue.extras.ReversePatternTransformer;
import org.junit.Test;


public class Miscellaneous {

	public void tt(String[] s){
		for(String ss:s)
			System.out.println(ss);
	}
	
	@Test
	public void arrayTest() {
		
		//Player player = new Player();
		//Pattern pattern = new Pattern("C D E F G A B");
		//player.play(pattern);
		// "Frere Jacques"
		Pattern pattern1 = new Pattern("C5q D5q E5q C5q");

		// "Dormez-vous?"
		Pattern pattern2 = new Pattern("E5q F5q G5h");

		// "Sonnez les matines"
		Pattern pattern3 = new Pattern("G5i A5i G5i F5i E5q C5q");

		// "Ding ding dong"
		Pattern pattern4 = new Pattern("E5q G4q C5h");

		// Put all of the patters together to form the song
		Pattern song = new Pattern();
		song.add(pattern1, 2); // Adds 'pattern1' to 'song' twice
		song.add(pattern2, 2); // Adds 'pattern2' to 'song' twice
		song.add(pattern3, 2); // Adds 'pattern3' to 'song' twice
		song.add(pattern4, 2); // Adds 'pattern4' to 'song' twice

		// Play the song!
		
		Pattern doubleMeasureRest = new Pattern("Rw Rw");

		// Create the first voice
		Pattern round1 = new Pattern("V0");
		round1.add(song);

		// Create the second voice
		Pattern round2 = new Pattern("V1");
		round2.add(doubleMeasureRest);
		round2.add(song);

		// Create the third voice
		Pattern round3 = new Pattern("V2");
		round3.add(doubleMeasureRest, 2);
		round3.add(song);

		// Put the voices together
		Pattern roundSong = new Pattern();
		roundSong.add(round1);
		roundSong.add(round2);
		roundSong.add(round3);

		//Play the song!
		Pattern pattern = getPattern();
		Player player = new Player();
		File mid = new File("/home/ecsark/Downloads/Bach.mid");
		player.close();
		try {
			player.saveMidi(pattern, mid);
		} catch (IOException e) {
			System.err.println("Saving error");
			e.printStackTrace();
		}
		//player.play(pattern);
	}
	
    public Pattern getPattern()
    {
        Pattern canon = new Pattern("D5h E5h A5h Bb5h C#5h Rq A5q "+
                       "A5q Ab5h G5q G5q F#5h F5q F5q E5q Eb5q D5q "+
                       "C#5q A3q D5q G5q F5h E5h D5h F5h "+
                       "A5i G5i A5i D6i A5i F5i E5i F5i G5i A5i B5i C#6i "+
                       "D6i F5i G5i A5i Bb5i E5i F5i G5i A5i G5i F5i E5i "+
                       "F5i G5i A5i Bb5i C6i Bb5i A5i G5i A5i Bb5i C6i D6i "+
                       "Eb6i C6i Bb5i A5i B5i C#6i D6i E6i F6i D6i C#6i B5i "+
                       "C#6i D6i E6i F6i G6i E6i A5i E6i D6i E6i F6i G6i "+
                       "F6i E6i D6i C#6i D6q A5q F5q D5q");

        // Reverse the canon
        ReversePatternTransformer rpt = new ReversePatternTransformer();
        Pattern reverseCanon = rpt.transform(canon);

        // Lower the octaves of the reversed pattern
        PatternTransformer octaveTransformer = new PatternTransformer() {
            public void noteEvent(Note note)
            {
                byte currentValue = note.getValue();
                note.setValue((byte)(currentValue - 12));
                getReturnPattern().addElement(note);
            }
        };
        Pattern octaveCanon = octaveTransformer.transform(reverseCanon);

        // Combine the two parts
        Pattern pattern = new Pattern();
        pattern.add("T90 V0 " + canon.getMusicString());
        pattern.add("V1 " + octaveCanon.getMusicString());

        return pattern;
    }

}
