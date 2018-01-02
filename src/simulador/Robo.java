package simulador;

import java.util.TreeMap;

public class Robo 
{
	//Definicao e iniciacao de variaveis e constantes
    private static final int VIDAMAX = 750; 		//Vida maxima do robo
    private static final int ENERGMAX = 500; 		//Energia maxima do robo
    private static final int CURA1 = 100; 		//Vida recuperada com a cura 1
    private static final int CURA2 = 200; 		//Vida recuperada com a cura 2
    private static final int CURA3 = 400; 		//Vida recuperada com a cura 3
    private static final int ENERGCURA1 = 100; 	//Energia gasta pela cura 1
    private static final int ENERGCURA2 = 200; 	//Energia gasta pela cura 2
    private static final int ENERGCURA3 = 400;	//Energia gasta pela cura 3
    private static final int ENERGGRUA = 300; 	//Energia gasta pelo ataque de grua
    private static final int ENERGSOM = 50; 		//Energia gasta pelo ataque de som
    private static final int ENERGTOQUE = 150; 	//Energia gasta pelo ataque de toque
    private static final int DANOGRUA = 200; 		//Dano do ataque de grua
    private static final int DANOSOM = 50; 		//Dano do ataque de som
    private static final int DANOTOQUE = 100; 	//Dano do ataque de toque
    private int energAtual;							//Energia atual do robo
    private int vidaAtual;							//Vida atual do robo
    static int posicaoAtual;						//Posicao atual do robo
    
    //Variaveis dos perfis
    static int VIDA_CURAR = 200;		//se a vida do robo < VIDA_CURAR entao cura
    static int ENERG_ATAQUE_RES = 50;	//energia de reserva ao atacar
    static int ENERG_CURA_RES = 50;		//energia de reserva ao curar
    static int VIDA_CURA_1 = 300;		//vida do robo <= VIDA_CURA1 para usar a cura1
    static int VIDA_CURA_2 = 200;		//vida do robo <= VIDA_CURA2 para usar a cura2
    static int VIDA_CURA_3 = 100;		//vida do robo <= VIDA_CURA3 para usar a cura3
    static int VIDA_INI_GRUA = 250;		//vida do inimigo > VIDA_INI_GRUA para usar o ataque de grua
    static int VIDA_INI_TOQUE = 100;	//vida do inimigo > VIDA_INI_TOQUE para usar o ataque de toque
    static int VIDA_INI_SOM = 0;		//vida do inimigo > VIDA_INI_SOM para usar o ataque de som

    //Construtor
    public Robo() 
    {
        vidaAtual = VIDAMAX;
        energAtual = ENERGMAX;
        posicaoAtual = 1;
    }

    //Getters
    //Retorna a energia atual
    public int getEnergDisponivel() 
    {
        return energAtual;
    }

    //Retorna a vida atual
    public int getVida() 
    {
        return vidaAtual;
    }

    //Retorna a posicao atual
    public int getPosicaoAtual() 
    {
        return posicaoAtual;
    }

    //Setters
    //Atualiza a energia atual
    public void setEnergAtual(int energAtual) 
    {
        this.energAtual = energAtual; 
    }

    //Atualiza a vida atual
    public void setVidaAtual(int vidaAtual) 
    {
        this.vidaAtual = vidaAtual;
    }

    //Atualiza a posicao atual
    public void setPosicaoAtual(int valor) 
    {
        posicaoAtual = valor;
    }

    //Escolhe o ataque a ser usado no inimigo passado como parametro
    public void escolheAtaque(Inimigo inimigo)
    {
        int energ_disp = getEnergDisponivel(); //Energia atual do robo

        if (vidaAtual > 0) //Se o robo estiver vivo
        {
        	//Consoante as variaveis de perfil
            if (inimigo.getVida() > VIDA_INI_GRUA && energ_disp > ENERGGRUA + ENERG_ATAQUE_RES)
            {
                ataqueGrua(inimigo); //Escolhe o ataque de grua
            } 
            else if (inimigo.getVida() > VIDA_INI_TOQUE && energ_disp > ENERGTOQUE + ENERG_ATAQUE_RES)
            {
                ataqueToque(inimigo); //Escolhe o ataque de toque
            } 
            else if (inimigo.getVida() > VIDA_INI_SOM && energ_disp > ENERGSOM + ENERG_ATAQUE_RES)
            {
                ataqueSom(inimigo); //Escolhe o ataque de som
            }
        }
    }

    //Escolhe o ataque a ser usado no inimigo passado como parametro e imprime informacao na consola
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

    //Usa o ataque de som
    public void ataqueSom(Inimigo inimigo)
	{
		if(energAtual - ENERGSOM >= 0) //Se tem energia suficiente para usar este ataque
		{
			energAtual -= ENERGSOM; //Retira a energia gasta
			inimigo.recebeDano(DANOSOM); //Inflige o dano no inimigo
		}
	}
	
