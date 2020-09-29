package components;

public class Motherboard 
{
	public static CPU CPU;
	public static RAM RAM;
	public static GPU GPU;
	
	public Motherboard() 
	{
		CPU = new CPU();
		RAM = new RAM();
		GPU = new GPU();
	}
}
