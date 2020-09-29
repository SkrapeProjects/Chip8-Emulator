package main;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import components.Motherboard;

public class Window
{
	private JFrame frame;
	
	public Window()
	{	
		frame = new JFrame(Settings.TITLE);
		
		frame.setSize(Settings.WIDTH + frame.getInsets().left + frame.getInsets().right, Settings.HEIGHT + frame.getInsets().top + frame.getInsets().bottom);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.addComponentListener
		(
			new ComponentAdapter() 
			{
				@Override
				public void componentResized(ComponentEvent e) 
				{
					Settings.OFFSET_X = frame.getInsets().left + frame.getInsets().right;
					Settings.OFFSET_Y = frame.getInsets().top + frame.getInsets().bottom;
					
					Settings.WIDTH = frame.getWidth() - Settings.OFFSET_X;
					Settings.HEIGHT = frame.getHeight() - Settings.OFFSET_Y;
					
					Motherboard.GPU.calculateViewportDimensions();
				}
			}
		);
		
		frame.setMinimumSize(new Dimension(64, 32));
		
		frame.add(Motherboard.GPU);
		
		frame.setVisible(true);
	}
}
