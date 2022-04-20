package game.bee;

import game.SweetSwarm;
import game.honeycomb.Honeycomb;
import game.object.Object;
import game.object.Resource;

import java.awt.Point;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public abstract class Bee extends JLabel {
    protected int[] cell = new int[2];
    protected int health; // bee health
    protected int power; // bee power
    protected Point target = new Point(); // bee destination
    protected String status; // bee status
    protected List<String> states = List.of("dead", "looking", "attacking", "collecting"); // bee states

    // getters and setters

    public int[] getCell() {
        return cell;
    }

    public void setCell(int cellRow, int cellColum) {
        this.cell[0] = cellRow;
        this.cell[1] = cellColum;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Point getTarget() {
        return target;
    }

    public void setTarget(int xPosition, int yPosition) {
        this.target.x = xPosition;
        this.target.y = yPosition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String beeStatus) {
        String source;
        int stateIndex = states.indexOf(beeStatus);
        if (stateIndex > 0 && stateIndex < 3) {
            // looking for resource or attacking state
            source = "src/resources/img/__bee-" + getClass().getSimpleName() + "-0.png";
        } else if (stateIndex == 3) {
            // collecting the resource state
            source = "src/resources/img/__bee-" + getClass().getSimpleName() + "-1.png";
        } else {
            // die
            source = "src/resources/img/__null.png";
        }
        setIcon(new ImageIcon(source));
        setBounds(getLocation().x, getLocation().y, getIcon().getIconWidth(), getIcon().getIconHeight());
        status = states.get(stateIndex);
    }

    public List<String> getStates() {
        return states;
    }

    public void collect(Resource resource, SweetSwarm sweetSwarm) {
        if (resource.getResistance() > 0) {
            resource.setResistance(resource.getResistance() - 1);
            resource.updateStatus();
            if (resource.getResistance() == 0) {
                sweetSwarm.remove(resource); // remove the resource from Sweet Swarm window
                sweetSwarm.honeycomb.getMap()[resource.getCell()[0]][resource.getCell()[1]].setEntity("Cell"); // set the Honeycomb Cell available
                sweetSwarm.resources.remove(resource); // remove the resource from Sweet Swarm objects list
                setTarget(sweetSwarm.base[0].getX(), sweetSwarm.base[0].getY()); // move to honeycomb base main cell (center) SweetWarm.base[0]
                setStatus(getStates().get(3)); // status = collecting
            }
        }
        System.out.println(Arrays.toString(resource.getCell()) + " | " +resource.getLocation() + " | " + resource.getResistance() + " | " + resource.getIcon());
    }

    private void getRoute(int resultX, int resultY) {
        if (resultX > 0) {
            this.setCell(this.getCell()[0] - 1, this.cell[1]);
        }
        if (resultX < 0) {
            this.setCell(this.getCell()[0] + 1, this.cell[1]);
        }
        if (resultY < 0) {
            this.setCell(this.getCell()[0], this.cell[1] + 1);
        }
        if (resultY > 0) {
            this.setCell(this.getCell()[0], this.cell[1] - 1);
        }
    }

    public void moveToCollect(Resource resource, Honeycomb honeycomb, SweetSwarm sweetSwarm) {
        int resultX = this.getCell()[0] - resource.getCell()[0];
        int resultY = this.getCell()[1] - resource.getCell()[1];

        if ((1 - Math.abs(resultX) == 0 & 1 - Math.abs(resultY) == 0) | (1 - Math.abs(resultX) == 0 & resultY == 0) | (resultX == 0 & 1 - Math.abs(resultY) == 0)) {
            collect(resource, sweetSwarm);
        } else {
            getRoute(resultX, resultY);
        }
        this.setLocation(honeycomb.getMap()[this.getCell()[0]][this.getCell()[1]].getX(), honeycomb.getMap()[this.getCell()[0]][this.getCell()[1]].getY()); // calc next position (cell)
    }

    public void moveToCenter(Point base, Honeycomb honeycomb) {
        int resultX = this.getCell()[0] - base.x;
        int resultY = this.getCell()[1] - base.y;
//        System.out.println("Results x = " + resultX + " y = " + resultY);
        if ((1 - Math.abs(resultX) == 0 & 1 - Math.abs(resultY) == 0) | (1 - Math.abs(resultX) == 0 & resultY == 0) | (resultX == 0 & 1 - Math.abs(resultY) == 0)) {
            this.setStatus(getStates().get(1));
        } else {
            getRoute(resultX, resultY);
        }
        this.setLocation(honeycomb.getMap()[this.getCell()[0]][this.getCell()[1]].getX(), honeycomb.getMap()[this.getCell()[0]][this.getCell()[1]].getY());
    }

    public abstract void controller(SweetSwarm sweetSwarm);
}
//        if(resultX < 1 & resultY == 0){
//            this.setCell(this.getCell()[0]+1,this.cell[1]);
//        }
//        if(resultX > 1 & resultY == 0){
//            this.setCell(this.getCell()[0]-1,this.cell[1]);
//        }
//        if(resultX == 0 & resultY < 1){
//            this.setCell(this.getCell()[0],this.cell[1]+1);
//        }
//        if(resultX ==0 & resultY > 1){
//            this.setCell(this.getCell()[0],this.cell[1]-1);
//        }
//        if(resultX > 1 & resultY > 1){
//            this.setCell(this.getCell()[0]-1,this.cell[1]-1);
//        }
//        if(resultX < 1 & resultY < 1) {
//            this.setCell(this.getCell()[0]+1,this.cell[1]+1);
//        }
//        if(resultX > 1 & resultY < 1) {
//            this.setCell(this.getCell()[0]-1,this.cell[1]+1);
//        }
//        if(resultX < 1 & resultY > 1) {
//            this.setCell(this.getCell()[0]+1,this.cell[1]-1);
//        }



