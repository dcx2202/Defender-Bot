package PacoteJava;

public class Artilharia {

	int forcaArtilharia, numAtaquesArtilharia, vidaArtilharia, impactoArtilharia;
	
	public Artilharia()
	{
		vidaArtilharia = 50; //uv
	}
	
	public int getImpacto(int forca, int vida, int vidaAtual) 
	{ //vida correspondente à força atacante
		int impacto = forca * ((vidaAtual * 100 ) / vida);
		return impacto;
	}
}
