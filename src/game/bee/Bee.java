package game.bee;

import javax.swing.*;
import java.util.*;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public abstract class Bee {
    public JLabel sprite = new JLabel(); // bee image
    public int health; // bee health
    public int power; // bee power
    public int[] position = new int[2]; // bee position
    public int[] target = new int[2]; // bee destination
    public String status; // bee status
    public List<String> states = List.of("dead", "looking", "attacking", "collecting"); // bee states

    /**
     *
     * @param beeStatus String
     * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
     */
    public void setStatus(String beeStatus) {
        String source;
        int stateIndex = states.indexOf(beeStatus);
        if (stateIndex > 0 && stateIndex < 3) { // looking for resource or attacking state
            source = "src/resources/img/__bee-" + getClass().getSimpleName() + "-0.png";
        } else if (stateIndex == 3) { // collecting the resource state
            source = "src/resources/img/__bee-" + getClass().getSimpleName() + "-1.png";
        } else { // die
            source = "src/resources/img/__honeycomb-item.png";
        }
        sprite.setIcon(new ImageIcon(source));
        status = states.get(stateIndex);
    }

    /**
     *
     * @param xPosition int
     * @param yPosition int
     * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
     */
    public void move(int xPosition, int yPosition) {
        target[0] = xPosition;
        target[1] = yPosition;
    }

    /**
     * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
     */
    protected abstract void controller();
}