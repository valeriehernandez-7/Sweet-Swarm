package game.bee;

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
    public Collector(int[][] currentPosition) {
        this.health = 3;
        this.power = 1;
        this.position = currentPosition;
        this.status = this.states.get(1);
        this.setStatus(this.getClass().getSimpleName(), this.status);
    }

    protected void controller() {}
}