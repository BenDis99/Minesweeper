package com.company.main.GUI;

import com.company.main.Game.Coords;

public interface MinesweeperGUI {
    /**
     * Method for starting the game-loop.
     */
    void run();

    /**
     * Method for selecting cell in game
     */
    void selectCell();

    /**
     * Updates the gameboard by the gameboard
     */
    void update();
}
