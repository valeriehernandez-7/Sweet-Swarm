package game.bee;

import game.SweetSwarm;
import game.object.Threat;

import java.awt.*;

/**
 *
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
    public void attackResponse(SweetSwarm sweetSwarm){
        for(Threat e: sweetSwarm.threats){
            if(this.getCell().equals(e.getCell())){
                e.setResistance(e.getResistance()-this.getPower());
                this.setHealth(this.getHealth()-e.getPower());
                if(e.getResistance()==0){
                    sweetSwarm.remove(e); // remove the resource from Sweet Swarm window
                    sweetSwarm.honeycomb.getMap()[e.getCell()[0]][e.getCell()[1]].setEntity("Cell"); // set the Honeycomb Cell available
                    sweetSwarm.resources.remove(e);
                    this.setStatus(this.getStates().get(1));
                }
                if(this.getHealth()==0){
                    sweetSwarm.remove(this); // remove the resource from Sweet Swarm window
                    sweetSwarm.honeycomb.getMap()[this.getCell()[0]][this.getCell()[1]].setEntity("Cell"); // set the Honeycomb Cell available
                    sweetSwarm.resources.remove(this);
                }
                break;
            }
        }
    }

}