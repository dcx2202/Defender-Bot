package simulador;

//Imports
import java.io.IOException;
import java.util.Random;
import java.util.TreeMap;

public class Simulacao
{
	//Definicao e inicializacao de variaveis
	static int turno = 0; //Turno atual
	static int vitorias = 0; //Numero de vitorias
    static int derrotas = 0; //Numero de derrotas
    static TreeMap<Integer, Inimigo> inimigos = new TreeMap<Integer, Inimigo>(); //Guarda os inimigos detetados
    private static Robo robo = new Robo();
    static int i = 1;
    static int j = 0;
    static boolean jogoSimulado = false;
    static boolean jogoGanho = false;
    static String[] estrategia = new String[6];

    //Da reset as variaveis usadas em cada simulacao
    public static void resetVar()
    {
    	turno = 0;
    	j = 0;
    	jogoSimulado = false;
        jogoGanho = false;
        inimigos = new TreeMap<Integer, Inimigo>();
        preencheVazios();
        robo = new Robo();
    }
    
    //Preenche o treemap dos inimigos com inimigos vazios (id = 3)
	public static void preencheVazios()
	{
        for (int i = 1; i <= 6; i++)
        {
            inimigos.put(i, new Inimigo(0, false));
        }
    }
	
	//Preenche um array para simular um jogo aleatorio
	public static void preencheArray(int array[])
	{
        Random n = new Random();

        for (int i = 0; i < 6; i++) //Preenche um array com inteiros de 1 a 6 (sao usados como ids dos inimigos)
        {
            array[i] = n.nextInt(6) + 1;
        }
    }

	//Preenche arrays usados para simular um jogo
    public static void preencheDados()
    {
        preencheArray(Simulador.tipoInimigo); //Array com ids dos inimigos
        preencheArray(Simulador.turnoInimigo); //Array com os turnos em que sao colocados
    }
	
    //Comeca um novo jogo - simulacao de varios casos
    public static void novoJogo()
    {
    	//Reset a vida e energia do robo
        robo.setVidaAtual(750);
        robo.setEnergAtual(500);
        
        //Inicializacao dos arrays
        preencheVazios();
        preencheDados();

        turno = 1;
        j = 0;
        jogoSimulado = false;
        jogoGanho = false;

        //Turno 1 - espera que coloque os inimigos no tabuleiro para os detetar
        //-----------
        detetaInimigos(turno); //Deteta inimigos

        //Turno 2 - atacar/curar
        robo.recuperaEnergia(); //Recupera 50% da energia atual
        decideJogada(); //Decide se ataca ou cura

        //Turno 3 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        defender(); //Defende-se de cada um dos inimigos
        detetaInimigos(turno);

        //Turno 4 - atacar/curar
        robo.recuperaEnergia();
        decideJogada();

        //Turno 5 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        defender();
        detetaInimigos(turno);

        //Turno 6 - atacar/curar
        robo.recuperaEnergia();
        decideJogada();

        //Turno 7 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        defender();
        detetaInimigos(turno);

        //Turno 8 - atacar/curar
        robo.recuperaEnergia();
        decideJogada();

        //Turno 9 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        defender();
        detetaInimigos(turno);

        //Turno 10 - atacar/curar
        robo.recuperaEnergia();
        decideJogada();

        //Turno 11 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        defender();
        detetaInimigos(turno);

        //Turno 12 - atacar/curar
        robo.recuperaEnergia();
        decideJogada();

        //Turno 13 - defende-se pela ultima vez
        defender();

        if (robo.getVida() > 0) 
        {
            vitorias++;
        } 
        else 
        {
            derrotas++;
        }

        inimigos.clear(); //Reset ao treemap dos inimigos
    }
    
    //Comeca um novo jogo - simulacao de todos os casos
    public static void novoJogo(String str1, String str) throws IOException
	{
		//Turno 1
    	turno = 1;
    	j = 0;
    	jogoSimulado = false;
        jogoGanho = false;
    	
		//Turno 2
    	turno++; //Incrementa o turno
		robo.recuperaEnergia(); //Recupera 50% da energia atual
		registaInimigos(str1.toCharArray()[0], str); //Coloca no treemap os inimigos passados como parametro (do metodo geracombinacoes)
		decideJogada(); //Decide se ataca ou cura
		
		//Turno 3
		turno++;
		defender(); //Defende-se dos inimigos vivos
		
		//Turno 4
		turno++;
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[1], str);
		decideJogada();
		
		//Turno 5
		turno++;
		defender();
		