    //Usa o ataque de toque
	public void ataqueToque(Inimigo inimigo)
	{
		if(energAtual - ENERGTOQUE >= 0) //Se tem energia suficiente para usar este ataque
		{
			energAtual -= ENERGTOQUE; //Retira a energia gasta
			inimigo.recebeDano(DANOTOQUE); //Inflige o dano no inimigo
		}
	}
	
	//Usa o ataque de grua
	public void ataqueGrua(Inimigo inimigo)
	{
		if(energAtual - ENERGGRUA >= 0) //Se tem energia suficiente para usar este ataque
		{
			energAtual -= ENERGGRUA; //Retira a energia gasta
			inimigo.recebeDano(DANOGRUA); //Inflige o dano no inimigo
		}
	}

    //Recebe o dano passado como parametro
    public void recebeDano(int valor) 
    {
        if (vidaAtual - valor < 0) //Se o robo ficar com vida negativa
        {
            vidaAtual = 0; //Coloca a vida a 0
        } 
        else 
        {
            vidaAtual -= valor; //Retira a vida perdida
        }
    }

    //Tenta usar uma cura
    public void curar()
    {
        if (vidaAtual > 0) //Se o robo estiver vivo
        {
            int energ_disp = getEnergDisponivel(); //Energia atual

            //Escolhe uma cura conforme o perfil atual
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

    //Tenta usar uma cura imprimindo informacao na consola
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
    
    //Recupera 50% da energia atual
    public void recuperaEnergia()
    {
        if (vidaAtual > 0) //Se o robo estiver vivo
        {
            if ((energAtual * 1.5) <= ENERGMAX) //Se nao exceder a energia maxima
            {
                energAtual = (int) Math.round(energAtual * 1.5); //Recupera 50% da energia atual
            }
            else
            {
                energAtual = ENERGMAX; //Coloca a energia igual a energia maxima
            }
        }
    }

    //Recupera 50% da energia atual imprimindo informacao na consola
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
    
    //Altera a estrategia
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
				break;
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
			case "base":
				ENERG_CURA_RES = 50;
				ENERG_ATAQUE_RES = 50;
				VIDA_CURA_3 = 100;
				VIDA_CURA_2 = 200;
				VIDA_CURA_1 = 300;
				VIDA_CURAR = 200;
				VIDA_INI_GRUA = 250;
				VIDA_INI_TOQUE = 100;
				VIDA_INI_SOM = 0;
				break;
		}
	}
	
