public class Robo
{
	
	//Criacao/Iniciacao de variaveis/objetos
	private static final int VIDAMAX = 750;
	private static final int ENERGMAX = 500;
	private static final int CURA1 = 100;
	private static final int CURA2 = 200;
	private static final int CURA3 = 400;
	private static final int ENERGCURA1 = 100;
	private static final int ENERGCURA2 = 200;
	private static final int ENERGCURA3 = 400;
	private static final int ENERGGRUA = 300;
	private static final int ENERSOM = 50;
	private static final int ENERGTOQUE = 150;
	private static final int DANOGRUA = 200;
	private static final int DANOSOM = 50;
	private static final int DANOTOQUE = 100;
	private int energAtual;
	private int vidaAtual;
	static int posicaoAtual;
	
	
	//Construtor
	public Robo()
	{
		vidaAtual = VIDAMAX;
		energAtual = ENERGMAX;
		posicaoAtual = 1;
	}
	
	//Getters
	public int getEnergDisponivel()
	{
		return ENERGMAX - energAtual;
	}

	public int getEnergia() {
		return energAtual;
	}

	public int getVida() {
		return vidaAtual;
	}

	public int getPosicaoAtual()
	{
		return posicaoAtual;
	}
	
	
	//Setters
	public void setPosicaoAtual(int valor)
	{
		posicaoAtual = valor;
	}
	
	
	//Ataque
	public void escolheAtaque(Inimigo inimigo)
	{
		if(vidaAtual > 0)
		{
			if(energAtual > 200)
			{
				if(inimigo.getVida() > 150)
					ataqueGrua(inimigo);
				else if(inimigo.getVida() > 50)
					ataqueToque(inimigo);
				else if(inimigo.getVida() > 0)
					ataqueSom(inimigo);
			}
		}
	}
	
	public void ataqueSom(Inimigo inimigo)
	{
		energAtual -= ENERSOM;
		inimigo.recebeDano(DANOSOM);
	}
	
	public void ataqueToque(Inimigo inimigo)
	{
		energAtual -= ENERGTOQUE;
		inimigo.recebeDano(DANOTOQUE);
	}
	
	public void ataqueGrua(Inimigo inimigo)
	{
		energAtual -= ENERGGRUA;
		inimigo.recebeDano(DANOGRUA);
	}
	
	
	//Defesa
	public void recebeDano(int valor)
	{
		if(vidaAtual-valor < 0)
		{
			vidaAtual = 0;
		}
		else
			vidaAtual -=valor;
	}
	
	
	//Cura
	public void curar()
	{
		if(vidaAtual > 0)
		{
			int energ_disp = getEnergDisponivel();
			
			if(energ_disp >= ENERGCURA3 + 50 && getVida() <= 100)
			{
				vidaAtual += CURA3;
				energAtual -= ENERGCURA3;
			}
			else if(energ_disp >= ENERGCURA2 + 50 && getVida() <= 200)
			{
				vidaAtual += CURA2;
				energAtual -= ENERGCURA2;
			}
			else if(energ_disp >= ENERGCURA1 + 50 && getVida() <= 300)
			{
				vidaAtual += CURA1;
				energAtual -= ENERGCURA1;
			}
		}
	}
	
	//Outros
	public void recuperaEnergia()
	{
		if(vidaAtual > 0)
		{
			if((energAtual * 1.5) <= ENERGMAX)
				energAtual = (int) Math.round(energAtual * 1.5);
			else
				energAtual = ENERGMAX;
		}
	}
}
