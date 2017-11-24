package simulador;

import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class VariasSimulacoes {

    private static int turno = 0;
    private static int vitorias = 0;
    private static int derrotas = 0;
    private static int tipoInimigo[];
    private static int turnoInimigo[];
    private static TreeMap<Integer, VariasSimulacoesInimigo> inimigos;
    private static VariasSimulacoesRobo robo = new VariasSimulacoesRobo();

    public static void simular()
    {
    	inimigos = new TreeMap<>();
        tipoInimigo = new int[6];
        turnoInimigo = new int[6];

        Scanner scan = new Scanner(System.in);

        System.out.print("Quantas simulações quer realizar: ");
        int n = scan.nextInt();

        System.out.println("\nSimulando...\n");

        for (int i = 0; i < n; i++) {
            novoJogo();
        }

        double percent_vitorias = ((double) vitorias / n) * 100;
        double percent_derrotas = ((double) derrotas / n) * 100;

        System.out.println("Vitórias: " + vitorias + " - " + percent_vitorias + "%");
        System.out.println("Derrotas: " + derrotas + " - " + percent_derrotas + "%\n\n");

        scan.close();
    }

    public static void preencheArray(int array[]) {
        Random n = new Random();

        for (int i = 0; i < 6; i++) {
            array[i] = n.nextInt(6) + 1;
        }
    }

    public static void preencheDados() {
        preencheArray(tipoInimigo);
        preencheArray(turnoInimigo);
    }

    public static void preencheVazios() {
        for (int i = 1; i <= 6; i++) {
            inimigos.put(i, new VariasSimulacoesInimigo(0));
        }
    }

    public static void novoJogo() {
        robo.setVidaAtual(750);
        robo.setEnergAtual(500);
        preencheVazios();
        preencheDados();

        turno = 1;

        //Turno 1 - espera que coloque os inimigos no tabuleiro para os detetar
        //-----------
        detetaInimigos(turno);

        //Turno 2 - atacar
        robo.recuperaEnergia();
        decideAtacaOuCura();

        //Turno 3 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        defender();
        detetaInimigos(turno);

        //Turno 4 - atacar
        robo.recuperaEnergia();
        decideAtacaOuCura();

        //Turno 5 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        defender();
        detetaInimigos(turno);

        //Turno 6 - atacar
        robo.recuperaEnergia();
        decideAtacaOuCura();

        //Turno 7 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        defender();
        detetaInimigos(turno);

        //Turno 8 - atacar
        robo.recuperaEnergia();
        decideAtacaOuCura();

        //Turno 9 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        defender();
        detetaInimigos(turno);

        //Turno 10 - atacar
        robo.recuperaEnergia();
        decideAtacaOuCura();

        //Turno 11 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        defender();
        detetaInimigos(turno);

        //Turno 12 - atacar
        robo.recuperaEnergia();
        decideAtacaOuCura();

        //Turno 13
        defender();

        if (robo.getVida() > 0) {
            vitorias++;
        } else {
            derrotas++;
        }

        inimigos.clear();

        //System.out.println(robo.getVida() + " | " + robo.getEnergDisponivel());
    }

    public static void defender() {
        for (VariasSimulacoesInimigo inimigo : inimigos.values()) {
            robo.recebeDano((int) inimigo.getDano());
        }
    }

    public static void atacar() {
        for (VariasSimulacoesInimigo inimigo : inimigos.values()) {
            robo.escolheAtaque(inimigo);
        }
    }

    public static void detetaInimigos(int turno) {
        for (int i = 0; i < 6; i++) {
            if (turnoInimigo[i] == turno) {
                int j = 1;
                while (inimigos.get(j).getId() != 3) {
                    j++;
                }
                inimigos.put(j, new VariasSimulacoesInimigo(tipoInimigo[i]));
            }
        }
    }

    public static void decideAtacaOuCura() {
        if (robo.getVida() < Simulador.VIDA_CURAR) {
            robo.curar();
        } else {
            atacar();
        }
    }
}
