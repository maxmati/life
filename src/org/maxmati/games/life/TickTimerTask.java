package org.maxmati.games.life;

import java.util.TimerTask;

/**
 * Created by maxmati on 12/14/14.
 */
public class TickTimerTask extends TimerTask {

    private final Board board;

    public TickTimerTask(Board board) {
        this.board = board;
    }

    @Override
    public void run() {
        this.board.tick();
    }
}
