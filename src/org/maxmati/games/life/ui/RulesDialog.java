package org.maxmati.games.life.ui;

import org.gnome.gtk.*;
import org.maxmati.games.life.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by maxmati on 1/8/15.
 */
public class RulesDialog {
    private final Dialog dialog;
    private final CheckButton[] bornButtons = new CheckButton[9];
    private final CheckButton[] surviveButtons = new CheckButton[9];
    private final Board board;

    public RulesDialog(Builder builder, Board board) {
        this.board = board;

        dialog = (Dialog) builder.getObject("ChangeRulesDialog");

        for (int i = 0; i < 9; ++i)
            bornButtons[i] = (CheckButton) builder.getObject("born" + String.valueOf(i));

        for (int i = 0; i < 9; ++i)
            surviveButtons[i] = (CheckButton) builder.getObject("survive" + String.valueOf(i));

        Button acceptButton = (Button) builder.getObject("changeRulesAccept");
        acceptButton.connect(new Button.Clicked() {
            @Override
            public void onClicked(Button source) {
                dialog.emitResponse(ResponseType.APPLY);
                dialog.hide();
            }
        });

        Button cancelButton = (Button) builder.getObject("changeRulesCancel");
        cancelButton.connect(new Button.Clicked() {
            @Override
            public void onClicked(Button source) {
                dialog.emitResponse(ResponseType.CANCEL);
                dialog.hide();
            }
        });

    }

    public void run() {
        getRules();
        if (dialog.run() == ResponseType.APPLY)
            setRules();


    }

    private void getRules() {
        List<Integer> survive = Arrays.asList(board.getSurvive());
        for (int i = 0; i < 9; ++i)
            surviveButtons[i].setActive(survive.contains(i));

        List<Integer> born = Arrays.asList(board.getBorn());
        for (int i = 0; i < 9; ++i)
            bornButtons[i].setActive(born.contains(i));
    }

    private void setRules() {
        List<Integer> born = new ArrayList<Integer>();
        for (int i = 0; i < 9; ++i)
            if (bornButtons[i].getActive())
                born.add(i);

        List<Integer> survive = new ArrayList<Integer>();
        for (int i = 0; i < 9; ++i)
            if (surviveButtons[i].getActive())
                survive.add(i);

        Integer[] surviveArr = new Integer[survive.size()];
        Integer[] bornArr = new Integer[born.size()];
        board.changeRules(survive.toArray(surviveArr), born.toArray(bornArr));

    }
}
