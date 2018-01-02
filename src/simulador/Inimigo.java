package simulador;

public class Inimigo 
{
	//Definicao e inicializacao de constantes e variaveis
	private static final int VIDATANQUE = 200; 		//Vida dos tanques
    private static final int VIDAART = 50; 			//Vida das artilharias
    private static final int VIDAINF = 100; 			//Vida das infantarias
    private static final int FORCATANQUE = 200; 		//Forca dos tanques
    private static final int FORCAART = 500; 			//Forca das artilharias
    private static final int FORCAINF = 100; 			//Forca das infantarias
    private int vidaAtual; 								//Vida atual
    private int vidaMax; 								//Vida maxima
    private int forca; 									//Forca
    private int id; 									//0-tanque, 1-artilharia, 2-infantaria, 3-vazio;
    int posicao;
	
    //Construtor
	public Inimigo(int id, boolean todosCasos)
	{
		this.id = id; //id do inimigo
		this.posicao = Robo.posicaoAtual; //posicao do inimigo
		
		if(todosCasos) //Se for uma simulacao de todos os casos usa ids de 0 a 3
		{
			if (id == 0) //Se o inimigo for um tanque
	        {
	            vidaAtual = VIDATANQUE;
	            vidaMax = VIDATANQUE;
	            forca = FORCATANQUE;
	            this.id = 0;
	        }
	        else if (id == 1) //Se o inimigo for uma artilharia
	        {
	            vidaAtual = VIDAART;
	            vidaMax = VIDAART;
	            forca = FORCAART;
	            this.id = 1;
	        }
	        else if (id == 2) //Se o inimigo for uma infantaria
	        {
	            vidaAtual = VIDAINF;
	            vidaMax = VIDAINF;
	            forca = FORCAINF;
	            this.id = 2;
	        }
	        else if (id == 3) //Se o inimigo for vazio
	        {
	            vidaAtual = 0;
	            vidaMax = 0;
	            forca = 0;
	            this.id = 3;
	        }
		}
		else //Se for outro tipo de simulacao usa os ids de 1 a 6
		{
			if (id == 1 || id == 2)
	        {
	            vidaAtual = VIDATANQUE;
	            vidaMax = VIDATANQUE;
	            forca = FORCATANQUE;
	            this.id = 0;
	        }
	        else if (id == 3 || id == 4)
	        {
	            vidaAtual = VIDAART;
	            vidaMax = VIDAART;
	            forca = FORCAART;
	            this.id = 1;
	        }
	        else if (id == 5 || id == 6)
	        {
	            vidaAtual = VIDAINF;
	            vidaMax = VIDAINF;
	            forca = FORCAINF;
	            this.id = 2;
	        }
	        else if (id == 0)
	        {
	            vidaAtual = 0;
	            vidaMax = 0;
	            forca = 0;
	            this.id = 3;
	        }
		}
	}
	
	//Getters
	//Retorna o id do inimigo
	public int getId()
	{
		return id;
	}
	
	//Retorna a vida do inimigo
	public int getVida()
	{
		return vidaAtual;
	}
	
	//Retorna o dano capaz de infligir
	public double getDano()
	{
		if(id != 3) //Se nao for um inimigo vazio
			return forca*((double)vidaAtual/vidaMax);
		else
			return 0;
	}
	
	//Setters
	//Atualiza a vida do inimigo
	public void setVida(int valor)
	{
		vidaAtual = valor;
	}
	
	//Recebe dano infligido pelo robo
	public void recebeDano(int valor)
	{
		if(vidaAtual-valor < 0) //Se ficar com vida negativa
		{
			vidaAtual = 0; //Coloca a vida a 0
		}
		else
			vidaAtual -=valor; //Retira a vida perdida
	}
	
	//Retorna uma string com a informacao do inimigo
	public String toString()
    {
        String string = "";
        if(id == 0)
        {
            string += "Tanque-" + vidaAtual;
        }
        else if(id == 1)
        {
            string += "Artilharia-" + vidaAtual;
        }
        else if(id == 2)
        {
            string += "Infantaria-" + vidaAtual;
        }
        else if(id == 3)
        {
            string += "Vazio-" + vidaAtual;
        }
        return string;
    }
}
