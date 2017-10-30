package Projeto;

import lejos.hardware.motor.Motor;

public class Robo 
{
	private static final int vidaMax = 750;
	private static final int energMax = 500;
	private static final int energGrua = 300;
	private static final int energSom = 50;
	private static final int energToque = 150;
	private static final int danoGrua = 200;
	private static final int danoSom = 50;
	private static final int danoToque = 100;
	private int energAtual;
	private int vidaAtual;
	
	
	public Robo()
	{
		vidaAtual = vidaMax;
		energAtual = energMax;
	}
	
	public void travelForward(int speed)
	{
		Motor.A.setSpeed(speed);
		Motor.B.setSpeed(speed);
		
		Motor.A.forward();
		Motor.B.forward();
	}
	
	public void travelBackward(int speed)
	{
		Motor.A.setSpeed(speed);
		Motor.B.setSpeed(speed);
		
		Motor.A.backward();
		Motor.B.backward();
	}
	
	public void recebeDano(int valor)
	{
		if(vidaAtual-valor < 0)
			vidaAtual = 0;
		else
			vidaAtual -=valor;
	}
	
	public int getVida()
	{
		return vidaAtual;
	}
	
	public int getEnergia()
	{
		return energAtual;
	}
	
	public void setVida(int valor){
		vidaAtual = valor;
	}
	
	public void ataqueSom(Inimigo inimigo)
	{
		energAtual -= energSom;
		inimigo.recebeDano(danoSom);
	}
	
	public void ataqueGrua(Inimigo inimigo)
	{
		energAtual -= energGrua;
		inimigo.recebeDano(danoGrua);
	}
	
	public void ataqueToque(Inimigo inimigo)
	{
		energAtual -= energToque;
		inimigo.recebeDano(danoToque);
	}
	
	public int getEnergDisponivel()
	{
		return energMax - energAtual;
	}
}
