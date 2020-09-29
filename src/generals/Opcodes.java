package generals;

import java.util.Random;

import components.Motherboard;
import main.Settings;

public class Opcodes 
{	
	public static void opc1NNN(int opcode)
	{
		int addr = opcode & 0x0FFF;
		
		Motherboard.CPU.programCounter = addr;
	}
	
	public static void opc00E0()
	{
		for (int x = 0; x < 64; x ++)
		{
			for (int y = 0; y < 32; y ++)
			{
				Motherboard.RAM.screenData[y][x] = 1;
			}
		}
	}
	
	public static void opc00EE()
	{
		Motherboard.CPU.programCounter = Motherboard.RAM.stack.popBack();
	}
	
	public static void opc2NNN(int opcode)
	{
		Motherboard.RAM.stack.pushBack(Motherboard.CPU.programCounter);
		Motherboard.CPU.programCounter = opcode & 0x0FFF;
	}
	
	public static void opc3XNN(int opcode)
	{
		if ((Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8]) == (opcode & 0x00FF))
		{
			Motherboard.CPU.programCounter += 2;
		}
	}
	
	public static void opc4XNN(int opcode)
	{
		if ((Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8]) != (opcode & 0x00FF))
		{
			Motherboard.CPU.programCounter += 2;
		}
	}
	
	public static void opc5XY0(int opcode)
	{
		if ((Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8]) == Motherboard.CPU.registers[(opcode & 0x00F0) >>> 4])
		{
			Motherboard.CPU.programCounter += 2;
		}
	}
	
	public static void opc6XNN(int opcode)
	{
		Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] = (opcode & 0x00FF);
	}
	
	public static void opc7XNN(int opcode)
	{
		int value = Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] + (opcode & 0x00FF);
	
		Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] = mask8bit(value);
	}
	
	public static void opc8XY0(int opcode)
	{
		Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] = Motherboard.CPU.registers[(opcode & 0x00F0) >>> 4];
	}
	
	public static void opc8XY1(int opcode)
	{
		Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] |= Motherboard.CPU.registers[(opcode & 0x00F0) >>> 4];
	}
	
	public static void opc8XY2(int opcode)
	{
		Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] &= Motherboard.CPU.registers[(opcode & 0x00F0) >>> 4];
	}
	
	public static void opc8XY3(int opcode)
	{
		Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] ^= Motherboard.CPU.registers[(opcode & 0x00F0) >>> 4];
	}
	
	public static void opc8XY4(int opcode)
	{
		Motherboard.CPU.registers[0xF] = 0;
		
		int value = Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] + Motherboard.CPU.registers[(opcode & 0x00F0) >>> 4];
	
		if (value > Settings.MAX_8BIT_VALUE) Motherboard.CPU.registers[0xF] = 1;
		
		Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] = mask8bit(value);
	}
	
	public static void opc8XY5(int opcode)
	{
		Motherboard.CPU.registers[0xF] = 1;
		
		int value = Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] - Motherboard.CPU.registers[(opcode & 0x00F0) >>> 4];
		
		if (value < 0) Motherboard.CPU.registers[0xF] = 0;
		
		Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] = mask8bit(value);
	}
	
	public static void opc8XY6(int opcode)
	{
		Motherboard.CPU.registers[0xF] = Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] & 0x1;
		Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] >>>= 1;
	}
	
	public static void opc8XY7(int opcode)
	{
		Motherboard.CPU.registers[0xF] = 1;
		
		int value = Motherboard.CPU.registers[(opcode & 0x00F0) >>> 4] - Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8];
		
		if (value < 0) Motherboard.CPU.registers[0xF] = 0;
		
		Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] = mask8bit(value);
	}
	
	public static void opc8XYE(int opcode)
	{
		Motherboard.CPU.registers[0xF] = Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] >>> 7;
		Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] <<= 1;
	}
	
	public static void opc9XY0(int opcode)
	{
		if (Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] != Motherboard.CPU.registers[(opcode & 0x00F0) >>> 4])
		{
			Motherboard.CPU.programCounter += 2;
		}
	}
	
	public static void opcANNN(int opcode)
	{
		Motherboard.CPU.addressI = (opcode & 0x0FFF);
	}
	
	public static void opcBNNN(int opcode)
	{
		Motherboard.CPU.programCounter = mask16bit(Motherboard.CPU.registers[0x0] + (opcode & 0xFFF));
	}
	
	public static void opcCXNN(int opcode)
	{
		Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] = (new Random().nextInt(Settings.MAX_8BIT_VALUE + 1)) & (opcode & 0x00FF);
	}
	
	public static void opcDXYN(int opcode)
	{
		int x = Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8];
		int y = Motherboard.CPU.registers[(opcode & 0x00F0) >>> 4];
		int height = (opcode & 0x000F);
		
		Motherboard.CPU.registers[0xF] = 0;
		
		for (int yLine = 0; yLine < height; yLine ++)
		{
			int data = Motherboard.RAM.gameMemory[Motherboard.CPU.addressI + yLine];
			
			for (int xPixel = 0; xPixel < 8; xPixel ++)
			{
				int mask = 1 << (7 - xPixel);
				
				if ((data & mask) != 0)
				{
					if ((yLine + y) < 32 && (xPixel + x) < 64)
					{
						if (Motherboard.RAM.screenData[y + yLine][x + xPixel] == 1)
						{			
							Motherboard.CPU.registers[0xF] = 1;
						}
						
						Motherboard.RAM.screenData[y + yLine][x + xPixel] ^= 1;
					}
				}
			}
		}
	}
	
	public static void opcEX9E(int opcode)
	{
		if (Motherboard.RAM.keyStates[Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8]] == 1)
		{
			Motherboard.CPU.programCounter += 2;
		}
	}
	
	public static void opcEXA1(int opcode)
	{
		if (Motherboard.RAM.keyStates[Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8]] != 1)
		{
			Motherboard.CPU.programCounter += 2;
		}
	}
	
	public static void opcFX07(int opcode)
	{
		Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] = mask8bit(Motherboard.CPU.delayTimer);
	}
	
	public static void opcFX0A(int opcode)
	{
		int keyPressed = Motherboard.RAM.getKeyPressed();
	
		if (keyPressed < 0) Motherboard.CPU.programCounter -= 2;
		else Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] = keyPressed;
	}
	
	public static void opcFX15(int opcode)
	{
		Motherboard.CPU.delayTimer = mask8bit(Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8]);
	}
	
	public static void opcFX18(int opcode)
	{
		Motherboard.CPU.soundTimer = Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8];
	}
	
	public static void opcFX1E(int opcode)
	{
		Motherboard.CPU.addressI += Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8];
	}
	
	public static void opcFX29(int opcode)
	{
		Motherboard.CPU.addressI = /*mask16bit*/(Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8] * 5);
	}
	
	public static void opcFX33(int opcode)
	{
		int value = Motherboard.CPU.registers[(opcode & 0x0F00) >>> 8];
		
		int[] vals = { (value / 100), ((value / 10) % 10), (value % 10) };
		
		for (int i = 0; i < 3; i ++)
		{
			Motherboard.RAM.gameMemory[Motherboard.CPU.addressI + i] = vals[i];
		}
	}
	
	public static void opcFX55(int opcode)
	{
		int steps = (opcode & 0x0F00) >>> 8;
		
		for (int i = 0; i <= steps; i ++)
		{
			Motherboard.RAM.gameMemory[Motherboard.CPU.addressI + i] = Motherboard.CPU.registers[i];
		}
		
		Motherboard.CPU.addressI += steps + 1;
	}
	
	public static void opcFX65(int opcode)
	{
		int steps = (opcode & 0x0F00) >>> 8;
		
		for (int i = 0; i <= steps; i ++)
		{
			Motherboard.CPU.registers[i] = Motherboard.RAM.gameMemory[Motherboard.CPU.addressI + i];
		}
		
		Motherboard.CPU.addressI += steps + 1;
	}
	
	public static void decodeOpcodeF(int opcode)
	{
		switch (opcode & 0x00FF)
		{
		case 0x0007: opcFX07(opcode); break;
		case 0x000A: opcFX0A(opcode); break;
		case 0x0015: opcFX15(opcode); break;
		case 0x0018: opcFX18(opcode); break;
		case 0x001E: opcFX1E(opcode); break;
		case 0x0029: opcFX29(opcode); break;
		case 0x0033: opcFX33(opcode); break;
		case 0x0055: opcFX55(opcode); break;
		case 0x0065: opcFX65(opcode);break;
		}
	}
	
	public static void decodeOpcodeE(int opcode)
	{
		switch (opcode & 0x000F)
		{
		case 0x000E: opcEX9E(opcode); break;
		case 0x0001: opcEXA1(opcode); break;
		}
	}
	
	public static void decodeOpcode8(int opcode)
	{
		switch (opcode & 0x000F)
		{
		case 0x0000: opc8XY0(opcode); break;
		case 0x0001: opc8XY1(opcode); break;
		case 0x0002: opc8XY2(opcode); break;
		case 0x0003: opc8XY3(opcode); break;
		case 0x0004: opc8XY4(opcode); break;
		case 0x0005: opc8XY5(opcode); break;
		case 0x0006: opc8XY6(opcode); break;
		case 0x0007: opc8XY7(opcode); break;
		case 0x000E: opc8XYE(opcode); break;
		}
	}
	
	public static void decodeOpcode0(int opcode)
	{
		switch(opcode & 0x000F)
		{
		case 0x0000: opc00E0(); break;
		case 0x000E: opc00EE(); break;
		}
	}
	
	public static int mask8bit(int value)
	{
		if (value > Settings.MAX_8BIT_VALUE) do { value = (value - 1) - Settings.MAX_8BIT_VALUE; } while (value > Settings.MAX_8BIT_VALUE);
		else if (value < 0) do { value = Settings.MAX_8BIT_VALUE + (value + 1); } while (value < 0);
	
		return value;
	}
	
	public static int mask16bit(int value)
	{
		if (value > Settings.MAX_16BIT_VALUE) do { value = (value - 1) - Settings.MAX_16BIT_VALUE; } while (value > Settings.MAX_16BIT_VALUE);
		else if (value < 0) do { value = Settings.MAX_16BIT_VALUE + (value + 1); } while (value < 0);
	
		return value;
	}
}