		//Turno 6
		turno++;
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[2], str);
		decideJogada();
		
		//Turno 7
		turno++;
		defender();
		
		//Turno 8
		turno++;
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[3], str);
		decideJogada();
		
		//Turno 9
		turno++;
		defender();
		
		//Turno 10
		turno++;
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[4], str);
		decideJogada();
		
		//Turno 11
		turno++;
		defender();
		
		//Turno 12
		turno++;
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[5], str);
		decideJogada();
		
		//Turno 13
		turno++;
		defender();
		fimJogo();
	}

    //Comeca um novo jogo - simulacao de um jogo aleatorio ou especifico
    public static void novoJogoPrint()
    {
        System.out.println("Tipo|Turno");
        for (int i = 0; i < 6; i++)
        {
            System.out.println("  " + Simulador.tipoInimigo[i] + " | " + Simulador.turnoInimigo[i]);
        }

        turno = 1;
        j = 0;
        jogoSimulado = false;
        jogoGanho = false;
        
        System.out.println("\n---------------" + turno + "---------------\n");

        //Turno 1 - espera que coloque os inimigos no tabuleiro para os detetar
        //-----------
        detetaInimigos(turno / 2 + 1);
        System.out.println(inimigos);

        //Turno 2 - atacar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        robo.recuperaEnergiaPrint();
        decideJogadaPrint();
        System.out.println(inimigos);

        //Turno 3 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        defenderPrint();
        detetaInimigos(turno / 2 + 1);
        System.out.println(inimigos);

        //Turno 4 - atacar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        robo.recuperaEnergiaPrint();
        decideJogadaPrint();
        System.out.println(inimigos);

        //Turno 5 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        defenderPrint();
        detetaInimigos(turno / 2 + 1);
        System.out.println(inimigos);

        //Turno 6 - atacar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        robo.recuperaEnergiaPrint();
        decideJogadaPrint();
        System.out.println(inimigos);

        //Turno 7 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        defenderPrint();
        detetaInimigos(turno / 2 + 1);
        System.out.println(inimigos);

        //Turno 8 - atacar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        robo.recuperaEnergiaPrint();
        decideJogadaPrint();
        System.out.println(inimigos);

        //Turno 9 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        defenderPrint();
        detetaInimigos(turno / 2 + 1);
        System.out.println(inimigos);

        //Turno 10 - atacar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        robo.recuperaEnergiaPrint();
        decideJogadaPrint();
        System.out.println(inimigos);

        //Turno 11 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        defenderPrint();
        detetaInimigos(turno / 2 + 1);
        System.out.println(inimigos);

        //Turno 12 - atacar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        robo.recuperaEnergiaPrint();
        decideJogadaPrint();
        System.out.println(inimigos);

        //Turno 13
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        defenderPrint();
        System.out.println(inimigos);

        if (robo.getVida() > 0)
        {
            System.out.println("\n\nVitória!\n\n");
        } 
        else
        {
            System.out.println("\n\nDerrota...\n\n");
        }
        System.out.println("Vida:    " + robo.getVida());
        System.out.println("Energia: " + robo.getEnergDisponivel());
    }
    
    //Tenta atacar cada inimigo
    public static void atacar()
    {
        for (Inimigo inimigo : inimigos.values()) //Primeiro tenta atacar as artilharias
        {
        	if(inimigo.getId() == 1) //Se for uma artilharia (id = 1)
        		robo.escolheAtaque(inimigo); //Escolhe um ataque
        }
        for (Inimigo inimigo : inimigos.values()) //Tenta atacar todos os outros inimigos
        {
        	if(inimigo.getId() != 1 && inimigo.getId() != 3) //Se nao for uma artilharia e nao for vazio
        		robo.escolheAtaque(inimigo);
        }
    }
    
    //Tenta atacar cada inimigo imprimindo informacao na consola
    public static void atacarPrint()
    {
    	for (Inimigo inimigo : inimigos.values())
        {
        	if(inimigo.getId() == 1)
        		robo.escolheAtaquePrint(inimigo); //Escolhe um ataque e imprime informacao sobre o mesmo
        }
        for (Inimigo inimigo : inimigos.values())
        {
        	if(inimigo.getId() != 1 && inimigo.getId() != 3)
        		robo.escolheAtaquePrint(inimigo);
        }
    }

    //Defende-se de cada inimigo
    public static void defender()
	{
		for(Inimigo inimigo : inimigos.values()) //Para cada inimigo
		{
			robo.recebeDano((int)inimigo.getDano()); //Recebe o dano que o inimigo inflige
		}
	}

    //Defende-se de cada inimigo imprimindo informacao na consola
    public static void defenderPrint()
    {
        for (Inimigo inimigo : inimigos.values()) //Para cada inimigo
        {
            robo.recebeDano((int) inimigo.getDano()); //Recebe o dano que o inimigo inflige
            System.out.println("Dano receb.: " + (int) inimigo.getDano()); //Imprime quanto dano recebeu
        }
    }

    //Coloca inimigos usando os ids do array de inimigos gerado, nos turnos indicados (no outro array gerado)
    public static void detetaInimigos(int turno)
    {
        for (int i = 0; i < 6; i++)
        {
            if (Simulador.turnoInimigo[i] == turno)
            {
                int j = 1;
                while (inimigos.get(j).getId() != 3)
                {
                    j++;
                }
                inimigos.put(j, new Inimigo(Simulador.tipoInimigo[i], false));
            }
        }
    }
    
    //Decide que perfil deve usar e se ataca ou cura
    public static void decideJogada()
    {
    	if(!jogoSimulado || !jogoGanho) //Se ainda nao tiver simulado este jogo ou se ainda nao tiver encontrado uma estrategia vencedora
    	{
	    	if(turno == 12) //Se for o ultimo turno do robo ataca o maximo que puder
	    		Robo.estrategia("ataquemax");
	    	else if(inimigos.get(6).getId() != 3 && !jogoSimulado) //Se forem detetados todos os inimigos e o jogo ainda nao tiver sido simulado
	    	{
	    		estrategia = robo.escolheEstrategiaJogo(); //Simula o resto do jogo atual
	    		if(jogoGanho) //Se houver uma estrategia vencedora
	    		{
	    			Robo.estrategia(estrategia[j]); //Aplica o perfil da estrategia vencedora para este turno
		    		j++;
	    		}
	    		else
	    			robo.escolheEstrategia(); //Escolhe uma estrategia atraves da simulacao do proximo turno
	    	}
	    	else
	    		robo.escolheEstrategia(); //Escolhe uma estrategia atraves da simulacao do proximo turno
    	}
    	else //Se este jogo ja tiver sido simulado e encontrada uma estrategia vencedora
    	{
    		Robo.estrategia(estrategia[j]); //Aplica o perfil da estrategia vencedora para este turno
    		j++;
    	}
        if (robo.getVida() < Robo.VIDA_CURAR) //Curar
        {
            robo.curar();
        } 
        else //Atacar
        {
            atacar();
        }
    }
    
    //Decide que perfil deve usar e se ataca ou cura, imprimindo informacao na consola
    public static void decideJogadaPrint()
    {
    	if(!jogoSimulado || !jogoGanho)
    	{
	    	if(turno == 12)
	    	{
	    		Robo.estrategia("ataquemax");
	    		System.out.println("Perfil ataquemax escolhido!\n");
	    	}
	    	else if(inimigos.get(6).getId() != 3 && !jogoSimulado)
	    	{
	    		estrategia = robo.escolheEstrategiaJogo();
	    		System.out.println("Todos os inimigos detetados, calculando a melhor estrategia...");
	    		if(jogoGanho)
	    		{
	    			System.out.println("Detetada estrategia vencedora! A usa-la ate ao final do jogo...");
	    			String out = "[";
	    			for(String string : estrategia)
	    				out += string + ", ";
	    			System.out.println(out.substring(0, out.length() - 2) + "]");
	    			System.out.println("Perfil escolhido: " + estrategia[j] + "\n");
	    			Robo.estrategia(estrategia[j]);
		    		j++;
	    		}
	    		else
	    		{
	    			System.out.println("Impossivel ganhar! Fazendo o melhor que posso...");
	    			robo.escolheEstrategiaPrint();
	    		}
	    	}
	    	else
	    		robo.escolheEstrategiaPrint();
    	}
    	else
    	{
    		Robo.estrategia(estrategia[j]);
    		System.out.println("Perfil escolhido: " + estrategia[j] + "\n");
    		j++;
    	}
        if (robo.getVida() < Robo.VIDA_CURAR)
        {
            System.out.println("Curar");
            robo.curarPrint();
        } 
        else
        {
            System.out.println("Atacar");
            atacarPrint();
        }
        System.out.println("Vida: " + robo.getVida());
        System.out.println("Ener: " + robo.getEnergDisponivel());
    }
    
    //Voltar ao inicio do tabuleiro a partir de qualquer ponto
    public void voltarInicio()
	{	
		robo.setPosicaoAtual(1); //Coloca a posicao do robo a 1
	}
	
    //Regista inimigos usando os parametros passados (quantos deverao ser colocados em determinado turno e quais)
	public static void registaInimigos(char turno, String string)
	{
		int aux = Integer.parseInt(turno + ""); //Quantos inimigos sao colocados neste turno
		if(inimigos.size() <= 6)
		{
			for(int l = 1 ; l <= aux ; l++) //Enquanto falta colocar inimigos
			{
				inimigos.put(i, new Inimigo(Integer.parseInt(string.toCharArray()[i-1] + ""), true)); //Coloca um inimigo com o id respetivo passado por "string"
				i++;
			}
		}
	}
	
	//Chamado no fim do jogo ou se o robo morreu
	public static void fimJogo() throws IOException
	{
		if(robo.getVida() == 0) //Se o robo morreu
		{
			derrotas++; //Incrementa o numero de derrotas
			Simulador.bw.write(Simulador.posicao + 1 + "\t-\tDerrota\tn." + String.format("%06d", derrotas) + "\t-\t" + Simulador.arr2.get(Simulador.posicao) + "\t-\t"); //Imprime no ficheiro da simulacao os dados do jogo
		}
		else //Se o jogo chegou ao fim (ultimo turno) e o robo nao morreu
		{
			vitorias++; //Incrementa o numero de vitorias
			Simulador.bw.write(Simulador.posicao + 1 + "\t-\tVitoria\tn." + String.format("%06d", vitorias) + "\t-\t" + Simulador.arr2.get(Simulador.posicao) + "\t-\t"); //Imprime no ficheiro da simulacao os dados do jogo
		}
		i = 1;
	}
}
