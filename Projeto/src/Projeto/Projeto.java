package Projeto;

import lejos.robotics.Color;

public class Projeto {

	//Criacao/Iniciacao de variaveis/objetos
	Robo robo = new Robo();
	
	
	//Main
	public static void main(String[] args) 
	{
		
		
	}

	
	//Metodos de jogo
	public void inicio() //Voltar ao inicio do tabuleiro a partir de qualquer ponto
	{	
		while(robo.detetaCor() != Color.WHITE)
			robo.mover(-1, 400);
		robo.parar();
		robo.mover(1, 200);
		robo.espera(1);
		robo.parar();
	}
	
	public void detetaInimigos()
	{
		
	}
	
	
}
