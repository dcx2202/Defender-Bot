package Projeto;

import java.io.File;

import lejos.hardware.Sound;

public class Coluna extends Thread
{
	
	String ficheiro;
	
	public Coluna(String ficheiro)
	{
		this.ficheiro = ficheiro;
	}
	
	public void run()
	{
		Sound.playSample(new File("/home/root/" + ficheiro + ".wav"), 100);
		return;
	}
}
