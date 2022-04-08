package game.honeycomb;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Cell {
    private JLabel sprite = new JLabel(); // cell image
    private boolean available = true;
    private Point position = new Point(); // cell position
    private String entity;

    public Cell(int xPosition, int yPosition) {
        setPosition(xPosition, yPosition);
        spriteSetup(true);
    }

    public void spriteSetup(boolean visible) {
        sprite.setIcon(new ImageIcon("src/resources/img/__honeycomb-item.png"));
        sprite.setBounds(getPosition().x, getPosition().y, this.sprite.getIcon().getIconWidth(), this.sprite.getIcon().getIconHeight());
        sprite.setVisible(visible);
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
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}