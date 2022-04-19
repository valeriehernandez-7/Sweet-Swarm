package game.object;

import javax.swing.ImageIcon;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Resource extends Object {
    private final int id;

    public Resource(int xPosition, int yPosition) {
        this.id = this.getRandomInteger(3);
        this.setResistance(2);
        this.setLocation(xPosition, yPosition);
        this.setPoints(100);
        this.updateStatus();
    }

    public void updateStatus() {
        setIcon(new ImageIcon("src/resources/img/__object-Resource-" + this.id + "-" + this.getResistance() + ".png"));
        setBounds(getLocation().x, getLocation().y, getIcon().getIconWidth(), getIcon().getIconHeight());
    }
}