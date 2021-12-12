package com.company.main;

import java.util.Scanner;

public class ConsoleGame {
    Minesweeper minesMatrix;
    Scanner scan;

    public ConsoleGame(int width, int height, int numMines){
        minesMatrix = new Minesweeper(width,height,numMines);
        scan = new Scanner(System.in);
    }

    public void run() {
        while(!minesMatrix.isExploded()){
            minesMatrix.select(selectCell());

            print(minesMatrix.getVisitedCells());

        }
    }

    private int[] selectCell() {
        int[] pos = new int[2];
        System.out.print("Select x-pos : ");
        pos[0] = scan.nextInt();
        System.out.print("Select y-pos : ");
        pos[1] = scan.nextInt();
        return pos;
    }
    private void print(boolean[] openedCells) {
        String gameBoard = "";
        gameBoard += minesMatrix.getMineAmount() + " amount of mines \n";

        gameBoard += "   ";
        for(int x = 0; x < minesMatrix.getBoardWidth(); x++){
            gameBoard += x + " ";
        }
        gameBoard += "\n";

        for(int y = 0; y < minesMatrix.getBoardHeight(); y++){
            gameBoard += y + ". ";
            for(int x = 0; x < minesMatrix.getBoardWidth(); x++){
                if(openedCells[minesMatrix.coordinatesToIndex(x,y)]){
                    gameBoard += minesMatrix.getCell(x,y) + " ";
                }else {
                    gameBoard += "□ ";
                }
            }
            gameBoard += "\n";
        }
        System.out.println(gameBoard);
    }
}
