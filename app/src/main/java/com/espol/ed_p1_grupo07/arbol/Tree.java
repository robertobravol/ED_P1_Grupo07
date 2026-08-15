package com.espol.ed_p1_grupo07.arbol;

import com.espol.ed_p1_grupo07.Tablero;

import java.util.ArrayList;
import java.util.List;

public class Tree {

    private Node root;

    public Tree(Node root) {

        this.root = root;

    }

    public Tree() {

        this.root = null;

    }

    public void generalEstadosPosibles(){

        ArrayList<Tree> nuevosHijos = new ArrayList<>();
        List<int[]> casillasVacias = root.getEstado().obtenerCasillasVacias();

        for(int i = 0; i < casillasVacias.size(); i++){

            int[] coordenada = casillasVacias.get(i);
            int fila = coordenada[0];
            int columna = coordenada[1];

            Tablero nuevoTablero = root.getEstado().clonarTablero();

            nuevoTablero.marcarCasillas(fila, columna, root.getJugadorEnTurno());

            Node nuevoNodo = new Node(nuevoTablero, cambiarJugadorEnTurno(root.getJugadorEnTurno()));

            Tree nuevoArbol = new Tree(nuevoNodo);

            nuevosHijos.add(nuevoArbol);
        }

        root.setHijos(nuevosHijos);


    }


    public int cambiarJugadorEnTurno(int i){

        if(i == 1){

            return -1;

        }

        if( i == -1 ){

            return 1;

        }

        return 0;

    }

    public Node getRoot() {

        return root;

    }

    public void setRoot(Node root) {

        this.root = root;

    }
    
}
