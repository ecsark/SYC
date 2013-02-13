package org.syc.rhapsody.test;


import org.junit.Test;


public class Miscellaneous {

	public void tt(String[] s){
		for(String ss:s)
			System.out.println(ss);
	}
	
	@Test
	public void arrayTest() {
		tt(new String[]{"a","b","c"});
	}
	


}
