package simulador;

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

    static final int VIDA_CURAR = 200;		//se a vida do robo < VIDA_CURAR entao cura
    static final int ENERG_ATAQUE_RES = 50;	//energia de reserva ao atacar
    static final int ENERG_CURA_RES = 50;		//energia de reserva ao curar
    static final int VIDA_CURA1 = 300;		//vida do robo <= VIDA_CURA1 para usar a cura1
    static final int VIDA_CURA2 = 200;		//vida do robo <= VIDA_CURA2 para usar a cura2
    static final int VIDA_CURA3 = 100;		//vida do robo <= VIDA_CURA3 para usar a cura3
    static final int VIDA_INI_GRUA = 150;		//vida do inimigo > VIDA_INI_GRUA para usar o ataque de grua
    static final int VIDA_INI_TOQUE = 50;	//vida do inimigo > VIDA_INI_TOQUE para usar o ataque de toque
    static final int VIDA_INI_SOM = 0;		//vida do inimigo > VIDA_INI_SOM para usar o ataque de som
	
    
    static int tipoInimigo[];
    static int turnoInimigo[];
    
    
    static ArrayList<Integer> arr1 = new ArrayList<>();
	static ArrayList<String> arr2 = new ArrayList<>();
	static ArrayList<Integer> arr3 = new ArrayList<>();
	static ArrayList<String> arr4 = new ArrayList<>();
	public static int posicao = 0;
	static File file = new File("simulacao.txt");
	static FileWriter fw;
	static BufferedWriter bw;
    
    
	public static void main(String[] args) throws IOException
	{
        tipoInimigo = new int[6];
        turnoInimigo = new int[6];
        Simulacao.preencheVazios();

        Scanner scan = new Scanner(System.in);
        System.out.println("Que tipo de simulacao pretende?\n1- Caso aleatorio\n2- Caso especifico\n3- Varias simulacoes\n4- Todos os casos possiveis\n\nOpcao: ");
        
        int opcao = scan.nextInt();
        
        switch (opcao) 
        {
			case 1:
				Simulacao.preencheDados();
				Simulacao.novoJogoPrint();
				break;
			
			case 2:
				casoEspecifico();
				break;
						
			case 3:
				variasSimulacoes();
				break;
				
			case 4:
				todosCasos();
				break;
	
			default:
				break;
		}
        scan.close();
        System.exit(0);
	}
	
	private static void casoEspecifico()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Insira o lançamento dos dados:");

        for (int i = 0; i < 6; i++) 
        {
            int n = i + 1;

            System.out.print("lançamento " + n + " - Tipo:  ");
            tipoInimigo[i] = scan.nextInt();
            System.out.print("lançamento " + n + " - Turno: ");
            turnoInimigo[i] = scan.nextInt();
        }

        System.out.println("\n\nPressione ENTER para simular\n\n");
        scan.nextLine();
        scan.nextLine();
        scan.close();
        Simulacao.novoJogoPrint();
	}
	
	private static void variasSimulacoes()
	{
        Scanner scan = new Scanner(System.in);

        System.out.print("Quantas simulações quer realizar: ");
        int n = scan.nextInt();

        System.out.println("\nSimulando...\n");

        for (int i = 0; i < n; i++) {
            Simulacao.novoJogo();
        }

        double percent_vitorias = ((double) Simulacao.vitorias / n) * 100;
        double percent_derrotas = ((double) Simulacao.derrotas / n) * 100;

        System.out.println("Vitórias: " + Simulacao.vitorias + " - " + percent_vitorias + "%");
        System.out.println("Derrotas: " + Simulacao.derrotas + " - " + percent_derrotas + "%\n\n");

        scan.close();
	}
	
	private static void todosCasos() throws IOException
	{
		fw = new FileWriter(file);
		bw = new BufferedWriter(fw);
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
		geraCombinacoesInimigos(6, arr1);
		geraCombinacoesTurnos(6, arr3);
		for(String str1 : arr4)
		{
			bw.write("\n\n===========================");
			bw.write("\t\t" + str1 + "\t\t");
			bw.write("============================\n\n");
			posicao = 0;
			for(String str : arr2)
			{
				Simulacao.preencheVazios();
				Simulacao.novoJogo(str1, str);
				for(Inimigo inimigo : Simulacao.inimigos.values())
				{
					bw.write("\t" + inimigo.getVida());
				}
				posicao++;
				bw.write("\n");
				Simulacao.resetVar();
			}
		}
		NumberFormat formatter = new DecimalFormat("#0.00");   
		double aux1 =(double) Simulacao.vitorias;
		double aux2 =(double) Simulacao.derrotas;
		double k = (aux1 / (aux1 + aux2)) * 100;
		bw.write("\n\nVitorias:\t" + formatter.format(k) + "%\n");
		k = (aux2 / (aux1 + aux2)) * 100;
		bw.write("Derrotas:\t" + formatter.format(k) + "%");
		bw.close();
	}
	
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
	        arr2.add(str);

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
	    while(carry != 1); // Call this method iteratively until a carry is left over
	}
	
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
	        	aux += Integer.parseInt(c + "");
	        }
	        
	        if(aux == 6)
	        	arr4.add(str);
	        
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
	    while(carry != 1); // Call this method iteratively until a carry is left over
	}
}
