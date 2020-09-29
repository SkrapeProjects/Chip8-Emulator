package components;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;

import generals.KeypadInput;
import main.Settings;

public class GPU extends Canvas
{
	private static final long serialVersionUID = -991107518309227119L;
	
	private int viewportY, viewportX, viewportHeight, viewportWidth;
	private final int aspectRatio = 64 / 32;
	
	public void init()
	{
		this.createBufferStrategy(3);
		this.addKeyListener(new KeypadInput());
	}
	
	public void render()
	{
		Graphics2D g = (Graphics2D) this.getBufferStrategy().getDrawGraphics();
		
		//RENDERING//
		
		g.setColor(Color.RED);
		g.fillRect(0, 0, Settings.WIDTH, Settings.HEIGHT);
		
		int pixelWidth = viewportWidth / 64;
		int pixelHeight = viewportHeight / 32;
		
		int offsetX = (viewportWidth - (pixelWidth * 64)) / 2;
		int offsetY = (viewportHeight - (pixelHeight * 32)) / 2;
		
		for (int y = 0; y < 32; y ++)
		{
			for (int x = 0; x < 64; x ++)
			{
				if (Motherboard.RAM.screenData[y][x] == 0) g.setColor(Color.BLACK);
				else if (Motherboard.RAM.screenData[y][x] == 1) g.setColor(Color.WHITE);
					
				g.fillRect((viewportX + x * pixelWidth) + offsetX, (viewportY + y * pixelHeight) + offsetY, pixelWidth, pixelHeight);
			}
		}
		
		/////////////
		
		g.dispose();
		this.getBufferStrategy().show();
	}
	
	public void calculateViewportDimensions()
	{
		if (Settings.WIDTH < Settings.HEIGHT * aspectRatio) {
			
			viewportWidth = Settings.WIDTH;
			viewportHeight = (int) ((1f / (float) aspectRatio) * viewportWidth);
			
			viewportX = 0;
			viewportY = (Settings.HEIGHT / 2) - (viewportHeight / 2);
			
		} else {
			
			viewportHeight = Settings.HEIGHT;
			viewportWidth = aspectRatio * viewportHeight;
			
			viewportY = 0;
			viewportX = (Settings.WIDTH / 2) - (viewportWidth / 2);
		}
	}
}
