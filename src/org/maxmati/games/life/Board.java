package org.maxmati.games.life;


import java.util.Arrays;

/**
 * Created by maxmati on 11/7/14.
 */
public class Board {
    private final int height;
    private final int width;
    private Cell[] cells;
    private Integer[] born = {3};
    private Integer[] survive = {2, 3};

    public Board(int height, int width) {
        this.height = height;
        this.width = width;

        cells = new Cell[height * width];
        for (int i = 0; i < height * width; ++i)
            cells[i] = new Cell();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private Cell getCell(int x, int y) {
        return cells[y * width + x];
    }

    public void setCellState(int x, int y, boolean alive) {
        getCell(x, y).setAlive(alive);
    }

    public Boolean getCellState(int x, int y) {
        return getCell(x, y).getAlive();
    }

    public void tick() {
        Cell[] tmpCells = new Cell[getHeight() * getWidth()];
        for (int i = 0; i < getHeight() * getHeight(); ++i) tmpCells[i] = new Cell();

        for (int i = 0; i < getWidth(); ++i)
            for (int j = 0; j < getHeight(); ++j) {
                int aliveCellsAround = countAliveCellsAround(i, j);
                if (getCellState(i, j) && Arrays.asList(survive).contains(aliveCellsAround))
                    tmpCells[i + j * getHeight()].setAlive(true);
                if (!getCellState(i, j) && Arrays.asList(born).contains(aliveCellsAround))
                    tmpCells[i + j * getHeight()].setAlive(true);
            }

        cells = tmpCells;
    }

    private int countAliveCellsAround(int x, int y) {
        int count = 0;
        for (int i = -1; i < 2; ++i)
            for (int j = -1; j < 2; ++j) {
                if (x + i < 0 || x + i >= getWidth()) continue;
                if (y + j < 0 || y + j >= getHeight()) continue;
                if (i == 0 && j == 0) continue;
                if (getCellState(x + i, y + j)) ++count;
            }
        return count;
    }

    public void changeRules(Integer[] survive, Integer[] born) {
        this.survive = survive;
        this.born = born;
    }
}