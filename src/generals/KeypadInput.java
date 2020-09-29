package generals;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import components.Motherboard;

public class KeypadInput extends KeyAdapter
{
	public void keyPressed(KeyEvent e)
	{
		int key = getKeyCode(e);
		
		if (key > -1) Motherboard.RAM.keyStates[key] = 1;
	}
	
	public void keyReleased(KeyEvent e)
	{
		int key = getKeyCode(e);
		
		if (key > -1) Motherboard.RAM.keyStates[key] = 0;
	}
	
	private int getKeyCode(KeyEvent e)
	{
		int key = -1;
		
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_X: key = 0; break;
		case KeyEvent.VK_1: key = 1; break;
		case KeyEvent.VK_2: key = 2; break;
		case KeyEvent.VK_3: key = 3; break;
		case KeyEvent.VK_Q: key = 4; break;
		case KeyEvent.VK_W: key = 5; break;
		case KeyEvent.VK_E: key = 6; break;
		case KeyEvent.VK_A: key = 7; break;
		case KeyEvent.VK_S: key = 8; break;
		case KeyEvent.VK_D: key = 9; break;
		case KeyEvent.VK_Z: key = 10; break;
		case KeyEvent.VK_C: key = 11; break;
		case KeyEvent.VK_4: key = 12; break;
		case KeyEvent.VK_R: key = 13; break;
		case KeyEvent.VK_F: key = 14; break;
		case KeyEvent.VK_V: key = 15; break;
		default: break;
		}
	
		return key;
	}
}
