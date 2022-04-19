package game.bee;

import game.SweetSwarm;

/**
 *
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public class Guard extends Bee {

    public Guard(int xPosition, int yPosition, int row, int column) {
        this.setCell(row, column);
        this.setHealth(6);
        this.setPower(2);
        this.setLocation(xPosition, yPosition);
        this.setStatus(this.getStates().get(1));
    }

    public void controller(SweetSwarm sweetSwarm) {
    }
}