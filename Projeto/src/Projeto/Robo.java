package Projeto;

import Projeto.Robo;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Robo 
{
	
	//Criacao/Iniciacao de variaveis/objetos
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
	static int posicaoAtual;
	private static final Port s1 = LocalEV3.get().getPort("S1");
	private static final EV3ColorSensor sensorCor = new EV3ColorSensor(s1);
	private static final Port s2 = LocalEV3.get().getPort("S2");
	private static final EV3TouchSensor sensorToque = new EV3TouchSensor(s2);
	private static final SampleProvider obtemAmostra = sensorToque.getTouchMode();
	private static float[] amostra = new float[obtemAmostra.sampleSize()];
	
	
	//Construtor
	public Robo()
	{
		vidaAtual = vidaMax;
		energAtual = energMax;
	}
	
	
	//Movimento
	public void mover(int direcao, int graus) // (1 -> frente  ;  -1 -> tras  ;  graus/s)
	{
		Motor.A.setSpeed(graus);
		Motor.B.setSpeed(graus);
		if(direcao == 1)
		{
			Motor.A.forward();
			Motor.B.forward();
		}
		else if (direcao == -1)
		{
			Motor.A.backward();
			Motor.B.backward();
		}
	}
	
	public void moverPos(int direcao, int numPosicoes) //Move-se na direcao especificada durante 1s para cada posicao
	{
		mover(direcao, 400);
		espera(numPosicoes * 1000);
		parar();
		posicaoAtual = posicaoAtual + (direcao * numPosicoes);
	}
	
	public void parar() //Para ambos os motores simultaneamente
	{
		Motor.A.stop(true);
		Motor.B.stop(true);
	}
	
	
	//Sensores
	public int detetaCor() //Retorna o id da cor detetada (0 se nao detetar nenhuma)
	{
		return sensorCor.getColorID();
	}
	
	public boolean detetaToque()
	{
		obtemAmostra.fetchSample(amostra, 0);
		return amostra[0] != 0;
	}
	
	
	//Getters
	public int getEnergDisponivel()
	{
		return energMax - energAtual;
	}
	
	public int getVida()
	{
		return vidaAtual;
	}
	
	public int getEnergia()
	{
		return energAtual;
	}
	
	
	//Setters
	public void setVida(int valor)
	{
		vidaAtual = valor;
	}
	
	
	//Ataque
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
	
	
	//Defesa
	public void recebeDano(int valor)
	{
		if(vidaAtual-valor < 0)
		{
			vidaAtual = 0;
			Projeto.fimJogo();
		}
		else
			vidaAtual -=valor;
	}
	
	
	//Outros
	public void espera(int ms)
	{
		Delay.msDelay(ms);
	}
	
	
}
