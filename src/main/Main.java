package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import components.Motherboard;

public class Main 
{
	public Main() 
	{
		init();
		loop();
	}
	
	private void init()
	{
		new Motherboard();
		
		new Window();	
		
		Motherboard.GPU.init();
		Motherboard.CPU.init();
		Motherboard.RAM.init();
		
		reset();
	}
	
	private void reset()
	{
		Motherboard.CPU.reset();
		Motherboard.RAM.reset();
	
		loadGame();
	}
	
	private void loop()
	{
		long ticks = System.nanoTime();
		float interval = 1000.0f;
		int opcodesPerFrame = Settings.OPCODES_PER_SECOND / Settings.FPS;
		
		while (true) 
		{
			long current = System.nanoTime();
			
			if ((ticks + interval) < current)
			{
				Motherboard.CPU.decreaseTimers();
				
				for (int i = 0; i < opcodesPerFrame; i ++)
				{
					Motherboard.CPU.execute();
				}
				
				Motherboard.GPU.render();
				
				ticks = current;
			}
			
			ticks ++;
		}
	}
	
	private void loadGame()
	{
		try {
		
			byte[] gameData = Files.readAllBytes(Paths.get(Settings.GAME_FILE));
			
			for (int i = 0; i < gameData.length; i ++)
			{
				Motherboard.RAM.gameMemory[0x200 + i] = (gameData[i] & 0xFF);
			}
		
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public static void main(String[] args)
	{
		new Main();
	}
}
