package com.espol.ed_p1_grupo07;

import com.espol.ed_p1_grupo07.arbol.Node;
import com.espol.ed_p1_grupo07.arbol.Tree;

import java.util.List;

/**
 * Clase que representa a la Computadora (IA) en el juego Tres en Raya.
 * Implementa el algoritmo Minimax de 2 niveles y la función de utilidad heurística:
 * U_jugador(t) = P_jugador - P_oponente
 *
 * Autor: Daniel Rincón (Integrante 2 - El Cerebro Artificial)
 */
public class Computadora {

    public static final int VALOR_VICTORIA = 100;
    public static final int VALOR_DERROTA = -100;

    private int simbolo;
    private Tree arbolDecision;

    /**
     * Constructor por defecto. Asigna el símbolo 1 ('X') por defecto.
     */
    public Computadora() {
        this(1);
    }

    /**
     * Constructor que inicializa la computadora con un símbolo específico.
     * @param simbolo 1 para 'X' o -1 para 'O'.
     */
    public Computadora(int simbolo) {
        this.simbolo = simbolo;
        this.arbolDecision = null;
    }

    public int getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(int simbolo) {
        this.simbolo = simbolo;
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
     * Cuenta cuántas filas de 3 casillas están disponibles para el jugador en el tablero.
     * Una fila está disponible si no contiene fichas del oponente (-jugador).
     */
    public static int contarFilasDisponibles(Tablero tablero, int jugador) {
        int oponente = -jugador;
        int filasDisponibles = 0;

        for (int i = 0; i < 3; i++) {
            boolean disponible = true;
            for (int j = 0; j < 3; j++) {
                if (tablero.getCelda(i, j) == oponente) {
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
     * Cuenta cuántas columnas de 3 casillas están disponibles para el jugador en el tablero.
     * Una columna está disponible si no contiene fichas del oponente (-jugador).
     */
    public static int contarColumnasDisponibles(Tablero tablero, int jugador) {
        int oponente = -jugador;
        int columnasDisponibles = 0;

        for (int j = 0; j < 3; j++) {
            boolean disponible = true;
            for (int i = 0; i < 3; i++) {
                if (tablero.getCelda(i, j) == oponente) {
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
    public static int contarDiagonalesDisponibles(Tablero tablero, int jugador) {
        int oponente = -jugador;
        int diagonalesDisponibles = 0;

        // Diagonal principal: (0,0), (1,1), (2,2)
        boolean diagPrincipal = (tablero.getCelda(0, 0) != oponente &&
                                 tablero.getCelda(1, 1) != oponente &&
                                 tablero.getCelda(2, 2) != oponente);
        if (diagPrincipal) {
            diagonalesDisponibles++;
        }

        // Diagonal secundaria: (0,2), (1,1), (2,0)
        boolean diagSecundaria = (tablero.getCelda(0, 2) != oponente &&
                                  tablero.getCelda(1, 1) != oponente &&
                                  tablero.getCelda(2, 0) != oponente);
        if (diagSecundaria) {
            diagonalesDisponibles++;
        }

        return diagonalesDisponibles;
    }

    /**
     * Calcula P_jugador: número total de filas, columnas y diagonales disponibles
     * en el tablero para el jugador indicado.
     * @param tablero Tablero a evaluar.
     * @param jugador Símbolo del jugador (1 o -1).
     * @return Total de líneas disponibles (entre 0 y 8).
     */
    public static int calcularP(Tablero tablero, int jugador) {
        return contarFilasDisponibles(tablero, jugador) +
               contarColumnasDisponibles(tablero, jugador) +
               contarDiagonalesDisponibles(tablero, jugador);
    }

    /**
     * Calcula la utilidad pura según la fórmula:
     * U_jugador(t) = P_jugador - P_oponente
     * @param tablero Tablero a evaluar.
     * @param jugador Jugador desde cuya perspectiva se calcula la utilidad.
     * @return Valor entero de la utilidad heurística.
     */
    public static int calcularUtilidadHeuristicaPura(Tablero tablero, int jugador) {
        int pJugador = calcularP(tablero, jugador);
        int pOponente = calcularP(tablero, -jugador);
        return pJugador - pOponente;
    }

    /**
     * Evalúa matemáticamente un tablero específico.
     * Si el tablero es terminal (victoria/derrota), asigna valores extremos (+100 / -100).
     * En caso contrario, aplica la fórmula U_jugador(t) = P_jugador - P_oponente.
     *
     * @param tablero Tablero a evaluar.
     * @param jugador Jugador con respecto al cual se calcula la utilidad.
     * @return Utilidad del tablero.
     */
    public static int calcularUtilidad(Tablero tablero, int jugador) {
        int ganador = tablero.verificarGanador();
        if (ganador == jugador) {
            return VALOR_VICTORIA;
        } else if (ganador == -jugador) {
            return VALOR_DERROTA;
        }
        return calcularUtilidadHeuristicaPura(tablero, jugador);
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
     * @param tableroActual Estado actual del juego.
     * @return Árbol N-ario completo con las utilidades calculadas en cada nivel.
     */
    public Tree construirArbolMinimax(Tablero tableroActual) {
        if (tableroActual == null) {
            return null;
        }

        Node nodoRaiz = new Node(tableroActual, this.simbolo);
        Tree arbol = new Tree(nodoRaiz);

        // Paso 1: Generar estados de nivel 1 (posibles movimientos propios de la computadora)
        arbol.generalEstadosPosibles();
        List<Tree> movimientosComputadora = nodoRaiz.getHijos();

        if (movimientosComputadora == null || movimientosComputadora.isEmpty()) {
            nodoRaiz.setUtilidad(calcularUtilidad(tableroActual, this.simbolo));
            this.arbolDecision = arbol;
            return arbol;
        }

        int mejorUtilidad = Integer.MIN_VALUE;

        // Paso 2 y Paso 3: Proyección del oponente y cálculo de utilidad mínima por familia
        for (Tree subArbolNivel1 : movimientosComputadora) {
            Node nodoNivel1 = subArbolNivel1.getRoot();
            Tablero estadoNivel1 = nodoNivel1.getEstado();

            // Si la computadora gana inmediatamente o el tablero se llena:
            if (estadoNivel1.verificarGanador() == this.simbolo || estadoNivel1.estaLleno()) {
                int utilidadTerminal = calcularUtilidad(estadoNivel1, this.simbolo);
                nodoNivel1.setUtilidad(utilidadTerminal);
            } else {
                // Paso 2: Generar segundo nivel de estados (movimientos del humano / oponente)
                subArbolNivel1.generalEstadosPosibles();
                List<Tree> respuestasOponente = nodoNivel1.getHijos();

                if (respuestasOponente == null || respuestasOponente.isEmpty()) {
                    int u = calcularUtilidad(estadoNivel1, this.simbolo);
                    nodoNivel1.setUtilidad(u);
                } else {
                    int minUtilidadFamilia = Integer.MAX_VALUE;

                    for (Tree subArbolNivel2 : respuestasOponente) {
                        Node nodoNivel2 = subArbolNivel2.getRoot();
                        int uNieto = calcularUtilidad(nodoNivel2.getEstado(), this.simbolo);
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
     * Aplica el algoritmo Minimax para elegir y retornar el tablero resultante
     * después del movimiento óptimo de la computadora.
     *
     * @param tableroActual Estado actual del juego.
     * @return Nuevo Tablero con la jugada óptima aplicada, o null si no hay casillas vacías.
     */
    public Tablero decidirMejorMovimiento(Tablero tableroActual) {
        Tree arbol = construirArbolMinimax(tableroActual);
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
     * @param tableroActual Estado actual del juego.
     * @return Arreglo de 2 enteros {fila, columna}, o null si no hay movimiento posible.
     */
    public int[] obtenerMejorMovimiento(Tablero tableroActual) {
        if (tableroActual == null) {
            return null;
        }

        Tablero mejorTablero = decidirMejorMovimiento(tableroActual);
        if (mejorTablero == null) {
            return null;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tableroActual.getCelda(i, j) == 0 && mejorTablero.getCelda(i, j) == this.simbolo) {
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
     * @param tableroActual Estado actual del juego.
     * @param jugadorHumano Símbolo del jugador humano (1 o -1).
     * @return Arreglo {fila, columna} con la casilla recomendada.
     */
    public static int[] sugerirMovimiento(Tablero tableroActual, int jugadorHumano) {
        if (tableroActual == null || tableroActual.estaLleno()) {
            return null;
        }
        Computadora motorSugerencia = new Computadora(jugadorHumano);
        return motorSugerencia.obtenerMejorMovimiento(tableroActual);
    }
}
