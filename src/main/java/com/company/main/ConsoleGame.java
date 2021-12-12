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
            print(minesMatrix.getVisitedCells());
            minesMatrix.select(selectCell());
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
        int xMargin = Integer.toString(minesMatrix.getBoardWidth()).length();
        int yMargin = Integer.toString(minesMatrix.getBoardHeight()).length()+2;
        gameBoard += minesMatrix.getMineAmount() + " amount of mines \n";

        gameBoard += addSpaces("", yMargin);
        for(int x = 0; x < minesMatrix.getBoardWidth(); x++){
            gameBoard += addSpaces(Integer.toString(x),xMargin);
        }
        gameBoard += "\n";

        for(int y = 0; y < minesMatrix.getBoardHeight(); y++){
            gameBoard += addSpaces(y + ".",yMargin);
            for(int x = 0; x < minesMatrix.getBoardWidth(); x++){
                if(openedCells[minesMatrix.coordinatesToIndex(x,y)]){
                    gameBoard += addSpaces(minesMatrix.getCell(x,y),xMargin);
                }else {
                    gameBoard += addSpaces("□",xMargin);
                }
            }
            gameBoard += "\n";
        }
        System.out.println(gameBoard);
    }
    private String addSpaces(String str, int n){
        String st = "";
        for(int i = str.length(); i<n; i++)
            st += " ";
        return str+st;
    }
}
