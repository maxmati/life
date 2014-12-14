package org.maxmati.games.life;

import org.junit.Assert;
import org.junit.Test;

public class BoardTest {

    @Test
    public void createBoardWithGivenSize() {
        Board board = new Board(50, 20);
        Assert.assertEquals("Board have to have same height as while creating (" + 20 + ")", 20, board.getHeight());
        Assert.assertEquals("Board have to have same width as while creating (" + 50 + ")", 50, board.getWidth());
    }

    @Test
    public void resizeBoard() {
        Board board = new Board(6, 5);
        Assert.assertEquals(5, board.getHeight());
        Assert.assertEquals(6, board.getWidth());

        board.resize(8, 7);
        Assert.assertEquals(7, board.getHeight());
        Assert.assertEquals(8, board.getWidth());
    }

    @Test
    public void gettingJustSettedCell() {
        Board board = new Board(50, 20);

        board.setCellState(1, 5, true);
        Assert.assertEquals(true, board.getCellState(1, 5));
    }

    @Test
    public void atBeginningAllCellsShouldBeDead() {
        Board board = new Board(50, 20);

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

    @Test
    public void checkOnTickListener() {
        OnTickListenerMock tickedMock = new OnTickListenerMock();
        Board board = new Board(5, 5);
        board.setOnTickListener(tickedMock);
        Assert.assertFalse(tickedMock.check());
        board.tick();
        Assert.assertTrue(tickedMock.check());

        tickedMock.reset();
        Assert.assertFalse(tickedMock.check());
        board.tick();
        Assert.assertTrue(tickedMock.check());
    }

    @Test
    public void checkOnResizeListener() {
        OnResizeListenerMock resizeMock = new OnResizeListenerMock();
        Board board = new Board(5, 5);
        board.setOnResizeListener(resizeMock);

        Assert.assertEquals(-1, resizeMock.getHeight());
        Assert.assertEquals(-1, resizeMock.getWidth());

        board.resize(7, 6);
        Assert.assertEquals(6, resizeMock.getHeight());
        Assert.assertEquals(7, resizeMock.getWidth());

        board.resize(8, 9);
        Assert.assertEquals(9, resizeMock.getHeight());
        Assert.assertEquals(8, resizeMock.getWidth());

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

    private class OnResizeListenerMock implements OnResizeListener {
        int width = -1;
        int height = -1;

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        @Override
        public void resize(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    private class OnTickListenerMock implements OnTickListener {
        boolean ticked = false;

        public void reset() {
            ticked = false;
        }

        public boolean check() {
            return ticked;
        }

        @Override
        public void onTick() {
            ticked = true;
        }
    }

}