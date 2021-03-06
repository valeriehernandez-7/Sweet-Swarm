package game.bee;

import game.SweetSwarm;
import game.object.Threat;

import java.awt.Point;

/**
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public class Guard extends Bee {

    public Guard(int xPosition, int yPosition, int row, int column) {
        this.setCell(row, column);
        this.setHealth(6);
        this.setPower(5);
        this.setLocation(xPosition, yPosition);
        this.setStatus(this.getStates().get(1));
    }
    private  boolean isNeighbor(SweetSwarm sweetSwarm){
        Point[] originNeighbors = sweetSwarm.honeycomb.getNeighbors(new Point(this.getCell()[0], this.getCell()[1]));
        for (Point neighbors : originNeighbors) {
            if (this.getTarget().equals(neighbors)) {
                return true;
            }
        }
        return  false;
    }

    @Override
    public void attackResponse(SweetSwarm sweetSwarm) {
        boolean isNeighbor = isNeighbor(sweetSwarm);

        if (!isNeighbor) {
            this.moveToNextCell(sweetSwarm);
            isNeighbor = isNeighbor(sweetSwarm);
        }
        if (isNeighbor) {
            for (Threat threat : sweetSwarm.threats) {
                if (this.getTarget().equals(new Point(threat.getCell()[0], threat.getCell()[1]))) {
                    if (!isReacting()) {
                        threat.setResistance(threat.getResistance() - this.getPower());
                    } else {
                        setReacting(false);
                    }
                    System.out.println(threat.getResistance());
                    if (threat.getResistance() <= 0) {
                        sweetSwarm.honeycomb.getMap()[threat.getCell()[0]][threat.getCell()[1]].setEntity("Cell"); // set the Honeycomb Cell available
                        sweetSwarm.respawnThreats(sweetSwarm,threat);
                        sweetSwarm.repaint();
                        System.out.println("⬢\tDESTROYED THREAT " + sweetSwarm.threats.indexOf(threat));
                        if (this.isCollecting()) {
                            this.setStatus(this.getStates().get(3));
                        } else {
                            this.setStatus(this.getStates().get(1));
                        }
                    }
                    break;
                }
            }
        } else {
            if (this.isCollecting()) {
                this.setStatus(this.getStates().get(3));
            } else {
                this.setStatus(this.getStates().get(1));
            }
        }
    }
}