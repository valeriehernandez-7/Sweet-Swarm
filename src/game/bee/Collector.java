package game.bee;

import game.SweetSwarm;
import game.object.Threat;

/**
 *
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
    public void attackResponse(SweetSwarm sweetSwarm){}

    @Override
    public void controller(SweetSwarm sweetSwarm) {
        if(this.getStatus() == this.getStates().get(1)){
            this.nearestResource(sweetSwarm);
        }
        else if(this.getStatus() == this.getStates().get(2)){
            this.attackResponse(sweetSwarm);
        }
        else if(this.getStatus() == this.getStates().get(3)){
            this.moveToCenter(sweetSwarm);
        }
    }
}