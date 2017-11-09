package Projeto;

import java.io.File;
import Projeto.Robo;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Robo 
{
	
	//Criacao/Iniciacao de variaveis/objetos
	private static final int VIDAMAX = 750;
	private static final int ENERGMAX = 500;
	private static final int CURA1 = 100;
	private static final int CURA2 = 200;
	private static final int CURA3 = 400;
	private static final int ENERGCURA1 = 100;
	private static final int ENERGCURA2 = 200;
	private static final int ENERGCURA3 = 400;
	private static final int ENERGGRUA = 300;
	private static final int ENERSOM = 50;
	private static final int ENERGTOQUE = 150;
	private static final int DANOGRUA = 200;
	private static final int DANOSOM = 50;
	private static final int DANOTOQUE = 100;
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
		vidaAtual = VIDAMAX;
		energAtual = ENERGMAX;
		posicaoAtual = 1;
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
	
	public void moverPos(int direcao, int numPosicoes)
	{
		int pos = numPosicoes;
		while(pos > 0)
		{
			mover(direcao, 200);
			while(detetaCor() != Color.RED)
				espera(50);
			espera(700);
			pos--;
		}
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
		return ENERGMAX - energAtual;
	}
	
	public int getVida()
	{
		return vidaAtual;
	}
	
	public int getEnergia()
	{
		return energAtual;
	}
	
	public int getEnergGrua()
	{
		return ENERGGRUA;
	}
	
	
	public int getEnergSom() {
		return ENERSOM;
	}


	public int getEnergToque() {
		return ENERGTOQUE;
	}

	public int getPosicaoAtual()
	{
		return posicaoAtual;
	}
	
	
	//Setters
	public void setVida(int valor)
	{
		vidaAtual = valor;
	}
	
	public void setPosicaoAtual(int valor)
	{
		posicaoAtual = valor;
	}
	
	
	//Ataque
	public void escolheAtaque(Inimigo inimigo)
	{
		if(energAtual > 200)
		{
			if(inimigo.getVida() > 150)
				ataqueGrua(inimigo);
			else if(inimigo.getVida() > 50)
				ataqueToque(inimigo);
			else if(inimigo.getVida() > 0)
				ataqueSom(inimigo);
		}
	}
	
	public void ataqueSom(Inimigo inimigo)
	{
		energAtual -= ENERSOM;
		inimigo.recebeDano(DANOSOM);
		//tocaSom("som27"); //"Pew (laser)"
	}
	
	public void ataqueToque(Inimigo inimigo)
	{
		energAtual -= ENERGTOQUE;
		inimigo.recebeDano(DANOTOQUE);
		int vel = Motor.C.getSpeed();
		mover(-1, 300);
		espera(750);
		parar();
		Motor.C.resetTachoCount();
		Motor.C.rotate(-30);
		espera(200);
		Motor.C.setSpeed(60);
		Motor.C.resetTachoCount();
		Motor.C.rotate(29);
		espera(200);
		mover(1, 300);
		espera(750);
		parar();
		Motor.C.setSpeed(vel);
	}
	
	public void ataqueGrua(Inimigo inimigo)
	{
		energAtual -= ENERGGRUA;
		inimigo.recebeDano(DANOGRUA);
		int vel = Motor.C.getSpeed();
		Motor.C.resetTachoCount();
		Motor.C.rotate(47);
		espera(200);
		Motor.C.resetTachoCount();
		Motor.C.setSpeed(47);
		Motor.C.rotate(-47);
		Motor.C.setSpeed(vel);
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
	
	
	//Cura
	public void curar()
	{
		int energ_disp = getEnergDisponivel();
		
		if(/*num == 0 &&*/ energ_disp >= ENERGCURA3)
			vidaAtual += CURA3;
		else if(/*num == 1 &&*/ energ_disp >= ENERGCURA2)
			vidaAtual += CURA2;
		else if(/*num == 2 &&*/ energ_disp >= ENERGCURA1)
			vidaAtual += CURA1;
	}
	
	//Outros
	public void tocaSom(String ficheiro)
	{
		Sound.playSample(new File("/home/root/" + ficheiro + ".wav"), 100);
		espera(1000);
	}
	
	public void recuperaEnergia()
	{
		//tocaSom("som26"); //"Recuperando"
		//tocaSom("som5"); //"Energia"
		if((energAtual * 1.5) <= ENERGMAX)
			energAtual = (int) Math.round(energAtual * 1.5);
		else
			energAtual = ENERGMAX;
		Projeto.dadosRobo();
	}
	
	public static void espera(int ms)
	{
		Delay.msDelay(ms);
	}
	
	
}
