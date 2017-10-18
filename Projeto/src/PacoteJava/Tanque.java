package PacoteJava;

public class Tanque {

	int forcaTanque, numAtaquesTanque, vidaTanque, impactoTanque;
	
	public Tanque()
	{
		vidaTanque = 200;
	}
	
	public int getImpacto(int forca, int vida, int vidaAtual) 
	{ //vida correspondente à força atacante
		int impacto = forca * ((vidaAtual * 100 ) / vida);
		return impacto;
	}
}
