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

    private Coords selectCell() {
        System.out.print("Select x-pos : ");
        int x = scan.nextInt();
        System.out.print("Select y-pos : ");
        int y = scan.nextInt();
        return new Coords(x,y);
    }
    private void print(boolean[] openedCells) {
        String gameBoard = "";
        int xMargin = Integer.toString(minesMatrix.getBoardWidth()).length();
        int yMargin = Integer.toString(minesMatrix.getBoardHeight()).length()+2;
        gameBoard += minesMatrix.getMineAmount() + " amount of mines \n";

        gameBoard += addSpacesToString("", yMargin);
        for(int x = 0; x < minesMatrix.getBoardWidth(); x++){
            gameBoard += addSpacesToString(Integer.toString(x),xMargin);
        }
        gameBoard += "\n";

        for(int y = 0; y < minesMatrix.getBoardHeight(); y++){
            gameBoard += addSpacesToString(y + ".",yMargin);
            for(int x = 0; x < minesMatrix.getBoardWidth(); x++){
                if(openedCells[minesMatrix.coordinatesToIndex(new Coords(x,y))]){
                    gameBoard += addSpacesToString(minesMatrix.getCell(new Coords(x,y)),xMargin);
                }else {
                    gameBoard += addSpacesToString("□",xMargin);
                }
            }
            gameBoard += "\n";
        }
        System.out.println(gameBoard);
    }

    private String addSpacesToString(String str, int n){
        String st = "";
        for(int i = str.length(); i<n; i++)
            st += " ";
        return str+st;
    }
}
