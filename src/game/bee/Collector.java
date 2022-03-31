package game.bee;

import javax.swing.*;

/**
 *
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public class Collector extends Bee {
    /**
     * Collector Bee subclass constructor.
     * @param currentPosition int[][]
     * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
     */
    public Collector(JLabel beeSprite, int[][] currentPosition) {
        this.sprite = beeSprite;
        this.health = 3;
        this.power = 1;
        this.position = currentPosition;
        this.status = super.states.get(1);
    }

    protected void controller() {}
}