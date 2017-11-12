import java.io.IOException;
import java.util.Scanner;

import javaConsole.JavaConsole;

public class SimulacaoUnica {

	public static void main(String[] args) throws IOException 
	{
		@SuppressWarnings("unused")
		JavaConsole consola = new JavaConsole();
		Scanner scan = new Scanner(System.in);
		System.out.println("Quantos inimigos sao colocados em cada turno (111111 -> 1 no turno 1, 1 no turno 2, ...)?\n");
		String str1 = scan.nextLine();
		System.out.println("Quais os inimigos a serem colocados (sequencialmente, 002101 -> 0 - tanque ; 1 - artilharia ; 2 - infantaria)?\n");
		String str2 = scan.nextLine();
		scan.close();
		System.out.println("\n\n\n");
		System.out.println("=============================================================");
		System.out.println("\t\t" + str1 + "\t\t");
		System.out.println("=============================================================\n");
		
		Projeto projeto = new Projeto();
		projeto.preencheVazios();
		System.out.println("Turno 1\n");
		System.out.println("Vida robo: " + projeto.robo.getVida() + " ; " + projeto.robo.getEnergia());
		imprimeInimigos(projeto);
		
		
		System.out.println("\nTurno 2\n");
		projeto.robo.recuperaEnergia();
		projeto.registaInimigos(str1.toCharArray()[0], str2);
		projeto.atacar();
		projeto.robo.curar();
		System.out.println("Vida robo: " + projeto.robo.getVida() + " ; " + projeto.robo.getEnergia());
		imprimeInimigos(projeto);
		
		
		System.out.println("\nTurno 3\n");
		projeto.robo.recuperaEnergia();
		projeto.defender();
		System.out.println("Vida robo: " + projeto.robo.getVida() + " ; " + projeto.robo.getEnergia());
		imprimeInimigos(projeto);
		
		
		System.out.println("\nTurno 4\n");
		projeto.robo.recuperaEnergia();
		projeto.registaInimigos(str1.toCharArray()[1], str2);
		projeto.atacar();
		projeto.robo.curar();
		System.out.println("Vida robo: " + projeto.robo.getVida() + " ; " + projeto.robo.getEnergia());
		imprimeInimigos(projeto);
		
		
		System.out.println("\nTurno 5\n");
		projeto.robo.recuperaEnergia();
		projeto.defender();
		System.out.println("Vida robo: " + projeto.robo.getVida() + " ; " + projeto.robo.getEnergia());
		imprimeInimigos(projeto);
		
		
		System.out.println("\nTurno 6\n");
		projeto.robo.recuperaEnergia();
		projeto.registaInimigos(str1.toCharArray()[2], str2);
		projeto.atacar();
		projeto.robo.curar();
		System.out.println("Vida robo: " + projeto.robo.getVida() + " ; " + projeto.robo.getEnergia());
		imprimeInimigos(projeto);
		
		
		System.out.println("\nTurno 7\n");
		projeto.robo.recuperaEnergia();
		projeto.defender();
		System.out.println("Vida robo: " + projeto.robo.getVida() + " ; " + projeto.robo.getEnergia());
		imprimeInimigos(projeto);
		
		
		System.out.println("\nTurno 8\n");
		projeto.robo.recuperaEnergia();
		projeto.registaInimigos(str1.toCharArray()[3], str2);
		projeto.atacar();
		projeto.robo.curar();
		System.out.println("Vida robo: " + projeto.robo.getVida() + " ; " + projeto.robo.getEnergia());
		imprimeInimigos(projeto);
		
		
		System.out.println("\nTurno 9\n");
		projeto.robo.recuperaEnergia();
		projeto.defender();
		System.out.println("Vida robo: " + projeto.robo.getVida() + " ; " + projeto.robo.getEnergia());
		imprimeInimigos(projeto);
		
		
		System.out.println("\nTurno 10\n");
		projeto.robo.recuperaEnergia();
		projeto.registaInimigos(str1.toCharArray()[4], str2);
		projeto.atacar();
		projeto.robo.curar();
		System.out.println("Vida robo: " + projeto.robo.getVida() + " ; " + projeto.robo.getEnergia());
		imprimeInimigos(projeto);
		
		
		System.out.println("\nTurno 11\n");
		projeto.robo.recuperaEnergia();
		projeto.defender();
		System.out.println("Vida robo: " + projeto.robo.getVida() + " ; " + projeto.robo.getEnergia());
		imprimeInimigos(projeto);
		
		
		System.out.println("\nTurno 12\n");
		projeto.robo.recuperaEnergia();
		projeto.registaInimigos(str1.toCharArray()[5], str2);
		projeto.atacar();
		projeto.robo.curar();
		System.out.println("Vida robo: " + projeto.robo.getVida() + " ; " + projeto.robo.getEnergia());
		imprimeInimigos(projeto);
		
		
		System.out.println("\nTurno 13\n");
		projeto.robo.recuperaEnergia();
		projeto.defender();
		System.out.println("Vida robo: " + projeto.robo.getVida() + " ; " + projeto.robo.getEnergia());
		imprimeInimigos(projeto);
		
		if(projeto.robo.getVida() > 0)
			System.out.println("\n\nVitoria!");
		else
			System.out.println("\n\nDerrota!");
	}
	
	public static void imprimeInimigos(Projeto projeto)
	{
		for(Inimigo inimigo : projeto.inimigos.values())
		{
			String nome = "";
			
			if(inimigo.getId() == 0)
				nome = "Tanque";
			else if(inimigo.getId() == 1)
				nome = "Artilharia";
			else if(inimigo.getId() == 2)
				nome = "Infantaria";
			else if(inimigo.getId() == 3)
				nome = "Vazio";
			
			System.out.print(nome + " - " + inimigo.getVida() + " ; ");
		}
	}
}
