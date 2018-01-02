package simulador;

//Imports
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Simulador
{ 
	//Definicao e inicializacao de variaveis
    static int tipoInimigo[]; //Array usado para simular um jogo especifico ou aleatorio
    static int turnoInimigo[]; //Array usado para simular um jogo especifico ou aleatorio
    static ArrayList<Integer> arr1 = new ArrayList<>(); //ArrayList usado para gerar todos os jogos possiveis
	static ArrayList<String> arr2 = new ArrayList<>(); //ArrayList usado para gerar todos os jogos possiveis
	static ArrayList<Integer> arr3 = new ArrayList<>(); //ArrayList usado para gerar todos os jogos possiveis
	static ArrayList<String> arr4 = new ArrayList<>(); //ArrayList usado para gerar todos os jogos possiveis
	public static int posicao = 0;
	static File file = new File("simulacao.txt"); //Ficheiro onde guarda o resultado da simulacao de todos os jogos possiveis
	static FileWriter fw;
	static BufferedWriter bw;
    
    //Main
	public static void main(String[] args) throws IOException
	{
		//Inicializacao dos arrays
        tipoInimigo = new int[6];
        turnoInimigo = new int[6];
        Simulacao.preencheVazios(); //Preenche os inimigos com vazios

        Scanner scan = new Scanner(System.in); //Que simulacao devera ser efetuada
        System.out.println("Que tipo de simulacao pretende?\n1- Caso aleatorio\n2- Caso especifico\n3- Varias simulacoes\n4- Todos os casos possiveis\n\nOpcao: ");
        
        int opcao = scan.nextInt();
        
        switch (opcao) 
        {
			case 1: //Caso aleatorio
				Simulacao.preencheDados();
				Simulacao.novoJogoPrint();
				break;
			
			case 2: //Caso especifico
				casoEspecifico();
				break;
						
			case 3: //Varias simulacoes
				variasSimulacoes();
				break;
				
			case 4: //Todos os casos possiveis
				todosCasos();
				break;
	
			default:
				break;
		}
        scan.close();
        System.exit(0);
	}
	
	//Simula um caso especifico
	private static void casoEspecifico()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Insira o lançamento dos dados:"); //Pede que insira os dados do jogo

        for (int i = 0; i < 6; i++) 
        {
            int n = i + 1;

            System.out.print("lançamento " + n + " - Tipo:  "); //Tipo de inimigo
            tipoInimigo[i] = scan.nextInt();
            System.out.print("lançamento " + n + " - Turno: "); //Turno em que deve ser colocado
            turnoInimigo[i] = scan.nextInt();
        }

        System.out.println("\n\nPressione ENTER para simular\n\n");
        scan.nextLine();
        scan.nextLine();
        scan.close();
        Simulacao.novoJogoPrint(); //Inicia simulacao com impressao de informacao na consola
	}

	//Efetua um numero de simulacoes
	private static void variasSimulacoes()
	{
        Scanner scan = new Scanner(System.in);

        System.out.print("Quantas simulações quer realizar: ");
        int n = scan.nextInt(); //Numero de simulacoes a realizar

        System.out.println("\nSimulando...\n");

        for (int i = 0; i < n; i++) {
            Simulacao.novoJogo(); //Simula um novo jogo
        }

        //Percentagens de vitorias/derrotas
        double percent_vitorias = ((double) Simulacao.vitorias / n) * 100;
        double percent_derrotas = ((double) Simulacao.derrotas / n) * 100;

        //Imprime na consola os resultados
        System.out.println("Vitórias: " + Simulacao.vitorias + " - " + percent_vitorias + "%");
        System.out.println("Derrotas: " + Simulacao.derrotas + " - " + percent_derrotas + "%\n\n");

        scan.close();
	}

	//Simula todos os jogos possiveis
	private static void todosCasos() throws IOException
	{
		fw = new FileWriter(file);
		bw = new BufferedWriter(fw);
		//Inicializa ArrayLists com dados (ids dos inimigos e turnos em que podem ser colocados)
		arr1.add(0);
		arr1.add(1);
		arr1.add(2);
		arr3.add(0);
		arr3.add(1);
		arr3.add(2);
		arr3.add(3);
		arr3.add(4);
		arr3.add(5);
		arr3.add(6);
		geraCombinacoesInimigos(6, arr1); //Gera todas as combinacoes de inimigos possiveis
		geraCombinacoesTurnos(6, arr3); //Gera todas as combinacoes de turnos em que os inimigos podem ser colocados
		for(String str1 : arr4) //Para cada combinacao de turnos em que os inimigos podem ser colocados
		{
			//Imprime no ficheiro a combinacao a ser simulada atualmente
			bw.write("\n\n===========================");
			bw.write("\t\t" + str1 + "\t\t");
			bw.write("============================\n\n");
			posicao = 0;
			for(String str : arr2) //Para cada combinacao de inimigos que podem ser colocados
			{
				Simulacao.preencheVazios(); //Preenche o treemap dos inimigos com vazios
				Simulacao.novoJogo(str1, str); //Comeca um novo jogo com a combinacao atual
				
				//Imprime informacao acerca dos inimigos deste jogo no ficheiro
				for(Inimigo inimigo : Simulacao.inimigos.values())
				{
					bw.write("\t" + inimigo.getVida());
				}
				posicao++;
				bw.write("\n");
				Simulacao.resetVar(); //Reset as variaveis de simulacao
			}
		}
		NumberFormat formatter = new DecimalFormat("#0.00"); //Formatador de casas decimais
		
		//Calcula as percentagens e arredondamentos e imprime no ficheiro
		double aux1 =(double) Simulacao.vitorias;
		double aux2 =(double) Simulacao.derrotas;
		double k = (aux1 / (aux1 + aux2)) * 100;
		bw.write("\n\nVitorias:\t" + formatter.format(k) + "%\n");
		k = (aux2 / (aux1 + aux2)) * 100;
		bw.write("Derrotas:\t" + formatter.format(k) + "%");
		bw.close();
	}
	
	//Gera todas as combinacoes de inimigos que podem ser colocados
	private static void geraCombinacoesInimigos(int arraySize, ArrayList<Integer> possibleValues) //insere no arr2 todas as combinacoes de inimigos possiveis
	{
	    int carry;
	    int[] indices = new int[arraySize];
	    do
	    {
	    	String str = "";
	        for(int index : indices)
	        {
	            str +=  possibleValues.get(index);
	        }
	        arr2.add(str); //Adiciona a combinacao ao ArrayList

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
	}
	
	//Gera todas as combinacoes de turnos em que os inimigos podem ser colocados
	private static void geraCombinacoesTurnos(int arraySize, ArrayList<Integer> possibleValues) //insere no arr4 todas as combinacoes de turnos em que os inimigos sao inseridos
	{
	    int carry;
	    int[] indices = new int[arraySize];
	    do
	    {
	    	String str = "";
	    	int aux = 0;
	        for(int index : indices)
	        {
	            str +=  possibleValues.get(index);
	        }
	        for(char c : str.toCharArray())
	        {
	        	aux += Integer.parseInt(c + ""); //Adiciona o numero de inimigos a serem colocados ao total
	        }
	        
	        if(aux == 6) //Se o numero total de inimigos a serem colocados for 6
	        	arr4.add(str); //Adiciona a combinacao ao ArrayList
	        
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
	}
}
