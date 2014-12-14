package org.maxmati.games.life.ui;

import org.gnome.gdk.Event;
import org.gnome.gtk.*;
import org.maxmati.games.life.Board;


/**
 * Created by maxmati on 11/20/14.
 */
public class GameWindow extends org.gnome.gtk.Window {
    private final Board board;
    private Table table;
    private Entry heightEntry;
    private Entry widthEntry;

    public GameWindow(Board board) {
        this.board = board;
        setTitle("Convay Life - Mateusz Nowotyński");

        connect(new org.gnome.gtk.Window.DeleteEvent() {
            @Override
            public boolean onDeleteEvent(Widget source, Event event) {
                Gtk.mainQuit();
                return false;
            }

        });

        setDefaultSize(250, 150);
        setPosition(WindowPosition.CENTER);

        initUI(board.getHeight(), board.getWidth());

    }


    private void initUI(int width, int height) {
        if (getChild() != null)
            remove(getChild());//TODO:

        HBox hbox1 = new HBox(false, 5);

        initTable(height, width);

        hbox1.packStart(table, false, true, 0);


        VBox menuContainer = new VBox(false, 5);

        HBox boardSizeContainer = new HBox(false, 5);
        heightEntry = new Entry();
        heightEntry.setText(String.valueOf(height));
        heightEntry.setWidthChars(2);

        widthEntry = new Entry();
        widthEntry.setText(String.valueOf(width));
        widthEntry.setWidthChars(2);

        boardSizeContainer.packStart(widthEntry, false, false, 0);
        boardSizeContainer.packStart(new Label("x"), false, false, 0);
        boardSizeContainer.packStart(heightEntry, false, false, 0);
        Button setButton = new Button("Set");
        setButton.connect(new Button.Clicked() {
            @Override
            public void onClicked(Button source) {
                Integer height = Integer.valueOf(heightEntry.getText());
                Integer width = Integer.valueOf(widthEntry.getText());
                GameWindow.this.board.resize(height, width);
                GameWindow.this.initUI(height, width);//TODO;
            }
        });
        boardSizeContainer.packStart(setButton, false, false, 0);
        menuContainer.packStart(boardSizeContainer, false, false, 0);

        HBox tickContainer = new HBox(false, 5);
        tickContainer.packStart(new Button("Tick"), false, false, 0);
        tickContainer.packStart(new Button("Start"), false, false, 0);
        tickContainer.packStart(new HScale(1, 20, 1), true, true, 0);
        tickContainer.packStart(new Button("Pause"), false, false, 0);
        menuContainer.packStart(tickContainer, false, true, 0);


        hbox1.packStart(menuContainer, false, false, 0);
        add(hbox1);

        showAll();
    }

    private void initTable(int x, int y) {
        table = new Table(x, y, true);

        for (int i = 0; i < x; ++i)
            for (int j = 0; j < y; ++j) {
                CheckButton button = new CheckButton();
                table.attach(button, i, i + 1, j, j + 1);
            }
    }
}
