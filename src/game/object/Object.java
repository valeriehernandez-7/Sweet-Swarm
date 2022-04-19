package game.object;

import javax.swing.JLabel;
import java.util.Random;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public abstract class Object extends JLabel {
    protected int[] cell = new int[2];
    protected int resistance; // object resistance
    protected int points; // object score points

    public int[] getCell() {
        return cell;
    }

    public void setCell(int cellRow, int cellColum) {
        this.cell[0] = cellRow;
        this.cell[1] = cellColum;
    }

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

    protected int getRandomInteger(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }
}