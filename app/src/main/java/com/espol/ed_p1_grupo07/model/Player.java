package com.espol.ed_p1_grupo07.model;

public class Player {
    private String name;
    private int symbol;

    public Player(String name, int symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public int getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
