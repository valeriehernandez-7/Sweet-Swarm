package game.bee;

/**
 *
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public class Guard extends Bee {
    /**
     * Guard Bee subclass constructor.
     * @param xPosition int
     * @param yPosition int
     * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
     */
    public Guard(int xPosition, int yPosition) {
        this.health = 6;
        this.power = 2;
        this.position[0] = xPosition;
        this.position[1] = yPosition;
        this.setStatus(this.states.get(1));
    }

    protected void controller() {}
}