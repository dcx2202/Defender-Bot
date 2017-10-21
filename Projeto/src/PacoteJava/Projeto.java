package PacoteJava;

import java.io.File;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;

/*import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
//import lejos.robotics.navigation.DifferentialPilot;
//import lejos.robotics.navigation.MovePilot;
//import lejos.robotics.chassis.*;
import lejos.utility.Delay;*/

public class Projeto{

	//DifferentialPilot pilot;
	
	//MovePilot pilot;
	
	//SONS
	static File som1 = new File("../../resources/som1.wav"); //"Coloque-me na posicao 1 e pressione o botao para comecar."
	static File som2 = new File("../../resources/som2.wav"); //"Detecao de inimigos concluida."
	static File som3 = new File("../../resources/som3.wav"); //"Voltando a posicao 1."
	static File som4 = new File("../../resources/som4.wav"); //"Preparando-me para atacar."
	static File som5 = new File("../../resources/som5.wav"); //"Energia"
	static File som6 = new File("../../resources/som6.wav"); //"Vida"
	static File som7 = new File("../../resources/som7.wav"); //"Preparando-me para defender."
	static File som8 = new File("../../resources/som8.wav"); //"Inimigo abatido."
	static File som9 = new File("../../resources/som9.wav"); //"Vida a 0. Fim do jogo!"
	static File som10 = new File("../../resources/som10.wav"); //"Comecando o jogo."
	static File som11 = new File("../../resources/som11.wav"); //"Vitoria! Todos os inimigos abatidos. Fim do jogo!"
	static File som12 = new File("../../resources/som12.wav"); //"Vida perdida"
	static File som13 = new File("../../resources/som13.wav"); //"Vida recuperada"
	static File som14 = new File("../../resources/som14.wav"); //"Energia gasta"
	static File som15 = new File("../../resources/som15.wav"); //"Energia recuperada"
	static File som16 = new File("../../resources/som16.wav"); //"Detetando inimigos"
	
	//USAR PARA SOLETRAR QUALQUER NUMERO
	static File um = new File("../../resources/um.wav"); //"um"
	static File dois = new File("../../resources/dois.wav"); //"dois"
	static File tres = new File("../../resources/tres.wav"); //"tres"
	static File quatro = new File("../../resources/quatro.wav"); //"quatro"
	static File cinco = new File("../../resources/cinco.wav"); //"cinco"
	static File seis = new File("../../resources/seis.wav"); //"seis"
	static File sete = new File("../../resources/sete.wav"); //"sete"
	static File oito = new File("../../resources/oito.wav"); //"oito"
	static File nove = new File("../../resources/nove.wav"); //"nove"
	
	//SENSOR DE TOQUE
	static Port s2 = LocalEV3.get().getPort("S2");
	static EV3TouchSensor touchSensor = new EV3TouchSensor(s2);
	static SampleProvider touchProvider = touchSensor.getTouchMode();
	static float[] touchSample;
	
	public Projeto() 
	{
		//pilot = new DifferentialPilot(1.5, 6, Motor.A, Motor.B);
		/*Wheel wheel1 = WheeledChassis.modelWheel(Motor.A, 1.5); //.offset();
		Wheel wheel2 = WheeledChassis.modelWheel(Motor.B, 1.5); //.offset();
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);*/
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*Motor.A.forward();
		Motor.B.forward();
		Delay.msDelay(10000);
		Motor.A.stop();
		Motor.B.stop();*/
		
		//new Projeto();
		
		LCD.clear();
		System.out.println("Coloque-me na posição 1 e pressione o botão para começar.");
		Sound.playSample(som1);
		touchProvider.fetchSample(touchSample, 0);
		
		//enquanto o botao nao for pressionado continua a espera
		while(touchSample[0] == 0)
		{
			Delay.msDelay(100);
		}
		
		Robo robo = new Robo();
		Sound.playSample(som16); //"Detetando inimigos"
		robo.detetaInimigos();
		Sound.playSample(som2); //"Detecao de inimigos concluida."
		Delay.msDelay(1000);
		Sound.playSample(som3); //"Voltando a posicao 1."
		robo.returnHome();
		Sound.playSample(som4); //"Preparando-me para atacar."
		robo.ataca();
	}
	// -----------------------------------------------
	
	public void sinaisVitaisTanqueVida() 
	{
		
	}
	
	public void sinaisVitaisTanqueEnergia() 
	{
		
	}
	
	public void sinaisVitaisArtilhariaVida() 
	{
		
	}
	
	
	public void sinaisVitaisArtilhariaEnergia() 
	{
		
	}
	
	public void sinaisVitaisInfantariaVida() 
	{
		
	}
	
	
	public void sinaisVitaisInfantariaEnergia() 
	{
		
	}
	
	public void ataqueStatus() 
	{
		
	}
	
	public void curaStatus() 
	{
		
	}
	
}
