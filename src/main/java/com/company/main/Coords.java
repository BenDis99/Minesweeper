package com.company.main;

public class Coords {
    int x;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    int y;
    public Coords(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object o){
        return (x == ((Coords) o).getX() && y == ((Coords) o).getY());
    }

    @Override
    public String toString(){
        return (x + " : " + y);
    }
}
