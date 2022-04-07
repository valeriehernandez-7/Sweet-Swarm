package game.object;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Block extends Object {

    public Block(int xPosition, int yPosition, boolean visible) {
        this.setPosition(xPosition, yPosition);
        this.setStatus(visible);
    }
}