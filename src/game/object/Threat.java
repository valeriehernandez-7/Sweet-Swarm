package game.object;

import game.SweetSwarm;
import game.bee.Bee;

import javax.swing.ImageIcon;
import java.awt.Point;

/**
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Threat extends Object {
    private int power;

    public Threat(int xPosition, int yPosition, int row, int column) {
        this.setCell(row, column);
        this.setPower(1);
        this.setResistance(10);
        this.setLocation(xPosition, yPosition);
        this.setPoints(1000);
        this.setIcon(new ImageIcon("src/resources/img/__object-Threat-" + this.getRandomInteger(3) + ".png"));
        this.setBounds(getLocation().x, getLocation().y, getIcon().getIconWidth(), getIcon().getIconHeight());
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void attackBee(SweetSwarm sweetSwarm) {
        Point[] threatNeighbors = sweetSwarm.honeycomb.getNeighbors(new Point(this.getCell()[0], this.getCell()[1]));
        for (Bee bee : sweetSwarm.bees) {
            for (Point point : threatNeighbors) {
                if (new Point(bee.getCell()[0], bee.getCell()[1]).equals(point)) {
                    System.out.println("\n⬢⬢⬢\t\uD83D\uDC1D " + bee.getClass().getSimpleName().toUpperCase() + " BEE " + sweetSwarm.bees.indexOf(bee) + "\t⬢⬢⬢");
                    bee.setHealth(bee.getHealth() - getPower());
                    System.out.println("⬢\tUNDER ATTACK BY THREAT " + sweetSwarm.threats.indexOf(this));
                    if (bee.getHealth() <= 0) {
                        bee.setStatus(bee.getStates().get(0));
                        sweetSwarm.honeycomb.getMap()[bee.getCell()[0]][bee.getCell()[1]].setEntity("Cell"); // set the Honeycomb Cell available
                        sweetSwarm.remove(bee); // remove bee from Sweet Swarm window
                        sweetSwarm.repaint();
                        System.out.println("⬢\tDEAD");
                        try {
                            Thread.sleep(sweetSwarm.speed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}