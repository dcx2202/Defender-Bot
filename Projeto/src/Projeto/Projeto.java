package Projeto;

import java.util.TreeMap;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class Projeto {

	//Criacao/Iniciacao de variaveis/objetos
	static Robo robo = new Robo();
	static TreeMap<Integer, Inimigo> inimigos = new TreeMap<>();
	
	//Main
	public static void main(String[] args)
	{
		preencheVazios();
		voltarInicio();
		novoJogo();
	}

	
	//Metodos de jogo
	public static void esperaToque()
	{
		while(!robo.detetaToque())
			espera(100);
		espera(500);
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
			robo.tocaSom("som9"); //"Vida a 0, fim do jogo."
			robo.tocaSom("som28");
			esperaToque();
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
			robo.tocaSom("som11"); //"Vitoria! Fim do jogo."
			robo.festejar();
			esperaToque();
			System.exit(0);
		}
	}
	
	public static void novoTurno(int turno)
	{
		robo.tocaSom("som23"); //"Turno"
		robo.tocaSom(turno + ""); //"1, 2, 3, ..."
	}
	
	public static void novoJogo()
	{
		//Espera para comecar o jogo
		robo.tocaSom("som20"); //"Pressione o botao para comecar"
		esperaToque();
		dadosRobo();
		robo.tocaSom("som10"); //"Comecando o jogo"
		
		
		//Turno 1 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(1); //"Turno 1"
		robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		dadosRobo();
		esperaToque();
		
		//Turno 2 - atacar
		novoTurno(2); //"Turno 2"
		robo.recuperaEnergia();
		detetaInimigos();
		dadosInimigos();
		espera(3000);
		dadosRobo();
		espera(3000);
		atacar();
		robo.curar();
		dadosInimigos();
		
		//Turno 3 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(3); //"Turno 3"
		robo.recuperaEnergia();
		defender();
		robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		dadosRobo();
		esperaToque();
		
		//Turno 4 - atacar
		novoTurno(4); //"Turno 2"
		robo.recuperaEnergia();
		detetaInimigos();
		dadosInimigos();
		espera(3000);
		dadosRobo();
		espera(3000);
		atacar();
		robo.curar();
		dadosInimigos();
		
		//Turno 5 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(5); //"Turno 3"
		robo.recuperaEnergia();
		defender();
		robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		dadosRobo();
		esperaToque();
		
		//Turno 6 - atacar
		novoTurno(6); //"Turno 2"
		robo.recuperaEnergia();
		detetaInimigos();
		dadosInimigos();
		espera(3000);
		dadosRobo();
		espera(3000);
		atacar();
		robo.curar();
		dadosInimigos();
		
		//Turno 7 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(7); //"Turno 3"
		robo.recuperaEnergia();
		defender();
		robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		dadosRobo();
		esperaToque();
		
		//Turno 8 - atacar
		novoTurno(8); //"Turno 2"
		robo.recuperaEnergia();
		detetaInimigos();
		dadosInimigos();
		espera(3000);
		dadosRobo();
		espera(3000);
		atacar();
		robo.curar();
		dadosInimigos();
		
		//Turno 9 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(9); //"Turno 3"
		robo.recuperaEnergia();
		defender();
		robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		dadosRobo();
		esperaToque();
		
		//Turno 10 - atacar
		novoTurno(10); //"Turno 2"
		robo.recuperaEnergia();
		detetaInimigos();
		dadosInimigos();
		espera(3000);
		dadosRobo();
		espera(3000);
		atacar();
		robo.curar();
		dadosInimigos();
		
		//Turno 11 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(11); //"Turno 3"
		robo.recuperaEnergia();
		defender();
		robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		dadosRobo();
		esperaToque();
		
		//Turno 12 - atacar
		novoTurno(12); //"Turno 2"
		robo.recuperaEnergia();
		detetaInimigos();
		dadosInimigos();
		espera(3000);
		dadosRobo();
		espera(3000);
		atacar();
		robo.curar();
		dadosInimigos();
		
		//Turno 13 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(13);
		defender();
		dadosInimigos();
		espera(3000);
		dadosRobo();
		espera(3000);
		fimJogo();
	}
	
	public static void voltarInicio() //Voltar ao inicio do tabuleiro a partir de qualquer ponto
	{	
		robo.tocaSom("som3"); //"Voltando a posicao 1"
		robo.mover(-1, 400);
		while(robo.detetaCor() != Color.WHITE)
			espera(20);
		robo.parar();
		espera(500);
		robo.mover(1, 200);
		espera(800);
		robo.parar();
		robo.setPosicaoAtual(1);
	}
	
	public static void detetaInimigos()
	{
		robo.tocaSom("som16"); //"Detetando inimigos"
		while(robo.getPosicaoAtual() <= 6)
		{
			if(inimigos.get(robo.getPosicaoAtual()).getId() == 3)
			{
				if(robo.detetaCor() == Color.BLUE)
				{
					robo.tocaSom("som17");
					registaInimigo(robo.getPosicaoAtual(), new Inimigo(0));
				}
				else if(robo.detetaCor() == Color.BLACK)
				{
					robo.tocaSom("som18");
					registaInimigo(robo.getPosicaoAtual(), new Inimigo(2));
				}
				else if(robo.detetaCor() == Color.YELLOW)
				{
					robo.tocaSom("som19");
					registaInimigo(robo.getPosicaoAtual(), new Inimigo(1));
				}
				else if(robo.detetaCor() == Color.NONE)
				{
					registaInimigo(robo.getPosicaoAtual(), new Inimigo(3));
				}
			}
			if(robo.getPosicaoAtual() < 6)
				robo.moverPos(1, 1);
			else
				robo.setPosicaoAtual(robo.getPosicaoAtual() + 1);
			espera(500);
		}
		robo.tocaSom("som2"); //"Detecao de inimigos concluida"
		voltarInicio();
	}
	
	public static void atacar()
	{
		
		robo.setPosicaoAtual(1);
		robo.tocaSom("som4"); //"Preparando-me para atacar"
		while(robo.getPosicaoAtual() <= 6)
		{
			dadosRobo();
			robo.escolheAtaque(inimigos.get(robo.getPosicaoAtual()));
			
			if(robo.getPosicaoAtual() < 6)
				robo.moverPos(1, 1);
			else
				robo.setPosicaoAtual(robo.getPosicaoAtual() + 1);
			espera(500);
		}
		voltarInicio();
	}
	
	public static void defender()
	{
		robo.tocaSom("som7"); //"Preparando-me para defender"
		for(Inimigo inimigo : inimigos.values())
		{
			Sound.beep();
			dadosRobo();
			espera(1000);
			robo.recebeDano(inimigo.getDano());
		}
	}
		
	
	//Outros
	public static void preencheVazios()
	{
		for(int i = 1 ; i <= 6 ; i++)
		{
			registaInimigo(i, new Inimigo(3));
		}
	}
	
	public static void dadosRobo()
	{
		limpaEcra();
		System.out.println("-------------");
		System.out.println("Sinais vitais");
		System.out.println("-------------\n\n");
		System.out.println("Vida: " + robo.getVida() + " uv");
		System.out.println("Energia: " + robo.getEnergia() + " en");
	}
	
	public static void dadosInimigos()
	{
		limpaEcra();
		int n = 1;
		System.out.println("-------------");
		System.out.println("   Inimigos   ");
		System.out.println("-------------\n\n");
		
		for(Inimigo i : inimigos.values())
		{
			System.out.println(n + "-" + i.toString() + "-" + i.getVida());
			n++;
		}
	}
	
	public static void limpaEcra()
	{
		LCD.clear();
	}
	
	public static void imprime(String string)
	{
		System.out.println(string);
	}
	
	public static void registaInimigo(int posicao, Inimigo inimigo)
	{
		inimigos.put(posicao, inimigo);
	}

	public static void espera(int ms)
	{
		Delay.msDelay(ms);
	}

	
}
