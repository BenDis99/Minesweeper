package com.company.test;

import com.company.main.Minesweeper;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class MinesweeperTest {
    String[] gameBoard = {"M","M","M",
                            ""  ,  ""  ,  ""  ,
                            ""  ,  ""  ,  ""  };
    @Test
    public void testIfBomCountIsCorrect(){
        Minesweeper game = new Minesweeper(gameBoard,3);
        game.setCellNumberByAmountOfBombNeighbours();
        assertEquals("M",game.getCell(0,0)); // Does not count itself
        assertEquals("M",game.getCell(1,0)); // Does not count itself
        assertEquals("M",game.getCell(2,0)); // Does not count itself
        assertEquals("2",game.getCell(0,1));
        assertEquals("3",game.getCell(1,1));
        assertEquals("2",game.getCell(2,1));
        assertEquals("0",game.getCell(0,2));
        assertEquals("0",game.getCell(1,2));
        assertEquals("0",game.getCell(2,2));

    }
}
