package org.maxmati.games.life.ui;

import org.gnome.gtk.Builder;
import org.gnome.gtk.Button;
import org.maxmati.games.life.Board;

/**
 * Created by maxmati on 12/13/14.
 */
public class ControlPanel {
    private final Button tickButton;
    private final Board board;

    public ControlPanel(Builder builder, Board board) {
        this.board = board;

        tickButton = (Button) builder.getObject("tickButton");
        tickButton.connect(new Button.Clicked() {
            @Override
            public void onClicked(Button source) {
                ControlPanel.this.board.tick();
            }
        });
    }
}
