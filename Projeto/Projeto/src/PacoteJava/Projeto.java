package PacoteJava;

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*Motor.A.forward();
		Motor.B.forward();
		Delay.msDelay(10000);
		Motor.A.stop();
		Motor.B.stop();*/
		new Projeto();
	}
	
	public Projeto() 
	{
		Robo robo = new Robo();
		
		//pilot = new DifferentialPilot(1.5, 6, Motor.A, Motor.B);
		/*Wheel wheel1 = WheeledChassis.modelWheel(Motor.A, 1.5); //.offset();
		Wheel wheel2 = WheeledChassis.modelWheel(Motor.B, 1.5); //.offset();
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);*/
		
		robo.detetaCor();
			
		/*switch (currentDetectedColor) {
		case Color.RED:
			//colorSensor.setFloodlight(Color.RED);
			travelStop();
			break;
		case Color.GREEN:
			colorSensor.setFloodlight(Color.GREEN);
			break;
		case Color.BLUE:
			colorSensor.setFloodlight(Color.BLUE);
			break;
		default:
			colorSensor.setFloodlight(Color.NONE);
			break;
		}
		Delay.msDelay(250);*/

		/*while(!(colorSample == Color.RED)) {
			travelAndRotate();*/
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
