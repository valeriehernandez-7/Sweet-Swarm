package game.bee;

import game.SweetSwarm;
import game.object.Threat;

import java.awt.Point;

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
        for (Threat threat : sweetSwarm.threats) {
            if (this.getTarget().equals(new Point(threat.getCell()[0], threat.getCell()[1]))) {
                Point[] neighborsList = sweetSwarm.honeycomb.getNeighbors(new Point(this.getCell()[0], this.getCell()[1]));
                for (Point neighbor : neighborsList) {
                    if (sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].isAvailable()) {
                        sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].setEntity("Cell");
                        this.setCell(neighbor.x, neighbor.y);
                        sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].setEntity("Bee");
                        this.setLocation(sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].getX(), sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].getY());
                        System.out.println("⬢\tMOVE TO R[" + getCell()[0] + "] C[" + getCell()[1] + "]");
                        if (!isReacting()) {
                            this.setStatus(this.getStates().get(5));
                        }
                        break;
                    }
                }
            }
        }
    }
}