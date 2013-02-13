package org.syc.rhapsody.common;

import java.util.ArrayList;

public class Measure {

	
	public Rhythm rhythm;
	
	public ArrayList<Sod> sods = new ArrayList<Sod>();
	
	public void add(Sod sod){
		sods.add(sod);
	}
}
