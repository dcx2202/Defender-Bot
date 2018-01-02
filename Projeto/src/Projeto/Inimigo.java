package Projeto;

public class Inimigo {

	//Definicao e inicializacao de constantes e variaveis
	private static final int vidaTanque = 200;	//vida dos tanques	
	private static final int vidaArt = 50;		//vida das artilharias	
	private static final int vidaInf = 100;		//vida das infantarias	
	private static final int forcaTanque = 200;	//forÃ§a dos tanques
	private static final int forcaArt = 500;	//forÃ§a das artilharias
	private static final int forcaInf = 100;	//forÃ§a das infantarias	
	private int vidaAtual;						
	private int vidaMax;						
	private int forca;
	private int id; //0-tanque, 1-artilharia, 2-infantaria, 3-vazio;
	int posicao;
	
	//Construtor
	public Inimigo(int id)
	{
		this.id = id;
		this.posicao = Robo.posicaoAtual;
		
		if (id == 0)		//se o inimigo for um tanque
		{
			vidaAtual = vidaTanque;
			vidaMax = vidaTanque;
			forca = forcaTanque;
		}
		else if (id == 1)	//se o inimigo for uma artilharia
		{
			vidaAtual = vidaArt;
			vidaMax = vidaArt;
			forca = forcaArt;
		}
		else if (id == 2)	//se o inimigo for uma infantaria
		{
			vidaAtual = vidaInf;
			vidaMax = vidaInf;
			forca = forcaInf;
		}
		else if (id == 3)	//se nao existir inimigo 
		{
			vidaAtual = 0;
			vidaMax = 0;
			forca = 0;
		}
	}
	
	//Getters
	public int getId()
	{
		return id;
	}
	
	public int getVida()
	{
		return vidaAtual;
	}
	
	public double getDano()		//dano dado pelos inimigos
	{
		if(id != 3)		//se o inimigo existir (nao ser uma posicao vazia)
			return forca*((double)vidaAtual/vidaMax);	//calcula a forca com que os inimigos atacam o robo
		else
			return 0;
	}
	
	//Setters
	public void setVida(int valor)
	{
		vidaAtual = valor;
	}
	
	public void recebeDano(int valor)	//dano recebido pelos inimigos
	{
		if(vidaAtual-valor < 0)		//se o inimigo ficar com vida negativa
		{
			vidaAtual = 0;			
		}
		else
			vidaAtual -=valor;
	}
	
	public String toString() 	//retorna a string com os dados do inimigo
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