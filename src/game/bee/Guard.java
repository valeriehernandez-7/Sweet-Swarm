package game.bee;

import javax.swing.*;

/**
 *
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public class Guard extends Bee {
    /**
     * Guard Bee subclass constructor.
     * @param currentPosition int[][]
     * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
     */
    public Guard(JLabel beeSprite, int[][] currentPosition) {
        this.sprite = beeSprite;
        this.health = 6;
        this.power = 2;
        this.position = currentPosition;
        this.status = super.states.get(1);
    }

    protected void controller() {}
}