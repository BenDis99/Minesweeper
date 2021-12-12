package com.company.main;

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
    public Minesweeper(String[] gameBoard, int width){
        this.gameBoard = gameBoard;
        this.boardWidth = width;
        this.boardHeight = gameBoard.length/width;
    }
    public int coordinatesToIndex(int x, int y){return x+y*boardWidth;}

    public boolean onBoard(int x, int y){
        return (x >= 0 && x < boardWidth && y >= 0 && y < boardHeight);
    }

    public String getCell(int x, int y){
        if(onBoard(x,y)){
            return gameBoard[coordinatesToIndex(x,y)];
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
                if(getCell(x,y) == "M"){
                    continue;
                }
                gameBoard[x+y*boardWidth] = Integer.toString(numberOfMineNeighbours(x,y));
            }
        }
    }

    private int numberOfMineNeighbours(int x, int y) {
        int count = 0;
        int[][] neighPos = {{x - 1, y - 1}, {x - 1, y}, {x - 1, y + 1}, {x, y - 1}, {x, y + 1}, {x + 1, y - 1}, {x + 1, y}, {x + 1, y + 1}};
        for (int[] pos : neighPos) {
            if (onBoard(pos[0], pos[1]) && getCell(pos[0],pos[1]) == "M") {
                count++;
            }
        }
        return count;
    }

    public void select(int[] pos) {
        String selected = getCell(pos[0], pos[1]);
        if(selected == "M"){
            exploded = true;
        }
        visitedCells[coordinatesToIndex(pos[0], pos[1])] = true;

    }


}
