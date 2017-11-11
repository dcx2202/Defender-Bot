import java.io.IOException;
import java.util.TreeMap;

public class Projeto {

	//Criacao/Iniciacao de variaveis/objetos
	Robo robo = new Robo();
	TreeMap<Integer, Inimigo> inimigos = new TreeMap<>();
	int i = 1;

	
	//Metodos de jogo
	public void fimJogo() throws IOException
	{
		if(robo.getVida() == 0)
		{
			simulador.derrotas++;
			simulador.bw.write(simulador.posicao + 1 + "\t-\tDerrota\tn." + String.format("%06d", simulador.derrotas) + "\t-\t" + simulador.arr2.get(simulador.posicao) + "\t-\t");
		}
		else
		{
			simulador.vitorias++;
			simulador.bw.write(simulador.posicao + 1 + "\t-\tVitoria\tn." + String.format("%06d", simulador.vitorias) + "\t-\t" + simulador.arr2.get(simulador.posicao) + "\t-\t");
		}
	}
	
	public void novoJogo(String str1, String str) throws IOException
	{
		//Turno 1
		
		//Turno 2
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[0], str);
		atacar();
		robo.curar();
		
		//Turno 3
		robo.recuperaEnergia();
		defender();
		
		//Turno 4
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[1], str);
		atacar();
		robo.curar();
		
		//Turno 5
		robo.recuperaEnergia();
		defender();
		
		//Turno 6
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[2], str);
		atacar();
		robo.curar();
		
		//Turno 7
		robo.recuperaEnergia();
		defender();
		
		//Turno 8
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[3], str);
		atacar();
		robo.curar();
		
		//Turno 9
		robo.recuperaEnergia();
		defender();
		
		//Turno 10
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[4], str);
		atacar();
		robo.curar();
		
		//Turno 11
		robo.recuperaEnergia();
		defender();
		
		//Turno 12
		robo.recuperaEnergia();
		registaInimigos(str1.toCharArray()[5], str);
		atacar();
		robo.curar();
		
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
				inimigos.put(i, new Inimigo(Integer.parseInt(string.toCharArray()[i-1] + "")));
				i++;
			}
		}
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
		for(Inimigo inimigo : inimigos.values())
		{
			robo.recebeDano(inimigo.getDano());
		}
	}
		
	
	//Outros
	public void preencheVazios()
	{
		for(int i = 1 ; i <= 6 ; i++)
		{
			registaInimigo(i, new Inimigo(3));
		}
	}

	public void registaInimigo(int posicao, Inimigo inimigo)
	{
		inimigos.put(posicao, inimigo);
	}
}
