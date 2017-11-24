package simulador;
import java.io.IOException;
import java.util.TreeMap;

public class TodosCasosProjeto {

	//Criacao/Iniciacao de variaveis/objetos
	TodosCasosRobo robo = new TodosCasosRobo();
	TreeMap<Integer, TodosCasosInimigo> inimigos = new TreeMap<>();
	int i = 1;

	
	//Metodos de jogo
	public void fimJogo() throws IOException
	{
		if(robo.getVida() == 0)
		{
			TodosCasosSimulador.derrotas++;
			TodosCasosSimulador.bw.write(TodosCasosSimulador.posicao + 1 + "\t-\tDerrota\tn." + String.format("%06d", TodosCasosSimulador.derrotas) + "\t-\t" + TodosCasosSimulador.arr2.get(TodosCasosSimulador.posicao) + "\t-\t");
		}
		else
		{
			TodosCasosSimulador.vitorias++;
			TodosCasosSimulador.bw.write(TodosCasosSimulador.posicao + 1 + "\t-\tVitoria\tn." + String.format("%06d", TodosCasosSimulador.vitorias) + "\t-\t" + TodosCasosSimulador.arr2.get(TodosCasosSimulador.posicao) + "\t-\t");
		}
	}
	
	public void novoJogo(String str1, String str) throws IOException
	{
		//Turno 1
		
		//Turno 2
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[0], str);
		decideJogada();
		
		//Turno 3
		defender();
		
		//Turno 4
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[1], str);
		decideJogada();
		
		//Turno 5
		defender();
		
		//Turno 6
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[2], str);
		decideJogada();
		
		//Turno 7
		defender();
		
		//Turno 8
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[3], str);
		decideJogada();
		
		//Turno 9
		defender();
		
		//Turno 10
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[4], str);
		decideJogada();
		
		//Turno 11
		defender();
		
		//Turno 12
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[5], str);
		decideJogada();
		
		//Turno 13
		defender();
		fimJogo();
	}
	
	public void voltarInicio() //Voltar ao inicio do tabuleiro a partir de qualquer ponto
	{	
		robo.setPosicaoAtual(1);
	}
	
	public void registaInimigos(char turno, String string)
	{
		int aux = Integer.parseInt(turno + "");
		if(inimigos.size() <= 6)
		{
			for(int l = 1 ; l <= aux ; l++)
			{
				inimigos.put(i, new TodosCasosInimigo(Integer.parseInt(string.toCharArray()[i-1] + "")));
				i++;
			}
		}
	}
	
	public void decideJogada()
	{
		if(robo.getVida() < Simulador.VIDA_CURAR)
			robo.curar();
		else
			atacar();
	}
	
	public void atacar()
	{
		
		robo.setPosicaoAtual(1);
		while(robo.getPosicaoAtual() <= 6)
		{
			robo.escolheAtaque(inimigos.get(robo.getPosicaoAtual()));
			robo.setPosicaoAtual(robo.getPosicaoAtual() + 1);
		}
	}
	
	public void defender()
	{
		for(TodosCasosInimigo inimigo : inimigos.values())
		{
			robo.recebeDano((int)inimigo.getDano());
		}
	}
		
	
	//Outros
	public void preencheVazios()
	{
		for(int i = 1 ; i <= 6 ; i++)
		{
			registaInimigo(i, new TodosCasosInimigo(3));
		}
	}

	public void registaInimigo(int posicao, TodosCasosInimigo inimigo)
	{
		inimigos.put(posicao, inimigo);
	}
}
