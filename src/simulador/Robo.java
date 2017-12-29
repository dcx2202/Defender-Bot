package simulador;

import java.util.TreeMap;

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
    private static final int ENERGSOM = 50;
    private static final int ENERGTOQUE = 150;
    private static final int DANOGRUA = 200;
    private static final int DANOSOM = 50;
    private static final int DANOTOQUE = 100;
    private int energAtual;
    private int vidaAtual;
    static int posicaoAtual;
    
    //Variaveis fase 2
    static int VIDA_CURAR = 200;		//se a vida do robo < VIDA_CURAR entao cura
    static int ENERG_ATAQUE_RES = 50;	//energia de reserva ao atacar
    static int ENERG_CURA_RES = 50;		//energia de reserva ao curar
    static int VIDA_CURA_1 = 300;		//vida do robo <= VIDA_CURA1 para usar a cura1
    static int VIDA_CURA_2 = 200;		//vida do robo <= VIDA_CURA2 para usar a cura2
    static int VIDA_CURA_3 = 100;		//vida do robo <= VIDA_CURA3 para usar a cura3
    static int VIDA_INI_GRUA = 150;		//vida do inimigo > VIDA_INI_GRUA para usar o ataque de grua
    static int VIDA_INI_TOQUE = 50;	//vida do inimigo > VIDA_INI_TOQUE para usar o ataque de toque
    static int VIDA_INI_SOM = 0;		//vida do inimigo > VIDA_INI_SOM para usar o ataque de som

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
        return energAtual;
    }

    public int getEnergia() 
    {
        return energAtual;
    }

    public int getVida() 
    {
        return vidaAtual;
    }

    public int getPosicaoAtual() 
    {
        return posicaoAtual;
    }

    public void setEnergAtual(int energAtual) 
    {
        this.energAtual = energAtual;
    }

    public void setVidaAtual(int vidaAtual) 
    {
        this.vidaAtual = vidaAtual;
    }

    //Setters
    public void setPosicaoAtual(int valor) 
    {
        posicaoAtual = valor;
    }

    //Ataque
    public void escolheAtaque(Inimigo inimigo)
    {
        int energ_disp = getEnergDisponivel();

        if (vidaAtual > 0)
        {
            if (inimigo.getVida() > VIDA_INI_GRUA && energ_disp > ENERGGRUA + ENERG_ATAQUE_RES)
            {
                ataqueGrua(inimigo);
            } 
            else if (inimigo.getVida() > VIDA_INI_TOQUE && energ_disp > ENERGTOQUE + ENERG_ATAQUE_RES)
            {
                ataqueToque(inimigo);
            } 
            else if (inimigo.getVida() > VIDA_INI_SOM && energ_disp > ENERGSOM + ENERG_ATAQUE_RES)
            {
                ataqueSom(inimigo);
            }
        }
    }

    public void escolheAtaquePrint(Inimigo inimigo) 
    {
        int energ_disp = getEnergDisponivel();

        System.out.print("Ataque:      ");
        if (vidaAtual > 0) 
        {
            if (inimigo.getVida() > VIDA_INI_GRUA && energ_disp > ENERGGRUA + ENERG_ATAQUE_RES) 
            {
                ataqueGrua(inimigo);
                System.out.println("Grua");
            } 
            else if (inimigo.getVida() > VIDA_INI_TOQUE && energ_disp > ENERGTOQUE + ENERG_ATAQUE_RES) 
            {
                ataqueToque(inimigo);
                System.out.println("Toque");
            } 
            else if (inimigo.getVida() > VIDA_INI_SOM && energ_disp > ENERGSOM + ENERG_ATAQUE_RES) 
            {
                ataqueSom(inimigo);
                System.out.println("Som");
            } 
            else 
            {
                System.out.println("Nenhum");
            }
        } 
        else 
        {
            System.out.println("Nenhum");
        }
    }
    
    public void ataqueSom(Inimigo inimigo)
	{
		if(energAtual - ENERGSOM >= 0)
		{
			energAtual -= ENERGSOM;
			inimigo.recebeDano(DANOSOM);
		}
	}
	
	public void ataqueToque(Inimigo inimigo)
	{
		if(energAtual - ENERGTOQUE >= 0)
		{
			energAtual -= ENERGTOQUE;
			inimigo.recebeDano(DANOTOQUE);
		}
	}
	
	public void ataqueGrua(Inimigo inimigo)
	{
		if(energAtual - ENERGGRUA >= 0)
		{
			energAtual -= ENERGGRUA;
			inimigo.recebeDano(DANOGRUA);
		}
	}

    //Defesa
    public void recebeDano(int valor) 
    {
        if (vidaAtual - valor < 0) 
        {
            vidaAtual = 0;
        } 
        else 
        {
            vidaAtual -= valor;
        }
    }

    //Cura
    public void curar()
    {
        if (vidaAtual > 0) 
        {
            int energ_disp = getEnergDisponivel();

            if (energ_disp >= ENERGCURA3 + ENERG_CURA_RES && getVida() <= VIDA_CURA_3) 
            {
                vidaAtual += CURA3;
                energAtual -= ENERGCURA3;
            } 
            else if (energ_disp >= ENERGCURA2 + ENERG_CURA_RES && getVida() <= VIDA_CURA_2) 
            {
                vidaAtual += CURA2;
                energAtual -= ENERGCURA2;
            } 
            else if (energ_disp >= ENERGCURA1 + ENERG_CURA_RES && getVida() <= VIDA_CURA_1) 
            {
                vidaAtual += CURA1;
                energAtual -= ENERGCURA1;
            }
        }
    }

    public void curarPrint()
    {
        System.out.print("Vida:        " + vidaAtual + "->");
        if (vidaAtual > 0)
        {
            int energ_disp = getEnergDisponivel();

            if (energ_disp >= ENERGCURA3 + ENERG_CURA_RES && getVida() <= VIDA_CURA_3) 
            {
                vidaAtual += CURA3;
                energAtual -= ENERGCURA3;
            } 
            else if (energ_disp >= ENERGCURA2 + ENERG_CURA_RES && getVida() <= VIDA_CURA_2) 
            {
                vidaAtual += CURA2;
                energAtual -= ENERGCURA2;
            } 
            else if (energ_disp >= ENERGCURA1 + ENERG_CURA_RES && getVida() <= VIDA_CURA_1) 
            {
                vidaAtual += CURA1;
                energAtual -= ENERGCURA1;
            }
        }
        System.out.println(vidaAtual);
    }
    
    public void recuperaEnergia()
    {
        if (vidaAtual > 0)
        {
            if ((energAtual * 1.5) <= ENERGMAX)
            {
                energAtual = (int) Math.round(energAtual * 1.5);
            }
            else
            {
                energAtual = ENERGMAX;
            }
        }
    }
    
    public void recuperaEnergiaPrint()
    {
        System.out.print("Recuperação: " + energAtual + "->");
        if (vidaAtual > 0)
        {
            if ((energAtual * 1.5) <= ENERGMAX)
            {
                energAtual = (int) Math.round(energAtual * 1.5);
            } 
            else 
            {
                energAtual = ENERGMAX;
            }
        }
        System.out.println(energAtual);
    }
    
    public static void estrategia(String perfil)
	{
		switch (perfil)
		{
			case "agressivo":
				ENERG_CURA_RES = 25;		//energia de reserva ao curar
				ENERG_ATAQUE_RES = 25;		//energia de reserva ao atacar
				VIDA_CURA_3 = 50;			//vida do robo <= VIDA_CURA3 para usar a cura3
				VIDA_CURA_2 = 75;			//vida do robo <= VIDA_CURA2 para usar a cura2
				VIDA_CURA_1 = 100;			//vida do robo <= VIDA_CURA1 para usar a cura1
				VIDA_CURAR = 100;			//se a vida do robo < VIDA_CURAR entao cura
				VIDA_INI_GRUA = 0;			//vida do inimigo > VIDA_INI_GRUA para usar o ataque de grua
				VIDA_INI_TOQUE = 0;			//vida do inimigo > VIDA_INI_TOQUE para usar o ataque de toque
				VIDA_INI_SOM = 0;			//vida do inimigo > VIDA_INI_SOM para usar o ataque de som
				break;
			case "passivo":
				ENERG_CURA_RES = 100;
				ENERG_ATAQUE_RES = 100;
				VIDA_CURA_3 = 200;
				VIDA_CURA_2 = 300;
				VIDA_CURA_1 = 400;
				VIDA_CURAR = 400;
				VIDA_INI_GRUA = 150;
				VIDA_INI_TOQUE = 100;
				VIDA_INI_SOM = 0;
				break;
			case "equilibrado":
				ENERG_CURA_RES = 50;
				ENERG_ATAQUE_RES = 50;
				VIDA_CURA_3 = 100;
				VIDA_CURA_2 = 150;
				VIDA_CURA_1 = 250;
				VIDA_CURAR = 250;
				VIDA_INI_GRUA = 150;
				VIDA_INI_TOQUE = 50;
				VIDA_INI_SOM = 0;
				break;
			case "ataquemax":
				ENERG_CURA_RES = 1000; //impede de curar
				ENERG_ATAQUE_RES = 0;
				VIDA_CURA_3 = 0;
				VIDA_CURA_2 = 0;
				VIDA_CURA_1 = 0;
				VIDA_CURAR = 0;
				VIDA_INI_GRUA = 0;
				VIDA_INI_TOQUE = 0;
				VIDA_INI_SOM = 0;
				break;
			case "curamax":
				ENERG_CURA_RES = 0;
				ENERG_ATAQUE_RES = 1000; //impede de atacar
				VIDA_CURA_3 = 750;
				VIDA_CURA_2 = 750;
				VIDA_CURA_1 = 750;
				VIDA_CURAR = 750;
				VIDA_INI_GRUA = 0;
				VIDA_INI_TOQUE = 0;
				VIDA_INI_SOM = 0;
			case "sosom":
				ENERG_CURA_RES = 1000; //impede de curar
				ENERG_ATAQUE_RES = 0;
				VIDA_CURA_3 = 0;
				VIDA_CURA_2 = 0;
				VIDA_CURA_1 = 0;
				VIDA_CURAR = 0;
				VIDA_INI_GRUA = 1000;
				VIDA_INI_TOQUE = 1000;
				VIDA_INI_SOM = 0;
				break;
		}
	}
	
    public void escolheEstrategia()
	{
		int vidaAtual = this.vidaAtual;
		int energAtual = this.energAtual;
		TreeMap<Integer, Inimigo> inimigos_aux = copiaTreemap(Simulacao.inimigos);
		double score = 0;
		String perfil_escolhido = "agressivo";
		
		estrategia("agressivo");
		if(vidaAtual < Robo.VIDA_CURAR)
		{
			if(energAtual >= ENERGCURA3 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_3)
			{
				vidaAtual += CURA3;
				energAtual -= ENERGCURA3;
			}
			else if(energAtual >= ENERGCURA2 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_2)
			{
				vidaAtual += CURA2;
				energAtual -= ENERGCURA2;
			}
			else if(energAtual >= ENERGCURA1 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_1)
			{
				vidaAtual += CURA1;
				energAtual -= ENERGCURA1;
			}
		}
		else
		{
			for(Inimigo inimigo : inimigos_aux.values())
			{
				if(inimigo.getId() == 1)
				{
					if(Simulacao.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
			for(Inimigo inimigo : inimigos_aux.values())
			{
				if(inimigo.getId() != 1 && inimigo.getId() != 3)
				{
					if(Simulacao.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
		}
		score = calculaScore(inimigos_aux, vidaAtual, energAtual);
		
		vidaAtual = this.vidaAtual;
		energAtual = this.energAtual;
		inimigos_aux = copiaTreemap(Simulacao.inimigos);
		estrategia("equilibrado");
		if(vidaAtual < Robo.VIDA_CURAR)
		{
			if(energAtual >= ENERGCURA3 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_3)
			{
				vidaAtual += CURA3;
				energAtual -= ENERGCURA3;
			}
			else if(energAtual >= ENERGCURA2 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_2)
			{
				vidaAtual += CURA2;
				energAtual -= ENERGCURA2;
			}
			else if(energAtual >= ENERGCURA1 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_1)
			{
				vidaAtual += CURA1;
				energAtual -= ENERGCURA1;
			}
		}
		else
		{
			for(Inimigo inimigo : inimigos_aux.values())
			{
				if(inimigo.getId() == 1)
				{
					if(Simulacao.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
			for(Inimigo inimigo : inimigos_aux.values())
			{
				if(inimigo.getId() != 1 && inimigo.getId() != 3)
				{
					if(Simulacao.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
		}
		double score_aux = calculaScore(inimigos_aux, vidaAtual, energAtual);
		if(score_aux >= score)
		{
			score = score_aux;
			perfil_escolhido = "equilibrado";
		}
		
		vidaAtual = this.vidaAtual;
		energAtual = this.energAtual;
		inimigos_aux = copiaTreemap(Simulacao.inimigos);
		estrategia("passivo");
		if(vidaAtual < Robo.VIDA_CURAR)
		{
			if(energAtual >= ENERGCURA3 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_3)
			{
				vidaAtual += CURA3;
				energAtual -= ENERGCURA3;
			}
			else if(energAtual >= ENERGCURA2 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_2)
			{
				vidaAtual += CURA2;
				energAtual -= ENERGCURA2;
			}
			else if(energAtual >= ENERGCURA1 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_1)
			{
				vidaAtual += CURA1;
				energAtual -= ENERGCURA1;
			}
		}
		else
		{
			for(Inimigo inimigo : inimigos_aux.values())
			{
				if(inimigo.getId() == 1)
				{
					if(Simulacao.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
			for(Inimigo inimigo : inimigos_aux.values())
			{
				if(inimigo.getId() != 1 && inimigo.getId() != 3)
				{
					if(Simulacao.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
		}
		score_aux = calculaScore(inimigos_aux, vidaAtual, energAtual);
		if(score_aux > score)
		{
			score = score_aux;
			perfil_escolhido = "passivo";
		}
		
		vidaAtual = this.vidaAtual;
		energAtual = this.energAtual;
		inimigos_aux = copiaTreemap(Simulacao.inimigos);
		estrategia("sosom");
		if(vidaAtual < Robo.VIDA_CURAR)
		{
			if(energAtual >= ENERGCURA3 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_3)
			{
				vidaAtual += CURA3;
				energAtual -= ENERGCURA3;
			}
			else if(energAtual >= ENERGCURA2 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_2)
			{
				vidaAtual += CURA2;
				energAtual -= ENERGCURA2;
			}
			else if(energAtual >= ENERGCURA1 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_1)
			{
				vidaAtual += CURA1;
				energAtual -= ENERGCURA1;
			}
		}
		else
		{
			for(Inimigo inimigo : inimigos_aux.values())
			{
				if(inimigo.getId() == 1)
				{
					if(Simulacao.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
			for(Inimigo inimigo : inimigos_aux.values())
			{
				if(inimigo.getId() != 1 && inimigo.getId() != 3)
				{
					if(Simulacao.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
		}
		score_aux = calculaScore(inimigos_aux, vidaAtual, energAtual);
		if(score_aux >= score)
		{
			score = score_aux;
			perfil_escolhido = "sosom";
		}
		if(score < 0)
			estrategia("curamax");
		else
			estrategia(perfil_escolhido);
	}
    
	public void escolheEstrategiaPrint()
	{
		int vidaAtual = this.vidaAtual;
		int energAtual = this.energAtual;
		TreeMap<Integer, Inimigo> inimigos_aux = copiaTreemap(Simulacao.inimigos);
		double score = 0;
		String perfil_escolhido = "agressivo";
		
		estrategia("agressivo");
		if(vidaAtual < Robo.VIDA_CURAR)
		{
			if(energAtual >= ENERGCURA3 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_3)
			{
				vidaAtual += CURA3;
				energAtual -= ENERGCURA3;
			}
			else if(energAtual >= ENERGCURA2 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_2)
			{
				vidaAtual += CURA2;
				energAtual -= ENERGCURA2;
			}
			else if(energAtual >= ENERGCURA1 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_1)
			{
				vidaAtual += CURA1;
				energAtual -= ENERGCURA1;
			}
		}
		else
		{
			for(Inimigo inimigo : inimigos_aux.values())
			{
				if(inimigo.getId() == 1)
				{
					if(Simulacao.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
			for(Inimigo inimigo : inimigos_aux.values())
			{
				if(inimigo.getId() != 1 && inimigo.getId() != 3)
				{
					if(Simulacao.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
		}
		score = calculaScore(inimigos_aux, vidaAtual, energAtual);
		System.out.println("Perfil agressivo: Score = " + score);
		
		vidaAtual = this.vidaAtual;
		energAtual = this.energAtual;
		inimigos_aux = copiaTreemap(Simulacao.inimigos);
		estrategia("equilibrado");
		if(vidaAtual < Robo.VIDA_CURAR)
		{
			if(energAtual >= ENERGCURA3 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_3)
			{
				vidaAtual += CURA3;
				energAtual -= ENERGCURA3;
			}
			else if(energAtual >= ENERGCURA2 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_2)
			{
				vidaAtual += CURA2;
				energAtual -= ENERGCURA2;
			}
			else if(energAtual >= ENERGCURA1 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_1)
			{
				vidaAtual += CURA1;
				energAtual -= ENERGCURA1;
			}
		}
		else
		{
			for(Inimigo inimigo : inimigos_aux.values())
			{
				if(inimigo.getId() == 1)
				{
					if(Simulacao.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
			for(Inimigo inimigo : inimigos_aux.values())
			{
				if(inimigo.getId() != 1 && inimigo.getId() != 3)
				{
					if(Simulacao.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
		}
		double score_aux = calculaScore(inimigos_aux, vidaAtual, energAtual);
		if(score_aux >= score)
		{
			score = score_aux;
			perfil_escolhido = "equilibrado";
		}
		System.out.println("Perfil equilibrado: Score = " + score_aux);
		
		vidaAtual = this.vidaAtual;
		energAtual = this.energAtual;
		inimigos_aux = copiaTreemap(Simulacao.inimigos);
		estrategia("passivo");
		if(vidaAtual < Robo.VIDA_CURAR)
		{
			if(energAtual >= ENERGCURA3 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_3)
			{
				vidaAtual += CURA3;
				energAtual -= ENERGCURA3;
			}
			else if(energAtual >= ENERGCURA2 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_2)
			{
				vidaAtual += CURA2;
				energAtual -= ENERGCURA2;
			}
			else if(energAtual >= ENERGCURA1 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_1)
			{
				vidaAtual += CURA1;
				energAtual -= ENERGCURA1;
			}
		}
		else
		{
			for(Inimigo inimigo : inimigos_aux.values())
			{
				if(inimigo.getId() == 1)
				{
					if(Simulacao.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
			for(Inimigo inimigo : inimigos_aux.values())
			{
				if(inimigo.getId() != 1 && inimigo.getId() != 3)
				{
					if(Simulacao.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
		}
		score_aux = calculaScore(inimigos_aux, vidaAtual, energAtual);
		if(score_aux > score)
		{
			score = score_aux;
			perfil_escolhido = "passivo";
		}
		System.out.println("Perfil passivo: Score = " + score_aux);
		
		vidaAtual = this.vidaAtual;
		energAtual = this.energAtual;
		inimigos_aux = copiaTreemap(Simulacao.inimigos);
		estrategia("sosom");
		if(vidaAtual < Robo.VIDA_CURAR)
		{
			if(energAtual >= ENERGCURA3 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_3)
			{
				vidaAtual += CURA3;
				energAtual -= ENERGCURA3;
			}
			else if(energAtual >= ENERGCURA2 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_2)
			{
				vidaAtual += CURA2;
				energAtual -= ENERGCURA2;
			}
			else if(energAtual >= ENERGCURA1 + ENERG_CURA_RES && vidaAtual <= VIDA_CURA_1)
			{
				vidaAtual += CURA1;
				energAtual -= ENERGCURA1;
			}
		}
		else
		{
			for(Inimigo inimigo : inimigos_aux.values())
			{
				if(inimigo.getId() == 1)
				{
					if(Simulacao.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
			for(Inimigo inimigo : inimigos_aux.values())
			{
				if(inimigo.getId() != 1 && inimigo.getId() != 3)
				{
					if(Simulacao.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
						{
							energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
						{
							energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
						{
							energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
		}
		score_aux = calculaScore(inimigos_aux, vidaAtual, energAtual);
		if(score_aux >= score)
		{
			score = score_aux;
			perfil_escolhido = "sosom";
		}
		System.out.println("Perfil so som: Score = " + score_aux);
		if(score < 0)
		{
			estrategia("curamax");
			System.out.println("Perfil curamax escolhido!\n");
		}
		else
		{
			estrategia(perfil_escolhido);
			System.out.println("Perfil " + perfil_escolhido + " escolhido com o score " + score + "!\n");
		}
	}
	
	public double calculaScore(TreeMap<Integer, Inimigo> inimigos, int vidaAtual, int energAtual)
	{
		double score = 0;
		int vidaTotalInimigos = 0;
		double danoTotalInimigos = 0;
		
		//Pesos
		double p_vidaAtual = 0.55;
		double p_energAtual = 0.85;
		double p_tan = 0.75;
		double p_art = 1.5;
		double p_inf = 0.4;
		double p_vidaTotalInimigos = 0.3;
		double p_danoTotalInimigos = 0.5;
		
		
		
		score += p_vidaAtual * vidaAtual + p_energAtual * energAtual;
		
		for (Inimigo inimigo : inimigos.values())
		{
			if (inimigo.getVida() <= 0)
			{
				if (inimigo.getId() == 0) //se for um tanque
					score += p_tan * 1000;
				else if (inimigo.getId() == 1) //se for uma artilharia
					score += p_art * 1000;
				else if (inimigo.getId() == 2) //se for uma infantaria
					score += p_inf * 1000;
			}
			else
			{
				vidaTotalInimigos += inimigo.getVida();
				danoTotalInimigos += inimigo.getDano();
			}
		}
		score -= p_vidaTotalInimigos * vidaTotalInimigos - p_danoTotalInimigos * danoTotalInimigos;
		
		//System.out.println(vidaAtual + " " + danoTotalInimigos);
		if (vidaAtual - danoTotalInimigos <= 0)
			score -= 10000;
		return score;	
	}
	
	public TreeMap<Integer, Inimigo> copiaTreemap(TreeMap<Integer, Inimigo> original)
	{
		TreeMap<Integer, Inimigo> copia = new TreeMap<>();
		int i = 1;
		for (Inimigo inimigo : original.values())
		{
			copia.put(i, new Inimigo(inimigo.getId(), true));
			copia.get(i).setVida(inimigo.getVida());
			i++;
		}
		return copia;
	}
	
	public TreeMap<Integer, Inimigo> simulaAtaque(TreeMap<Integer, Inimigo> inimigos_aux)
	{
		for(Inimigo inimigo : inimigos_aux.values())
		{
			if(inimigo.getId() == 1)
			{
				if(Simulacao.turno != 12)
				{
					if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
					{
						energAtual -= ENERGGRUA;
						inimigo.recebeDano(DANOGRUA);
					}
					else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
					{
						energAtual -= ENERGTOQUE;
						inimigo.recebeDano(DANOTOQUE);
					}
					else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
					{
						energAtual -= ENERGSOM;
						inimigo.recebeDano(DANOSOM);
					}
				}
				else
				{
					if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
					{
						energAtual -= ENERGGRUA;
						inimigo.recebeDano(DANOGRUA);
					}
					else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
					{
						energAtual -= ENERGTOQUE;
						inimigo.recebeDano(DANOTOQUE);
					}
					else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
					{
						energAtual -= ENERGSOM;
						inimigo.recebeDano(DANOSOM);
					}
				}
			}
		}
		for(Inimigo inimigo : inimigos_aux.values())
		{
			if(inimigo.getId() != 1 && inimigo.getId() != 3)
			{
				if(Simulacao.turno != 12)
				{
					if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
					{
						energAtual -= ENERGGRUA;
						inimigo.recebeDano(DANOGRUA);
					}
					else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
					{
						energAtual -= ENERGTOQUE;
						inimigo.recebeDano(DANOTOQUE);
					}
					else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
					{
						energAtual -= ENERGSOM;
						inimigo.recebeDano(DANOSOM);
					}
				}
				else
				{
					if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
					{
						energAtual -= ENERGGRUA;
						inimigo.recebeDano(DANOGRUA);
					}
					else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
					{
						energAtual -= ENERGTOQUE;
						inimigo.recebeDano(DANOTOQUE);
					}
					else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
					{
						energAtual -= ENERGSOM;
						inimigo.recebeDano(DANOSOM);
					}
				}
			}
		}
		return inimigos_aux;
	}
}
