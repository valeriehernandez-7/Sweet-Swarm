package game.object;

import javax.swing.*;
import java.util.Random;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public class Object {
    protected JLabel sprite = new JLabel(); // object image
    protected int resistance; // object resistance
    protected int[] position = new int[2]; // object position
    protected int points; // object score points

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int xPosition, int yPosition) {
        this.position[0] = xPosition;
        this.position[1] = yPosition;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setStatus(boolean available) {
        String source;
        if (available) {
            // common state
            if (!getClass().getSimpleName().equals("Block")) {
                source = "src/resources/img/__object-" + getClass().getSimpleName() + "-" + getRandomInteger(1, 3) + ".png";
            } else {
                source = "src/resources/img/__object-" + getClass().getSimpleName() + "-1.png";
            }
        } else {
            // destroyed or not available state
            source = "src/resources/img/__null.png";
        }
        sprite.setIcon(new ImageIcon(source));
    }

    private int getRandomInteger(int origin, int bound) {
        Random random = new Random();
        return random.nextInt(origin, bound + 1);
    }
}