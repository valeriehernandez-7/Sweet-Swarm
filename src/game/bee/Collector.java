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
    public void attackResponse(SweetSwarm sweetSwarm){

    }

    @Override
    public void controller(SweetSwarm sweetSwarm) {
        switch(this.getStatus()){
            case "looking" -> {this.nearestResource(sweetSwarm);}
            case "collecting" -> {this.moveToCenter(sweetSwarm);}
            case "attacking" -> {this.attackResponse(sweetSwarm);}
        }
    }
}