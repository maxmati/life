package org.maxmati.games.life;

import org.junit.Assert;
import org.junit.Test;

public class CellTest {

    @Test
    public void cellShouldHoldTheirState() {
        Cell cell = new Cell();

        cell.setAlive(true);
        Assert.assertEquals(true, cell.getAlive());

        cell.setAlive(false);
        Assert.assertEquals(false, cell.getAlive());

    }

}