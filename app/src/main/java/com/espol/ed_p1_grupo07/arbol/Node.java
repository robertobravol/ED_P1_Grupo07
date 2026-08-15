package com.espol.ed_p1_grupo07.arbol;

import java.util.ArrayList;

public class Node {

    private ArrayList<Tree> hijos;

    public Node(ArrayList<Tree> hijos) {

        this.hijos = hijos;

    }

    public ArrayList<Tree> getHijos() {

        return hijos;

    }

    public void setHijos(ArrayList<Tree> hijos) {

        this.hijos = hijos;

    }

}

