package com.espol.ed_p1_grupo07.model;

import com.espol.ed_p1_grupo07.tree.TreeNode;
import com.espol.ed_p1_grupo07.tree.Tree;

import java.util.List;

/**
 * Clase que representa a la Computadora (IA) en el juego Tres en Raya.
 * Implementa el algoritmo Minimax de 2 niveles y la función de utilidad heurística:
 * U_jugador(t) = P_jugador - P_oponente
 *
 * Autor: Daniel Rincón (Integrante 2 - El Cerebro Artificial)
 */
public class Computer {

    public static final int VALOR_VICTORIA = 100;
    public static final int VALOR_DERROTA = -100;

    private int symbol;
    private Tree arbolDecision;

    /**
     * Constructor que inicializa la computadora con un símbolo específico.
     * @param symbol 1 para 'X' o -1 para 'O'.
     */
    public Computer(int symbol) {
        this.symbol = symbol;
        this.arbolDecision = null;
    }

    public int getsymbol() {
        return symbol;
    }

    public void setsymbol(int symbol) {
        this.symbol = symbol;
    }

    public Tree getArbolDecision() {
        return arbolDecision;
    }

    public void setArbolDecision(Tree arbolDecision) {
        this.arbolDecision = arbolDecision;
    }

    // =========================================================================
    // FUNCIÓN DE UTILIDAD (Integrante 2 - Tarea 1)
    // =========================================================================

    /**
     * Cuenta cuántas filas de 3 casillas están disponibles para el jugador en el Board.
     * Una fila está disponible si no contiene fichas del oponente (-jugador).
     */
    public static int contarFilasDisponibles(Board Board, int jugador) {
        int oponente = -jugador;
        int filasDisponibles = 0;

        for (int i = 0; i < 3; i++) {
            boolean disponible = true;
            for (int j = 0; j < 3; j++) {
                if (Board.getCell(i, j) == oponente) {
                    disponible = false;
                    break;
                }
            }
            if (disponible) {
                filasDisponibles++;
            }
        }
        return filasDisponibles;
    }

    /**
     * Cuenta cuántas columnas de 3 casillas están disponibles para el jugador en el Board.
     * Una columna está disponible si no contiene fichas del oponente (-jugador).
     */
    public static int contarColumnasDisponibles(Board Board, int jugador) {
        int oponente = -jugador;
        int columnasDisponibles = 0;

        for (int j = 0; j < 3; j++) {
            boolean disponible = true;
            for (int i = 0; i < 3; i++) {
                if (Board.getCell(i, j) == oponente) {
                    disponible = false;
                    break;
                }
            }
            if (disponible) {
                columnasDisponibles++;
            }
        }
        return columnasDisponibles;
    }

    /**
     * Cuenta cuántas diagonales (principal y secundaria) están disponibles para el jugador.
     * Una diagonal está disponible si no contiene fichas del oponente (-jugador).
     */
    public static int contarDiagonalesDisponibles(Board Board, int jugador) {
        int oponente = -jugador;
        int diagonalesDisponibles = 0;

        // Diagonal principal: (0,0), (1,1), (2,2)
        boolean diagPrincipal = (Board.getCell(0, 0) != oponente &&
                                 Board.getCell(1, 1) != oponente &&
                                 Board.getCell(2, 2) != oponente);
        if (diagPrincipal) {
            diagonalesDisponibles++;
        }

        // Diagonal secundaria: (0,2), (1,1), (2,0)
        boolean diagSecundaria = (Board.getCell(0, 2) != oponente &&
                                  Board.getCell(1, 1) != oponente &&
                                  Board.getCell(2, 0) != oponente);
        if (diagSecundaria) {
            diagonalesDisponibles++;
        }

        return diagonalesDisponibles;
    }

