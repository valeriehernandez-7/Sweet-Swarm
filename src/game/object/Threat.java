package game.object;

import javax.swing.ImageIcon;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Threat extends Object {
    private int power;

    public Threat(int xPosition, int yPosition, int row, int column) {
        this.setCell(row, column);
        this.setPower(2);
        this.setResistance(10);
        this.setLocation(xPosition, yPosition);
        this.setPoints(1000);
        this.setIcon(new ImageIcon("src/resources/img/__object-Threat-" + this.getRandomInteger(3) + ".png"));
        this.setBounds(getLocation().x, getLocation().y, getIcon().getIconWidth(), getIcon().getIconHeight());
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}