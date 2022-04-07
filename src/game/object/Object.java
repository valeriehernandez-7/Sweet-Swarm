package game.object;

import game.honeycomb.Cell;

import javax.swing.JLabel;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public abstract class Object extends Cell {
    protected JLabel sprite = new JLabel(); // object image
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

    public abstract void setStatus(boolean available);
}