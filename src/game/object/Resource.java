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
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
        updateStatus();
    }

    private void updateStatus() {
        String source;
        if (resistance > 0) {
            source = "src/resources/img/__object-Resource-" + this.id + "-" + this.getResistance() + ".png"; // common state
        } else {
            source = "src/resources/img/__null.png"; // destroyed or not available state
        }
        setIcon(new ImageIcon(source));
        setBounds(getLocation().x, getLocation().y, getIcon().getIconWidth(), getIcon().getIconHeight());
    }
}