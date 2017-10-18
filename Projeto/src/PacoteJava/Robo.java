package PacoteJava;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Robo {

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
	
	EV3ColorSensor colorSensor;
	SampleProvider colorProvider;
	float[] colorSample;
	
	
	//------ Construtor ------
	
	public Robo()
	{
		energiaRobo = 500;
		vidaRobo = 750;
	}
	
	
	//------ Movimento ------
	
	public void travel() 
	{
		Motor.A.setSpeed(200);
		Motor.B.setSpeed(200);
		Motor.A.forward();
		Motor.B.forward();
	}
	
	public void travelStop() 
	{
		Motor.A.stop();
		Motor.B.stop();
	}
	
	
	//-----------------------
	
	public void detetaCor()
	{
		while(Button.ESCAPE.isUp()) 
		{
			//Define a porta do sensor de cor
			Port s1 = LocalEV3.get().getPort("S1");
			colorSensor = new EV3ColorSensor(s1);
			colorProvider = colorSensor.getColorIDMode();
			colorSample = new float[colorProvider.sampleSize()];
			int currentDetectedColor = colorSensor.getColorID();
			
			//EXTRA --------
			if (currentDetectedColor == Color.RED) {
				System.out.println("       RED       ");
				System.out.println("-----------------");
				travelStop();
				Delay.msDelay(3000);
			}
			//----------------
			
			
			//ARTILHARIA
			else if (currentDetectedColor == Color.YELLOW) {
				travelStop();
				System.out.println("    ARTILHARIA   ");
				System.out.println("-----------------");
				Delay.msDelay(3000);
			}
			
			
			//TANQUE
			else if (currentDetectedColor == Color.BLUE) {
				travelStop();
				System.out.println("      TANQUE     ");
				System.out.println("-----------------");
				Delay.msDelay(3000);
			}
			
			
			//INFANTARIA
			else if (currentDetectedColor == Color.GREEN) {
				travelStop();
				System.out.println("    INFANTARIA   ");
				System.out.println("-----------------");
				Delay.msDelay(3000);
			}
			else
				travel();
		}
		colorSensor.close();
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
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|---- " + vidaRobo + "uv ----|");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void vida7() 
	{
		//uv entre 750 e 657
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|---- " + vidaRobo + "uv --- |");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void vida6() 
	{
		//uv entre 657 e 564
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|---- " + vidaRobo + "uv --  |");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void vida5() 
	{
		//uv entre 564 e 471
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|---- " + vidaRobo + "uv -   |");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void vida4() 
	{
		//uv entre 471 e 378
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|---- " + vidaRobo + "uv     |");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void vida3() 
	{
		//uv entre 378 e 285
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|---  " + vidaRobo + "uv     |");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void vida2() 
	{
		//uv entre 285 e 192
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|--   " + vidaRobo + "uv     |");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void vida1() 
	{
		//uv entre 192 e 99
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|-    " + vidaRobo + "uv     |");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void vida0() 
	{
		//uv menor que 99
		System.out.println(" ----  VIDA ---- ");
		System.out.println("|      " + vidaRobo + "uv     |");
		System.out.println(" --------------- ");
		System.out.println("                 ");
	}
	
	public void energia8() 
	{
		//en 500
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|---- " + energiaRobo + "en ----|");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	public void energia7() 
	{
		//en entre 500 e 438
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|---- " + energiaRobo + "en --- |");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	public void energia6() 
	{
		//en entre 438 e 376
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|---- " + energiaRobo + "en --  |");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	public void energia5() 
	{
		//en entre 376 e 314
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|---- " + energiaRobo + "en -   |");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	public void energia4() 
	{
		//en entre 314 e 252
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|---- " + energiaRobo + "en     |");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	public void energia3() 
	{
		//en entre 252 e 190
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|---  " + energiaRobo + "en     |");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	public void energia2() 
	{
		//en entre 190 e 128
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|--   " + energiaRobo + "en     |");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	public void energia1() 
	{
		//en entre 128 e 66
		System.out.println(" --  ENERGIA --- ");
		System.out.println("|-    " + energiaRobo + "en     |");
		System.out.println(" --------------- ");
		Delay.msDelay(10000);
	}
	
	public void energia0() 
	{
		//en menor que 66
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
