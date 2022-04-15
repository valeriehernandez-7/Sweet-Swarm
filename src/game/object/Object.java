package game.object;

import javax.swing.JLabel;
// import java.util.Random;


/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public abstract class Object extends JLabel {
    protected int resistance; // object resistance
    protected int points; // object score points

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

    protected int getRandomInteger(int origin, int bound) {
        return (int) Math.floor(Math.random() * (bound - origin + 1) + origin);
    }

    public abstract void setStatus(boolean available);
}