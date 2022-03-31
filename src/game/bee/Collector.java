package game.bee;

/**
 *
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public class Collector extends Bee {
    /**
     * Collector Bee subclass constructor.
     * @param xPosition int
     * @param yPosition int
     * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
     */
    public Collector(int xPosition, int yPosition) {
        this.health = 3;
        this.power = 1;
        this.position[0] = xPosition;
        this.position[1] = yPosition;
        this.setStatus(this.states.get(1));
    }

    protected void controller() {}
}