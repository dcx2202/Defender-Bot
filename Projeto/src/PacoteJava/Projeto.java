package PacoteJava;

import java.io.File;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
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
	
	//SENSOR DE TOQUE
	static Port s2 = LocalEV3.get().getPort("S2");
	static EV3TouchSensor touchSensor = new EV3TouchSensor(s2);
	static SampleProvider touchProvider = touchSensor.getTouchMode();
	static float[] touchSample = new float[touchProvider.sampleSize()];
	
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
		Sound.playSample(Robo.som1, 100);
		touchProvider.fetchSample(touchSample, 0);
		
		//enquanto o botao nao for pressionado continua a espera
		while(!isTouched())
			Delay.msDelay(100);
		
		Robo robo = new Robo();
		Sound.playSample(Robo.som16, 100); //"Detetando inimigos"
		robo.detetaInimigos();
		Sound.playSample(Robo.som2, 100); //"Detecao de inimigos concluida."
		//Delay.msDelay(1000);
		Sound.playSample(Robo.som3, 100); //"Voltando a posicao 1."
		robo.returnHome();
		Sound.playSample(Robo.som4, 100); //"Preparando-me para atacar."
		robo.posicao = 3;
		//robo.ataca();
		touchSensor.close();
		robo.colorSensor.close();
	}
	// -----------------------------------------------
	
	public static boolean isTouched()
	{
		touchProvider.fetchSample(touchSample, 0);
		return touchSample[0] != 0;
	}
	
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
