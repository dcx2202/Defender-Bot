package Projeto;

import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;

public class Projeto {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Sound.beep();
		Motor.A.setSpeed(400);
		Motor.B.setSpeed(400);
		while(true) {
		Motor.A.forward();
		Motor.B.forward();}
	}

}
