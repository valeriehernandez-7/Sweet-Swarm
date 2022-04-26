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
        Point[] originNeighbors = sweetSwarm.honeycomb.getNeighbors(new Point(this.getCell()[0], this.getCell()[1]));
        boolean isNeighbor = false;
        for (Point neighbors : originNeighbors) {
            System.out.println("----Start-----");
            System.out.println("----Original Point: "+new Point(this.getCell()[0],this.getCell()[1])+" -----");
            System.out.println(this.getTarget());
            System.out.println(neighbors);
            System.out.println("----End-----");
            if (this.getTarget().equals(neighbors)) {
                isNeighbor = true;
                break;
            }
        }
        if(!isNeighbor){
            //this.moveToCollect(this.getTarget(),sweetSwarm);
            System.out.println("NO PASO AA");
        }

        if (isNeighbor) {
            for (Threat threat : sweetSwarm.threats) {
                if (this.getTarget().equals(new Point(threat.getCell()[0], threat.getCell()[1]))) {
                    threat.setResistance(threat.getResistance() - this.getPower());
                    this.setHealth(this.getHealth() - threat.getPower());
                    if (threat.getResistance() == 0) {
                        sweetSwarm.remove(threat); // remove threat from Sweet Swarm window
                        sweetSwarm.honeycomb.getMap()[threat.getCell()[0]][threat.getCell()[1]].setEntity("Cell"); // set the Honeycomb Cell available
                        sweetSwarm.threats.remove(threat);
                        if (this.isCollecting()) {
                            this.setStatus(this.getStates().get(3));
                        } else {
                            this.setStatus(this.getStates().get(1));
                        }
                    }
                    if (this.getHealth() == 0) {
                        System.out.println("Dead Guard "+this.isCollecting());
                        sweetSwarm.honeycomb.getMap()[this.getCell()[0]][this.getCell()[1]].setEntity("Cell"); // set the Honeycomb Cell available
                        sweetSwarm.remove(this); // remove bee from Sweet Swarm window
                        this.setStatus(this.getStates().get(0));

                    }
                    break;
                }
            }
        }
    }
}