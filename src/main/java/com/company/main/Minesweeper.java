package com.company.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * MinesweeperBoard
 * It manages the cells of the game-matrix-board.
 * M is a mine and any numbered cell has that amount of
 * mines in neighbouring cells.
 */
public class Minesweeper {
    String[] gameBoard;
    boolean[] visitedCells;

    int boardWidth;
    int boardHeight;
    int mineAmount;

    boolean exploded;

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getMineAmount() {
        return mineAmount;
    }

    public boolean isExploded() {
        return exploded;
    }
    public boolean[] getVisitedCells() {
        return visitedCells;
    }

    public Minesweeper(int width, int height, int mineAmount){
        this.mineAmount = mineAmount;
        this.boardWidth = width;
        this.boardHeight = height;
        exploded = false;
        gameBoard = new String[width*height];
        visitedCells = new boolean[width*height];
        for(int b = 0; b < visitedCells.length; b++){ visitedCells[b] = false;}
        setMinesRandomly();
        setCellNumberByAmountOfBombNeighbours();

    }

    /**
     * Constructer where you can set board manually
     * Used for testing
     * @param gameBoard - manually set gameboard
     * @param width     - width of board - it figures the height
     */
    public Minesweeper(String[] gameBoard, int width){
        this.gameBoard = gameBoard;
        this.boardWidth = width;
        this.boardHeight = gameBoard.length/width;
        visitedCells = new boolean[width*boardHeight];
        for(int b = 0; b < visitedCells.length; b++){ visitedCells[b] = false;}
    }
    public int coordinatesToIndex(Coords cell){return cell.getX()+cell.getY()*boardWidth;}

    public boolean onBoard(Coords cell){
        int x = cell.getX();
        int y = cell.getY();
        return (x >= 0 && x < boardWidth && y >= 0 && y < boardHeight);
    }

    public String getCell(Coords cell){
        if(onBoard(cell)){
            return gameBoard[coordinatesToIndex(cell)];
        }
        return null;
    }

    public void setMinesRandomly(){
        int i = mineAmount;
        Random rand = new Random();
        while(i>0){
            int minePos = rand.nextInt(boardWidth*boardHeight);
            if(gameBoard[minePos] != "M" ) {
                gameBoard[minePos] = "M";
                i--;
            }
        }
    }
    public void setCellNumberByAmountOfBombNeighbours(){
        for(int x = 0 ; x < boardWidth ; x++){
            for(int y = 0 ; y < boardHeight ; y++){
                if(getCell(new Coords(x,y)) == "M"){
                    continue;
                }
                gameBoard[x+y*boardWidth] = Integer.toString(numberOfMineNeighbours(new Coords(x,y)));
            }
        }
    }

    private int numberOfMineNeighbours(Coords cell) {
        int count = 0;
        HashSet<Coords> neighPos = getNeighbouringCells(cell);
        for (Coords pos : neighPos) {
            if (getCell(pos) == "M") {
                count++;
            }
        }
        return count;
    }
    private HashSet<Coords> getNeighbouringCells(Coords cell){
        int x = cell.getX();
        int y = cell.getY();
        int[][] neighPos = {{x - 1, y - 1}, {x - 1, y}, {x - 1, y + 1}, {x, y - 1}, {x, y + 1}, {x + 1, y - 1}, {x + 1, y}, {x + 1, y + 1}};
        HashSet<Coords> neighbours = new HashSet();
        for(int[] coords: neighPos){
            Coords c = new Coords(coords[0],coords[1]);
            if(onBoard(c))
                neighbours.add(c);
        }
        return neighbours;
    }

    public void select(Coords cell) {
        String selected = getCell(cell);
        if(selected == "M"){
            exploded = true;
        }
        visitedCells[coordinatesToIndex(cell)] = true;

    }


}
