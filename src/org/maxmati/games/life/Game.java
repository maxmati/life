package org.maxmati.games.life;

import org.gnome.gtk.Builder;
import org.gnome.gtk.DrawingArea;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Window;
import org.maxmati.games.life.ui.BoardArea;
import org.maxmati.games.life.ui.ControlPanel;

import java.io.FileNotFoundException;
import java.text.ParseException;

/**
 * Created by maxmati on 11/20/14.
 */
public class Game {

    static Board board = new Board(20, 20);

    public static void main(String[] args) {
        Gtk.init(args);

        board.setCellState(2, 2, true);
        board.setCellState(1, 2, true);
        board.setCellState(3, 2, true);


        Builder builder = new Builder();

        try {
            builder.addFromFile("main.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Window window = (Window) builder.getObject("window");
        window.showAll();

        DrawingArea area = (DrawingArea) builder.getObject("board");
        BoardArea boardArea = new BoardArea(area, board);


        ControlPanel panel = new ControlPanel(builder, board);


        //new GameWindow(board);
        Gtk.main();
    }
}
