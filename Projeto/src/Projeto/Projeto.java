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
	static int turno = 0;
	
	
	//Main
	public static void main(String[] args)
	{
		LCD.setAutoRefresh(false);
		preencheVazios();
		robo.tocaSom("som3"); //"Voltando a posicao 1"
		voltarInicio();			//o robo volta ao inicio do tabuleiro
		turno = 0;
		novoJogo();				//chama o metodo "principal" do jogo
	}

	
	//Metodos de jogo
	public static void esperaToque()
	{
		while(!robo.detetaToque())
			espera(100);
		espera(500);
	}
	
	
	//Metodo chamado no fim do jogo
	public static void fimJogo()
	{
		if(robo.getVida() == 0)    //se a vida do robo for 0 quando o jogo tiver acabado
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
			robo.tocaSom("som11"); //"Vitoria! Fim do jogo."
			esperaToque();
			System.exit(0);
		}
	}
	
	
	//Metodo chamado no inicio de cada turno, dizendo o numero do turno a iniciar
	public static void novoTurno(int t)
	{
		robo.tocaSom("som23"); //"Turno"
		robo.tocaSom(t + ""); //"1, 2, 3, ..."
		turno = t;
	}
	
	
	//Metodo "principal" do jogo
	public static void novoJogo()
	{
		//Espera para comecar o jogo
		robo.tocaSom("som20"); //"Pressione o botao para comecar"
		esperaToque();		   //Fica em pausa ate ser o botao "lateral" do robo ser carregado
		dadosRobo();		   //Mostra os dados do robo no ecran (vida, energia, etc)
		robo.tocaSom("som10"); //"Comecando o jogo"
		
		
		//Turno 1 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(1); //"Turno 1"
		robo.tocaSom("som24"); //"Coloque os inimigos e pressione o botao para continuar"
		dadosRobo();
		esperaToque();
		
		//Turno 2 - atacar
		novoTurno(2); //"Turno 2"
		detetaInimigos();		//Robo percorre as posiÃ§oes dos inimigos, guardando o tipo de inimigo, ate encontrar uma posiÃ§ao vazia ou ja ter detetado todos os 6 inimigos
		dadosInimigos();		//Mostra os dados dos inimigos no ecran (tipo de inimigo, vida)
		espera(2000);
		dadosRobo();
		espera(1500);
		decideJogada();			//Decide se vai atacar ou curar
		dadosInimigos();		//Mostra os dados dos inimigos no ecran (tipo de inimigo, vida) apos serem atacados
		
		//Turno 3 - espera que coloque os inimigos no tabuleiro para os detetar
		novoTurno(3); //"Turno 3"
		defender();				//Efetua o calculo do dano proveniente dos inimigos e subtrai esse dano Ã  vida do robo
		robo.tocaSom("som24");  //"Coloque os inimigos e pressione o botao para continuar"
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
		if(robo.getPosicaoAtual() != 1)					//se o robo nao estiver na posiÃ§ao 1
		{
			robo.mover(-1, 400);						//robo move-se em direÃ§ao Ã  posiÃ§ao 1
			while(robo.detetaCor() != Color.WHITE)		//enquanto nao deteta a cor branca
				espera(20);
			robo.parar();								//robo pÃ¡ra
			espera(500);
			robo.mover(1, 200);						
			espera(800);
			robo.parar();
			robo.setPosicaoAtual(1);
		}
	}
	
	public static void detetaInimigos()	//Robo percorre as posiÃ§oes dos inimigos, guardando o tipo de inimigo, ate encontrar uma posiÃ§ao vazia ou ja ter detetado todos os 6 inimigos
	{
		if(inimigos.get(6).getId() == 3)	//se ainda nao tiverem sido detetados todos os 6 inimigos (posiÃ§ao 6 do array de inimigos estÃ¡ vazia)
		{
			robo.tocaSom("som16"); //"Detetando inimigos"
			while(robo.getPosicaoAtual() <= 6)		//enquanto nao chegar Ã  posiÃ§ao 6
			{
				if(inimigos.get(robo.getPosicaoAtual()).getId() == 3)	//se a posiÃ§ao atual do robo nao tiver inimigos anteriormente detetados
				{
					if(robo.detetaCor() == Color.BLUE)	//se nessa posiÃ§ao detetar azul
					{
						robo.tocaSom("som17");
						registaInimigo(robo.getPosicaoAtual(), new Inimigo(0));		//regista um tanque ao array de inimigos
					}
					else if(robo.detetaCor() == Color.BLACK)	//se nessa posiÃ§ao detetar preto
					{
						robo.tocaSom("som18");
						registaInimigo(robo.getPosicaoAtual(), new Inimigo(2));		//regista uma infantaria ao array de inimigos
					}
					else if(robo.detetaCor() == Color.YELLOW)	//se nessa posiÃ§ao detetar amarelo
					{
						robo.tocaSom("som19");
						registaInimigo(robo.getPosicaoAtual(), new Inimigo(1));		//regista uma artilharia ao array de inimigos
					}
					else if(robo.detetaCor() == Color.NONE)		//se nessa posiÃ§ao nao detetar nenhuma cor, acabou a deteÃ§ao de inimigos
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
	}
	
	public static void decideJogada()
	{
		boolean tudoCheioEMorto = true;
		if(inimigos.get(6).getId() != 3) //se ja foram detetados todos os inimigos
		{
			for(Inimigo inimigo : inimigos.values())	//para cada inimigo
			{
				if(inimigo.getVida() > 0)		//se o inimigo estiver vivo
				{
					tudoCheioEMorto = false;
					break;
				}
			}
			if(tudoCheioEMorto)		//se todos os inimigos estao mortos
				fimJogo();			//acaba o jogo
		}
		if(turno == 12)				//se o turno for o ultimo turno do robo (ultima possibilidade de ataque ou cura)
    		Robo.estrategia("ataquemax");	//o robo utiliza toda a sua energia no ataque
    	else if(inimigos.get(6).getId() != 3 && robo.getVida() >= 150)		//se ja tiverem sido detetados todos os inimigos e o robo tiver pelo menos 150 de vida
    		Robo.estrategia("sosom");	//o robo utiliza apenas ataques de som
    	else
    		robo.escolheEstrategia();	//o robo decide a melhor estrategia
		if(robo.getVida() < Robo.VIDA_CURAR)	//se a vida do robo for menor que o parametro VIDA_CURAR 
			robo.curar();		//o robo cura-se
		else
			atacar();			//o robo ataca
		
	}
	
	public static void atacar()
	{
		int posUltimoVivo = 0;
		
		for(Inimigo inimigo : inimigos.values())
		{
			if(inimigo.getVida() > 0)
				if(inimigo.posicao > posUltimoVivo)
					posUltimoVivo = inimigo.posicao;
		}
		
		robo.setPosicaoAtual(1);
		robo.tocaSom("som4"); //"Preparando-me para atacar"
		
		boolean artVivas = false;
		
		for(Inimigo inimigo : inimigos.values())
		{
			if(inimigo.getId() == 1 && inimigo.getVida() > 0)
			{
				artVivas = true;
				break;
			}
		}
		
		if(artVivas)
		{
			while(robo.getPosicaoAtual() <= posUltimoVivo)
			{
				dadosRobo();
				if(inimigos.get(robo.getPosicaoAtual()).getVida() > 0 && inimigos.get(robo.getPosicaoAtual()).getId() == 1)
					robo.escolheAtaque(inimigos.get(robo.getPosicaoAtual()));
				espera(500);
				if(robo.getPosicaoAtual() < posUltimoVivo)
				{
					int posPrimeiroVivo = -1;
					for(Inimigo inimigo : inimigos.values())
					{
						if(inimigo.getVida() > 0 && inimigo.posicao > robo.getPosicaoAtual())
						{
							posPrimeiroVivo = inimigo.posicao;
							break;
						}
					}
					robo.moverPos(1, posPrimeiroVivo - robo.getPosicaoAtual());
				}
				else
					break;
			}
			voltarInicio();
		}
		while(robo.getPosicaoAtual() <= posUltimoVivo)
		{
			dadosRobo();
			if(inimigos.get(robo.getPosicaoAtual()).getVida() > 0 && inimigos.get(robo.getPosicaoAtual()).getId() != 1)
				robo.escolheAtaque(inimigos.get(robo.getPosicaoAtual()));
			espera(500);
			if(robo.getPosicaoAtual() < posUltimoVivo)
			{
				int posPrimeiroVivo = -1;
				for(Inimigo inimigo : inimigos.values())
				{
					if(inimigo.getVida() > 0 && inimigo.posicao > robo.getPosicaoAtual())
					{
						posPrimeiroVivo = inimigo.posicao;
						break;
					}
				}
				robo.moverPos(1, posPrimeiroVivo - robo.getPosicaoAtual());
			}
			else
				break;
		}
		espera(500);
		voltarInicio();
	}
	
	public static void defender()		
	{
		robo.tocaSom("som7"); //"Preparando-me para defender"
		for(Inimigo inimigo : inimigos.values())	//para cada inimigo
		{
			if(inimigo.getVida() > 0)	//se o inimigo estiver vivo
			{
				Sound.beep();
				robo.recebeDano((int)inimigo.getDano());	//Efetua o calculo do dano proveniente dos inimigos e subtrai esse dano Ã  vida do robo
				dadosRobo();	//imprime os dados do robo		
				espera(1000);
			}
		}
	}
		
	
	//Outros
	public static void preencheVazios()		//preenche o array inimigos com vazios
	{
		for(int i = 1 ; i <= 6 ; i++)
		{
			registaInimigo(i, new Inimigo(3));
		}
	}
	
	public static void dadosRobo()		//Mostra os dados do robo (vida e energia)
	{
		limpaEcra();
		String out = "\n-------------";
		out += "\nSinais vitais";
		out +=  "\n-------------\n\n";
		out += "\nVida: " + robo.getVida() + " uv";
		out += "\nEnergia: " + robo.getEnergia() + " en";
		imprime(out);
	}
	
	public static void dadosInimigos()		//Mostra os dados dos inimigos no ecran (tipo de inimigo, vida)
	{
		limpaEcra();
		int n = 1;
		String out = "\n-------------";
		out += "\n   Inimigos   ";
		out += "\n-------------\n\n";
		
		for(Inimigo i : inimigos.values())		//para cada inimigo
		{
			out += "\n" + n + "-" + i.toString() + "-" + i.getVida();
			n++;
		}
		imprime(out);
	}
	
	public static void limpaEcra()		//limpa o ecra
	{
		LCD.clearDisplay();
		LCD.refresh();
	}
	
	public static void imprime(String string)	//imprime no ecra
	{
		System.out.println(string);
	}
	
	public static void registaInimigo(int posicao, Inimigo inimigo)		//adiciona inimigos ao array inimigos
	{
		inimigos.put(posicao, inimigo);
	}

	public static void espera(int ms)		//espera x tempo
	{
		Delay.msDelay(ms);
	}
}