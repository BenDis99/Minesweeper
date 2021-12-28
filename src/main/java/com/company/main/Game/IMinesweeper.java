package com.company.main.Game;

import java.util.Set;

public interface IMinesweeper {
    /**
     * Select a cell to open
     * @param coords
     * @return list of coordinates of the opened cells
     */
    public Set<Coords> selectCell(Coords coords);

    /**
     * checks if the player has won
     * @return
     */
    public boolean victory();

    /**
     * checks if the player has lost
     * @return
     */
    public boolean lost();

    /**
     * @return return boardWidth
     */
    public int getWidth();

    /**
     * @return return boardHeight
     */
    public int getHeight();

    /**
     * Checks if the given cell is visited
     * @param cell
     * @return
     */
    public boolean isCellVisited(Coords cell);

    /**
     * gets the item of the given cell
     * @param cell
     * @return
     */
    public String getCell(Coords cell);

    /**
     * @return the last cell the user selected
     */
    public Coords getLastSelected();
}
