package game.bee;

import game.SweetSwarm;
import game.object.Threat;

import java.awt.*;

/**
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public class Guard extends Bee {

    public Guard(int xPosition, int yPosition, int row, int column) {
        this.setCell(row, column);
        this.setHealth(6);
        this.setPower(2);
        this.setLocation(xPosition, yPosition);
        this.setStatus(this.getStates().get(1));
    }

    @Override
    public void attackResponse(SweetSwarm sweetSwarm) {
        System.out.println("Attacking");
        for (Threat threat : sweetSwarm.threats) {
            if (this.getTarget().equals(new Point(threat.getCell()[0], threat.getCell()[1]))) {
                threat.setResistance(threat.getResistance() - this.getPower());
                this.setHealth(this.getHealth() - threat.getPower());
                if (threat.getResistance() == 0) {
                    sweetSwarm.remove(threat); // remove threat from Sweet Swarm window
                    sweetSwarm.honeycomb.getMap()[threat.getCell()[0]][threat.getCell()[1]].setEntity("Cell"); // set the Honeycomb Cell available
                    sweetSwarm.threats.remove(threat);
                    this.setStatus(this.getStates().get(1));
                }
                if (this.getHealth() == 0) {
                    //sweetSwarm.bees.remove(this);
                    sweetSwarm.honeycomb.getMap()[this.getCell()[0]][this.getCell()[1]].setEntity("Cell"); // set the Honeycomb Cell available
                    sweetSwarm.remove(this); // remove bee from Sweet Swarm window
                    this.setStatus(this.getStates().get(0));

                }
                break;
            }
        }
    }
}