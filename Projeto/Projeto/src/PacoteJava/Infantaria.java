package PacoteJava;

public class Infantaria {

	int forcaInfantaria, numAtaquesInfantaria, vidaInfantaria, impactoInfantaria;
	
	public Infantaria()
	{
		vidaInfantaria = 100;
	}
	
	public int getImpacto(int forca, int vida, int vidaAtual) 
	{ //vida correspondente à força atacante
		int impacto = forca * ((vidaAtual * 100 ) / vida);
		return impacto;
	}
}
