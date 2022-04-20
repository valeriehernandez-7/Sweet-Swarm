package game.bee;

import game.SweetSwarm;
import game.honeycomb.Honeycomb;
import game.object.Object;
import game.object.Resource;

import java.awt.Point;
import javax.swing.*;
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

    private void detectNearest(){

    }
    public void collect(Object res, SweetSwarm sweetSwarm) {
        if (res.getResistance() > 0) {
            res.setResistance(res.getResistance() - 1);
            if (res.getResistance() == 0) {
                sweetSwarm.remove(res); // remove the resource from Sweet Swarm window
                sweetSwarm.honeycomb.getMap()[res.getCell()[0]][res.getCell()[1]].setEntity("Cell"); // set the Honeycomb Cell available
                sweetSwarm.resources.remove(res); // remove the resource from Sweet Swarm objects list
            }
        }
        setTarget(sweetSwarm.base[0].getX(), sweetSwarm.base[0].getY()); // move to honeycomb base main cell (center) SweetWarm.base[0]
        setStatus(getStates().get(3)); // status = collecting
        // move to the honeycomb base
        Point temp = new Point(7,6);
        moveToCenter(temp, sweetSwarm.honeycomb);
    }


    public void moveToCollect(Resource res, Honeycomb honeycomb, SweetSwarm sweetSwarm){
        Point temp = new Point(res.getCell()[0],res.getCell()[1]);

        int resultX = this.getCell()[0] - temp.x;
        int resultY = this.getCell()[1] - temp.y;

        if((1 - Math.abs(resultX) == 0 & 1 - Math.abs(resultY) ==0)|(1 - Math.abs(resultX) == 0 & resultY ==0)|(resultX == 0 & 1 - Math.abs(resultY) ==0) ){
            collect(res,sweetSwarm);
        }
        else{
            if(resultX>0){ this.setCell(this.getCell()[0]-1,this.cell[1]);  }
            if(resultX<0){ this.setCell(this.getCell()[0]+1,this.cell[1]);  }
            if(resultY<0){ this.setCell(this.getCell()[0],this.cell[1]+1); }
            if(resultY>0){ this.setCell(this.getCell()[0],this.cell[1]-1); }
        }

        this.setLocation(honeycomb.getMap()[this.getCell()[0]][this.getCell()[1]].getX(), honeycomb.getMap()[this.getCell()[0]][this.getCell()[1]].getY()); // calc next position (cell)
    }
    public void moveToCenter(Point base, Honeycomb honeycomb){

        int resultX = this.getCell()[0] - base.x;
        int resultY = this.getCell()[1] - base.y;
        boolean x = true;

        while(x) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if((1 - Math.abs(resultX) == 0 & 1 - Math.abs(resultY) ==0)|(1 - Math.abs(resultX) == 0 & resultY ==0)|(resultX == 0 & 1 - Math.abs(resultY) ==0) ){
                x = false;
            }
            else{
                if(resultX>0){ this.setCell(this.getCell()[0]-1,this.cell[1]);  }
                if(resultX<0){ this.setCell(this.getCell()[0]+1,this.cell[1]);  }
                if(resultY<0){ this.setCell(this.getCell()[0],this.cell[1]+1); }
                if(resultY>0){ this.setCell(this.getCell()[0],this.cell[1]-1); }
            }
            this.setLocation(honeycomb.getMap()[this.getCell()[0]][this.getCell()[1]].getX(), honeycomb.getMap()[this.getCell()[0]][this.getCell()[1]].getY());
        }


        // calc next position (cell)
    }

    public abstract void controller(SweetSwarm sweetSwarm);



    //Getters and Setters

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



