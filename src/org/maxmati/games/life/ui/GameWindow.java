package org.maxmati.games.life.ui;

import org.gnome.gdk.Event;
import org.gnome.gtk.*;
import org.maxmati.games.life.Board;

/**
 * Created by maxmati on 11/20/14.
 */
public class GameWindow {

    private final Window window;
    private final BoardArea boardArea;
    private final ControlPanel panel;


    public GameWindow(Builder builder, final Board board) {
        window = (Window) builder.getObject("window");
        window.showAll();
        window.showAll();

        boardArea = new BoardArea((DrawingArea) builder.getObject("board"), board);

        panel = new ControlPanel(builder, board, window, new RulesDialog(builder, board));

        window.connect(new Window.DeleteEvent() {
            @Override
            public boolean onDeleteEvent(Widget source, Event event) {
                Gtk.mainQuit();
                return false;
            }
        });
    }
}
