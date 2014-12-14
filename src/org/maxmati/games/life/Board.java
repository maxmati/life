package org.maxmati.games.life;


import java.util.Arrays;

/**
 * Created by maxmati on 11/7/14.
 */
public class Board {
    private int height;
    private int width;
    private Cell[] cells;
    private Integer[] born = {3};
    private Integer[] survive = {2, 3};
    private OnTickListener onTickListener;
    private OnResizeListener onResizeListener;

    public Board(int width, int height) {
        resize(width, height);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private Cell getCell(int x, int y) {
        return cells[x + y * width];
    }

    public void setCellState(int x, int y, boolean alive) {
        getCell(x, y).setAlive(alive);
    }

    public Boolean getCellState(int x, int y) {
        return getCell(x, y).getAlive();
    }

    public void tick() {
        Cell[] tmpCells = new Cell[getHeight() * getWidth()];
        for (int i = 0; i < getHeight() * getWidth(); ++i) tmpCells[i] = new Cell();

        for (int i = 0; i < getHeight(); ++i)
            for (int j = 0; j < getWidth(); ++j) {
                int aliveCellsAround = countAliveCellsAround(j, i);
                if (getCellState(j, i) && Arrays.asList(survive).contains(aliveCellsAround))
                    tmpCells[j + i * getWidth()].setAlive(true);
                if (!getCellState(j, i) && Arrays.asList(born).contains(aliveCellsAround))
                    tmpCells[j + i * getWidth()].setAlive(true);
            }

        cells = tmpCells;

        if (onTickListener != null) onTickListener.onTick();
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

    public void resize(int width, int height) {
        this.height = height;
        this.width = width;

        cells = new Cell[height * width];
        for (int i = 0; i < height * width; ++i)
            cells[i] = new Cell();

        if (this.onResizeListener != null) this.onResizeListener.resize(width, height);
    }

    public void setOnTickListener(OnTickListener onTickListener) {
        this.onTickListener = onTickListener;
    }

    public void setOnResizeListener(OnResizeListener onResizeListener) {
        this.onResizeListener = onResizeListener;
    }
}
