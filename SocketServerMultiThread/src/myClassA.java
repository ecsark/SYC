import java.io.Serializable;
import java.io.Serializable;

public class myClassA implements Serializable
{
	public int data;
	public String string;
	
	public myClassA(int data, String string)
	{
		this.data = data;
		this.string = string;
	}
	public myClassA()
	{
		this.data = 0;
		this.string = "";
	}
	
	public void print()
	{
		System.out.println("myClassA:");
		System.out.println("    data: " + this.data);
		System.out.println("    string: " + this.string);
	}
	
}