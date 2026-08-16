package com.espol.ed_p1_grupo07;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import com.espol.ed_p1_grupo07.arbol.Node;
import com.espol.ed_p1_grupo07.arbol.Tree;

import java.util.List;

/**
 * Pruebas unitarias para la clase Computadora, la función de utilidad y el algoritmo Minimax.
 *
 * Autor: Daniel Rincón (Integrante 2 - El Cerebro Artificial)
 */
public class ComputadoraTest {

    private Computadora computadoraX;
    private Computadora computadoraO;

    @Before
    public void setUp() {
        computadoraX = new Computadora(1);  // X
        computadoraO = new Computadora(-1); // O
    }

    @Test
    public void testP_TableroVacio() {
        Tablero vacio = new Tablero();
        // En un tablero vacío, las 8 líneas (3 filas, 3 columnas, 2 diagonales) están disponibles para ambos jugadores
        assertEquals(8, Computadora.calcularP(vacio, 1));
        assertEquals(8, Computadora.calcularP(vacio, -1));
        assertEquals(0, Computadora.calcularUtilidadHeuristicaPura(vacio, 1));
    }

    @Test
    public void testP_TableroConCentroMarcado() {
        Tablero tablero = new Tablero();
        tablero.setCelda(1, 1, 1); // X en el centro

        // Para X: 3 filas + 3 cols + 2 diags = 8 líneas disponibles
        assertEquals(8, Computadora.calcularP(tablero, 1));

        // Para O: las líneas con centro (fila 1, col 1, diag princ, diag sec) están bloqueadas.
        // Quedan fila 0, fila 2, col 0, col 2 = 4 líneas disponibles
        assertEquals(4, Computadora.calcularP(tablero, -1));

        // Utilidad de X: 8 - 4 = 4
        assertEquals(4, Computadora.calcularUtilidadHeuristicaPura(tablero, 1));
        // Utilidad de O: 4 - 8 = -4
        assertEquals(-4, Computadora.calcularUtilidadHeuristicaPura(tablero, -1));
    }

    @Test
    public void testContarFilasColumnasDiagonales() {
        Tablero tablero = new Tablero();
        tablero.setCelda(0, 0, 1);  // X en (0,0)
        tablero.setCelda(0, 1, -1); // O en (0,1)

        // Fila 0 tiene a ambos (X y O) -> bloqueada para ambos
        // Filas 1 y 2 vacías -> disponibles para ambos
        assertEquals(2, Computadora.contarFilasDisponibles(tablero, 1));
        assertEquals(2, Computadora.contarFilasDisponibles(tablero, -1));

        // Columnas:
        // Col 0 tiene X -> disponible para X (1), bloqueada para O
        // Col 1 tiene O -> bloqueada para X, disponible para O (-1)
        // Col 2 vacía -> disponible para ambos
        assertEquals(2, Computadora.contarColumnasDisponibles(tablero, 1));
        assertEquals(2, Computadora.contarColumnasDisponibles(tablero, -1));

        // Diagonales:
        // Principal (0,0),(1,1),(2,2) tiene X -> disp para X, bloq para O
        // Secundaria (0,2),(1,1),(2,0) vacía -> disp para ambos
        assertEquals(2, Computadora.contarDiagonalesDisponibles(tablero, 1));
        assertEquals(1, Computadora.contarDiagonalesDisponibles(tablero, -1));
    }

    @Test
    public void testUtilidadTablerosTerminales() {
        Tablero victoriaX = new Tablero();
        victoriaX.setCelda(0, 0, 1);
        victoriaX.setCelda(0, 1, 1);
        victoriaX.setCelda(0, 2, 1);

        assertEquals(1, victoriaX.verificarGanador());
        assertEquals(Computadora.VALOR_VICTORIA, Computadora.calcularUtilidad(victoriaX, 1));
        assertEquals(Computadora.VALOR_DERROTA, Computadora.calcularUtilidad(victoriaX, -1));
    }

