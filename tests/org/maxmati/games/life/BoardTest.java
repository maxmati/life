package org.maxmati.games.life;

import org.junit.Assert;
import org.junit.Test;

public class BoardTest {

    @Test
    public void createBoardWithGivenSize() {
        Board board = new Board(20, 50);
        Assert.assertEquals("Board have to have same height as while creating (" + 20 + ")", 20, board.getHeight());
        Assert.assertEquals("Board have to have same width as while creating (" + 50 + ")", 50, board.getWidth());
    }

    @Test
    public void gettingJustSettedCell() {
        Board board = new Board(20, 50);

        board.setCellState(1, 5, true);
        Assert.assertEquals(true, board.getCellState(1, 5));
    }

    @Test
    public void atBeginningAllCellsShouldBeDead() {
        Board board = new Board(20, 50);

        for (int i = 0; i < board.getHeight(); ++i)
            for (int j = 0; j < board.getWidth(); ++j)
                Assert.assertEquals(false, board.getCellState(j, i));
    }

    @Test
    public void afterTickSingleCellShouldDie() {
        boolean[][] before = {
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, true, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
        };

        boolean[][] after = {
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false},
        };

        Board board = new Board(5, 5);

        setBoard(before, board);

        board.tick();

        compareBoard(after, board);

    }

    @Test
    public void simpleOscillator() {
        boolean[][] before = {
                {false, true, false},
                {false, true, false},
                {false, true, false}
        };

        boolean[][] after = {
                {false, false, false},
                {true, true, true},
                {false, false, false}
        };

        Board board = new Board(6, 6);


        setBoard(before, board);

        board.tick();

        compareBoard(after, board);

        board.tick();

        compareBoard(before, board);

    }

    @Test
    public void constantStructure() {
        boolean[][] tub = {
                {false, true, false},
                {true, false, true},
                {false, true, false}
        };

        Board board = new Board(3, 3);

        setBoard(tub, board);
        for (int i = 0; i < 5; ++i) {
            board.tick();
            compareBoard(tub, board);
        }


    }

    @Test
    public void changeRules() {
        boolean[][] empty = {
                {false, false, false},
                {false, false, false},
                {false, false, false}
        };

        boolean[][] full = {
                {true, true, true},
                {true, true, true},
                {true, true, true}
        };


        Board board = new Board(3, 3);
        board.changeRules(new Integer[]{1, 2}, new Integer[]{0});

        setBoard(full, board);

        board.tick();

        compareBoard(empty, board);

        board.tick();

        compareBoard(full, board);

    }

    private void setBoard(boolean[][] before, Board board) {
        for (int i = 0; i < before.length; ++i)
            for (int j = 0; j < before[i].length; ++j)
                board.setCellState(j, i, before[i][j]);
    }

    private void compareBoard(boolean[][] expected, Board actual) {
        for (int i = 0; i < expected.length; ++i)
            for (int j = 0; j < expected[i].length; ++j)
                Assert.assertEquals(expected[i][j], actual.getCellState(j, i));
    }
}