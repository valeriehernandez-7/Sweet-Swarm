package game.bee;

import game.SweetSwarm;
import game.honeycomb.Honeycomb;
import game.object.Resource;

import java.awt.Point;
import javax.swing.*;
import java.util.List;

/**
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public abstract class Bee extends JLabel {
    protected int[] cell = new int[2];
    protected int health; // bee health
    protected int power; // bee power
    protected Point target = new Point(); // bee destination
    protected String status; // bee status
    protected List<String> states = List.of("dead", "looking", "reacting", "collecting", "competing"); // bee states

    //Change behaviour
    boolean wasTraspased=false;
    boolean collecting = false;

    // getters and setters

    public boolean isCollecting() {
        return collecting;
    }

    public void setCollecting(boolean collecting) {
        this.collecting = collecting;
    }

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
        if ((stateIndex > 0 && stateIndex < 3)|stateIndex==4) {
            // looking for resource or reacting state
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

    //---------------------- En of Getters and Setters ----------------------------------------------------------------
    protected Point getRoute(int row, int column, Honeycomb honeycomb) {
        Point bestCell = new Point();
        int cellRow = 0;
        int cellColumn = 0;
        if (row > 0) {
            cellRow = -1;
        } else if (row < 0) {
            cellRow = 1;
        }
        if (column < 0) {
            cellColumn = 1;
        } else if (column > 0) {
            cellColumn = -1;
        }
        bestCell.setLocation(getCell()[0] + cellRow, getCell()[1] + cellColumn);
        if (!honeycomb.getMap()[bestCell.x][bestCell.y].isAvailable()) {
            Point[] bestCellNeighbors = honeycomb.getNeighbors(bestCell);
            Point[] originCellNeighbors = honeycomb.getNeighbors(new Point(getCell()[0], getCell()[1]));
            for (Point bestCellNeighbor : bestCellNeighbors) {
                for (Point originCellNeighbor : originCellNeighbors) {
                    if (bestCellNeighbor == originCellNeighbor) {
                        bestCell.setLocation(bestCellNeighbor.x, bestCellNeighbor.y);
                    } else {
                        Point neighborAvailable = honeycomb.getNeighborAvailable(bestCellNeighbors[0]);
                        if (neighborAvailable != null) {
                            bestCell = neighborAvailable;
                        } else {
                            bestCell.setLocation(originCellNeighbors[0]);
                        }
                    }
                    break;
                }
            }
        }
        return bestCell;
    }

    protected void collect(Resource resource, SweetSwarm sweetSwarm) {
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
    }

    protected void lookForNearElements(SweetSwarm sweetSwarm,boolean collecting){
        Resource resourcePoint = detectNeighborResource(sweetSwarm,"Resource");
        Point entityPoint = detectNeighborEntity(sweetSwarm,"Threat");
        Point beePoint = detectNeighborEntity(sweetSwarm,"Bee");
        if(entityPoint!= null){
            this.setStatus(this.getStates().get(2));
            this.setTarget(entityPoint.x,entityPoint.y);
        }
        else if((resourcePoint!=null) & (!this.getStatus().equals(this.getStates().get(3)))){
            this.setCollecting(true);
            this.collect(resourcePoint,sweetSwarm);
        }
        else if(beePoint!=null){
            for(Bee bee: sweetSwarm.bees){
                if(new Point(bee.getCell()[0],bee.getCell()[1]).equals(beePoint)){
                    //if(bee.getStatus().equals(bee.getStates().get(2))){this.setTarget(bee.getTarget().x,bee.getTarget().y);this.setStatus(this.getStates().get(2));System.out.println("Guard x Attack");this.controller(sweetSwarm);break;} //Guard x Attack
                    if((this.getStatus().equals(this.getStates().get(1))) & (bee.getStatus().equals(bee.getStates().get(1)))){ //Looking x Looking
                        this.setStatus(this.getStates().get(4));
                        System.out.println("Looking x Looking");
                        break;}
                    if((this.getStatus().equals(this.getStates().get(3))) & (bee.getStatus().equals(bee.getStates().get(3)))){ //Collecting x Collecting
                        this.setStatus(this.getStates().get(4));
                        System.out.println("Collecting x Collecting");
                        break; }
                    if((this.getStatus().equals(this.getStates().get(1))) & (bee.getStatus().equals(bee.getStates().get(3)))){ //Looking x Collecting
                        if(!this.wasTraspased){
                            this.setStatus(this.getStates().get(3));
                            bee.setStatus(this.getStates().get(1));
                            System.out.println("Looking x Collecting");
                            wasTraspased=true;
                            break;}
                        break;}
                }

            }
            if(collecting){this.moveToCenter(sweetSwarm);}
            else{this.randomMovement(sweetSwarm);}
        }

        else{if(collecting){this.moveToCenter(sweetSwarm);} else{this.randomMovement(sweetSwarm);}}
    }

    protected Point detectNeighborEntity(SweetSwarm sweetSwarm, String entity) {
        Point[] neighborList = sweetSwarm.honeycomb.getNeighbors(new Point(this.getCell()[0],this.getCell()[1]));
        Point nearestEntity = null;
        for(Point neighbor : neighborList){
            if (sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].getEntity().equals(entity)){
                nearestEntity = neighbor;
            }
        }
        return nearestEntity;

    }

    protected Resource detectNeighborResource(SweetSwarm sweetSwarm, String entity) {
        Point[] neighborList = sweetSwarm.honeycomb.getNeighbors(new Point(this.getCell()[0],this.getCell()[1]));
        Point nearestEntity = null;
        for(Point neighbor : neighborList){
            if (sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].getEntity().equals(entity)){
                for(Resource resource: sweetSwarm.resources){
                    if(new Point (resource.getCell()[0],resource.getCell()[1]).equals(neighbor)){
                        return resource;
                    }
                }

            }
        }
        return null;
    }

    protected  void randomMovement(SweetSwarm sweetSwarm){
        Point[] listOfNeighbors = sweetSwarm.honeycomb.getNeighbors(new Point(this.getCell()[0],this.getCell()[1]));
        int randomInt = sweetSwarm.getRandomInteger(0,listOfNeighbors.length);

        if(sweetSwarm.honeycomb.getMap()[listOfNeighbors[randomInt].x][listOfNeighbors[randomInt].y].isAvailable()){
            sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].setEntity("Cell");
            this.setCell(listOfNeighbors[randomInt].x, listOfNeighbors[randomInt].y);
            sweetSwarm.honeycomb.getMap()[listOfNeighbors[randomInt].x][listOfNeighbors[randomInt].y].setEntity("Bee");
            this.setLocation(sweetSwarm.honeycomb.getMap()[listOfNeighbors[randomInt].x][listOfNeighbors[randomInt].y].getX(),sweetSwarm.honeycomb.getMap()[listOfNeighbors[randomInt].x][listOfNeighbors[randomInt].y].getY());
        }
        else{
            for(Point neighbor: listOfNeighbors) {
                if (sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].isAvailable()) {
                    sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].setEntity("Cell");
                    this.setCell(neighbor.x, neighbor.y);
                    sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].setEntity("Bee");
                    this.setLocation(sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].getX(),sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].getY());
                    break;
                }
            }
        }



    }

    protected void moveToCollect(Resource resource, SweetSwarm sweetSwarm) {
        int row = getCell()[0] - resource.getCell()[0];
        int column = getCell()[1] - resource.getCell()[1];
        if ((1 - Math.abs(row) == 0 & 1 - Math.abs(column) == 0) | (1 - Math.abs(row) == 0 & column == 0) | (row == 0 & 1 - Math.abs(column) == 0)) {
            collect(resource, sweetSwarm);
        } else {  // calc next position (cell)
            moveToNextCell(row, column, sweetSwarm);
        }
        setLocation(sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].getX(), sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].getY());
        //System.out.println("⬢\tMOVE TO R[" + getCell()[0] + "] C[" + getCell()[1] + "]");
    }

    protected void moveToCenter(SweetSwarm sweetSwarm) {
        int row = getCell()[0] - sweetSwarm.center.x;
        int column = getCell()[1] - sweetSwarm.center.y;
        if ((1 - Math.abs(row) == 0 & 1 - Math.abs(column) == 0) | (1 - Math.abs(row) == 0 & column == 0) | (row == 0 & 1 - Math.abs(column) == 0)) {
            setStatus(getStates().get(1));
            setCollecting(false);
            this.wasTraspased=false;
            sweetSwarm.score += 100;
        } else {  // calc next position (cell)
            moveToNextCell(row, column, sweetSwarm);
        }
        setLocation(sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].getX(), sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].getY());
        //System.out.println("⬢\tMOVE TO R[" + getCell()[0] + "] C[" + getCell()[1] + "]");
    }

    public void controller(SweetSwarm sweetSwarm) {
        switch (this.getStatus()) {
            //case "looking" -> this.nearestResource(sweetSwarm);
            case "looking" -> this.lookForNearElements(sweetSwarm,false);
            case "collecting" -> this.lookForNearElements(sweetSwarm,true);
            case "reacting" -> this.attackResponse(sweetSwarm);
        }
    }

    protected void moveToNextCell(int row, int column, SweetSwarm sweetSwarm) {
        Point nextCell = getRoute(row, column, sweetSwarm.honeycomb);
        sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].setEntity("Cell");
        setCell(nextCell.x, nextCell.y);
        sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].setEntity("Bee");
    }

    public abstract void attackResponse(SweetSwarm sweetSwarm);

    // no usar ji
//    protected void nearestResource(SweetSwarm sweetSwarm) {
//        Point nearestResourcePosition = detectEntity(sweetSwarm.honeycomb, "Resource");
//        if (nearestResourcePosition != null) {
//            Resource nearestResource = null;
//            for (Resource resource : sweetSwarm.resources) {
//                if (resource.getCell()[0] == nearestResourcePosition.x && resource.getCell()[1] == nearestResourcePosition.y) {
//                    nearestResource = resource;
//                }
//            }
//            if (nearestResource != null) {
//                moveToCollect(nearestResource, sweetSwarm);
//            }
//        } else {
//            moveToNextCell(getCell()[0], getCell()[1], sweetSwarm);
//        }
//    }
//
//    protected Point detectEntity(Honeycomb honeycomb, String entity) {
//        Point nearestObject = null;
//        int range = 1;
//        while (nearestObject == null && range < honeycomb.getMap().length) {
//            Point[] neighbors = honeycomb.getNeighbors(new Point(getCell()[0], getCell()[1]), range);
//            for (Point neighbor : neighbors) {
//                if (honeycomb.getMap()[neighbor.x][neighbor.y].getEntity() == entity) {
//                    nearestObject = neighbor;
//                    //System.out.println("⬢\tRANGE " + range);
//                    range = honeycomb.getMap().length;
//                    break;
//                }
//            }
//            range++;
//        }
//        if (nearestObject != null) {
//            //System.out.println("⬢\tNEAREST " + entity.toUpperCase() + " R[" + nearestObject.x + "] C[" + nearestObject.y + "]");
//        } else {
//            //System.out.println("⬢\tTHERE IS NO " + entity.toUpperCase() + " IN RANGE");
//        }
//        return nearestObject;
//    }


}



