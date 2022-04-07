package game.honeycomb;

import java.util.Random;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Cell {
    private boolean available;
    protected int[] position = new int[2]; // cell position

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int xPosition, int yPosition) {
        this.position[0] = xPosition;
        this.position[1] = yPosition;
    }

    public int getRandomInteger(int origin, int bound) {
        Random random = new Random();
        return random.nextInt(origin, bound + 1);
    }
}