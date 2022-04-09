package game.honeycomb;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Cell extends JLabel {
    private boolean available;
    private Point position = new Point(); // cell position
    private String entity;

    public Cell(boolean visible) {
        setIcon(new ImageIcon("src/resources/img/__honeycomb-item.png"));
        setVisible(visible);
        setAvailable(visible);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(int xPosition, int yPosition) {
        this.position.x = xPosition;
        this.position.y = yPosition;
        setBounds(xPosition, yPosition, getIcon().getIconWidth(), getIcon().getIconHeight());
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}