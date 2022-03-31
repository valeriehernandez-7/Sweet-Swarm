package game.bee;

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
    public Guard(int[][] currentPosition) {
        this.health = 6;
        this.power = 2;
        this.position = currentPosition;
        this.status = this.states.get(1);
        this.setStatus(this.getClass().getSimpleName(), this.status);
    }

    protected void controller() {}
}