package com.javarush.games.minesweeper;

public class GameObject {
    public int x;
    public int y;
    public boolean isMine;
    public boolean isOpen;
    public boolean isFlag;
    public int countMineNeighbors;

    public GameObject(int x, int y, boolean m) {
        this.x = x;
        this.y = y;
        this.isMine = m;
    }
}