    /**
     * Calcula P_jugador: número total de filas, columnas y diagonales disponibles
     * en el Board para el jugador indicado.
     * @param Board Board a evaluar.
     * @param jugador Símbolo del jugador (1 o -1).
     * @return Total de líneas disponibles (entre 0 y 8).
     */
    public static int calcularP(Board Board, int jugador) {
        return contarFilasDisponibles(Board, jugador) +
               contarColumnasDisponibles(Board, jugador) +
               contarDiagonalesDisponibles(Board, jugador);
    }

    /**
     * Calcula la utilidad pura según la fórmula:
     * U_jugador(t) = P_jugador - P_oponente
     * @param Board Board a evaluar.
     * @param jugador Jugador desde cuya perspectiva se calcula la utilidad.
     * @return Valor entero de la utilidad heurística.
     */
    public static int calcularUtilidadHeuristicaPura(Board Board, int jugador) {
        int pJugador = calcularP(Board, jugador);
        int pOponente = calcularP(Board, -jugador);
        return pJugador - pOponente;
    }

    /**
     * Evalúa matemáticamente un Board específico.
     * Si el Board es terminal (victoria/derrota), asigna valores extremos (+100 / -100).
     * En caso contrario, aplica la fórmula U_jugador(t) = P_jugador - P_oponente.
     *
     * @param Board Board a evaluar.
     * @param jugador Jugador con respecto al cual se calcula la utilidad.
     * @return Utilidad del Board.
     */
    public static int calcularUtilidad(Board Board, int jugador) {
        int ganador = Board.checkWinner();
        if (ganador == jugador) {
            return VALOR_VICTORIA;
        } else if (ganador == -jugador) {
            return VALOR_DERROTA;
        }
        return calcularUtilidadHeuristicaPura(Board, jugador);
    }

    // =========================================================================
    // ALGORITMO MINIMAX (Integrante 2 - Tareas 2 y 3)
    // =========================================================================

    /**
     * Construye el árbol de decisión N-ario de 2 niveles aplicando el algoritmo Minimax:
     * Paso 1: Generar posibles estados después del turno de la computadora (Nivel 1).
     * Paso 2: Generar posibles respuestas del oponente humano (Nivel 2) y calcular utilidades.
     * Paso 3: Encontrar la utilidad mínima de cada familia y asociarla a su nodo padre.
     * Paso 4: Encontrar la utilidad máxima de entre los hijos de nivel 1 y asociarla a la raíz.
     *
     * @param BoardActual Estado actual del juego.
     * @return Árbol N-ario completo con las utilidades calculadas en cada nivel.
     */
    public Tree construirArbolMinimax(Board BoardActual) {
        if (BoardActual == null) {
            return null;
        }

        TreeNode nodoRaiz = new TreeNode(BoardActual, this.symbol);
        Tree arbol = new Tree(nodoRaiz);

        // Paso 1: Generar estados de nivel 1 (posibles movimientos propios de la computadora)
        arbol.generalEstadosPosibles();
        List<Tree> movimientosComputadora = nodoRaiz.getHijos();

        if (movimientosComputadora == null || movimientosComputadora.isEmpty()) {
            nodoRaiz.setUtilidad(calcularUtilidad(BoardActual, this.symbol));
            this.arbolDecision = arbol;
            return arbol;
        }

        int mejorUtilidad = Integer.MIN_VALUE;

        // Paso 2 y Paso 3: Proyección del oponente y cálculo de utilidad mínima por familia
        for (Tree subArbolNivel1 : movimientosComputadora) {
            TreeNode nodoNivel1 = subArbolNivel1.getRoot();
            Board estadoNivel1 = nodoNivel1.getEstado();

            // Si la computadora gana inmediatamente o el Board se llena:
            if (estadoNivel1.checkWinner() == this.symbol || estadoNivel1.isFull()) {
                int utilidadTerminal = calcularUtilidad(estadoNivel1, this.symbol);
                nodoNivel1.setUtilidad(utilidadTerminal);
            } else {
                // Paso 2: Generar segundo nivel de estados (movimientos del humano / oponente)
                subArbolNivel1.generalEstadosPosibles();
                List<Tree> respuestasOponente = nodoNivel1.getHijos();

                if (respuestasOponente == null || respuestasOponente.isEmpty()) {
                    int u = calcularUtilidad(estadoNivel1, this.symbol);
                    nodoNivel1.setUtilidad(u);
                } else {
                    int minUtilidadFamilia = Integer.MAX_VALUE;

                    for (Tree subArbolNivel2 : respuestasOponente) {
                        TreeNode nodoNivel2 = subArbolNivel2.getRoot();
                        int uNieto = calcularUtilidad(nodoNivel2.getEstado(), this.symbol);
                        nodoNivel2.setUtilidad(uNieto);

                        if (uNieto < minUtilidadFamilia) {
                            minUtilidadFamilia = uNieto;
                        }
                    }

                    // Paso 3: Asociar la utilidad mínima de la familia al padre respectivo
                    nodoNivel1.setUtilidad(minUtilidadFamilia);
                }
            }

            if (nodoNivel1.getUtilidad() > mejorUtilidad) {
                mejorUtilidad = nodoNivel1.getUtilidad();
            }
        }

        // Paso 4: Asociar la máxima de todas las utilidades mínimas a la raíz
        nodoRaiz.setUtilidad(mejorUtilidad);
        this.arbolDecision = arbol;
        return arbol;
    }

