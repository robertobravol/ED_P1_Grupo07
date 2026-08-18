package com.example.ed_p1_grupo07;

import org.junit.Test;
import static org.junit.Assert.*;

import com.espol.ed_p1_grupo07.model.Board;
import com.espol.ed_p1_grupo07.tree.TreeNode;
import com.espol.ed_p1_grupo07.tree.Tree;

import java.util.List;

public class PrubaTree {

    @Test
    public void probarGeneracionDeEstados() {

        System.out.println("PRUEBA");

        Board boardInicial = new Board();
        boardInicial.marcarCasillas(1, 1, 1); // X en el centro

        TreeNode nodoRaiz = new TreeNode(boardInicial, -1);
        Tree arbolJuego = new Tree(nodoRaiz);

        arbolJuego.generalEstadosPosibles();
        List<Tree> subArbolesGenerados = nodoRaiz.getHijos();


        System.out.println("Se generaron " + subArbolesGenerados.size() + " sub-árboles.");

        // Validación automática (Si no son 8, la prueba marcará un error rojo)
        assertEquals(8, subArbolesGenerados.size());


        System.out.println("Board del primer estado generado:");
        //subArbolesGenerados.get(0).getRoot().getEstado().imprimirTablero();
    }

}
