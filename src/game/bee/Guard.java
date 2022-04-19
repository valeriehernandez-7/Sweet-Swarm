package game.bee;

/**
 *
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public class Guard extends Bee {

    public Guard(int xPosition, int yPosition) {
        this.setHealth(6);
        this.setPower(2);
        this.setLocation(xPosition, yPosition);
        this.setStatus(this.getStates().get(1));
    }

    public void controller() {
    }
}