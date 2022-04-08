package game.object;

import javax.swing.ImageIcon;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Threat extends Object {
    private final int id;
    private int power;

    public Threat(int xPosition, int yPosition) {
        this.id = this.getRandomInteger(1, 3);
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

    @Override
    public void setStatus(boolean available) {
        String source;
        if (available) {
            // common state
            source = "src/resources/img/__object-Threat-" + this.id + ".png";
        } else {
            // destroyed or not available state
            source = "src/resources/img/__null.png";
        }
        this.sprite.setIcon(new ImageIcon(source));
    }
}