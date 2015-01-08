package org.maxmati.games.life.ui;

import org.gnome.gtk.*;
import org.maxmati.games.life.Board;
import org.maxmati.games.life.TickTimerTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Timer;

/**
 * Created by maxmati on 12/13/14.
 */
public class ControlPanel {
    private final Timer timer = new Timer();

    private final Window window;

    private final Board board;
    private final SpinButton widthEntry;
    private final SpinButton heightEntry;
    private final Button setBoardSizeButton;
    private final Button tickButton;
    private final Button tickStartButton;
    private final Button tickStopButton;
    private final Scale tickRateScale;
    private final Adjustment tickRate;
    private final Button stateSaveButton;
    private final Button changeRulesButton;
    private final RulesDialog changeRulesDialog;
    private TickTimerTask tickTask;

    public ControlPanel(Builder builder, final Board board, final Window window, RulesDialog changeRulesDialog) {
        this.board = board;
        this.window = window;
        this.changeRulesDialog = changeRulesDialog;


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

        tickRate = (Adjustment) builder.getObject("tickRate");

        tickRateScale = (Scale) builder.getObject("tickRateScale");

        tickButton = (Button) builder.getObject("tickButton");
        tickButton.connect(new Button.Clicked() {
            @Override
            public void onClicked(Button source) {
                ControlPanel.this.board.tick();
            }
        });

        tickStopButton = (Button) builder.getObject("tickStopButton");
        tickStopButton.setSensitive(false);
        tickStopButton.connect(new Button.Clicked() {
            @Override
            public void onClicked(Button source) {
                tickTask.cancel();
                tickTask = null;
                tickStopButton.setSensitive(false);
                tickStartButton.setSensitive(true);
                tickButton.setSensitive(true);
                tickRateScale.setSensitive(true);
            }
        });

        tickStartButton = (Button) builder.getObject("tickStartButton");
        tickStartButton.connect(new Button.Clicked() {
            @Override
            public void onClicked(Button source) {
                tickTask = new TickTimerTask(board);
                timer.scheduleAtFixedRate(tickTask, 0, (long) (tickRate.getValue() * 1000));
                tickStartButton.setSensitive(false);
                tickButton.setSensitive(false);
                tickRateScale.setSensitive(false);
                tickStopButton.setSensitive(true);
            }
        });

        stateSaveButton = (Button) builder.getObject("saveButton");
        stateSaveButton.connect(new Button.Clicked() {
            @Override
            public void onClicked(Button source) {
                FileChooserDialog chooser = new FileChooserDialog(
                        "Select file to save",
                        window,
                        FileChooserAction.SAVE
                );


                if (chooser.run() == ResponseType.OK) {
                    try {
                        board.saveState(new File(chooser.getFilename()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        changeRulesButton = (Button) builder.getObject("changeRulesButton");
        changeRulesButton.connect(new Button.Clicked() {
            @Override
            public void onClicked(Button source) {
                ControlPanel.this.changeRulesDialog.run();
            }
        });

    }
}
