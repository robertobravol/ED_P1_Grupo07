package com.espol.ed_p1_grupo07;

import java.util.LinkedList;
import java.util.List;

public class Tablero {

    int[][] celdas = new int[3][3];

    public Tablero(){


    }

    public List<int[]> obtenerCasillasVacias(){

        if(celdas.length == 0){

            return null;

        }

        List<int[]> lista = new LinkedList<>();

        for(int i = 0; i < celdas.length; i++){

            for(int j = 0; j < celdas[i].length; j++){

                if(celdas[i][j] == 0){

                    lista.add(new int[]{i, j});

                }

            }

        }

        return lista;

    }


    public int verificarGanador(){

        for (int i = 0; i < 3; i++) {

            if (celdas[i][0] != 0 && celdas[i][0] == celdas[i][1] && celdas[i][1] == celdas[i][2]) {

                return celdas[i][0];

            }

        }

        for (int j = 0; j < 3; j++) {

            if (celdas[0][j] != 0 && celdas[0][j] == celdas[1][j] && celdas[1][j] == celdas[2][j]) {

                return celdas[0][j];

            }

        }
        
        if (celdas[0][0] != 0 && celdas[0][0] == celdas[1][1] && celdas[1][1] == celdas[2][2]) {

            return celdas[0][0];

        }

        if (celdas[0][2] != 0 && celdas[0][2] == celdas[1][1] && celdas[1][1] == celdas[2][0]) {

            return celdas[0][2];

        }

        return 0;

    }
    public boolean estaLleno(){

        return obtenerCasillasVacias().isEmpty();

    }

    public void marcarCasillas(int fila, int columna, char simbolo){} // por implementar


}
