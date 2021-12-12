package com.company.main;

import java.util.Random;

public class Minesweeper {
    String[] gameBoard;
    int boardWidth;
    int boardHeight;
    int mineAmount;

    public Minesweeper(int width, int height, int mineAmount){
        this.mineAmount = mineAmount;
        this.boardWidth = width;
        this.boardHeight = height;
        gameBoard = new String[width*height];

    }
    public Minesweeper(String[] gameBoard, int width){
        this.gameBoard = gameBoard;
        this.boardWidth = width;
        this.boardHeight = gameBoard.length/width;
    }

    public boolean onBoard(int x, int y){
        return (x >= 0 && x < boardWidth && y >= 0 && y < boardHeight);
    }

    public String getCell(int x, int y){
        if(onBoard(x,y)){
            System.out.println(x + " : " + y + " and " + (x+y*boardWidth));
            return gameBoard[x+y*boardWidth];
        }
        return null;
    }

    public void setMinesRandomly(){
        int i = mineAmount;
        Random rand = new Random();
        while(i>0){
            int minePos = rand.nextInt(boardWidth*boardHeight);
            if(gameBoard[minePos] != "MINE" ) {
                gameBoard[minePos] = "MINE";
                i--;
            }
        }
    }
    public void setCellNumberByAmountOfBombNeighbours(){
        for(int x = 0 ; x < boardWidth ; x++){
            for(int y = 0 ; y < boardHeight ; y++){
                if(getCell(x,y) == "MINE"){
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
            if (onBoard(pos[0], pos[1]) && getCell(pos[0],pos[1]) == "MINE") {
                count++;
            }
        }
        return count;
    }
}
