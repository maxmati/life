package org.maxmati.games.life.ui;

import org.gnome.gtk.Builder;
import org.gnome.gtk.Button;
import org.gnome.gtk.SpinButton;
import org.maxmati.games.life.Board;

/**
 * Created by maxmati on 12/13/14.
 */
public class ControlPanel {
    private final Button tickButton;
    private final Board board;
    private final SpinButton widthEntry;
    private final SpinButton heightEntry;
    private final Button setBoardSizeButton;

    public ControlPanel(Builder builder, Board board) {
        this.board = board;

        tickButton = (Button) builder.getObject("tickButton");
        tickButton.connect(new Button.Clicked() {
            @Override
            public void onClicked(Button source) {
                ControlPanel.this.board.tick();
            }
        });

        widthEntry = (SpinButton) builder.getObject("boardWidth");
        widthEntry.setValue(board.getWidth());

        heightEntry = (SpinButton) builder.getObject("boardHeight");
        heightEntry.setValue(board.getWidth());

        setBoardSizeButton = (Button) builder.getObject("setBoardSizeButton");
        setBoardSizeButton.connect(new Button.Clicked() {
            @Override
            public void onClicked(Button source) {
                ControlPanel.this.board.resize(
                        Integer.valueOf(widthEntry.getText()), Integer.valueOf(heightEntry.getText())
                );
            }
        });

    }
}
