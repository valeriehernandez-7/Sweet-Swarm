package game.object;

import javax.swing.*;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Object {
    private JLabel sprite = new JLabel(); // bee image
    private int health = 10;
    private int points;
    private int[] position = new int[2];
    private boolean available = true;

    public void setSprite(String object) {
        String source = "";
        int imageInt = (int) (Math.random()*(3-1) + 1);

        if (object.equals("Resource")) { // it's a flower
            //source = "src/resources/img/__object-resource-" + imageInt + ".png";     [esto funciona si le cambiamos los nombres a las iamgenes, asi nos evitamos hacer un if jsjs]
        } else if (object.equals("Block")) { // it's block time
            source = "src/resources/img/__object-block-1.png";
        } else { // it's a threat
            source = "src/resources/img/__object-threat-"+imageInt+".png";
        }
        sprite.setIcon(new ImageIcon(source));
    }

    public void destroy(){};

}