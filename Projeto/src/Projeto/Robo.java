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
		posicaoAtual = -1;
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
				Projeto.espera(50);
			Projeto.espera(750);
			pos--;
		}
		parar();
		Projeto.espera(500);
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
		return energAtual;
	}

	public int getEnergia() {
		return energAtual;
	}

	public int getVida() {
		return vidaAtual;
	}

	public int getPosicaoAtual()
	{
		return posicaoAtual;
	}
	
	
	//Setters
	public void setPosicaoAtual(int valor)
	{
		posicaoAtual = valor;
	}
	
	
	//Ataque
	public void escolheAtaque(Inimigo inimigo)
	{
		if(Projeto.turno != 12)
		{
			if(inimigo.getVida() > 150 && energAtual > ENERGGRUA + 50)
				ataqueGrua(inimigo);
			else if(inimigo.getVida() > 50 && energAtual > ENERGTOQUE + 50)
				ataqueToque(inimigo);
			else if(inimigo.getVida() > 0 && energAtual > ENERSOM + 50)
				ataqueSom(inimigo);
			if(inimigo.getVida() <= 0)
				tocaSom("som8");
			}
		else
		{
			if(inimigo.getVida() > 150 && energAtual >= ENERGGRUA)
				ataqueGrua(inimigo);
			else if(inimigo.getVida() > 50 && energAtual >= ENERGTOQUE)
				ataqueToque(inimigo);
			else if(inimigo.getVida() > 0 && energAtual >= ENERSOM)
				ataqueSom(inimigo);
			if(inimigo.getVida() <= 0)
				tocaSom("som8");
		}
	}
	
	public void ataqueSom(Inimigo inimigo)
	{
		energAtual -= ENERSOM;
		tocaSom("som27"); //"PewPew (laser)"
		inimigo.recebeDano(DANOSOM);
	}
	
	public void ataqueToque(Inimigo inimigo)
	{
		energAtual -= ENERGTOQUE;
		inimigo.recebeDano(DANOTOQUE);
		int vel = Motor.C.getSpeed();
		mover(-1, 300);
		Projeto.espera(750);
		parar();
		Motor.C.resetTachoCount();
		Motor.C.rotate(-30);
		Projeto.espera(200);
		Motor.C.setSpeed(60);
		Motor.C.resetTachoCount();
		Motor.C.rotate(29);
		Projeto.espera(200);
		mover(1, 300);
		Projeto.espera(750);
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
		Projeto.espera(200);
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
		if(energAtual >= ENERGCURA3 + 50 && getVida() <= 100)
		{
			vidaAtual += CURA3;
			energAtual -= ENERGCURA3;
			tocaSom("som6");
		}
		else if(energAtual >= ENERGCURA2 + 50 && getVida() <= 200)
		{
			vidaAtual += CURA2;
			energAtual -= ENERGCURA2;
			tocaSom("som6");
		}
		else if(energAtual >= ENERGCURA1 + 50 && getVida() <= 300)
		{
			vidaAtual += CURA1;
			energAtual -= ENERGCURA1;
			tocaSom("som6");
		}
	}
	
	//Outros
	public void tocaSom(String ficheiro)
	{
		Sound.playSample(new File("/home/root/" + ficheiro + ".wav"), 100);
	}
	
	public void recuperaEnergia()
	{
		tocaSom("som5"); //"Recuperando energia"
		if((energAtual * 1.5) <= ENERGMAX)
			energAtual = (int) Math.round(energAtual * 1.5);
		else
			energAtual = ENERGMAX;
		Projeto.dadosRobo();
	}
}