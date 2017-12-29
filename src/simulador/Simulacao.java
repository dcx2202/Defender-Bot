package simulador;

import java.io.IOException;
import java.util.Random;
import java.util.TreeMap;

public class Simulacao
{
	static int turno = 0;
	static int vitorias = 0;
    static int derrotas = 0;
    static TreeMap<Integer, Inimigo> inimigos = new TreeMap<Integer, Inimigo>();
    private static Robo robo = new Robo();
    static int i = 1;

    public static void resetVar()
    {
    	turno = 0;
        inimigos = new TreeMap<Integer, Inimigo>();
        preencheVazios();
        robo = new Robo();
    }
    
	public static void preencheVazios()
	{
        for (int i = 1; i <= 6; i++)
        {
            inimigos.put(i, new Inimigo(0, false));
        }
    }
	
	 public static void preencheArray(int array[])
	 {
        Random n = new Random();

        for (int i = 0; i < 6; i++) 
        {
            array[i] = n.nextInt(6) + 1;
        }
    }

    public static void preencheDados()
    {
        preencheArray(Simulador.tipoInimigo);
        preencheArray(Simulador.turnoInimigo);
    }
	
    public static void novoJogo()
    {
        robo.setVidaAtual(750);
        robo.setEnergAtual(500);
        preencheVazios();
        preencheDados();

        turno = 1;

        //Turno 1 - espera que coloque os inimigos no tabuleiro para os detetar
        //-----------
        detetaInimigos(turno);

        //Turno 2 - atacar
        robo.recuperaEnergia();
        decideJogada();

        //Turno 3 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        defender();
        detetaInimigos(turno);

        //Turno 4 - atacar
        robo.recuperaEnergia();
        decideJogada();

        //Turno 5 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        defender();
        detetaInimigos(turno);

        //Turno 6 - atacar
        robo.recuperaEnergia();
        decideJogada();

        //Turno 7 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        defender();
        detetaInimigos(turno);

        //Turno 8 - atacar
        robo.recuperaEnergia();
        decideJogada();

        //Turno 9 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        defender();
        detetaInimigos(turno);

        //Turno 10 - atacar
        robo.recuperaEnergia();
        decideJogada();

        //Turno 11 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        defender();
        detetaInimigos(turno);

        //Turno 12 - atacar
        robo.recuperaEnergia();
        decideJogada();

        //Turno 13
        defender();

        if (robo.getVida() > 0) 
        {
            vitorias++;
        } 
        else 
        {
            derrotas++;
        }

        inimigos.clear();

        //System.out.println(robo.getVida() + " | " + robo.getEnergDisponivel());
    }
    
    public static void novoJogo(String str1, String str) throws IOException
	{
		//Turno 1
    	turno = 1;
    	
		//Turno 2
    	turno++;
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[0], str);
		decideJogada();
		
		//Turno 3
		turno++;
		defender();
		
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
    
    public static void novoJogoPrint()
    {
        System.out.println("Tipo|Turno");
        for (int i = 0; i < 6; i++)
        {
            System.out.println("  " + Simulador.tipoInimigo[i] + " | " + Simulador.turnoInimigo[i]);
        }

        turno = 1;
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
    
    public static void atacar()
    {
        for (Inimigo inimigo : inimigos.values())
        {
        	if(inimigo.getId() == 1)
        		robo.escolheAtaque(inimigo);
        }
        for (Inimigo inimigo : inimigos.values())
        {
        	if(inimigo.getId() != 1 && inimigo.getId() != 3)
        		robo.escolheAtaque(inimigo);
        }
    }
    
    public static void atacarPrint()
    {
    	for (Inimigo inimigo : inimigos.values())
        {
        	if(inimigo.getId() == 1)
        		robo.escolheAtaquePrint(inimigo);
        }
        for (Inimigo inimigo : inimigos.values())
        {
        	if(inimigo.getId() != 1 && inimigo.getId() != 3)
        		robo.escolheAtaquePrint(inimigo);
        }
    }
    
    public static void defender()
	{
		for(Inimigo inimigo : inimigos.values())
		{
			robo.recebeDano((int)inimigo.getDano());
		}
	}
    
    public static void defenderPrint()
    {
        for (Inimigo inimigo : inimigos.values()) 
        {
            robo.recebeDano((int) inimigo.getDano());
            System.out.println("Dano receb.: " + (int) inimigo.getDano());
        }
    }
    
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
    
    public static void decideJogada()
    {
    	if(turno == 12)
    		Robo.estrategia("ataquemax");
    	else
    		robo.escolheEstrategia();
        if (robo.getVida() < Robo.VIDA_CURAR)
        {
            robo.curar();
        } 
        else
        {
            atacar();
        }
    }
    
    public static void decideJogadaPrint()
    {
    	if(turno == 12)
    	{
    		Robo.estrategia("ataquemax");
    		System.out.println("Perfil ataquemax escolhido!\n");
    	}
    	else
    		robo.escolheEstrategiaPrint();
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
    
    public void voltarInicio() //Voltar ao inicio do tabuleiro a partir de qualquer ponto
	{	
		robo.setPosicaoAtual(1);
	}
	
	public static void registaInimigos(char turno, String string)
	{
		int aux = Integer.parseInt(turno + "");
		if(inimigos.size() <= 6)
		{
			for(int l = 1 ; l <= aux ; l++)
			{
				inimigos.put(i, new Inimigo(Integer.parseInt(string.toCharArray()[i-1] + ""), true));
				i++;
			}
		}
	}
	
	public static void fimJogo() throws IOException
	{
		if(robo.getVida() == 0)
		{
			derrotas++;
			Simulador.bw.write(Simulador.posicao + 1 + "\t-\tDerrota\tn." + String.format("%06d", derrotas) + "\t-\t" + Simulador.arr2.get(Simulador.posicao) + "\t-\t");
		}
		else
		{
			vitorias++;
			Simulador.bw.write(Simulador.posicao + 1 + "\t-\tVitoria\tn." + String.format("%06d", vitorias) + "\t-\t" + Simulador.arr2.get(Simulador.posicao) + "\t-\t");
		}
		i = 1;
	}
}
