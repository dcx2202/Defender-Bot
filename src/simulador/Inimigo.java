package simulador;

public class Inimigo 
{
	private static final int VIDATANQUE = 200;
    private static final int VIDAART = 50;
    private static final int VIDAINF = 100;
    private static final int FORCATANQUE = 200;
    private static final int FORCAART = 500;
    private static final int FORCAINF = 100;
    private int vidaAtual;
    private int vidaMax;
    private int forca;
    private int id; //0-tanque, 1-artilharia, 2-infantaria, 3-vazio;
    int posicao;
	
	public Inimigo(int id, boolean todosCasos)
	{
		this.id = id;
		this.posicao = Robo.posicaoAtual;
		
		if(todosCasos)
		{
			if (id == 0)
	        {
	            vidaAtual = VIDATANQUE;
	            vidaMax = VIDATANQUE;
	            forca = FORCATANQUE;
	            this.id = 0;
	        }
	        else if (id == 1)
	        {
	            vidaAtual = VIDAART;
	            vidaMax = VIDAART;
	            forca = FORCAART;
	            this.id = 1;
	        }
	        else if (id == 2)
	        {
	            vidaAtual = VIDAINF;
	            vidaMax = VIDAINF;
	            forca = FORCAINF;
	            this.id = 2;
	        }
	        else if (id == 3)
	        {
	            vidaAtual = 0;
	            vidaMax = 0;
	            forca = 0;
	            this.id = 3;
	        }
		}
		else
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
