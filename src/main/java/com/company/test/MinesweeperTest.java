package com.company.test;

import com.company.main.Game.Coords;
import com.company.main.Game.Minesweeper;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class MinesweeperTest {
    String[] gameBoard = {"M","M","M",
                            ""  ,  ""  ,  ""  ,
                            ""  ,  ""  ,  ""  };
    Minesweeper game;
    @Before
    public void setup(){
        game = new Minesweeper(gameBoard,3);
        game.setCellNumberByAmountOfBombNeighbours();
    }
    @Test
    public void testIfBombCountIsCorrect(){

        assertEquals("M",game.getCell(new Coords(0,0))); // Does not count itself
        assertEquals("M",game.getCell(new Coords(1,0))); // Does not count itself
        assertEquals("M",game.getCell(new Coords(2,0))); // Does not count itself
        assertEquals("2",game.getCell(new Coords(0,1)));
        assertEquals("3",game.getCell(new Coords(1,1)));
        assertEquals("2",game.getCell(new Coords(2,1)));
        assertEquals("0",game.getCell(new Coords(0,2)));
        assertEquals("0",game.getCell(new Coords(1,2)));
        assertEquals("0",game.getCell(new Coords(2,2)));

    }
    @Test
    public void testIfVisitedCellsOpenNeighbouringCellsWithNoNeighbouringMines(){
        Coords pos = new Coords(0,2);
        game.select(pos);
        int count = 0;
        for(boolean visited : game.getVisitedCells()){
            if(visited)
                count++;
        }
        assertEquals(6, count);
        assertEquals(true,game.victory());
    }
    @Test
    public void testIfExplodedWhenChoosingAMineCell(){
        Coords pos = new Coords(1,0);
        game.select(pos);
        assertEquals(true,game.isExploded());
    }
}
