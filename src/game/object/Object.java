package game.object;

import java.awt.Point;
import javax.swing.JLabel;
import java.util.Random;


/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public abstract class Object {
    protected JLabel sprite = new JLabel(); // object image
    protected int resistance; // object resistance
    protected int points; // object score points
    protected Point position = new Point(); // object position

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(int xPosition, int yPosition) {
        this.position.x = xPosition;
        this.position.y = yPosition;
    }

    protected int getRandomInteger(int origin, int bound) {
        Random random = new Random();
        return random.nextInt(origin, bound + 1);
    }

    public abstract void setStatus(boolean available);
}