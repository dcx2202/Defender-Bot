package Projeto;

import java.io.File;
import java.util.Random;
import java.util.TreeMap;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.robotics.Color;

public class Projeto {

	//Criacao/Iniciacao de variaveis/objetos
	static Robo robo = new Robo();
	static TreeMap<Integer, Inimigo> inimigos = new TreeMap<>();
	
	//Main
	public static void main(String[] args)
	{
		voltarInicio();
		novoJogo();
	}

	
	//Metodos de jogo
	public static void esperaToque()
	{
		while(!robo.detetaToque())
			robo.espera(100);
	}
	
	public static void fimJogo()
	{
		if(robo.getVida() == 0)
		{
			limpaEcra();
			imprime("\n\n----------");
			imprime("Fim do jogo");
			imprime("----------\n");
			imprime("Vida acabou!\n");
			tocaSom("som9");
			robo.espera(5000);
			limpaEcra();
			System.exit(0);
		}
		else
		{
			limpaEcra();
			imprime("\n\n----------");
			imprime("Fim do jogo");
			imprime("----------\n");
			imprime("Vitoria!");
			imprime("Todos os inimigos foram abatidos.\n");
			tocaSom("som11");
			robo.espera(5000);
			limpaEcra();
			System.exit(0);
		}
	}
	
	public static void novoTurno(int turno)
	{
		tocaSom("som23"); //"Turno"
		tocaSom(turno + ""); //"1, 2, 3, ..."
		robo.espera(1000);
	}
	
	public static void novoJogo()
	{
		//Espera para comecar o jogo
		tocaSom("som20"); //"Pressione o botao para comecar"
		esperaToque();
		tocaSom("som10"); //"Comecando o jogo"
		
		
		//Turno 1 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(1); //"Turno 1"
		tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		esperaToque();
		detetaInimigos();
		
		
		//Turno 2 - atacar
		novoTurno(2); //"Turno 2"
		atacar();
		
		
		//Turno 3 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(3); //"Turno 3"
		tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		esperaToque();
		detetaInimigos();
		defender();
		
		
		//Turno 4 - atacar
		novoTurno(4);
		atacar();
		
		
		//Turno 5 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(5);
		tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		esperaToque();
		detetaInimigos();
		defender();
		
		
		//Turno 6 - atacar
		novoTurno(6);
		atacar();
		
		
		//Turno 7 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(7);
		tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		esperaToque();
		detetaInimigos();
		defender();
		
		
		//Turno 8 - atacar
		novoTurno(8);
		atacar();
		
		
		//Turno 9 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(9);
		tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		esperaToque();
		detetaInimigos();
		defender();
		
		
		//Turno 10 - atacar
		novoTurno(10);
		atacar();
		
		
		//Turno 11 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(11);
		tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		esperaToque();
		detetaInimigos();
		defender();
		
		
		//Turno 12 - atacar
		novoTurno(12);
		atacar();
		
		
		//Turno 13 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(13);
		tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		esperaToque();
		detetaInimigos();
		defender();
	}
	
	public static void voltarInicio() //Voltar ao inicio do tabuleiro a partir de qualquer ponto
	{	
		tocaSom("som3"); //"Voltando a posicao 1"
		robo.mover(-1, 400);
		while(robo.detetaCor() != Color.WHITE)
			robo.espera(50);
		robo.parar();
		robo.mover(1, 200);
		robo.espera(1);
		robo.parar();
		Robo.posicaoAtual = 1;
	}
	
	public static void detetaInimigos()
	{
		int artilharias = 0;
		int infantarias= 0;
		int tanques = 0;
		
		tocaSom("som16"); //"Detetando inimigos"
		while(Robo.posicaoAtual < 6)
		{
			robo.moverPos(1, 1);
			if(!inimigos.containsKey(Robo.posicaoAtual))
			{
				if(robo.detetaCor() == Color.BLUE)
				{
					imprimeInimigo("Tanque");
					inimigos.put(Robo.posicaoAtual, new Inimigo(0));
					tanques++;
				}
				else if(robo.detetaCor() == Color.GREEN)
				{
					imprimeInimigo("Infantaria");
					inimigos.put(Robo.posicaoAtual, new Inimigo(2));
					infantarias++;
				}
				else if(robo.detetaCor() == Color.YELLOW)
				{
					imprimeInimigo("Artilharia");
					inimigos.put(Robo.posicaoAtual, new Inimigo(1));
					artilharias++;
				}
			}
		}
		tocaSom("som2"); //"Detecao de inimigos concluida"
		imprimeInimigosDet(artilharias, infantarias, tanques);
		voltarInicio();
	}
	
	public static void atacar()
	{
		tocaSom("som4");
		for(Inimigo inimigo : inimigos.values())
		{
			informaAtaque(inimigo);
			robo.moverPos(1, inimigo.getPosicao());
			escolheAtaque(inimigo);
		}
		voltarInicio();
	}
	
	public static void defender()
	{
		int vidaPerdida = 0;
		tocaSom("som7");
		for(Inimigo inimigo : inimigos.values())
		{
			robo.recebeDano(inimigo.getDano());
			vidaPerdida += inimigo.getDano();
		}
		informaDano(vidaPerdida);
	}
	
	
	//Outros
	public static void informaDano(int vidaPerdida)
	{
		String numero = String.valueOf(vidaPerdida);
		char[] digitos = numero.toCharArray();
		int i = 0; 
		
		tocaSom("som12");
		while(i < digitos.length - 1)
		{
			tocaSom(digitos[i] + "");
			i++;
		}
	}
	
	public static void informaAtaque(Inimigo inimigo)
	{
		tocaSom("som21");
		if(inimigo.getId() == 0)
			tocaSom("som17");
		else if(inimigo.getId() == 1)
			tocaSom("som19");
		else if(inimigo.getId() == 2)
			tocaSom("som18");
		tocaSom("som22");
		tocaSom(Integer.toString(inimigo.getPosicao()));
	}
	
	public static void escolheAtaque(Inimigo inimigo)
	{
		Random rand = new Random();
		int num = rand.nextInt(3);
		
		if(num == 0) 
			robo.ataqueGrua(inimigo);
		else if(num == 1) 
			robo.ataqueToque(inimigo);
		else if(num == 2) 
			robo.ataqueSom(inimigo);
	}
	
	public static void tocaSom(String ficheiro)
	{
		Sound.playSample(new File("/home/root/" + ficheiro + ".wav"), 100);
		robo.espera(1000);
	}
	
	public static void limpaEcra()
	{
		LCD.clear();
	}
	
	public static void imprime(String string)
	{
		System.out.println(string);
	}
	
	public static void imprimeInimigo(String inimigo)
	{
		limpaEcra();
		System.out.println("\n\n------------");
		System.out.println(inimigo);
		System.out.println("------------");
	}
	
	public static void imprimeInimigosDet(int artilharias, int infantarias, int tanques)
	{
		limpaEcra();
		System.out.println("------------");
		System.out.println("Inimigos Detetados");
		System.out.println("------------/n/n");
		System.out.println("Num Artilharias: " + artilharias);
		System.out.println("Num Infantarias: " + infantarias);
		System.out.println("Num Tanques: " + tanques);
	}
	
	public static void registaInimigo(int posicao, Inimigo inimigo)
	{
		inimigos.put(posicao, inimigo);
	}


}
