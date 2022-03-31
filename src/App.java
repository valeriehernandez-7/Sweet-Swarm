import game.bee.Collector;
import game.bee.Guard;

import java.util.Arrays;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public class App {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("⬢ \uD83D\uDC1D Sweet Swarm \uD83D\uDC1D ⬢");
        SweetSwarm sweetSwarm = new SweetSwarm();

        // bee testing
        Collector c = new Collector(1, 2);
        System.out.println(c.getClass().getSimpleName());
        System.out.println(c.sprite.getIcon() + " | " + c.health + " | " + c.power + " | " +
                Arrays.toString(c.position) + " | " + Arrays.toString(c.target) + " | " +
                c.status);
        Guard g = new Guard(3, 9);
        System.out.println(g.getClass().getSimpleName());
        System.out.println(g.sprite.getIcon() + " | " + g.health + " | " + g.power + " | " +
                Arrays.toString(g.position) + " | " + Arrays.toString(g.target) + " | " +
                g.status);
    }
}