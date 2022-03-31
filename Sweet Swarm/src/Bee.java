import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Bee {


    protected int health;                 //health of bee
    protected int [][] beePosition;       //Position of bee
    protected String status;              //Status of bee (alive,guarding,etc)

    protected JLabel SetSprite(String beeImagePath) throws IOException { //returns JLabel with image of bee
        JLabel bee = new JLabel(new ImageIcon(beeImagePath));
        return bee;
    }
    protected void move(int[][] positionsToMove){};
    protected abstract void controller();
}
