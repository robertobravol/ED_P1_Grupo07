package com.espol.ed_p1_grupo07.model;

import java.util.LinkedList;
import java.util.List;

public class Board {
    private int[][] cells;

    private final int CIRCLE = -1;
    private final int X = 1;
    private final int EMPTY_CELL = 0;

    public Board(){
        this.cells = new int[3][3];
    }

    public int[][] getCeldas() {
        return cells;
    }

    public int getCelda(int fila, int columna) {
        return cells[fila][columna];
    }

    public List<int[]> obtenerCasillasVacias(){

        if(cells.length == 0){

            return null;

        }

        List<int[]> lista = new LinkedList<>();

        for(int i = 0; i < cells.length; i++){

            for(int j = 0; j < cells[i].length; j++){

                if(cells[i][j] == 0){

                    lista.add(new int[]{i, j});

                }

            }

        }

        return lista;

    }


    public int verificarGanador(){

        for (int i = 0; i < 3; i++) {

            if (cells[i][0] != 0 && cells[i][0] == cells[i][1] && cells[i][1] == cells[i][2]) {

                return cells[i][0];

            }

        }

        for (int j = 0; j < 3; j++) {

            if (cells[0][j] != 0 && cells[0][j] == cells[1][j] && cells[1][j] == cells[2][j]) {

                return cells[0][j];

            }

        }
        
        if (cells[0][0] != 0 && cells[0][0] == cells[1][1] && cells[1][1] == cells[2][2]) {

            return cells[0][0];

        }

        if (cells[0][2] != 0 && cells[0][2] == cells[1][1] && cells[1][1] == cells[2][0]) {

            return cells[0][2];

        }

        return 0;

    }

    public boolean estaLleno(){

        return obtenerCasillasVacias().isEmpty();

    }

    public void marcarCasillas(int fila, int columna, int jugador){

        this.cells[fila][columna] = jugador;

    }


    public Board clonarTablero() {

        Board nuevoBoard = new Board();

        for (int i = 0; i < this.cells.length; i++) {

            for (int j = 0; j < this.cells[i].length; j++) {

                int valorActual = this.cells[i][j];
                nuevoBoard.cells[i][j] = valorActual;

            }

        }

        return nuevoBoard;

    }

    public void imprimirTablero() {
        System.out.println("-------");
        for (int i = 0; i < cells.length; i++) {
            System.out.print("|");
            for (int j = 0; j < cells[i].length; j++) {
                // Convierte los números a caracteres para mejor lectura
                char simbolo = ' ';
                if (cells[i][j] == 1) simbolo = 'X';
                else if (cells[i][j] == -1) simbolo = 'O';
                System.out.print(simbolo + "|");
            }
            System.out.println("\n-------");
        }
    }
}
