package simulador;

import java.util.Random;
import java.util.TreeMap;


public class SimulacaoUnica {

    private static int turno = 0;
    private static TreeMap<Integer, SimulacaoUnicaInimigo> inimigos = new TreeMap<Integer, SimulacaoUnicaInimigo>();
    private static SimulacaoUnicaRobo robo = new SimulacaoUnicaRobo();

    public static void preencheVazios() {
        for (int i = 1; i <= 6; i++) {
            inimigos.put(i, new SimulacaoUnicaInimigo(0));
        }
    }

    public static void novoJogo() {
        System.out.println("Tipo|Turno");
        for (int i = 0; i < 6; i++) {
            System.out.println("  " + Simulador.tipoInimigo[i] + " | " + Simulador.turnoInimigo[i]);
        }

        turno = 1;
        System.out.println("\n---------------" + turno + "---------------\n");

        //Turno 1 - espera que coloque os inimigos no tabuleiro para os detetar
        //-----------
        detetaInimigos(turno / 2 + 1);
        System.out.println(inimigos);

        //Turno 2 - atacar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        robo.recuperaEnergia();
        decideAtacaOuCura();
        System.out.println(inimigos);

        //Turno 3 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        defender();
        detetaInimigos(turno / 2 + 1);
        System.out.println(inimigos);

        //Turno 4 - atacar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        robo.recuperaEnergia();
        decideAtacaOuCura();
        System.out.println(inimigos);

        //Turno 5 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        defender();
        detetaInimigos(turno / 2 + 1);
        System.out.println(inimigos);

        //Turno 6 - atacar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        robo.recuperaEnergia();
        decideAtacaOuCura();
        System.out.println(inimigos);

        //Turno 7 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        defender();
        detetaInimigos(turno / 2 + 1);
        System.out.println(inimigos);

        //Turno 8 - atacar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        robo.recuperaEnergia();
        decideAtacaOuCura();
        System.out.println(inimigos);

        //Turno 9 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        defender();
        detetaInimigos(turno / 2 + 1);
        System.out.println(inimigos);

        //Turno 10 - atacar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        robo.recuperaEnergia();
        decideAtacaOuCura();
        System.out.println(inimigos);

        //Turno 11 - espera que coloque os inimigos no tabuleiro para os detetar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        defender();
        detetaInimigos(turno / 2 + 1);
        System.out.println(inimigos);

        //Turno 12 - atacar
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        robo.recuperaEnergia();
        decideAtacaOuCura();
        System.out.println(inimigos);

        //Turno 13
        turno++;
        System.out.println("\n---------------" + turno + "---------------\n");
        defender();
        System.out.println(inimigos);

        if (robo.getVida() > 0) {
            System.out.println("\n\nVitória!\n\n");
        } else {
            System.out.println("\n\nDerrota...\n\n");
        }

        System.out.println("Vida:    " + robo.getVida());
        System.out.println("Energia: " + robo.getEnergDisponivel());
    }

    public static void defender() {
        for (SimulacaoUnicaInimigo inimigo : inimigos.values()) {
            robo.recebeDano((int) inimigo.getDano());
            System.out.println("Dano receb.: " + (int) inimigo.getDano());
        }
    }

    public static void atacar() {
        for (SimulacaoUnicaInimigo inimigo : inimigos.values()) {
            robo.escolheAtaque(inimigo);
        }
    }

    public static void detetaInimigos(int turno) {
        for (int i = 0; i < 6; i++) {
            if (Simulador.turnoInimigo[i] == turno) {
                int j = 1;
                while (inimigos.get(j).getId() != 3) {
                    j++;
                }
                inimigos.put(j, new SimulacaoUnicaInimigo(Simulador.tipoInimigo[i]));
            }
        }
    }

    public static void decideAtacaOuCura() {
        if (robo.getVida() < Simulador.VIDA_CURAR) {
            System.out.println("Curar");
            robo.curar();
        } else {
            System.out.println("Atacar");
            atacar();
        }

        System.out.println("Vida: " + robo.getVida());
        System.out.println("Ener: " + robo.getEnergDisponivel());
    }

    public static void preencheDados() {
        preencheArray(Simulador.tipoInimigo);
        preencheArray(Simulador.turnoInimigo);
    }

    public static void preencheArray(int array[]) {
        Random n = new Random();

        for (int i = 0; i < 6; i++) {
            array[i] = n.nextInt(6) + 1;
        }
    }
}
