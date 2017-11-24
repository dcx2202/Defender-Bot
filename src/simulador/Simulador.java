package simulador;

import java.io.IOException;
import java.util.Scanner;

public class Simulador {

    static int tipoInimigo[];
    static int turnoInimigo[];
    static final int VIDA_CURAR = 200;		//se a vida do robo < VIDA_CURAR entao cura
    static final int ENERG_ATAQUE_RES = 50;	//energia de reserva ao atacar
    static final int ENERG_CURA_RES = 50;		//energia de reserva ao curar
    static final int VIDA_CURA1 = 300;		//vida do robo <= VIDA_CURA1 para usar a cura1
    static final int VIDA_CURA2 = 200;		//vida do robo <= VIDA_CURA2 para usar a cura2
    static final int VIDA_CURA3 = 100;		//vida do robo <= VIDA_CURA3 para usar a cura3
    static final int VIDA_INI_GRUA = 300;		//vida do inimigo > VIDA_INI_GRUA para usar o ataque de grua
    static final int VIDA_INI_TOQUE = 100;	//vida do inimigo > VIDA_INI_TOQUE para usar o ataque de toque
    static final int VIDA_INI_SOM = 0;		//vida do inimigo > VIDA_INI_SOM para usar o ataque de som
	
	public static void main(String[] args) throws IOException
	{
        tipoInimigo = new int[6];
        turnoInimigo = new int[6];
        SimulacaoUnica.preencheVazios();

        Scanner scan = new Scanner(System.in);
        System.out.println("Que tipo de simulacao pretende?\n1- Caso aleatorio\n2- Caso especifico\n3- Varias simulacoes\n4- Todos os casos possiveis\n\nOpcao: ");
        
        int opcao = scan.nextInt();
        
        switch (opcao) 
        {
			case 1:
				SimulacaoUnica.preencheDados();
				SimulacaoUnica.novoJogo();
				break;
			
			case 2:
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
				SimulacaoUnica.novoJogo();
				break;
						
			case 3:
				VariasSimulacoes.simular();
				break;
				
			case 4:
				TodosCasosSimulador.simular();
				break;
	
			default:
				break;
		}
        scan.close();
        System.exit(0);
	}
}
