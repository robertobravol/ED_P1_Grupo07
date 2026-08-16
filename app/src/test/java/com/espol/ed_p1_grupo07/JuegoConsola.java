package com.espol.ed_p1_grupo07;

import com.espol.ed_p1_grupo07.arbol.Node;
import com.espol.ed_p1_grupo07.arbol.Tree;

import java.util.List;
import java.util.Scanner;

/**
 * Probador interactivo por consola para jugar contra la Computadora (Minimax),
 * ver el árbol de decisiones, utilidades y sugerencias.
 *
 * Autor: Daniel Rincón (Integrante 2)
 */
public class JuegoConsola {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=================================================");
        System.out.println("   TRES EN RAYA - MOTOR MINIMAX (ED_P1_Grupo07)   ");
        System.out.println("=================================================");
        System.out.println("1. Jugar contra la Computadora");
        System.out.println("2. Ver demostración de árbol Minimax (Paso a paso)");
        System.out.println("3. Computadora vs Computadora");
        System.out.print("\nSeleccione una opción (1-3): ");

        int opcion = 1;
        if (scanner.hasNextInt()) {
            opcion = scanner.nextInt();
        }

        switch (opcion) {
            case 1:
                jugarContraComputadora(scanner);
                break;
            case 2:
                demostracionArbolMinimax();
                break;
            case 3:
                computadoraVsComputadora();
                break;
            default:
                jugarContraComputadora(scanner);
        }
    }

    public static void jugarContraComputadora(Scanner scanner) {
        System.out.println("\n--- CONFIGURACIÓN DE PARTIDA ---");
        System.out.print("¿Con qué símbolo deseas jugar? (1 = X, 2 = O) [Default 1]: ");
        int eleccionSimbolo = scanner.hasNextInt() ? scanner.nextInt() : 1;
        int simboloHumano = (eleccionSimbolo == 2) ? -1 : 1;
        int simboloComputadora = -simboloHumano;

        System.out.print("¿Quién inicia el juego? (1 = Humano, 2 = Computadora) [Default 1]: ");
        int eleccionTurno = scanner.hasNextInt() ? scanner.nextInt() : 1;
        int turno = (eleccionTurno == 2) ? simboloComputadora : simboloHumano;

        Computadora comp = new Computadora(simboloComputadora);
        Tablero tablero = new Tablero();

        System.out.println("\n¡Comienza el juego!");
        System.out.println("Humano: " + (simboloHumano == 1 ? "X" : "O") +
                           " | Computadora: " + (simboloComputadora == 1 ? "X" : "O"));
        System.out.println("Para jugar, ingresa fila (0-2) y columna (0-2).");
        System.out.println("Escribe '9 9' en tu turno si deseas una sugerencia de jugada.");

        imprimirTableroConIndices(tablero);

        while (tablero.verificarGanador() == 0 && !tablero.estaLleno()) {
            if (turno == simboloHumano) {
                System.out.println("\n--- TU TURNO (" + (simboloHumano == 1 ? "X" : "O") + ") ---");
                System.out.print("Ingresa fila y columna (ej: 0 0) o '9 9' para pista: ");
                int f = scanner.nextInt();
                int c = scanner.nextInt();

                if (f == 9 && c == 9) {
                    int[] pista = Computadora.sugerirMovimiento(tablero, simboloHumano);
                    if (pista != null) {
                        System.out.println(">> PISTA/SUGERENCIA: La mejor jugada es en [" + pista[0] + ", " + pista[1] + "]");
                    }
                    continue;
                }

                if (f < 0 || f > 2 || c < 0 || c > 2 || tablero.getCelda(f, c) != 0) {
                    System.out.println(">> ¡Movimiento inválido! Casilla ocupada o fuera de rango. Intenta de nuevo.");
                    continue;
                }

                tablero.marcarCasillas(f, c, simboloHumano);
                imprimirTableroConIndices(tablero);
                turno = simboloComputadora;
            } else {
                System.out.println("\n--- TURNO DE LA COMPUTADORA (" + (simboloComputadora == 1 ? "X" : "O") + ") ---");
                Tree arbol = comp.construirArbolMinimax(tablero);
                int[] mov = comp.obtenerMejorMovimiento(tablero);

                System.out.println("Minimax evaluó " + arbol.getRoot().getHijos().size() + " movimientos posibles.");
                System.out.println("Utilidad máxima elegida por la Computadora: " + arbol.getRoot().getUtilidad());
                System.out.println("La computadora juega en [" + mov[0] + ", " + mov[1] + "]");

                tablero.marcarCasillas(mov[0], mov[1], simboloComputadora);
                imprimirTableroConIndices(tablero);
                turno = simboloHumano;
            }
        }

        int ganador = tablero.verificarGanador();
        System.out.println("\n=================================================");
        if (ganador == simboloHumano) {
            System.out.println("          ¡FELICIDADES! ¡HAS GANADO!");
        } else if (ganador == simboloComputadora) {
            System.out.println("          ¡LA COMPUTADORA HA GANADO!");
        } else {
            System.out.println("                 ¡EMPATE!");
        }
        System.out.println("=================================================");
    }

    public static void demostracionArbolMinimax() {
        System.out.println("\n--- DEMOSTRACIÓN DEL ÁRBOL MINIMAX Y UTILIDADES ---");
        System.out.println("Estado inicial: Tablero Vacío");
        Tablero vacio = new Tablero();
        Computadora compX = new Computadora(1); // X = 1

        Tree arbol = compX.construirArbolMinimax(vacio);
        System.out.println("Utilidad calculada para la raíz (máxima de mínimas): " + arbol.getRoot().getUtilidad());
        System.out.println("\nEvaluación detallada de cada movimiento posible de Nivel 1:");

        List<Tree> hijos = arbol.getRoot().getHijos();
        for (int i = 0; i < hijos.size(); i++) {
            Node nodoHijo = hijos.get(i).getRoot();
            Tablero tableroHijo = nodoHijo.getEstado();

            // Buscar qué casilla se marcó
            int fila = -1, col = -1;
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (tableroHijo.getCelda(r, c) == 1) {
                        fila = r;
                        col = c;
                    }
                }
            }

            int cantRespuestasHumano = nodoHijo.getHijos() != null ? nodoHijo.getHijos().size() : 0;
            System.out.println(" • Opción " + (i + 1) + ": Casilla [" + fila + ", " + col + "] | " +
                               "Utilidad Mínima Oponente = " + nodoHijo.getUtilidad() +
                               " (Generó " + cantRespuestasHumano + " tableros de nivel 2)");
        }

        int[] mejor = compX.obtenerMejorMovimiento(vacio);
        System.out.println("\n>> Decisión Óptima: Casilla [" + mejor[0] + ", " + mejor[1] + "] (Centro)");
    }

    public static void computadoraVsComputadora() {
        System.out.println("\n--- MODO COMPUTADORA VS COMPUTADORA ---");
        Computadora compX = new Computadora(1);
        Computadora compO = new Computadora(-1);
        Tablero tablero = new Tablero();

        int turno = 1;
        int jugada = 1;
        imprimirTableroConIndices(tablero);

        while (tablero.verificarGanador() == 0 && !tablero.estaLleno()) {
            if (turno == 1) {
                int[] mov = compX.obtenerMejorMovimiento(tablero);
                tablero.marcarCasillas(mov[0], mov[1], 1);
                System.out.println("\nJugada #" + jugada + ": Computadora X marca [" + mov[0] + ", " + mov[1] + "]");
                turno = -1;
            } else {
                int[] mov = compO.obtenerMejorMovimiento(tablero);
                tablero.marcarCasillas(mov[0], mov[1], -1);
                System.out.println("\nJugada #" + jugada + ": Computadora O marca [" + mov[0] + ", " + mov[1] + "]");
                turno = 1;
            }
            imprimirTableroConIndices(tablero);
            jugada++;
        }

        int ganador = tablero.verificarGanador();
        System.out.println("\nResultado final:");
        if (ganador == 1) {
            System.out.println("Ganador: Computadora X");
        } else if (ganador == -1) {
            System.out.println("Ganador: Computadora O");
        } else {
            System.out.println("Empate perfecto");
        }
    }

    public static void imprimirTableroConIndices(Tablero tablero) {
        System.out.println("\n    0   1   2  (Columnas)");
        System.out.println("  +---+---+---+");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < 3; j++) {
                int val = tablero.getCelda(i, j);
                char s = ' ';
                if (val == 1) s = 'X';
                else if (val == -1) s = 'O';
                System.out.print(" " + s + " |");
            }
            System.out.println("\n  +---+---+---+");
        }
    }
}
