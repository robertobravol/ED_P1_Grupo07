package com.espol.ed_p1_grupo07.model;

import java.util.LinkedList;
import java.util.List;

public class Board {
    private int[][] cells;

    public static final int CIRCLE = -1;
    public static final int X = 1;
    public static final int EMPTY_CELL = 0;

    public Board(){
        this.cells = new int[][] {
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL},
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL},
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL}
        };
    }

    // Obtiene el valor de una celda.
    public int getCell(int row, int column) {
        return cells[row][column];
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

    // Verifica cuál jugador es el ganador.
    public int checkWinner(){
        // Verifica filas
        for (int i = 0; i <= 2; i++) {
            if (cells[i][0] != 0 && cells[i][0] == cells[i][1] && cells[i][1] == cells[i][2]) {
                return cells[i][0];
            }
        }

        // Verifica columnas
        for (int j = 0; j <= 2; j++) {
            if (cells[0][j] != 0 && cells[0][j] == cells[1][j] && cells[1][j] == cells[2][j]) {
                return cells[0][j];
            }
        }

        // Verifica diagonales
        if (cells[0][0] != 0 && cells[0][0] == cells[1][1] && cells[1][1] == cells[2][2]) {
            return cells[0][0];
        }

        if (cells[0][2] != 0 && cells[0][2] == cells[1][1] && cells[1][1] == cells[2][0]) {
            return cells[0][2];
        }

        // Empate
        return 0;
    }

    public boolean estaLleno(){

        return obtenerCasillasVacias().isEmpty();

    }

    // Marca una casilla en el tablero.
    public void markCell(int row, int column, int playerSymbol){
        this.cells[row][column] = playerSymbol;
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

    public int[][] getCeldas() {
        return cells;
    }

    public void setCeldas(int[][] cells) {
        if (cells == null || cells.length != 3) {
            throw new IllegalArgumentException("celdas debe ser una matriz 3x3 no nula");
        }
        for (int i = 0; i < 3; i++) {
            if (cells[i] == null || cells[i].length != 3) {
                throw new IllegalArgumentException("celdas debe ser una matriz 3x3 no nula");
            }
        }

        this.cells = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(cells[i], 0, this.cells[i], 0, 3);
        }
    }

    public int getCelda(int fila, int columna) {
        return cells[fila][columna];
    }

    public void setCelda(int fila, int columna, int jugador) {
        this.cells[fila][columna] = jugador;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Board other = (Board) obj;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.cells[i][j] != other.cells[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result = 31 * result + cells[i][j];
            }
        }
        return result;
    }
}
