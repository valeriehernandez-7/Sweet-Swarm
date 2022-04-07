package game.object;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Threat extends Object {
    private int power;

    public Threat(int xPosition, int yPosition) {
        this.setPower(2);
        this.setResistance(10);
        this.setPosition(xPosition, yPosition);
        this.setPoints(1000);
        this.setStatus(true);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}