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
	private int id; //0-tanque, 1-artilharia, 2-infantaria, 3-vazio;
	int posicao;
	
	public Inimigo(int id)
	{
		this.id = id;
		this.posicao = Robo.posicaoAtual;
		
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
		else if (id == 3)
		{
			vidaAtual = 0;
			vidaMax = 0;
			forca = 0;
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
		{
			vidaAtual = 0;
		}
		else
			vidaAtual -=valor;
	}
	
	public double getDano()
	{
		if(id != 3)
			return forca*((double)vidaAtual/vidaMax);
		else
			return 0;
	}
	
	public String toString()
	{
		String string = "";
		if(id == 0)
		{
			string += "Tanque";
		}
		else if(id == 1)
		{
			string += "Artilharia";
		}
		else if(id == 2)
		{
			string += "Infantaria";
		}
		else if(id == 3)
		{
			string += "Vazio";
		}
		return string;
	}
	
}