    /**
     * Aplica el algoritmo Minimax para elegir y retornar el Board resultante
     * después del movimiento óptimo de la computadora.
     *
     * @param BoardActual Estado actual del juego.
     * @return Nuevo Board con la jugada óptima aplicada, o null si no hay casillas vacías.
     */
    public Board decidirMejorMovimiento(Board BoardActual) {
        Tree arbol = construirArbolMinimax(BoardActual);
        if (arbol == null || arbol.getRoot() == null) {
            return null;
        }

        List<Tree> hijos = arbol.getRoot().getHijos();
        if (hijos == null || hijos.isEmpty()) {
            return null;
        }

        Tree mejorHijo = null;
        int maxUtilidad = Integer.MIN_VALUE;

        for (Tree hijo : hijos) {
            int u = hijo.getRoot().getUtilidad();
            if (u > maxUtilidad) {
                maxUtilidad = u;
                mejorHijo = hijo;
            }
        }

        return (mejorHijo != null) ? mejorHijo.getRoot().getEstado() : null;
    }

    /**
     * Retorna las coordenadas [fila, columna] del movimiento óptimo a realizar
     * por la computadora en su turno.
     *
     * @param BoardActual Estado actual del juego.
     * @return Arreglo de 2 enteros {fila, columna}, o null si no hay movimiento posible.
     */
    public int[] obtenerMejorMovimiento(Board BoardActual) {
        if (BoardActual == null) {
            return null;
        }

        Board mejorBoard = decidirMejorMovimiento(BoardActual);
        if (mejorBoard == null) {
            return null;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (BoardActual.getCell(i, j) == 0 && mejorBoard.getCell(i, j) == this.symbol) {
                    return new int[]{i, j};
                }
            }
        }

        return null;
    }

    /**
     * Funcionalidad Opcional (Sugerencias):
     * Permite recomendar al usuario humano cuál es su mejor movimiento posible,
     * evaluando el juego desde la perspectiva del humano con Minimax.
     *
     * @param BoardActual Estado actual del juego.
     * @param jugadorHumano Símbolo del jugador humano (1 o -1).
     * @return Arreglo {fila, columna} con la casilla recomendada.
     */
    public static int[] sugerirMovimiento(Board BoardActual, int jugadorHumano) {
        if (BoardActual == null || BoardActual.isFull()) {
            return null;
        }
        Computer motorSugerencia = new Computer(jugadorHumano);
        return motorSugerencia.obtenerMejorMovimiento(BoardActual);
    }
}
