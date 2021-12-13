package com.company.main.GUI;

import com.company.main.Game.Coords;
import com.company.main.Game.Minesweeper;

import java.util.Scanner;

public class ConsoleGUI implements MinesweeperGUI{
    Minesweeper minesMatrix;
    Scanner scan;

    public ConsoleGUI(int width, int height, int numMines){
        minesMatrix = new Minesweeper(width,height,numMines);
        scan = new Scanner(System.in);
    }

    public void run() {
        while(!minesMatrix.isExploded() && !minesMatrix.victory()){
            update();
            selectCell();
        }
        if(minesMatrix.isExploded()){
            update();
            System.out.println("You hit a mine and lost");
        }else if(minesMatrix.victory()){
            update();
            System.out.println("You won, you located all the non-mine cells");
        }

    }

    public void selectCell() {
        System.out.print("Select x-pos : ");
        int x = scan.nextInt();
        System.out.print("Select y-pos : ");
        int y = scan.nextInt();
        minesMatrix.select(new Coords(x,y));
    }

    public void update() {
        boolean[] openedCells = minesMatrix.getVisitedCells();
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
