package org.maxmati.games.life.ui;

import org.freedesktop.cairo.Context;
import org.gnome.gdk.Rectangle;
import org.gnome.gtk.DrawingArea;
import org.gnome.gtk.Widget;
import org.maxmati.games.life.Board;
import org.maxmati.games.life.OnResizeListener;
import org.maxmati.games.life.OnTickListener;

/**
 * Created by maxmati on 12/12/14.
 */
public class BoardArea implements Widget.Draw, Widget.SizeAllocate, OnTickListener, OnResizeListener {
    private final DrawingArea area;
    private final Board board;
    private int areaWidth;
    private int areaHeight;


    public BoardArea(DrawingArea area, Board board) {
        this.area = area;
        this.board = board;

        board.setOnTickListener(this);
        board.setOnResizeListener(this);

        area.connect((Widget.Draw) this);
        area.connect((Widget.SizeAllocate) this);

        areaHeight = area.getAllocatedHeight();
        areaWidth = area.getAllocatedWidth();

    }

    @Override
    public boolean onDraw(Widget source, Context cr) {

        double rectangleWidth = areaWidth / (double) board.getWidth();
        double rectangleHeight = areaHeight / (double) board.getHeight();
        cr.setLineWidth(2);
        for (int i = 0; i < board.getWidth(); ++i)
            for (int j = 0; j < board.getHeight(); ++j) {
                cr.rectangle(i * rectangleWidth, j * rectangleHeight, rectangleWidth, rectangleHeight);
                cr.setSource(0.5, 0.5, 0.5);
                cr.strokePreserve();

                if (board.getCellState(i, j))
                    cr.setSource(0, 1, 0);
                else
                    cr.setSource(1, 1, 1);

                cr.fill();
            }

        return false;
    }

    @Override
    public void onSizeAllocate(Widget source, Rectangle allocation) {
        areaHeight = area.getAllocatedHeight();
        areaWidth = area.getAllocatedWidth();
    }

    @Override
    public void onTick() {
        area.queueDraw();
    }

    @Override
    public void resize(int width, int height) {
        area.queueDraw();
    }
}
