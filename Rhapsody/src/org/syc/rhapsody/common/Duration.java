package org.syc.rhapsody.common;

public class Duration {
	
	public float time;
	
	public Duration(){
		time = 0;
	}
	
	//defines the duration of a note
	public Duration(int type){
		time = (float)4/type;
	}
	
	public Duration(int type, int tms){
		this(type);
		time *= tms;
	}
		
	public void addHalf(){
		time += time/2;
	}
	
	public void add(Duration d){
		this.time += d.time;
	}
	
	public boolean noGreaterThan(Duration d){
		return time<(d.time+error);
	}

	private static final float error = (float)1/1024;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(time);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Duration))
			return false;
		Duration other = (Duration) obj;
		if (Float.floatToIntBits(time) != Float.floatToIntBits(other.time))
			return false;
		return true;
	}


}