    @Test
    public void testMinimaxTableroVacioEligeCentro() {
        Tablero tableroVacio = new Tablero();

        // Según el ejemplo de la guía, en un tablero vacío, Minimax asigna:
        // Centro (1,1): min utilidad = 1 (la máxima de todas)
        // Esquinas: min utilidad = -1
        // Lados/Bordes: min utilidad = -2
        Tree arbol = computadoraX.construirArbolMinimax(tableroVacio);
        assertNotNull(arbol);
        assertNotNull(arbol.getRoot());

        List<Tree> hijosNivel1 = arbol.getRoot().getHijos();
        assertEquals(9, hijosNivel1.size());

        // La utilidad máxima asociada a la raíz debe ser 1
        assertEquals(1, arbol.getRoot().getUtilidad());

        // El movimiento óptimo elegido por la computadora debe ser el centro (1,1)
        int[] mejorMovimiento = computadoraX.obtenerMejorMovimiento(tableroVacio);
        assertNotNull(mejorMovimiento);
        assertEquals(1, mejorMovimiento[0]);
        assertEquals(1, mejorMovimiento[1]);

        Tablero mejorTablero = computadoraX.decidirMejorMovimiento(tableroVacio);
        assertNotNull(mejorTablero);
        assertEquals(1, mejorTablero.getCelda(1, 1));
    }

    @Test
    public void testBloquearVictoriaOponente() {
        // El oponente (-1) tiene 2 en fila 0: (0,0) y (0,1).
        // La casilla (0,2) está vacía. La computadora DEBE bloquear en (0,2).
        Tablero tablero = new Tablero();
        tablero.setCelda(0, 0, -1);
        tablero.setCelda(0, 1, -1);
        tablero.setCelda(1, 1, 1);

        int[] mov = computadoraX.obtenerMejorMovimiento(tablero);
        assertNotNull(mov);
        assertEquals(0, mov[0]);
        assertEquals(2, mov[1]);
    }

    @Test
    public void testGanarPartidaInmediata() {
        // La computadora (1) tiene 2 en columna 1: (0,1) y (1,1).
        // La casilla (2,1) le da la victoria inmediata.
        Tablero tablero = new Tablero();
        tablero.setCelda(0, 1, 1);
        tablero.setCelda(1, 1, 1);
        tablero.setCelda(0, 0, -1);
        tablero.setCelda(2, 0, -1);

        int[] mov = computadoraX.obtenerMejorMovimiento(tablero);
        assertNotNull(mov);
        assertEquals(2, mov[0]);
        assertEquals(1, mov[1]);
    }

    @Test
    public void testSugerirMovimientoHumano() {
        // El humano (-1) tiene oportunidad de ganar en (2,2)
        Tablero tablero = new Tablero();
        tablero.setCelda(0, 0, -1);
        tablero.setCelda(1, 1, -1);
        tablero.setCelda(0, 1, 1);
        tablero.setCelda(0, 2, 1);

        int[] sugerencia = Computadora.sugerirMovimiento(tablero, -1);
        assertNotNull(sugerencia);
        assertEquals(2, sugerencia[0]);
        assertEquals(2, sugerencia[1]);
    }

    @Test
    public void testComputadoraComoO() {
        // Computadora jugando con O (-1) bloquea a X (1)
        Tablero tablero = new Tablero();
        tablero.setCelda(2, 0, 1);
        tablero.setCelda(2, 1, 1);
        tablero.setCelda(1, 1, -1);

        int[] mov = computadoraO.obtenerMejorMovimiento(tablero);
        assertNotNull(mov);
        assertEquals(2, mov[0]);
        assertEquals(2, mov[1]);
    }

    @Test
    public void testTableroCasiLleno() {
        // Tablero con 1 sola casilla vacía restante en (2,2)
        Tablero tablero = new Tablero();
        tablero.setCelda(0, 0, 1);
        tablero.setCelda(0, 1, -1);
        tablero.setCelda(0, 2, 1);
        tablero.setCelda(1, 0, -1);
        tablero.setCelda(1, 1, 1);
        tablero.setCelda(1, 2, -1);
        tablero.setCelda(2, 0, -1);
        tablero.setCelda(2, 1, 1);
        // (2,2) está vacía

        int[] mov = computadoraX.obtenerMejorMovimiento(tablero);
        assertNotNull(mov);
        assertEquals(2, mov[0]);
        assertEquals(2, mov[1]);
    }
}
