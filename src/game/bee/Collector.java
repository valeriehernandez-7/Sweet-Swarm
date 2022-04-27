package game.bee;

import game.SweetSwarm;
import game.object.Threat;

import java.awt.*;

/**
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public class Collector extends Bee {

    public Collector(int xPosition, int yPosition, int row, int column) {
        this.setCell(row, column);
        this.setHealth(3);
        this.setPower(1);
        this.setLocation(xPosition, yPosition);

        this.setStatus(this.getStates().get(1));
    }

    @Override
    public void attackResponse(SweetSwarm sweetSwarm) {
        for(Threat threat: sweetSwarm.threats){
            if (this.getTarget().equals(new Point(threat.getCell()[0], threat.getCell()[1]))) {
                this.setHealth(this.getHealth() - threat.getPower());
                System.out.println(this.getHealth());
                if(this.getHealth()<=0){
                    System.out.println("Dead Collector"+this.isCollecting());
                    sweetSwarm.honeycomb.getMap()[this.getCell()[0]][this.getCell()[1]].setEntity("Cell"); // set the Honeycomb Cell available
                    this.setStatus(this.getStates().get(0));
                    sweetSwarm.remove(this);
                    break;
                }
                Point[] neighborsList = sweetSwarm.honeycomb.getNeighbors(new Point(this.getCell()[0],this.getCell()[1]));


                for(Point neighbor: neighborsList){
                    if(sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].isAvailable()){
                        sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].setEntity("Cell");
                        this.setCell(neighbor.x, neighbor.y);
                        sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].setEntity("Bee");
                        this.setLocation(sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].getX(),sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].getY());
                        this.setStatus(this.getStates().get(5));
                        break;
                    }
                }
            }
        }
    }
}