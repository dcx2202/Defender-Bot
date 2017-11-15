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
		LCD.setAutoRefresh(false);
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
		espera(2000);
		dadosRobo();
		espera(1500);
		decideJogada();
		dadosInimigos();
		
		//Turno 3 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(3); //"Turno 3"
		defender();
		robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		dadosRobo();
		esperaToque();
		
		//Turno 4 - atacar
		novoTurno(4); //"Turno 4"
		robo.recuperaEnergia();
		detetaInimigos();
		dadosInimigos();
		espera(2000);
		dadosRobo();
		espera(1500);
		decideJogada();
		dadosInimigos();
		
		//Turno 5 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(5); //"Turno 5"
		defender();
		robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		dadosRobo();
		esperaToque();
		
		//Turno 6 - atacar
		novoTurno(6); //"Turno 6"
		robo.recuperaEnergia();
		detetaInimigos();
		dadosInimigos();
		espera(2000);
		dadosRobo();
		espera(1500);
		decideJogada();
		dadosInimigos();
		
		//Turno 7 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(7); //"Turno 7"
		defender();
		robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		dadosRobo();
		esperaToque();
		
		//Turno 8 - atacar
		novoTurno(8); //"Turno 8"
		robo.recuperaEnergia();
		detetaInimigos();
		dadosInimigos();
		espera(2000);
		dadosRobo();
		espera(1500);
		decideJogada();
		dadosInimigos();
		
		//Turno 9 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(9); //"Turno 9"
		defender();
		robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		dadosRobo();
		esperaToque();
		
		//Turno 10 - atacar
		novoTurno(10); //"Turno 10"
		robo.recuperaEnergia();
		detetaInimigos();
		dadosInimigos();
		espera(2000);
		dadosRobo();
		espera(1500);
		decideJogada();
		dadosInimigos();
		
		//Turno 11 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(11); //"Turno 11"
		defender();
		robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		dadosRobo();
		esperaToque();
		
		//Turno 12 - atacar
		novoTurno(12); //"Turno 12"
		robo.recuperaEnergia();
		detetaInimigos();
		dadosInimigos();
		espera(2000);
		dadosRobo();
		espera(1500);
		decideJogada();
		dadosInimigos();
		
		//Turno 13 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(13); //"Turno 13"
		defender();
		dadosInimigos();
		espera(2000);
		dadosRobo();
		espera(1500);
		fimJogo();
	}
	
	public static void voltarInicio() //Voltar ao inicio do tabuleiro a partir de qualquer ponto
	{	
		robo.tocaSom("som3"); //"Voltando a posicao 1"
		//Coluna coluna = new Coluna("som3");
		robo.mover(-1, 400);
		while(robo.detetaCor() != Color.WHITE)
			espera(20);
		robo.parar();
		//if(coluna.isAlive())
		//	coluna.interrupt();
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
					break;
			}
			if(robo.getPosicaoAtual() < 6)
				robo.moverPos(1, 1);
			else
				break;
		}
		robo.tocaSom("som2"); //"Detecao de inimigos concluida"
		voltarInicio();
	}
	
	public static void decideJogada()
	{
		if(robo.getVida() < 200)
			robo.curar();
		else
			atacar();
	}
	
	public static void atacar()
	{
		int posUltimoVivo = -1;
		for(Inimigo inimigo : inimigos.values())
		{
			if(inimigo.getVida() > 0)
				if(inimigo.posicao > posUltimoVivo)
					posUltimoVivo = inimigo.posicao;
		}
		robo.setPosicaoAtual(1);
		robo.tocaSom("som4"); //"Preparando-me para atacar"
		while(robo.getPosicaoAtual() <= posUltimoVivo)
		{
			dadosRobo();
			robo.escolheAtaque(inimigos.get(robo.getPosicaoAtual()));
			
			if(robo.getPosicaoAtual() < posUltimoVivo)
				robo.moverPos(1, 1);
			else
				break;
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
			robo.recebeDano((int)inimigo.getDano());
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
		String out = "\n-------------";
		out += "\nSinais vitais";
		out +=  "\n-------------\n\n";
		out += "\nVida: " + robo.getVida() + " uv";
		out += "\nEnergia: " + robo.getEnergia() + " en";
		imprime(out);
	}
	
	public static void dadosInimigos()
	{
		limpaEcra();
		int n = 1;
		String out = "\n-------------";
		out += "\n   Inimigos   ";
		out += "\n-------------\n\n";
		
		for(Inimigo i : inimigos.values())
		{
			out += "\n" + n + "-" + i.toString() + "-" + i.getVida();
			n++;
		}
		imprime(out);
	}
	
	public static void limpaEcra()
	{
		LCD.clear();
		LCD.refresh();
		espera(10);
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
