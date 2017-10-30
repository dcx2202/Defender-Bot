package Projeto;

public class Inimigo {

	private static final int vidaTanque = 200;
	private static final int vidaArt = 50;
	private static final int vidaInf = 100;
	private static final int forcaTanque = 200;
	private static final int forcaArt = 500;
	private static final int forcaInf = 100;
	private int vidaAtual;
	private int vidaMax;
	private int forca;
	private int id; //0-tanque, 1-artilharia, 2-infantaria;
	
	public Inimigo(int id)
	{
		this.id = id;
		
		if (id == 0)
		{
			vidaAtual = vidaTanque;
			vidaMax = vidaTanque;
			forca = forcaTanque;
		}
		else if (id == 1)
		{
			vidaAtual = vidaArt;
			vidaMax = vidaArt;
			forca = forcaArt;
		}
		else if (id == 2)
		{
			vidaAtual = vidaInf;
			vidaMax = vidaInf;
			forca = forcaInf;
		}
	}
	
	public int getId()
	{
		return id;
	}
	
	public int getVida()
	{
		return vidaAtual;
	}
	
	public void setVida(int valor)
	{
		vidaAtual = valor;
	}
	
	public void recebeDano(int valor)
	{
		if(vidaAtual-valor < 0)
			vidaAtual = 0;
		else
			vidaAtual -=valor;
	}
	
	public int getDano()
	{
		return forca*(vidaAtual/vidaMax);
	}
}