    //Escolhe a melhor estrategia a usar no proximo turno
    public void escolheEstrategia()
	{
    	//Variaveis locais para simulacao do proximo turno usando determinado perfil (copia dos valores originais)
    	int vidaAtual = this.vidaAtual;
		int energAtual = this.energAtual;
		TreeMap<Integer, Inimigo>  inimigos_aux = copiaTreemap(Simulacao.inimigos);
		String perfil_escolhido = "base"; //Perfil escolhido
		estrategia("base"); //Aplica o perfil "base"
		
		//Simula o metodo decide jogada usando as variaveis locais
		//Curar
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
		else //Atacar
		{
			for(Inimigo inimigo : inimigos_aux.values()) //Para cada inimigo
			{
				if(inimigo.getId() == 1) //Tenta atacar primeiro as artilharias
				{
					if(Simulacao.turno != 12) //Se nao for o ultimo turno entao aplica as reservas de energia
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
					else //Se for nao reserva energia
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
			for(Inimigo inimigo : inimigos_aux.values()) //Tenta atacar todos os outros inimigos
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
		double score = calculaScore(inimigos_aux, vidaAtual, energAtual); //Calcula um score usando as variaveis locais resultantes da simulacao
		if(score <= 0) //Se o score for negativo (robo morre) entao volta a simular aplicando outro perfil
		{
			vidaAtual = this.vidaAtual;
			energAtual = this.energAtual;
			inimigos_aux = copiaTreemap(Simulacao.inimigos);
			estrategia("agressivo"); //Simula usando o perfil "agressivo"
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
			double score_aux = calculaScore(inimigos_aux, vidaAtual, energAtual); //Calcula o score usando o perfil agressivo
			if(score_aux > score) //Se for melhor que o previamente calculado fica com este
			{
				score = score_aux;
				perfil_escolhido = "agressivo"; //Indica que o perfil escolhido ate agora e o "agressivo"
			}

			
			vidaAtual = this.vidaAtual;
			energAtual = this.energAtual;
			inimigos_aux = copiaTreemap(Simulacao.inimigos);
			estrategia("equilibrado"); //Simula usando o perfil "equilibrado"
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
				perfil_escolhido = "equilibrado";
			}
			
			
			vidaAtual = this.vidaAtual;
			energAtual = this.energAtual;
			inimigos_aux = copiaTreemap(Simulacao.inimigos);
			estrategia("passivo"); //Simula usando o perfil "passivo"
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
			estrategia("sosom"); //Simula usando o perfil "sosom"
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
				perfil_escolhido = "sosom";
			}
		}
		
		if(score < 0 && energAtual >= 100) //Se nenhum dos perfis impedir que o robo morra e tiver pelo menos 100 energia
			estrategia("curamax"); //Aplica o perfil "curamax" com o objetivo de sobreviver recuperando vida
		else if(score < 0) //Se nao tiver pelo menos 100 energia
			estrategia("ataquemax"); //Aplica o perfil "ataquemax" tentando causar o maior dano possivel (recebendo menos dano na proxima defesa)
		else //Aplica o melhor perfil
			estrategia(perfil_escolhido);
	}
    
    //Escolhe a melhor estrategia a usar no proximo turno imprimindo informacao na consola
	public void escolheEstrategiaPrint()
	{
		int vidaAtual = this.vidaAtual;
		int energAtual = this.energAtual;
		TreeMap<Integer, Inimigo>  inimigos_aux = copiaTreemap(Simulacao.inimigos);
		String perfil_escolhido = "base";
		estrategia("base");
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
		double score = calculaScore(inimigos_aux, vidaAtual, energAtual);
		System.out.println("Perfil base: Score = " + score);
		if(score <= 0)
		{
			vidaAtual = this.vidaAtual;
			energAtual = this.energAtual;
			inimigos_aux = copiaTreemap(Simulacao.inimigos);
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
			double score_aux = calculaScore(inimigos_aux, vidaAtual, energAtual);
			if(score_aux > score)
			{
				score = score_aux;
				perfil_escolhido = "agressivo";
			}
			System.out.println("Perfil agressivo: Score = " + score_aux);
			
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
			score_aux = calculaScore(inimigos_aux, vidaAtual, energAtual);
			if(score_aux > score)
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
			if(score_aux > score)
			{
				score = score_aux;
				perfil_escolhido = "sosom";
			}
			System.out.println("Perfil sosom: Score = " + score_aux);
		}
		
		if(score < 0 && energAtual >= 100)
		{
			estrategia("curamax");
			System.out.println("Perfil curamax escolhido!\n");
		}
		else if(score < 0)
		{
			estrategia("ataquemax");
			System.out.println("Perfil ataquemax escolhido!\n");
		}
		else
		{
			estrategia(perfil_escolhido);
			System.out.println("Perfil " + perfil_escolhido + " escolhido com o score " + score + "!\n");
		}
	}
	
	//Calcula o score da proxima ronda usando determinado perfil e parametros passados
	public double calculaScore(TreeMap<Integer, Inimigo> inimigos, int vidaAtual, int energAtual)
	{
		//Variaveis locais
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
		
		score += p_vidaAtual * vidaAtual + p_energAtual * energAtual; //Adiciona ao score a vida e energia atuais multiplicadas pelos respetivos pesos
		
		for (Inimigo inimigo : inimigos.values()) //Para cada inimigo
		{
			if (inimigo.getVida() <= 0) //Se estiver morto
			{
				if (inimigo.getId() == 0) //Se for um tanque
					score += p_tan * 1000; //Adiciona ao score 1000 pontos multiplicados pelo peso do tanque
				else if (inimigo.getId() == 1) //Se for uma artilharia
					score += p_art * 1000; //Adiciona ao score 1000 pontos multiplicados pelo peso da artilharia
				else if (inimigo.getId() == 2) //Se for uma infantaria
					score += p_inf * 1000; //Adiciona ao score 1000 pontos multiplicados pelo peso da infantaria
			}
			else //Se estiver vivo
			{
				vidaTotalInimigos += inimigo.getVida(); //Adiciona a vida do inimigo ao total
				danoTotalInimigos += inimigo.getDano(); //Adiciona o dano do inimigo ao total
			}
		}
		score -= p_vidaTotalInimigos * vidaTotalInimigos - p_danoTotalInimigos * danoTotalInimigos; //Retira ao score a vida e dano totais multiplicados pelos respetivos pesos
		
		if (vidaAtual - danoTotalInimigos <= 0) //Se o robo morrer na proxima defesa
			score -= 10000; //Subtrai 10000 pontos, deixando o score negativo para indicar que morre
		return score;
	}
	
	//Cria uma copia do treemap passado como parametro
	public TreeMap<Integer, Inimigo> copiaTreemap(TreeMap<Integer, Inimigo> original)
	{
		TreeMap<Integer, Inimigo> copia = new TreeMap<>();
		int i = 1;
		for (Inimigo inimigo : original.values())
		{
			copia.put(i, new Inimigo(inimigo.getId(), true)); //Coloca os inimigos do treemap original
			copia.get(i).setVida(inimigo.getVida()); //Coloca a vida dos inimigos como as do treemap original
			i++;
		}
		return copia;
	}
}
