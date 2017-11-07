package Projeto;

import java.util.TreeMap;
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
		//novoJogo();
		robo.moverPos(1, 3);
		robo.ataqueGrua(new Inimigo(0));
		espera(1000);
		robo.ataqueToque(new Inimigo(0));
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
			//robo.tocaSom("som9"); //"Vida a 0, fim do jogo."
			espera(5000);
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
			//robo.tocaSom("som11"); //"Vitoria, todos os inimigos abatidos! Fim do jogo."
			espera(5000);
			limpaEcra();
			System.exit(0);
		}
	}
	
	public static void novoTurno(int turno)
	{
		//robo.tocaSom("som23"); //"Turno"
		//robo.tocaSom(turno + ""); //"1, 2, 3, ..."
		espera(1000);
	}
	
	public static void novoJogo()
	{
		//Espera para comecar o jogo
		//robo.tocaSom("som20"); //"Pressione o botao para comecar"
		esperaToque();
		dadosRobo();
		//robo.tocaSom("som10"); //"Comecando o jogo"
		
		
		//Turno 1 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(1); //"Turno 1"
		//robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		esperaToque();
		detetaInimigos();
		robo.recuperaEnergia();
		dadosInimigos();
		esperaToque();
		dadosRobo();
		
		
		//Turno 2 - atacar
		novoTurno(2); //"Turno 2"
		atacar();
		robo.recuperaEnergia();
		dadosInimigos();
		esperaToque();
		dadosRobo();
		
		
		//Turno 3 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(3); //"Turno 3"
		//robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		esperaToque();
		detetaInimigos();
		defender();
		robo.recuperaEnergia();
		dadosInimigos();
		esperaToque();
		dadosRobo();
		
		
		//Turno 4 - atacar
		novoTurno(4);
		atacar();
		robo.recuperaEnergia();
		dadosInimigos();
		esperaToque();
		dadosRobo();
		
		
		//Turno 5 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(5);
		//robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		esperaToque();
		detetaInimigos();
		defender();
		robo.recuperaEnergia();
		dadosInimigos();
		esperaToque();
		dadosRobo();
		
		
		//Turno 6 - atacar
		novoTurno(6);
		atacar();
		robo.recuperaEnergia();
		dadosInimigos();
		esperaToque();
		dadosRobo();
		
		
		//Turno 7 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(7);
		//robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		esperaToque();
		detetaInimigos();
		defender();
		robo.recuperaEnergia();
		dadosInimigos();
		esperaToque();
		dadosRobo();
		
		
		//Turno 8 - atacar
		novoTurno(8);
		atacar();
		robo.recuperaEnergia();
		dadosInimigos();
		esperaToque();
		dadosRobo();
		
		
		//Turno 9 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(9);
		//robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		esperaToque();
		detetaInimigos();
		defender();
		robo.recuperaEnergia();
		dadosInimigos();
		esperaToque();
		dadosRobo();
		
		
		//Turno 10 - atacar
		novoTurno(10);
		atacar();
		robo.recuperaEnergia();
		dadosInimigos();
		esperaToque();
		dadosRobo();
		
		
		//Turno 11 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(11);
		//robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		esperaToque();
		detetaInimigos();
		defender();
		robo.recuperaEnergia();
		dadosInimigos();
		esperaToque();
		dadosRobo();
		
		
		//Turno 12 - atacar
		novoTurno(12);
		atacar();
		robo.recuperaEnergia();
		dadosInimigos();
		esperaToque();
		dadosRobo();
		
		
		//Turno 13 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(13);
		//robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		esperaToque();
		detetaInimigos();
		defender();
		robo.recuperaEnergia();
		dadosInimigos();
		esperaToque();
		dadosRobo();
	}
	
	public static void voltarInicio() //Voltar ao inicio do tabuleiro a partir de qualquer ponto
	{	
		//robo.tocaSom("som3"); //"Voltando a posicao 1"
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
		int artilharias = 0;
		int infantarias= 0;
		int tanques = 0;
		
		robo.setPosicaoAtual(1);
		
		//robo.tocaSom("som16"); //"Detetando inimigos"
		while(robo.getPosicaoAtual() <= 6)
		{
			imprime(robo.getPosicaoAtual() + "");
			if(inimigos.get(robo.getPosicaoAtual()).getId() == 3)
			{
				if(robo.detetaCor() == Color.BLUE)
				{
					imprimeInimigo("Tanque");
					inimigos.put(robo.getPosicaoAtual(), new Inimigo(0));
					tanques++;
				}
				else if(robo.detetaCor() == Color.BLACK)
				{
					imprimeInimigo("Infantaria");
					inimigos.put(robo.getPosicaoAtual(), new Inimigo(2));
					infantarias++;
				}
				else if(robo.detetaCor() == Color.YELLOW)
				{
					imprimeInimigo("Artilharia");
					inimigos.put(robo.getPosicaoAtual(), new Inimigo(1));
					artilharias++;
				}
				else if(robo.detetaCor() == Color.NONE)
				{
					imprimeInimigo("Vazio");
					inimigos.put(robo.getPosicaoAtual(), new Inimigo(3));
				}
			}
			if(robo.getPosicaoAtual() < 6)
				robo.moverPos(1, 1);
			else
				robo.setPosicaoAtual(robo.getPosicaoAtual() + 1);
			espera(500);
		}
		//robo.tocaSom("som2"); //"Detecao de inimigos concluida"
		//informaInimigosDet(artilharias, infantarias, tanques);
		voltarInicio();
	}
	
	public static void atacar()
	{
		//robo.tocaSom("som4"); //"Preparando-me para atacar"
		for(Inimigo inimigo : inimigos.values())
		{
			if(inimigo.getId() != 3)
			{
				if(robo.getEnergDisponivel() >= robo.getEnergSom())
				{
					informaAtaque(inimigo);
					robo.moverPos(1, inimigo.getPosicao());
					escolheAtaque(inimigo);
				}
			}
		}
		robo.curar();
		//voltarInicio();
	}
	
	public static void defender()
	{
		int vidaPerdida = 0;
		//robo.tocaSom("som7"); //"Preparando-me para defender"
		for(Inimigo inimigo : inimigos.values())
		{
			robo.recebeDano(inimigo.getDano());
			vidaPerdida += inimigo.getDano();
		}
		informaDano(vidaPerdida);
	}
		
	
	//Outros
	public static void preencheVazios()
	{
		for(int i = 1 ; i <= 6 ; i++)
		{
			inimigos.put(i, new Inimigo(3));
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
	
	public static void informaDano(int vidaPerdida)
	{
		String numero = String.valueOf(vidaPerdida);
		char[] digitos = numero.toCharArray();
		int i = 0; 
		
		//robo.tocaSom("som12"); //"Vida perdida"
		while(i < digitos.length - 1)
		{
			//robo.tocaSom(digitos[i] + ""); //"1, 2, 3, ..."
			i++;
		}
	}
	
	public static void informaAtaque(Inimigo inimigo)
	{
		//robo.tocaSom("som21"); //"Atacando"
		//if(inimigo.getId() == 0)
			//robo.tocaSom("som17"); //"Tanque"
		//else if(inimigo.getId() == 1)
			//robo.tocaSom("som19"); //"Artilharia"
		//else if(inimigo.getId() == 2)
			//robo.tocaSom("som18"); //"Infantaria"
		//robo.tocaSom("som22"); //"na posicao"
		//robo.tocaSom(Integer.toString(inimigo.getPosicao())); //"1, 2, 3, ..."
	}
	
	public static void informaInimigosDet(int artilharias, int infantarias, int tanques)
	{
		//robo.tocaSom("som25"); //"Foram detetados"
		espera(1000);
		//robo.tocaSom(artilharias + ""); //"1, 2, 3, ..."
		//robo.tocaSom("som19"); //"Artilharia"
		espera(1000);
		//robo.tocaSom(infantarias + ""); //"1, 2, 3, ..."
		//robo.tocaSom("som18"); //"Infantaria"
		espera(1000);
		//robo.tocaSom(tanques + ""); //"1, 2, 3, ..."
		//robo.tocaSom("som17"); //"Tanque"
		espera(1000);
	}
	
	public static void escolheAtaque(Inimigo inimigo)
	{
		//Random rand = new Random();
		//int num = rand.nextInt(3);
		int energ_disp = robo.getEnergDisponivel();
		
		if(/*num == 0 &&*/ energ_disp >= robo.getEnergGrua())
			robo.ataqueGrua(inimigo);
		else if(/*num == 1 &&*/ energ_disp >= robo.getEnergToque())
			robo.ataqueToque(inimigo);
		else if(/*num == 2 &&*/ energ_disp >= robo.getEnergSom())
			robo.ataqueSom(inimigo);
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
	
	public static void registaInimigo(int posicao, Inimigo inimigo)
	{
		inimigos.put(posicao, inimigo);
	}

	public static void espera(int ms)
	{
		Delay.msDelay(ms);
	}

	
}
