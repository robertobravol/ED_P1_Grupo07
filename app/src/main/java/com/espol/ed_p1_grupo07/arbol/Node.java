package com.espol.ed_p1_grupo07.arbol;

import com.espol.ed_p1_grupo07.Tablero;

import java.util.ArrayList;
import java.util.List;

public class Node {

    Tablero estado;
    private List<Tree> hijos;
    int utilidad;
    int jugadorEnTurno;

    public Node(Tablero estado, int utilidad, int jugadorEnTurno) {

        this.hijos = new ArrayList<>();
        this.estado = estado;
        this.utilidad = utilidad;
        this.jugadorEnTurno = jugadorEnTurno;

    }

    public Node(Tablero estado, int jugadorEnTurno) {

        this.estado = estado;
        this.jugadorEnTurno = jugadorEnTurno;

    }


    public Tablero getEstado() {

        return estado;

    }

    public void setEstado(Tablero estado) {

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

