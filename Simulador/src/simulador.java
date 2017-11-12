import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class simulador {

	
	static ArrayList<Integer> arr1 = new ArrayList<>();
	static ArrayList<String> arr2 = new ArrayList<>();
	static ArrayList<Integer> arr3 = new ArrayList<>();
	static ArrayList<String> arr4 = new ArrayList<>();
	public static int posicao = 0;
	static int vitorias = 0;
	static int derrotas = 0;
	static File file = new File("simulacao.txt");
	static FileWriter fw;
	static BufferedWriter bw;
	
	
	public static void main(String[] args) throws IOException
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
				Projeto projeto = new Projeto();
				projeto.preencheVazios();
				projeto.novoJogo(str1, str);
				for(Inimigo inimigo : projeto.inimigos.values())
				{
					bw.write("\t" + inimigo.getVida());
				}
				posicao++;
				bw.write("\n");
			}
		}
		NumberFormat formatter = new DecimalFormat("#0.00");   
		double aux1 =(double) vitorias;
		double aux2 =(double) derrotas;
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
