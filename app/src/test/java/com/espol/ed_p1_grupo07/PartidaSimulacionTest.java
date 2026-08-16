package com.espol.ed_p1_grupo07;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

/**
 * Pruebas de integración para simular partidas completas entre computadoras
 * y contra jugadores con movimientos aleatorios.
 *
 * Autor: Daniel Rincón (Integrante 2 - El Cerebro Artificial)
 */
public class PartidaSimulacionTest {

    @Test
    public void testComputadoraVsComputadora() {
        Computadora compX = new Computadora(1);
        Computadora compO = new Computadora(-1);

        Tablero tablero = new Tablero();
        int turno = 1; // Empieza X

        int movimientos = 0;
        while (tablero.verificarGanador() == 0 && !tablero.estaLleno() && movimientos < 15) {
            if (turno == 1) {
                int[] mov = compX.obtenerMejorMovimiento(tablero);
                assertNotNull("El movimiento de X no debe ser nulo", mov);
                assertTrue("La casilla elegida debe estar vacía", tablero.getCelda(mov[0], mov[1]) == 0);
                tablero.marcarCasillas(mov[0], mov[1], 1);
                turno = -1;
            } else {
                int[] mov = compO.obtenerMejorMovimiento(tablero);
                assertNotNull("El movimiento de O no debe ser nulo", mov);
                assertTrue("La casilla elegida debe estar vacía", tablero.getCelda(mov[0], mov[1]) == 0);
                tablero.marcarCasillas(mov[0], mov[1], -1);
                turno = 1;
            }
            movimientos++;
        }

        int ganador = tablero.verificarGanador();
        System.out.println("Resultado Computadora vs Computadora: Ganador = " + ganador + ", Lleno = " + tablero.estaLleno());
        assertTrue("La partida debe terminar con victoria o empate en máximo 9 turnos", movimientos <= 9);
    }

    @Test
    public void testComputadoraVsMovimientosAleatorios() {
        Computadora compX = new Computadora(1);
        Random random = new Random(42);

        int victoriasComputadora = 0;
        int victoriasHumano = 0;
        int empates = 0;

        for (int partida = 0; partida < 100; partida++) {
            Tablero tablero = new Tablero();
            int turno = (partida % 2 == 0) ? 1 : -1; // Alternar quién inicia

            while (tablero.verificarGanador() == 0 && !tablero.estaLleno()) {
                if (turno == 1) {
                    int[] mov = compX.obtenerMejorMovimiento(tablero);
                    assertNotNull(mov);
                    tablero.marcarCasillas(mov[0], mov[1], 1);
                    turno = -1;
                } else {
                    List<int[]> vacias = tablero.obtenerCasillasVacias();
                    int[] mov = vacias.get(random.nextInt(vacias.size()));
                    tablero.marcarCasillas(mov[0], mov[1], -1);
                    turno = 1;
                }
            }

            int g = tablero.verificarGanador();
            if (g == 1) {
                victoriasComputadora++;
            } else if (g == -1) {
                victoriasHumano++;
            } else {
                empates++;
            }
        }

        System.out.println("100 partidas vs Aleatorio: Victorias Comp=" + victoriasComputadora +
                           ", Victorias Humano=" + victoriasHumano + ", Empates=" + empates);

        // La computadora debe ganar la gran mayoría o empatar
        assertTrue("La computadora debe ganar la mayoría de partidas contra jugadas aleatorias", victoriasComputadora > 80);
    }
}
