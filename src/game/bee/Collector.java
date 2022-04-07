package game.bee;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public class Collector extends Bee {

    public Collector(int xPosition, int yPosition) {
        this.setHealth(3);
        this.setPower(1);
        this.setPosition(xPosition, yPosition);
        this.setStatus(this.getStates().get(1));
    }

    public void controller() {
    }
}