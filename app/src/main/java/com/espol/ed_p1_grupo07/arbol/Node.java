package com.espol.ed_p1_grupo07.arbol;

import com.espol.ed_p1_grupo07.model.Board;

import java.util.ArrayList;
import java.util.List;

public class Node {

    Board estado;
    private List<Tree> hijos;
    int utilidad;
    int jugadorEnTurno;

    public Node(Board estado, int utilidad, int jugadorEnTurno) {

        this.hijos = new ArrayList<>();
        this.estado = estado;
        this.utilidad = utilidad;
        this.jugadorEnTurno = jugadorEnTurno;

    }

    public Node(Board estado, int jugadorEnTurno) {

        this.estado = estado;
        this.jugadorEnTurno = jugadorEnTurno;

    }


    public Board getEstado() {

        return estado;

    }

    public void setEstado(Board estado) {

        this.estado = estado;

    }

    public int getUtilidad() {

        return utilidad;

    }

    public void setUtilidad(int utilidad) {

        this.utilidad = utilidad;

    }

    public int getJugadorEnTurno() {

        return jugadorEnTurno;

    }

    public void setJugadorEnTurno(int jugadorEnTurno) {

        this.jugadorEnTurno = jugadorEnTurno;

    }

    public List<Tree> getHijos() {

        return hijos;

    }

    public void setHijos(List<Tree> hijos) {

        this.hijos = hijos;

    }

}

