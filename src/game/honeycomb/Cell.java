package game.honeycomb;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Cell extends JLabel {
    private boolean available;
    private String entity = "Empty";

    public Cell(boolean visible) {
        setIcon(new ImageIcon("src/resources/img/__honeycomb-item.png"));
        setVisible(visible);
        setAvailable(visible);
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println("⬢\t" + getEntity() + " [" + getX() + "," + getY() + "]");
            }
        });
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        setBounds(x, y, getIcon().getIconWidth(), getIcon().getIconHeight());
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}