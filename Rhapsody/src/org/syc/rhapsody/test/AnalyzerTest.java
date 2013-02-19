package org.syc.rhapsody.test;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.syc.rhapsody.analyzer.*;
import org.syc.rhapsody.common.*;

public class AnalyzerTest {
	
	BlueElf be;
	Converter conv;
	ArrayList<ArrayList<Tone>> progressions;
	ArrayList<Measure> accompaniment;

	@Before
	public void setUp() throws ParserException, AnalyzerException{
		be = new BlueElf();
		conv = new Converter();
		MeasureAnalyzer ma = new MeasureAnalyzer(be.sentence.measures, "C", M.MAJOR);
		ma.analyze();
		progressions = ma.getProgressions();
		
		//print
		for(int i=0; i<progressions.size();++i){
			System.out.print(Integer.toString(i)+": ");
			for(Tone h:progressions.get(i))
				System.out.print(" "+h+" ");
			System.out.println();
		}
		
		if(progressions.size()==0){
			System.out.println("Chord failure!");
			return;
		}
		
		accompaniment = new ArrayList<Measure>();
		Measure r = new Measure();
		r.add(SodFactory.newRest(8));
		accompaniment.add(r);
	}

	
	private Measure aMeasure() throws ParserException{
		Measure acc = new Measure();
		Duration dacc = new Duration(8);
		Sod sacc1;
		sacc1 = new Sod(new Tone(new Pitch("3C")), dacc);
		Tone cacc = new Tone(new Pitch("3E"));
		cacc.addPitch(new Pitch("3G"));
		Sod sacc2 = new Sod(cacc,dacc);
		acc.add(sacc1);acc.add(sacc2);acc.add(sacc1);acc.add(sacc2);
		acc.rhythm = be.rhythm;
		return acc;
	}

	@Test
	public void testTexture(){
		try {
			
			Texture texture = new Texture(aMeasure());
			
			ArrayList<Tone> prog = progressions.get(100);//only test the 14th progression
			
			for(int i=1; i<prog.size(); ++i){//skip the first measure
				
				Measure accpn = texture.compose(prog.get(i));
				accompaniment.add(accpn);
			}
			
		} catch (ParserException | AnalyzerException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//@Test
	public void testChorus(){
		try {
			
			ArrayList<Tone> prog = progressions.get(0);//only test the 14th progression
			
			for(int i=1; i<prog.size(); ++i){//skip the first measure
				
				SimpleChorus accpn = new SimpleChorus(prog.get(i), be.rhythm);
				accompaniment.add(accpn);
			}			
		} catch (AnalyzerException e) {
			e.printStackTrace();
		}		
	}
	
	
	@After
	public void play(){
		try {
			conv.addMeasures(be.sentence.measures,1);
			conv.addMeasures(accompaniment, 2);
			conv.play();
		} catch (ParserException e) {
			e.printStackTrace();
		}		
		
	}
	
}


class SimpleChorus extends Chorus{

	public SimpleChorus(Tone chord, Rhythm rhythm)
			throws AnalyzerException {
		super(chord, rhythm);
	}

	@Override
	public void expand() throws AnalyzerException{
		Tone root = new Tone(chord.pits.get(0));
		Tone trunk = new Tone();
		for(int i=1; i<chord.pits.size(); ++i){
			trunk.addPitch(chord.pits.get(i));
		}
		if(rhythm.beats == 0 || rhythm.unit == 0)
			throw new AnalyzerException("Rhythm has to be redefined!");
		Duration dur = new Duration(rhythm.unit*2);
		for(int i=0; i<rhythm.beats; ++i){
			sods.add(new Sod(root, dur));
			sods.add(new Sod(trunk, dur));
			
		}
	}
}


