package components;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import generals.Opcodes;

public class CPU 
{	
	public int[] registers;
	public int addressI;
	public int programCounter;
	public int delayTimer;
	public int soundTimer;
	
	public void init()
	{
		programCounter = 0x200;
		addressI = 0;
		registers = new int[16];
		delayTimer = 0;
		soundTimer = 0;
	}
	
	public void reset()
	{
		programCounter = 0x200;
		addressI = 0;
		
		for (int i = 0; i < registers.length; i ++)
		{
			registers[i] = 0;
		}
		
		delayTimer = 0;
		soundTimer = 0;
	}
	
	public void decreaseTimers()
	{
		if (delayTimer > 0) delayTimer --;
		if (soundTimer > 0) { if (soundTimer == 1) playBeep(); soundTimer --; }
	}
	
	public void playBeep()
	{
		try {
			
			File beepFile = new File("BEEP.wav");
			AudioInputStream ain = AudioSystem.getAudioInputStream(beepFile.toURI().toURL());
			Clip clip = AudioSystem.getClip();
			clip.open(ain);
			clip.start();
		
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	private int getNextOpcode()
	{
		int res = 0;
		res = Motherboard.RAM.gameMemory[programCounter];
		res <<= 8;
		res |= Motherboard.RAM.gameMemory[programCounter + 1];
		programCounter += 2;
		return res;
	}
	
	public void execute()
	{
		int opcode = getNextOpcode();
		
		switch (opcode & 0xF000)
		{
		case 0x0000: Opcodes.decodeOpcode0(opcode); break;		
		case 0x1000: Opcodes.opc1NNN(opcode); break;
		case 0x2000: Opcodes.opc2NNN(opcode); break;
		case 0x3000: Opcodes.opc3XNN(opcode); break;
		case 0x4000: Opcodes.opc4XNN(opcode); break;
		case 0x5000: Opcodes.opc5XY0(opcode); break;
		case 0x6000: Opcodes.opc6XNN(opcode); break;
		case 0x7000: Opcodes.opc7XNN(opcode); break;
		case 0x8000: Opcodes.decodeOpcode8(opcode); break;
		case 0x9000: Opcodes.opc9XY0(opcode); break;
		case 0xA000: Opcodes.opcANNN(opcode); break;
		case 0xB000: Opcodes.opcBNNN(opcode); break;
		case 0xC000: Opcodes.opcCXNN(opcode); break;
		case 0xD000: Opcodes.opcDXYN(opcode); break;
		case 0xE000: Opcodes.decodeOpcodeE(opcode); break;
		case 0xF000: Opcodes.decodeOpcodeF(opcode); break;
		default: break;
		}
	}
}
