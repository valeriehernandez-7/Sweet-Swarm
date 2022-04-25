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
            if (this.getCell() == threat.getCell()) {
                this.setHealth(this.getHealth() - threat.getPower());
                Point[] neighborsList = sweetSwarm.honeycomb.getNeighbors(new Point(this.getCell()[0],this.getCell()[1]));
                for(Point neighbor: neighborsList){
                    if(sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].isAvailable()){
                        this.setLocation(sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].getX(),sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].getY());
                    }
                    break;
                }
            }
        }
    }
}