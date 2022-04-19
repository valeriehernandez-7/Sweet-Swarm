package game.object;

import javax.swing.ImageIcon;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Block extends Object {

    public Block(int xPosition, int yPosition, int row, int column) {
        this.setCell(row, column);
        this.setLocation(xPosition, yPosition);
        this.setIcon(new ImageIcon("src/resources/img/__object-Block.png"));
        this.setBounds(getLocation().x, getLocation().y, getIcon().getIconWidth(), getIcon().getIconHeight());
    }
}