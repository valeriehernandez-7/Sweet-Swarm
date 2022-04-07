package game.object;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Resource extends Object {

    public Resource(int xPosition, int yPosition) {
        this.setResistance(2);
        this.setPosition(xPosition, yPosition);
        this.setPoints(100);
        this.setStatus(true);
    }
}
