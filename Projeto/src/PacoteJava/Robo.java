package PacoteJava;

import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Robo{

	//DADOS
	int energiaRobo, vidaRobo;
	
	//ATAQUES
	int ataqueGrua, ataqueSom, ataqueToque;
	
	//CURAS
	int cura1, cura2, cura3;
	
	//DANOS
	int danoGrua, danoToque, danoSom;
	
	//CONSUMOS
	int consumoGrua, consumoToque, consumoSom, consumoCura1, consumoCura2, consumoCura3;
	
	//VIDA RECUPERADA
	int vidaRecuperadaCura1, vidaRecuperadaCura2, vidaRecuperadaCura3;
	
	//INIMIGOS DETETADOS
	TreeMap<Integer, Object> inimigos;
	
	//NUMERO DE INIMIGOS DETETADOS
	int numArtilharias, numInfantarias, numTanques;
	
	//NUMERO DA POSICAO ATUAL
	int posicao;

	//Randomizer
	Random rand = new Random();
	
	//SENSOR DE COR
	EV3ColorSensor colorSensor;
	SampleProvider colorProvider;
	float[] colorSample;
	
	//------ Construtor ------
	
	public Robo()
	{
		//Inicializacao de variaveis
		energiaRobo = 500;
		vidaRobo = 750;
		inimigos = new TreeMap<Integer, Object>();
		numArtilharias = 0;
		numInfantarias = 0;
		numTanques = 0;
						   //  ___ ___ ___ ___ ___ ___
						   // | 0 | 1 | 2 | 3 | 4 | 5 |
		posicao =  0;      // |___|___|___|___|___|___|
	    
		//Define a porta do sensor de cor
		Port s1 = LocalEV3.get().getPort("S1");
		colorSensor = new EV3ColorSensor(s1);
		colorProvider = colorSensor.getColorIDMode();
		colorSample = new float[colorProvider.sampleSize()];
	}
	
	
	//------ Movimento ------
	
	public void travel(int speed) 
	{
		Motor.A.setSpeed(speed);
		Motor.B.setSpeed(speed);
		Motor.A.forward();
		Motor.B.forward();
	}
	
	public void travelStop() 
	{
		Motor.A.stop();
		Motor.B.stop();
	}
	
	
	//---------Detecoes-----------
	
	public int detetaCor()
	{
		return colorSensor.getColorID();
	}
	
	public void detetaInimigos()
	{
		while(Button.ESCAPE.isUp()) 
		{
			int currentDetectedColor = colorSensor.getColorID();
			
			//EXTRA --------
			if (currentDetectedColor == Color.RED) {
				LCD.clear(); //limpa o display
				System.out.println("\n\n-----------------");
				System.out.println("       RED       ");
				System.out.println("-----------------");
				posicao++; //usado para saber em que parte do tabuleiro se encontra
				
				if(posicao == 5)
				{
					Delay.msDelay(1000);
					travelStop();
					LCD.clear(); //limpa o display
					System.out.println("-----------------");
					System.out.println("Inimigos Detetados");
					System.out.println("-----------------\n");
					System.out.println(numArtilharias + " Artilharias");
					System.out.println(numInfantarias + " Infantarias");
					System.out.println(numTanques + " Tanques");
					Delay.msDelay(3000);
					break;
				}
				Delay.msDelay(3000);
			}
			
			//ARTILHARIA
			else if (currentDetectedColor == Color.YELLOW) {
				travelStop();
				LCD.clear(); //limpa o display
				System.out.println("-----------------");
				System.out.println("    ARTILHARIA   ");
				System.out.println("-----------------");
				
				Delay.msDelay(3000);
				
				if(inimigos.size() < 6)
				{
					inimigos.put(posicao, new Artilharia());
					numArtilharias++;
					travel(200);
				}
			}
			
			//TANQUE
			else if (currentDetectedColor == Color.BLUE) {
				travelStop();
				LCD.clear(); //limpa o display
				System.out.println("-----------------");
				System.out.println("      TANQUE     ");
				System.out.println("-----------------");
				
				Delay.msDelay(3000);
				
				if(inimigos.size() < 6)
				{
					inimigos.put(posicao, new Tanque());
					numTanques++;
					travel(200);
				}
			}
			
			//INFANTARIA
			else if (currentDetectedColor == Color.GREEN) {
				travelStop();
				LCD.clear();
				System.out.println("-----------------");
				System.out.println("    INFANTARIA   ");
				System.out.println("-----------------");
				
				Delay.msDelay(3000);
				
				if(inimigos.size() < 6)
				{
					inimigos.put(posicao, new Infantaria());
					numInfantarias++;
					travel(200);
				}
			}
			else
				travel(200);
		}
		colorSensor.close();
	}

	
	//----------Ataques-----------
	@SuppressWarnings("null")
	public void ataca()
	{
		int numAtaques = rand.nextInt(inimigos.size());
		List<Integer> posicoes = null;
		posicoes.add(1);
		posicoes.add(2);
		posicoes.add(3);
		posicoes.add(4);
		posicoes.add(5);
		posicoes.add(6);
		while(numAtaques > -1) //numAtaques pode variar entre 0 e 5
		{
			int posicaoAtaque = rand.nextInt(posicoes.size());
			if(posicoes.get(posicaoAtaque) < posicao)
			{
				travel(-200);
				while(posicoes.get(posicaoAtaque) != posicao)
				{
					if(detetaCor() == Color.RED);
						posicao--;
				}
				Delay.msDelay(2000);
				travelStop();
			}
			else if(posicoes.get(posicaoAtaque) > posicao)
			{
				travel(200);
				while(posicoes.get(posicaoAtaque) != posicao)
				{
					if(detetaCor() == Color.RED);
						posicao++;
				}
				Delay.msDelay(2000);
				travelStop();
			}
			else
			{
				int ataque = rand.nextInt(3);
				if(ataque == 0)
					ataqueSom();
				else if(ataque == 1)
					ataqueToque();
				else
					ataqueGrua();
				numAtaques--;
				posicoes.remove(posicaoAtaque);
			}
		}
	}
	
	public void ataqueSom()
	{
		Sound.beepSequenceUp();
		Sound.buzz();
		
		//remover vida e energia do inimigo e do robo
	}
	
	public void ataqueToque()
	{
		Motor.C.setSpeed(70);
		Motor.C.forward();
		Delay.msDelay(1000);
		Motor.C.stop();
		
		Motor.C.setSpeed(-70);
		Motor.C.forward();
		Delay.msDelay(1000);
		Motor.C.stop();
		
		//remover vida e energia do inimigo e do robo
	}
	
	public void ataqueGrua()
	{
		Motor.C.setSpeed(-70);
		Motor.C.forward();
		Delay.msDelay(1000);
		Motor.C.stop();
		
		Motor.C.setSpeed(70);
		Motor.C.forward();
		Delay.msDelay(1000);
		Motor.C.stop();
		
		//remover vida e energia do inimigo e do robo
	}
	
	
	//----------Defesa------------
	public void defende()
	{
		
	}
	
	
	public void sinaisVitaisRoboVida() 
	{
		//aplicação das barras de estado da vida
		if (vidaRobo == 750) {
			vida8();
		}
		else if (vidaRobo < 750 && vidaRobo >= 657) {
			vida7();
		}
		else if (vidaRobo < 657 && vidaRobo >= 564) {
			vida6();
		}
		else if (vidaRobo < 564 && vidaRobo >= 471) {
			vida5();
		}
		else if (vidaRobo < 471 && vidaRobo >= 378) {
			vida4();
		}
		else if (vidaRobo < 378 && vidaRobo >= 285) {
			vida3();
		}
		else if (vidaRobo < 285 && vidaRobo >= 192) {
			vida2();
		}
		else if (vidaRobo < 192 && vidaRobo >= 99) {
			vida1();
		}
		else if (vidaRobo < 99) {
			vida0();
		}
	}
	
	public void sinaisVitaisRoboEnergia() 

	{
		//aplicação das barras de estado da vida
		if (energiaRobo == 500) {
			vida8();
		}
		else if (energiaRobo < 500 && energiaRobo >= 438) {
			vida7();
		}
		else if (energiaRobo < 438 && energiaRobo >= 376) {
			vida6();
		}
		else if (energiaRobo < 376 && energiaRobo >= 314) {
			vida5();
		}
		else if (energiaRobo < 314 && energiaRobo >= 252) {
			vida4();
		}
		else if (energiaRobo < 252 && energiaRobo >= 190) {
			vida3();
		}
		else if (energiaRobo < 190 && energiaRobo >= 128) {
			vida2();
		}
		else if (energiaRobo < 128 && energiaRobo >= 66) {
			vida1();
		}
		else if (energiaRobo < 66) {
			vida0();
		}
	}
	
	
	//------ BARRAS DE PROGRESSO ------
	
	public void vida8() 
	{
		//uv 750
		LCD.clear();
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|---- " + vidaRobo + "uv ----|");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void vida7() 
	{
		//uv entre 750 e 657
		LCD.clear();
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|---- " + vidaRobo + "uv --- |");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void vida6() 
	{
		//uv entre 657 e 564
		LCD.clear();
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|---- " + vidaRobo + "uv --  |");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void vida5() 
	{
		//uv entre 564 e 471
		LCD.clear();
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|---- " + vidaRobo + "uv -   |");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void vida4() 
	{
		//uv entre 471 e 378
		LCD.clear();
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|---- " + vidaRobo + "uv     |");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void vida3() 
	{
		//uv entre 378 e 285
		LCD.clear();
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|---  " + vidaRobo + "uv     |");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void vida2() 
	{
		//uv entre 285 e 192
		LCD.clear();
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|--   " + vidaRobo + "uv     |");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void vida1() 
	{
		//uv entre 192 e 99
		LCD.clear();
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|-    " + vidaRobo + "uv     |");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void vida0() 
	{
		//uv menor que 99
		LCD.clear();
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|      " + vidaRobo + "uv     |");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void energia8() 
	{
		//en 500
		LCD.clear();
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|---- " + energiaRobo + "en ----|");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	public void energia7() 
	{
		//en entre 500 e 438
		LCD.clear();
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|---- " + energiaRobo + "en --- |");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	public void energia6() 
	{
		//en entre 438 e 376
		LCD.clear();
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|---- " + energiaRobo + "en --  |");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	public void energia5() 
	{
		//en entre 376 e 314
		LCD.clear();
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|---- " + energiaRobo + "en -   |");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	public void energia4() 
	{
		//en entre 314 e 252
		LCD.clear();
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|---- " + energiaRobo + "en     |");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	public void energia3() 
	{
		//en entre 252 e 190
		LCD.clear();
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|---  " + energiaRobo + "en     |");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	public void energia2() 
	{
		//en entre 190 e 128
		LCD.clear();
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|--   " + energiaRobo + "en     |");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	public void energia1() 
	{
		//en entre 128 e 66
		LCD.clear();
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|-    " + energiaRobo + "en     |");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	public void energia0() 
	{
		//en menor que 66
		LCD.clear();
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|     " + energiaRobo + "en     |");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	// --------- ATAQUES -------------
	
	//Cálculo do impacto de ataque (em uv)
	public int getImpacto(int forca, int vida, int vidaAtual) { //vida correspondente à força atacante
		int impacto = forca * ((vidaAtual * 100 ) / vida);
		return impacto;
	}
	
	//Cálculo do ataque com grua
	public void setAtaqueGrua() {
		vidaRobo = vidaRobo - danoGrua;
		energiaRobo = energiaRobo - consumoGrua;
	}
	
	//Cálculo do ataque com toque
	public void setAtaqueToque() {
		vidaRobo = vidaRobo - danoToque;
		energiaRobo = energiaRobo - consumoToque;	
	}
		
	//Cálculo do ataque com som
	public void setAtaqueSom() {
		vidaRobo = vidaRobo - danoSom;
		energiaRobo = energiaRobo - consumoSom;
	}
	
	
	// ---------- CURAS --------------
	
	//Cálculo da cura 1
	public void setCura1() {
		vidaRobo = vidaRobo + vidaRecuperadaCura1;
		energiaRobo = energiaRobo - consumoCura1;
	}
	
	//Cálculo da cura 2
	public void setCura2() {
		vidaRobo = vidaRobo + vidaRecuperadaCura2;
		energiaRobo = energiaRobo - consumoCura2;
		
	}
	
	//Cálculo da cura 3
	public void setCura3() 
	{
		vidaRobo = vidaRobo + vidaRecuperadaCura3;
		energiaRobo = energiaRobo - consumoCura3;
	}
}
