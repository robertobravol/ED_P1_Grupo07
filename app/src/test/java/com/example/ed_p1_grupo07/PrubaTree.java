package com.example.ed_p1_grupo07;

import org.junit.Test;
import static org.junit.Assert.*;

import com.espol.ed_p1_grupo07.Tablero;
import com.espol.ed_p1_grupo07.arbol.Node;
import com.espol.ed_p1_grupo07.arbol.Tree;

import java.util.List;

public class PrubaTree {

    @Test
    public void probarGeneracionDeEstados() {

        System.out.println("PRUEBA");

        Tablero tableroInicial = new Tablero();
        tableroInicial.marcarCasillas(1, 1, 1); // X en el centro

        Node nodoRaiz = new Node(tableroInicial, -1);
        Tree arbolJuego = new Tree(nodoRaiz);

        arbolJuego.generalEstadosPosibles();
        List<Tree> subArbolesGenerados = nodoRaiz.getHijos();


        System.out.println("Se generaron " + subArbolesGenerados.size() + " sub-árboles.");

        // Validación automática (Si no son 8, la prueba marcará un error rojo)
        assertEquals(8, subArbolesGenerados.size());


        System.out.println("Tablero del primer estado generado:");
        //subArbolesGenerados.get(0).getRoot().getEstado().imprimirTablero();
    }

}
