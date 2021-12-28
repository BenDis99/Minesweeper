package com.company.main.Game;

import java.util.*;

/**
 * MinesweeperBoard
 * It manages the cells of the game-matrix-board.
 * M is a mine and any numbered cell has that amount of
 * mines in neighbouring cells.
 */
public class Minesweeper implements IMinesweeper {
    String[] gameBoard;
    boolean[] visitedCells;
    int amountVisited;

    Coords lastSelected;

    int boardWidth;
    int boardHeight;
    int mineAmount;

    boolean exploded;

    public int getMineAmount() {
        return mineAmount;
    }

    public boolean isExploded() {
        return exploded;
    }

    public boolean[] getVisitedCells() {
        return visitedCells;
    }

    public void visitCell(Coords cell){
        if(!visitedCells[coordinatesToIndex(cell)]){
            visitedCells[coordinatesToIndex(cell)] = true;
            amountVisited++;
        }
    }

    public Minesweeper(int width, int height, int mineAmount){
        this.mineAmount = mineAmount;
        this.boardWidth = width;
        this.boardHeight = height;
        amountVisited = 0;
        exploded = false;
        gameBoard = new String[width*height];
        visitedCells = new boolean[width*height];
        for(int b = 0; b < gameBoard.length; b++){
            gameBoard[b] = "";
            visitedCells[b] = false;}
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
        exploded = false;
        amountVisited = 0;
        visitedCells = new boolean[width*boardHeight];
        mineAmount = 0;
        for(int b = 0; b < gameBoard.length; b++){
            if(gameBoard[b].equals("M"))
                mineAmount++;
            visitedCells[b] = false;
        }
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
            if(!gameBoard[minePos].equals("M")) {
                gameBoard[minePos] = "M";
                i--;
            }
        }
    }
    public void setCellNumberByAmountOfBombNeighbours(){
        for(int x = 0 ; x < boardWidth ; x++){
            for(int y = 0 ; y < boardHeight ; y++){
                if(getCell(new Coords(x,y)).equals("M")){
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
            if (getCell(pos).equals("M")) {
                count++;
            }
        }
        return count;
    }
    private HashSet<Coords> getNeighbouringCells(Coords cell){
        int x = cell.getX();
        int y = cell.getY();
        int[][] neighPos = {{x - 1, y - 1}, {x - 1, y},
                {x - 1, y + 1}, {x, y - 1},
                {x, y + 1}, {x + 1, y - 1},
                {x + 1, y}, {x + 1, y + 1}};
        HashSet<Coords> neighbours = new HashSet();
        for(int[] coords: neighPos){
            Coords c = new Coords(coords[0],coords[1]);
            if(onBoard(c))
                neighbours.add(c);
        }
        return neighbours;
    }

    public Coords getLastSelected() {
        return lastSelected;
    }

    private Set<Coords> setMinesVisible() {
        Set<Coords> mines = new HashSet<>();
        for(int y = 0; y < boardHeight; y++){
            for(int x = 0; x < boardWidth; x++){
                Coords xy = new Coords(x,y);
                if(getCell(xy).equals("M")) {
                    visitCell(xy);
                    mines.add(xy);
                }
            }
        }
        return mines;
    }

    /**
     * Loops through neighbouring "0"-cells and marks them as visited
     * @param cell
     */
    private Set<Coords> openVisitedByEmptyNeighbouringCells(Coords cell){
        LinkedList<Coords> searchNeighbours = new LinkedList<>();
        Set<Coords> searched = new HashSet<>();
        searchNeighbours.add(cell);
        while(!searchNeighbours.isEmpty()){
            Coords search = searchNeighbours.pop();
            searched.add(search);
            for(Coords coords : getNeighbouringCells(search)){
                if(!searched.contains(coords)){
                    if(getCell(coords).equals("0")){
                        searchNeighbours.add(coords);
                    } else if (!getCell(coords).equals("M")){
                        searched.add(coords);
                    }
                }
            }
        }
        for(Coords visited : searched){
            visitCell(visited);
        }
        return searched;
    }

    @Override
    public Set<Coords> selectCell(Coords cell) {
        Set<Coords> visited = new HashSet<>();
        if(!exploded || !victory())
            if(visitedCells[coordinatesToIndex(cell)])
                return visited;
        visitCell(cell);
        lastSelected = cell;
        String selected = getCell(cell);
        if(selected.equals("M")){
            exploded = true;
            return setMinesVisible();
        }else if(selected.equals("0")) {
            return openVisitedByEmptyNeighbouringCells(cell);
        }
        visited.add(cell);
        return visited;
    }

    public boolean victory() {
        return (!exploded && amountVisited == boardWidth*boardHeight-mineAmount);
    }

    @Override
    public boolean lost() {
        return exploded || victory();
    }

    @Override
    public int getWidth() {
        return boardWidth;
    }

    @Override
    public int getHeight() {
        return boardHeight;
    }

    @Override
    public boolean isCellVisited(Coords cell){
        return visitedCells[coordinatesToIndex(cell)];
    }
}
