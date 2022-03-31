package game.bee;

import javax.swing.*;
import java.util.*;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public abstract class Bee {
    protected JLabel sprite; // bee image
    protected int health; // bee health
    protected int power; // bee power
    protected int[][] position; // bee position
    protected int[][] target; // bee destination
    protected String status; // bee status
    protected List<String> states = List.of("dead", "looking", "attacking", "collecting"); // bee states

    /**
     *
     * @param beeName String
     * @param beeStatus String
     * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
     */
    protected void setSprite(String beeName, String beeStatus) {
        if (Objects.equals(beeName, "Collector") || Objects.equals(beeName, "Guard")) {
            if (states.contains(beeStatus)) {
                String source = "";
                int stateIndex = states.indexOf(beeStatus);
                if (stateIndex > 0 && stateIndex < 3) { // looking for resource or attacking state
                    source = "src/resources/img/__bee-"+ beeName +"-0.png";
                } else if (stateIndex == 3) { // collecting the resource state
                    source = "src/resources/img/__bee-"+ beeName +"-1.png";
                } else { // die
                    source = "src/resources/img/__honeycomb-item.png";
                }
                sprite.setIcon(new ImageIcon(source));
            } else {
                System.out.println(beeStatus + " state is not a Bee state.");
            }
        } else {
            System.out.println(beeName + " class is not a Bee subclass.");
        }
    }

    /**
     *
     * @param destiny int[][]
     * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
     */
    protected void move(int[][] destiny) {
        target = destiny;
    }

    /**
     *
     * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
     */
    protected abstract void controller();
}