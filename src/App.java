import game.bee.Collector;
import game.bee.Guard;
import game.object.Block;
import game.object.Resource;
import game.object.Threat;
import game.honeycomb.Cell;

import java.util.Arrays;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public class App {
    public static void main(String[] args) {
        SweetSwarm sweetSwarm = new SweetSwarm();
        // bee testing
        Collector c = new Collector(1, 2);
        c.move(6, 6);
        System.out.println("\n" + c.getClass().getSimpleName() +"\nH: "+ c.getHealth() + " | P: " + c.getPower()
                + " | Pos: " + c.getPosition() + " | Tar: " + c.getTarget()
                + " | S: " + c.getStatus());
//        System.out.println(c.sprite.getIcon() + "\n");

        Guard g = new Guard(1, 6);
        g.move(7, 7);
        System.out.println("\n" + g.getClass().getSimpleName() +"\nH: "+ g.getHealth() + " | P: " + g.getPower()
                + " | Pos: " + g.getPosition() + " | Tar: " + g.getTarget() + " | S: " + g.getStatus());
//        System.out.println(g.sprite.getIcon() + "\n");

        // object testing
        Block invisible = new Block(2, 7, false);
        System.out.println("\n" + invisible.getClass().getSimpleName() +"\nPos: "+ invisible.getPosition());
//        System.out.println(invisible.sprite.getIcon() + "\n");

        Block b = new Block(2, 8, true);
        System.out.println("\n" + b.getClass().getSimpleName() +"\nPos: "+ b.getPosition());
//        System.out.println(b.sprite.getIcon() + "\n");

        Resource r = new Resource(7, 9);
        System.out.println("\n" + r.getClass().getSimpleName() +"\nR: "+ r.getResistance() + " | P: " + r.getPoints()
                + " | Pos: " + r.getPosition());
//        System.out.println(r.sprite.getIcon() + "\n");

        Threat t = new Threat(9, 6);
        System.out.println("\n" + t.getClass().getSimpleName() +"\nR: "+ t.getResistance() + " | P: " + t.getPoints()
                + " | Pos: " + t.getPosition() + " | Pow: " + t.getPower());
//        System.out.println(t.sprite.getIcon() + "\n");

        //
        Cell[][] map = new Cell[13][15];
        map[0][0] = new Cell(11,11);
        System.out.println(Arrays.deepToString(map));
    }
}