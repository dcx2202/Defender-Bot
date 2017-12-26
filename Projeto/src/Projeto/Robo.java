package Projeto;

import java.io.File;
import java.util.TreeMap;
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
	
	private static int ENERG_CURA_RES = 50;	 //energia de reserva ao curar
	private static int ENERG_ATAQUE_RES = 50;  //energia de reserva ao atacar
	private static int VIDA_CURA_3 = 300;		//vida do robo <= VIDA_CURA3 para usar a cura3
	private static int VIDA_CURA_2 = 200;		//vida do robo <= VIDA_CURA2 para usar a cura2
	private static int VIDA_CURA_1 = 100;		//vida do robo <= VIDA_CURA1 para usar a cura1
	static int VIDA_CURAR = 200;				//se a vida do robo < VIDA_CURAR entao cura
	static int VIDA_INI_GRUA = 150;		//vida do inimigo > VIDA_INI_GRUA para usar o ataque de grua
    static int VIDA_INI_TOQUE = 50;		//vida do inimigo > VIDA_INI_TOQUE para usar o ataque de toque
    static int VIDA_INI_SOM = 0;		//vida do inimigo > VIDA_INI_SOM para usar o ataque de som
	
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
			if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
				ataqueGrua(inimigo);
			else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
				ataqueToque(inimigo);
			else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERSOM + ENERG_ATAQUE_RES)
				ataqueSom(inimigo);
			if(inimigo.getVida() <= 0)
				tocaSom("som8");
			}
		else
		{
			if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
				ataqueGrua(inimigo);
			else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
				ataqueToque(inimigo);
			else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERSOM)
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
		if(energAtual >= ENERGCURA3 + ENERG_CURA_RES && getVida() <= VIDA_CURA_3)
		{
			vidaAtual += CURA3;
			energAtual -= ENERGCURA3;
			tocaSom("som6");
		}
		else if(energAtual >= ENERGCURA2 + ENERG_CURA_RES && getVida() <= VIDA_CURA_2)
		{
			vidaAtual += CURA2;
			energAtual -= ENERGCURA2;
			tocaSom("som6");
		}
		else if(energAtual >= ENERGCURA1 + ENERG_CURA_RES && getVida() <= VIDA_CURA_1)
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
	
	public static void estrategia(String perfil)
	{
		switch (perfil)
		{
			case "agressivo":
				ENERG_CURA_RES = 25;		//energia de reserva ao curar
				ENERG_ATAQUE_RES = 25;		//energia de reserva ao atacar
				VIDA_CURA_3 = 150;			//vida do robo <= VIDA_CURA3 para usar a cura3
				VIDA_CURA_2 = 100;			//vida do robo <= VIDA_CURA2 para usar a cura2
				VIDA_CURA_1 = 50;			//vida do robo <= VIDA_CURA1 para usar a cura1
				VIDA_CURAR = 100;			//se a vida do robo < VIDA_CURAR entao cura
				VIDA_INI_GRUA = 75;			//vida do inimigo > VIDA_INI_GRUA para usar o ataque de grua
				VIDA_INI_TOQUE = 25;		//vida do inimigo > VIDA_INI_TOQUE para usar o ataque de toque
				VIDA_INI_SOM = 0;			//vida do inimigo > VIDA_INI_SOM para usar o ataque de som
				break;
			case "passivo":
				ENERG_CURA_RES = 100;
				ENERG_ATAQUE_RES = 100;
				VIDA_CURA_3 = 400;
				VIDA_CURA_2 = 300;
				VIDA_CURA_1 = 200;
				VIDA_CURAR = 300;
				VIDA_INI_GRUA = 200;
				VIDA_INI_TOQUE = 100;
				VIDA_INI_SOM = 50;
				break;
			case "equilibrado":
				ENERG_CURA_RES = 50;
				ENERG_ATAQUE_RES = 50;
				VIDA_CURA_3 = 300;
				VIDA_CURA_2 = 200;
				VIDA_CURA_1 = 100;
				VIDA_CURAR = 200;
				VIDA_INI_GRUA = 150;
				VIDA_INI_TOQUE = 50;
				VIDA_INI_SOM = 0;
				break;
		}
	}
	
	public void escolheEstrategia()
	{
		int vidaAtual = this.vidaAtual;
		int energAtual = this.energAtual;
		TreeMap<Integer, Inimigo> inimigos = Projeto.inimigos;
		int score = 0;
		String perfil_escolhido = "agressivo";
		
		estrategia("agressivo");
		if(vidaAtual < Robo.VIDA_CURAR)
		{
			if(energAtual >= ENERGCURA3 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_3)
			{
				vidaAtual += CURA3;
				energAtual -= ENERGCURA3;
			}
			else if(energAtual >= ENERGCURA2 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_2)
			{
				vidaAtual += CURA2;
				energAtual -= ENERGCURA2;
			}
			else if(energAtual >= ENERGCURA1 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_1)
			{
				vidaAtual += CURA1;
				energAtual -= ENERGCURA1;
			}
		}
		else
		{
			for(Inimigo inimigo : inimigos.values())
			{
				if(Projeto.turno != 12)
				{
					if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
					{
						energAtual -= ENERGGRUA;
						inimigo.recebeDano(DANOGRUA);
					}
					else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
					{
						energAtual -= ENERGTOQUE;
						inimigo.recebeDano(DANOTOQUE);
					}
					else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERSOM + ENERG_ATAQUE_RES)
					{
						energAtual -= ENERSOM;
						inimigo.recebeDano(DANOSOM);
					}
				}
				else
				{
					if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
					{
						energAtual -= ENERGGRUA;
						inimigo.recebeDano(DANOGRUA);
					}
					else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
					{
						energAtual -= ENERGTOQUE;
						inimigo.recebeDano(DANOTOQUE);
					}
					else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERSOM)
					{
						energAtual -= ENERSOM;
						inimigo.recebeDano(DANOSOM);
					}
				}
			}
		}
		score = calculaScore(Projeto.inimigos, vidaAtual, energAtual);
		
		
		vidaAtual = this.vidaAtual;
		energAtual = this.energAtual;
		inimigos = Projeto.inimigos;
		estrategia("equilibrado");
		if(vidaAtual < Robo.VIDA_CURAR)
		{
			if(energAtual >= ENERGCURA3 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_3)
			{
				vidaAtual += CURA3;
				energAtual -= ENERGCURA3;
			}
			else if(energAtual >= ENERGCURA2 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_2)
			{
				vidaAtual += CURA2;
				energAtual -= ENERGCURA2;
			}
			else if(energAtual >= ENERGCURA1 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_1)
			{
				vidaAtual += CURA1;
				energAtual -= ENERGCURA1;
			}
		}
		else
		{
			for(Inimigo inimigo : inimigos.values())
			{
				if(Projeto.turno != 12)
				{
					if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
					{
						energAtual -= ENERGGRUA;
						inimigo.recebeDano(DANOGRUA);
					}
					else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
					{
						energAtual -= ENERGTOQUE;
						inimigo.recebeDano(DANOTOQUE);
					}
					else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERSOM + ENERG_ATAQUE_RES)
					{
						energAtual -= ENERSOM;
						inimigo.recebeDano(DANOSOM);
					}
				}
				else
				{
					if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
					{
						energAtual -= ENERGGRUA;
						inimigo.recebeDano(DANOGRUA);
					}
					else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
					{
						energAtual -= ENERGTOQUE;
						inimigo.recebeDano(DANOTOQUE);
					}
					else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERSOM)
					{
						energAtual -= ENERSOM;
						inimigo.recebeDano(DANOSOM);
					}
				}
			}
		}
		if(calculaScore(inimigos, vidaAtual, energAtual) >= score)
		{
			score = calculaScore(inimigos, vidaAtual, energAtual);
			perfil_escolhido = "equilibrado";
		}
		
		
		vidaAtual = this.vidaAtual;
		energAtual = this.energAtual;
		inimigos = Projeto.inimigos;
		estrategia("passivo");
		if(vidaAtual < Robo.VIDA_CURAR)
		{
			if(energAtual >= ENERGCURA3 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_3)
			{
				vidaAtual += CURA3;
				energAtual -= ENERGCURA3;
			}
			else if(energAtual >= ENERGCURA2 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_2)
			{
				vidaAtual += CURA2;
				energAtual -= ENERGCURA2;
			}
			else if(energAtual >= ENERGCURA1 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_1)
			{
				vidaAtual += CURA1;
				energAtual -= ENERGCURA1;
			}
		}
		else
		{
			for(Inimigo inimigo : inimigos.values())
			{
				if(Projeto.turno != 12)
				{
					if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
					{
						energAtual -= ENERGGRUA;
						inimigo.recebeDano(DANOGRUA);
					}
					else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
					{
						energAtual -= ENERGTOQUE;
						inimigo.recebeDano(DANOTOQUE);
					}
					else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERSOM + ENERG_ATAQUE_RES)
					{
						energAtual -= ENERSOM;
						inimigo.recebeDano(DANOSOM);
					}
				}
				else
				{
					if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
					{
						energAtual -= ENERGGRUA;
						inimigo.recebeDano(DANOGRUA);
					}
					else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
					{
						energAtual -= ENERGTOQUE;
						inimigo.recebeDano(DANOTOQUE);
					}
					else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERSOM)
					{
						energAtual -= ENERSOM;
						inimigo.recebeDano(DANOSOM);
					}
				}
			}
		}
		if(calculaScore(inimigos, vidaAtual, energAtual) >= score)
		{
			score = calculaScore(inimigos, vidaAtual, energAtual);
			perfil_escolhido = "passivo";
		}
		estrategia(perfil_escolhido);
	}
	
	public int calculaScore(TreeMap<Integer, Inimigo> inimigos, int vidaAtual, int energAtual)
	{
		int score = 0;
		int vidaTotalInimigos = 0;
		double danoTotalInimigos = 0;
		
		score += vidaAtual + energAtual;
		
		for (Inimigo inimigo : inimigos.values())
		{
			if (inimigo.getVida() <= 0)
			{
				if (inimigo.getId() == 0) //se for um tanque
					score += 150;
				else if (inimigo.getId() == 1) //se for uma artilharia
					score += 300;
				else if (inimigo.getId() == 2) //se for uma infantaria
					score += 50;
			}
			else
			{
				vidaTotalInimigos += inimigo.getVida();
				danoTotalInimigos += inimigo.getDano();
			}
		}
		score -= vidaTotalInimigos - danoTotalInimigos;
		return score;	
	}
}