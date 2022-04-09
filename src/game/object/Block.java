package game.object;

import javax.swing.ImageIcon;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Block extends Object {

    public Block(int xPosition, int yPosition, boolean visible) {
        this.setPosition(xPosition, yPosition);
        this.setStatus(visible);
    }

    @Override
    public void setStatus(boolean available) {
        String source;
        if (available) {
            // common state
            source = "src/resources/img/__object-Block.png";
        } else {
            // destroyed or not available state
            source = "src/resources/img/__null.png";
        }
        setIcon(new ImageIcon(source));
    }
}