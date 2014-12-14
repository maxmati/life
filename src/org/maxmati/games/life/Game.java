package org.maxmati.games.life;

import org.gnome.gtk.Builder;
import org.gnome.gtk.Gtk;
import org.maxmati.games.life.ui.GameWindow;

import java.io.FileNotFoundException;
import java.text.ParseException;

/**
 * Created by maxmati on 11/20/14.
 */
public class Game {

    static Board board = new Board(20, 20);

    public static void main(String[] args) {
        Gtk.init(args);

        board.setCellState(1, 2, true);
        board.setCellState(2, 3, true);
        board.setCellState(3, 1, true);
        board.setCellState(3, 2, true);
        board.setCellState(3, 3, true);


        Builder builder = new Builder();

        try {
            builder.addFromFile("main.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        GameWindow gameWindow = new GameWindow(builder, board);
        Gtk.main();
    }
}
