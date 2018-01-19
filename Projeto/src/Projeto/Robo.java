package Projeto;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;
import Projeto.Robo;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;

public class Robo
{
	
	//Criacao/Iniciacao de variaveis/objetos
	private static final int VIDAMAX = 750;			//vida maxima do robo
	private static final int ENERGMAX = 500;			//energia maxima do robo
	private static final int CURA1 = 100;				//vida recuperada com a cura 1
	private static final int CURA2 = 200;				//vida recuperada com a cura 2
	private static final int CURA3 = 400;				//vida recuperada com a cura 3
	private static final int ENERGCURA1 = 100;		//energia perdida com a cura 1
	private static final int ENERGCURA2 = 200;		//energia perdida com a cura 2
	private static final int ENERGCURA3 = 400;		//energia perdida com a cura 3
	private static final int ENERGGRUA = 300;			//energia perdida o ataque de grua
	private static final int ENERGSOM = 50;			//energia perdida o ataque de som
	private static final int ENERGTOQUE = 150;		//energia perdida o ataque de toque
	private static final int DANOGRUA = 200;			//dano dado ao inimigo com o ataque de grua
	private static final int DANOSOM = 50;			//dano dado ao inimigo com o ataque de som
	private static final int DANOTOQUE = 100;			//dano dado ao inimigo com o ataque de toque
	
	private static int ENERG_CURA_RES = 50;	 		//energia de reserva ao curar
	private static int ENERG_ATAQUE_RES = 50;  		//energia de reserva ao atacar
	private static int VIDA_CURA_3 = 300;				//vida do robo <= VIDA_CURA3 para usar a cura3
	private static int VIDA_CURA_2 = 200;				//vida do robo <= VIDA_CURA2 para usar a cura2
	private static int VIDA_CURA_1 = 100;				//vida do robo <= VIDA_CURA1 para usar a cura1
	static int VIDA_CURAR = 200;						//se a vida do robo < VIDA_CURAR entao cura
	static int VIDA_INI_GRUA = 150;						//vida do inimigo > VIDA_INI_GRUA para usar o ataque de grua
    static int VIDA_INI_TOQUE = 50;						//vida do inimigo > VIDA_INI_TOQUE para usar o ataque de toque
    static int VIDA_INI_SOM = 0;						//vida do inimigo > VIDA_INI_SOM para usar o ataque de som
	
	private int energAtual;
	private int vidaAtual;
	static int posicaoAtual;
	private static final Port s1 = LocalEV3.get().getPort("S1");
	private static final EV3ColorSensor sensorCor = new EV3ColorSensor(s1);
	private static final Port s2 = LocalEV3.get().getPort("S2");
	private static final EV3TouchSensor sensorToque = new EV3TouchSensor(s2);
	private static final SampleProvider obtemAmostra = sensorToque.getTouchMode();
	private static float[] amostra = new float[obtemAmostra.sampleSize()];
	
	//Variaveis para as simulacoes
    int a_vidaAtual = vidaAtual;
	int a_energAtual = energAtual;
	TreeMap<Integer, Inimigo> a_inimigos = copiaTreemap(Projeto.inimigos);
	
	
	//Construtor
	public Robo()
	{
		vidaAtual = VIDAMAX;
		energAtual = ENERGMAX;
		posicaoAtual = -1;
	}
	
	
	//Movimento
	public void mover(int direcao, int graus) // (1 -> frente  ;  -1 -> tras  ;  graus/s)
	{
		Motor.A.setSpeed(graus);
		Motor.B.setSpeed(graus);
		if(direcao == 1)
		{	
			//para ambos os motores em simultaneo
			Motor.A.forward();
			Motor.B.forward();
		}
		else if (direcao == -1)
		{
			//para ambos os motores em simultaneo
			Motor.A.backward();
			Motor.B.backward();
		}
	}
	
