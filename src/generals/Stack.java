package generals;

import java.util.ArrayList;
import java.util.List;

public class Stack 
{
	private List<Integer> stack;
	
	public Stack() 
	{
		stack = new ArrayList<Integer>();
	}
	
	public void pushBack(int toAdd)
	{
		stack.add(toAdd);
	}
	
	public void clear()
	{
		stack.clear();
	}
	
	public int popBack()
	{
		int toReturn = stack.get(stack.size() - 1);
		stack.remove(stack.size() - 1);
		
		return toReturn;
	}
}