	public void moverPos(int direcao, int numPosicoes)		//movimenta o robo para a direÃ§ao desejada e um numero especifico de posiÃ§oes
	{
		int pos = numPosicoes;
		while(pos > 0)		//enquanto a posiÃ§ao for maior que a posiÃ§ao 0
		{
			mover(direcao, 200);
			while(detetaCor() != Color.RED)		//enquanto nao detetar a cor vermelha
				Projeto.espera(50);
			Projeto.espera(750);
			pos--;
		}
		parar();
		Projeto.espera(500);
		posicaoAtual = posicaoAtual + (direcao * numPosicoes);
	}
	
	public void parar() //Para ambos os motores simultaneamente
	{
		Motor.A.stop(true);
		Motor.B.stop(true);
	}
	
	
	//Sensores
	public int detetaCor() //Retorna o id da cor detetada (0 se nao detetar nenhuma)
	{
		return sensorCor.getColorID();
	}
	
	public boolean detetaToque()
	{
		obtemAmostra.fetchSample(amostra, 0);
		return amostra[0] != 0;
	}
	
	
	//Getters
	public int getEnergDisponivel()
	{
		return energAtual;
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
	
	
	//Escolhe o ataque a ser usado no inimigo passado como parametro
	public void escolheAtaque(Inimigo inimigo)
	{
		if(Projeto.turno != 12)		//se nao estiver no turno 12 (ultimo turno do robo)
		{
			//Consoante as variaveis de perfil
			if(inimigo.getVida() > VIDA_INI_GRUA && energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
				ataqueGrua(inimigo);	//Escolhe o ataque de grua
			else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
				ataqueToque(inimigo);	//Escolhe o ataque de toque
			else if(inimigo.getVida() > VIDA_INI_SOM && energAtual > ENERGSOM + ENERG_ATAQUE_RES)
				ataqueSom(inimigo);		//Escolhe o ataque de som
			if(inimigo.getVida() <= 0)	//se o inimigo tiver morrido
				tocaSom("som8");		//"Inimigo abatido"
			}
		else	//se estiver no turno 12 (ultimo turno do robo)
		{
			//Consoante as variaveis de perfil
			if(inimigo.getVida() > VIDA_INI_GRUA && energAtual >= ENERGGRUA)
				ataqueGrua(inimigo);	//Escolhe o ataque de grua
			else if(inimigo.getVida() > VIDA_INI_TOQUE && energAtual >= ENERGTOQUE)
				ataqueToque(inimigo);	//Escolhe o ataque de toque
			else if(inimigo.getVida() > VIDA_INI_SOM && energAtual >= ENERGSOM)
				ataqueSom(inimigo);		//Escolhe o ataque de som
			if(inimigo.getVida() <= 0)	//se o inimigo tiver morrido
				tocaSom("som8");		//"Inimigo abatido"
		}
	}
	
	public void ataqueSom(Inimigo inimigo)
	{
		energAtual -= ENERGSOM;			//retira a energia gasta Ã  energia do robo
		tocaSom("som27"); //"PewPew (laser)"
		inimigo.recebeDano(DANOSOM);	//inimigo recebe o dano do ataque
	}
	
	public void ataqueToque(Inimigo inimigo)
	{	
		energAtual -= ENERGTOQUE;		//retira a energia gasta Ã  energia do robo
		inimigo.recebeDano(DANOTOQUE);	//inimigo recebe o dano do ataque
		
		//Motor C realiza o ataque de toque
		int vel = Motor.C.getSpeed();
		mover(-1, 300);
		Projeto.espera(750);
		parar();
		Motor.C.resetTachoCount();
		Motor.C.rotate(-30);
		Projeto.espera(200);
		Motor.C.setSpeed(60);
		Motor.C.resetTachoCount();
		Motor.C.rotate(29);
		Projeto.espera(200);
		mover(1, 300);
		Projeto.espera(750);
		parar();
		Motor.C.setSpeed(vel);
	}
	
	public void ataqueGrua(Inimigo inimigo)
	{
		energAtual -= ENERGGRUA;		//retira a energia gasta Ã  energia do robo
		inimigo.recebeDano(DANOGRUA);	//inimigo recebe o dano do ataque
		
		//Motor C realiza o ataque de grua
		int vel = Motor.C.getSpeed();
		Motor.C.resetTachoCount();
		Motor.C.rotate(47);
		Projeto.espera(200);
		Motor.C.resetTachoCount();
		Motor.C.setSpeed(47);
		Motor.C.rotate(-47);
		Motor.C.setSpeed(vel);
	}
	
	
	//Recebe o dano passado como parametro
	public void recebeDano(int valor)
	{
		if(vidaAtual-valor < 0)		//Se o robo ficar com vida negativa
		{
			vidaAtual = 0;			//Coloca a vida a 0
			Projeto.fimJogo();		//Acaba o jogo (Derrota)
		}
		else
			vidaAtual -=valor;		//Retira a vida perdida
	}
	
	
	//Tenta usar uma cura
	public void curar()
	{
		//Escolhe uma cura conforme o perfil atual
		if(energAtual >= ENERGCURA3 + ENERG_CURA_RES && getVida() <= VIDA_CURA_3)
		{
			vidaAtual += CURA3;			//adiciona a vida da cura Ã  vida do robo
			energAtual -= ENERGCURA3;	//retira a energia gasta pela cura
			tocaSom("som6");
		}
		else if(energAtual >= ENERGCURA2 + ENERG_CURA_RES && getVida() <= VIDA_CURA_2)
		{
			vidaAtual += CURA2;			//adiciona a vida da cura Ã  vida do robo
			energAtual -= ENERGCURA2;	//retira a energia gasta pela cura
			tocaSom("som6");
		}
		else if(energAtual >= ENERGCURA1 + ENERG_CURA_RES && getVida() <= VIDA_CURA_1)
		{
			vidaAtual += CURA1;			//adiciona a vida da cura Ã  vida do robo
			energAtual -= ENERGCURA1;	//retira a energia gasta pela cura
			tocaSom("som6");
		}
	}
	
	//Outros
	public void tocaSom(String ficheiro)
	{
		Sound.playSample(new File("/home/root/" + ficheiro + ".wav"), 100);
	}
	
	public void recuperaEnergia()
	{
		tocaSom("som5"); //"Recuperando energia"
		if((energAtual * 1.5) <= ENERGMAX)						//Se nao exceder a energia maxima
			energAtual = (int) Math.round(energAtual * 1.5);	//Recupera 50% da energia atual
		else													
			energAtual = ENERGMAX;								//Coloca a energia igual a energia maxima
		Projeto.dadosRobo();		//imprime os dados do robo
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
			case "nada": //Impede de jogar
				ENERG_CURA_RES = 1000; //Impede de curar
				ENERG_ATAQUE_RES = 1000; //Impede de atacar
				VIDA_CURA_3 = 0;
				VIDA_CURA_2 = 0;
				VIDA_CURA_1 = 0;
				VIDA_CURAR = 0;
				VIDA_INI_GRUA = 0;
				VIDA_INI_TOQUE = 0;
				VIDA_INI_SOM = 0;
				break;
		}
	}
	
	
	//Escolhe a melhor estrategia a usar no proximo turno
	public void escolheEstrategia()
	{
		//Variaveis locais para simulacao do proximo turno usando determinado perfil (copia dos valores originais)
    	a_vidaAtual = this.vidaAtual;
		a_energAtual = this.energAtual;
		a_inimigos = copiaTreemap(Projeto.inimigos);
		String perfil_escolhido = "base"; //Perfil escolhido
		estrategia("base"); //Aplica o perfil "base"
		
		//Simula o metodo decide jogada usando as variaveis locais
		//Curar
		double score = simulaTurno();
		if(score <= 0) //Se o score for negativo (robo morre) entao volta a simular aplicando outro perfil
		{
			a_vidaAtual = this.vidaAtual;
			a_energAtual = this.energAtual;
			a_inimigos = copiaTreemap(Projeto.inimigos);
			estrategia("agressivo"); //Simula usando o perfil "agressivo"
			double score_aux = simulaTurno();
			if(score_aux > score) //Se for melhor que o previamente calculado fica com este
			{
				score = score_aux;
				perfil_escolhido = "agressivo"; //Indica que o perfil escolhido ate agora e o "agressivo"
			}
			
			a_vidaAtual = this.vidaAtual;
			a_energAtual = this.energAtual;
			a_inimigos = copiaTreemap(Projeto.inimigos);
			estrategia("equilibrado"); //Simula usando o perfil "equilibrado"
			score_aux = simulaTurno();
			if(score_aux > score)
			{
				score = score_aux;
				perfil_escolhido = "equilibrado";
			}
						
			a_vidaAtual = this.vidaAtual;
			a_energAtual = this.energAtual;
			a_inimigos = copiaTreemap(Projeto.inimigos);
			estrategia("passivo"); //Simula usando o perfil "passivo"
			score_aux = simulaTurno();
			if(score_aux > score)
			{
				score = score_aux;
				perfil_escolhido = "passivo";
			}
			
			a_vidaAtual = this.vidaAtual;
			a_energAtual = this.energAtual;
			a_inimigos = copiaTreemap(Projeto.inimigos);
			estrategia("sosom"); //Simula usando o perfil "sosom"
			score_aux = simulaTurno();
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
		
		
		
		score += p_vidaAtual * vidaAtual + p_energAtual * energAtual;	//Adiciona ao score a vida e energia atuais multiplicadas pelos respetivos pesos
		
		for (Inimigo inimigo : inimigos.values())	//Para cada inimigo
		{
			if (inimigo.getVida() <= 0)		//Se estiver morto
			{
				if (inimigo.getId() == 0) //se for um tanque
					score += p_tan * 1000;		//Adiciona ao score 1000 pontos multiplicados pelo peso do tanque
				else if (inimigo.getId() == 1)  //se for uma artilharia
					score += p_art * 1000;		//Adiciona ao score 1000 pontos multiplicados pelo peso da artilharia
				else if (inimigo.getId() == 2)  //se for uma infantaria
					score += p_inf * 1000;		//Adiciona ao score 1000 pontos multiplicados pelo peso da infantaria
			}
			else	//Se ele estiver vivo
			{
				vidaTotalInimigos += inimigo.getVida(); //Adiciona a vida do inimigo ao total
                danoTotalInimigos += inimigo.getDano(); //Adiciona o dano do inimigo ao total
			}
		}
		score -= p_vidaTotalInimigos * vidaTotalInimigos - p_danoTotalInimigos * danoTotalInimigos;	 //Retira ao score a vida e dano totais multiplicados pelos respetivos pesos
		
		if (vidaAtual - danoTotalInimigos <= 0)		//Se o robo morrer na proxima defesa
			score -= 1000000;		//Subtrai 10000 pontos, deixando o score negativo para indicar que morre
		return score;	
	}
	
	//Simula o restante do jogo e se possivel ganhar retorna uma estrategia vencedora
	public String[] escolheEstrategiaJogo()
	{
		//Copia os valores originais para as variaveis a serem usadas nas simulacoes
		a_vidaAtual = vidaAtual;
		a_energAtual = energAtual;
		a_inimigos = copiaTreemap(Projeto.inimigos);
		
		//Variaveis locais
		double scoreJogo = 0; //Score das simulacoes dos turnos restantes do robo
		int numRondas = calculaNumRondas(); //Numero de turnos do robo
		String estrategia[] = new String[numRondas]; //Array de strings que ira guardar os perfis da estrategia vencedora
		ArrayList<String> jogos = new ArrayList<>(); //ArrayList que guarda as combinacoes de perfis a testar
		ArrayList<Integer> aux = new ArrayList<>(); //ArrayList usado para gerar as combinacoes
		//Valores usados para gerar as combinacoes
		aux.add(1);
		aux.add(2);
		aux.add(3);
		aux.add(4);
		aux.add(5);
		jogos = geraCombinacoes(calculaNumRondas(), aux); //Gera as combinacoes de perfis
		
		for(String perfis : jogos) //Para cada combinacao
		{
			scoreJogo = simulaJogo(perfis, numRondas); //Simula o jogo
			if(scoreJogo > 0) //Se o score for positivo entao a estrategia indicada por "perfis" e vencedora
			{
				int i = 0;
				while(i < perfis.length()) //Coloca os perfis a usar no array estrategia
				{
					switch (Integer.parseInt(perfis.toCharArray()[i] + "")) 
					{
						case 1:
							estrategia[i] = "base";
							break;
						case 2:
							estrategia[i] = "sosom";
							break;
						case 3:
							estrategia[i] = "ataquemax";
							break;
						case 4:
							estrategia[i] = "curamax";
							break;
						case 5:
							estrategia[i] = "nada";
							break;
						default:
							break;
					}
					i++;
				}
				//Jogo ja foi simulado e encontrada uma estrategia vencedora
				Projeto.jogoSimulado = true;
				Projeto.jogoGanho = true; 
				break;
			}
			else //Se esta estrategia nao e vencedora da reset as variaveis de simulacao
			{
				scoreJogo = 0;
				a_vidaAtual = vidaAtual;
				a_energAtual = energAtual;
				a_inimigos = copiaTreemap(Projeto.inimigos);
			}
		}
		Projeto.jogoSimulado = true; //Indica que este jogo ja foi simulado
		
		//Reset as variaveis de simulacao
		a_vidaAtual = vidaAtual;
		a_energAtual = energAtual;
		a_inimigos = copiaTreemap(Projeto.inimigos);
		return estrategia;
	}
	
	//Simula um turno do robo usando variaveis auxiliares de simulacao
	public double simulaTurno()
	{
		//Curar
		if(a_vidaAtual < Robo.VIDA_CURAR)
		{
			if(a_energAtual >= ENERGCURA3 + ENERG_CURA_RES && a_vidaAtual <= VIDA_CURA_3)
			{
				a_vidaAtual += CURA3;
				a_energAtual -= ENERGCURA3;
			}
			else if(a_energAtual >= ENERGCURA2 + ENERG_CURA_RES && a_vidaAtual <= VIDA_CURA_2)
			{
				a_vidaAtual += CURA2;
				a_energAtual -= ENERGCURA2;
			}
			else if(a_energAtual >= ENERGCURA1 + ENERG_CURA_RES && a_vidaAtual <= VIDA_CURA_1)
			{
				a_vidaAtual += CURA1;
				a_energAtual -= ENERGCURA1;
			}
		}
		else //Atacar
		{
			for(Inimigo inimigo : a_inimigos.values())
			{
				if(inimigo.getId() == 1) //Tenta atacar primeiro as artilharias
				{
					if(Projeto.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && a_energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							a_energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && a_energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							a_energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && a_energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							a_energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && a_energAtual >= ENERGGRUA)
						{
							a_energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && a_energAtual >= ENERGTOQUE)
						{
							a_energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && a_energAtual >= ENERGSOM)
						{
							a_energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
			for(Inimigo inimigo : a_inimigos.values())
			{
				if(inimigo.getId() != 1 && inimigo.getId() != 3) //Tenta atacar os restantes inimigos
				{
					if(Projeto.turno != 12)
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && a_energAtual > ENERGGRUA + ENERG_ATAQUE_RES)
						{
							a_energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && a_energAtual > ENERGTOQUE + ENERG_ATAQUE_RES)
						{
							a_energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && a_energAtual > ENERGSOM + ENERG_ATAQUE_RES)
						{
							a_energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
					else
					{
						if(inimigo.getVida() > VIDA_INI_GRUA && a_energAtual >= ENERGGRUA)
						{
							a_energAtual -= ENERGGRUA;
							inimigo.recebeDano(DANOGRUA);
						}
						else if(inimigo.getVida() > VIDA_INI_TOQUE && a_energAtual >= ENERGTOQUE)
						{
							a_energAtual -= ENERGTOQUE;
							inimigo.recebeDano(DANOTOQUE);
						}
						else if(inimigo.getVida() > VIDA_INI_SOM && a_energAtual >= ENERGSOM)
						{
							a_energAtual -= ENERGSOM;
							inimigo.recebeDano(DANOSOM);
						}
					}
				}
			}
		}
		double score = calculaScore(a_inimigos, a_vidaAtual, a_energAtual); //Calcula o score usando as variaveis de simulacao
		return score;
	}
	
	//Simula um turno de defesa
	public void simulaDefesa()
	{
		for(Inimigo inimigo : a_inimigos.values()) //Para cada inimigo
		{
			if (a_vidaAtual - inimigo.getDano() < 0) //Se o robo ficar com vida negativa
	        {
	            a_vidaAtual = 0; //Coloca a vida a 0
	        } 
	        else 
	        {
	            a_vidaAtual -= inimigo.getDano(); //Retira a vida perdida
	        }
		}
	}
	
	//Simula o restante do jogo apos todos os inimigos serem detetados
	public double simulaJogo(String perfis, int numRondas)
	{
		//Variaveis locais
		double score = 0;
		int i = 0;
		
		while(numRondas > 0 && a_vidaAtual > 0) //Enquanto houver turnos do robo para simular e o robo estiver vivo
		{
			switch (Integer.parseInt(perfis.toCharArray()[i] + ""))  //Aplica o perfil indicado na string da combinacao a testar
			{
				case 1:
					estrategia("base");
					break;
				case 2:
					estrategia("sosom");
					break;
				case 3:
					estrategia("ataquemax");
					break;
				case 4:
					estrategia("curamax");
					break;
				case 5:
					estrategia("nada");
					break;
				default:
					break;
			}
			i++;
			if (i != 1 && a_vidaAtual > 0) //Se o robo estiver vivo
	        {
	            if ((a_energAtual * 1.5) <= ENERGMAX) //Se nao exceder a energia maxima
	            {
	                a_energAtual = (int) Math.round(a_energAtual * 1.5); //Recupera 50% da energia atual
	            }
	            else
	            {
	                a_energAtual = ENERGMAX; //Coloca a energia igual a energia maxima
	            }
	        }
			score += simulaTurno(); //Simula o proximo turno do robo com a estrategia aplicada
			simulaDefesa(); //Simula a proxima defesa do robo
			numRondas--;
		}
		return score;
	}
	
	//Cria uma copia do treemap passado como parametro
	public TreeMap<Integer, Inimigo> copiaTreemap(TreeMap<Integer, Inimigo> original)
	{
		TreeMap<Integer, Inimigo> copia = new TreeMap<>();
		int i = 1;
		for (Inimigo inimigo : original.values())
		{
			copia.put(i, new Inimigo(inimigo.getId())); //Coloca os inimigos do treemap original
			copia.get(i).setVida(inimigo.getVida()); //Coloca a vida dos inimigos como as do treemap original
			i++;
		}
		return copia;
	}
	
	//Calcula o numero de turnos do robo restantes no jogo
	public int calculaNumRondas()
	{
		if((13 - Projeto.turno) % 2 == 0)
			return ((13 - Projeto.turno) / 2);
		else
			return (int) ((13 - Projeto.turno) / 2) + 1;
	}
	
	//Gera as combinacoes de perfis a testar pelo metodo simulaJogo
	private static ArrayList<String> geraCombinacoes(int arraySize, ArrayList<Integer> possibleValues) //insere no arr2 todas as combinacoes de inimigos possiveis
	{
		ArrayList<String> jogos = new ArrayList<>();
	    int carry;
	    int[] indices = new int[arraySize];
	    do
	    {
	    	String str = "";
	        for(int index : indices)
	        {
	            str +=  possibleValues.get(index);
	        }
	        jogos.add(str); //Adiciona a combinacao ao ArrayList

	        carry = 1;
	        for(int i = indices.length - 1; i >= 0; i--)
	        {
	            if(carry == 0)
	                break;

	            indices[i] += carry;
	            carry = 0;

	            if(indices[i] == possibleValues.size())
	            {
	                carry = 1;
	                indices[i] = 0;
	            }
	        }
	    }
	    while(carry != 1);
	    return jogos;
	}
}