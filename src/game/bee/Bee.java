package game.bee;

import game.SweetSwarm;
import game.honeycomb.Honeycomb;
import game.object.Resource;

import javax.swing.*;
import java.awt.*;
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
    protected List<String> states = List.of("dead", "looking", "reacting", "collecting", "competing", "running"); // bee states
    protected boolean reacting = false;   //reacting to another bee's movement
    protected boolean collecting = false; //if bee was collecting before change of status
    protected boolean resourceTransferred = false; //if flower was given by another bee

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
        if ((stateIndex > 0 && stateIndex < 3) | (stateIndex == 4 & !isCollecting()) | (stateIndex == 5 & !isCollecting())) { // looking for resource or reacting state
            source = "src/resources/img/__bee-" + getClass().getSimpleName() + "-0.png";
        } else if (stateIndex == 3 | (stateIndex == 4 & isCollecting()) | (stateIndex == 5 & this.isCollecting())) { // collecting the resource state
            source = "src/resources/img/__bee-" + getClass().getSimpleName() + "-1.png";
        } else {
            source = "src/resources/img/__null.png"; // die
        }
        setIcon(new ImageIcon(source));
        setBounds(getLocation().x, getLocation().y, getIcon().getIconWidth(), getIcon().getIconHeight());
        status = states.get(stateIndex);
    }

    public List<String> getStates() {
        return states;
    }

    public boolean isReacting() {
        return reacting;
    }

    public void setReacting(boolean reacting) {
        this.reacting = reacting;
    }

    public boolean isCollecting() {
        return collecting;
    }

    public void setCollecting(boolean collecting) {
        this.collecting = collecting;
    }

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
                sweetSwarm.honeycomb.getMap()[resource.getCell()[0]][resource.getCell()[1]].setEntity("Cell"); // set the Honeycomb Cell available
                sweetSwarm.respawnResources(sweetSwarm, resource);
                setTarget(sweetSwarm.base[0].getX(), sweetSwarm.base[0].getY()); // move to honeycomb base main cell (center) SweetWarm.base[0]
                setStatus(getStates().get(3)); // status = collecting
                setCollecting(true);
            }
        }
    }

    protected void lookForNearElements(SweetSwarm sweetSwarm, boolean collecting) { //Reacts to environment variables
        Resource resourcePoint = detectNeighborResource(sweetSwarm);
        Point entityPoint = detectNeighborEntity(sweetSwarm, "Threat");
        Point beePoint = detectNeighborEntity(sweetSwarm, "Bee");
        if (entityPoint != null) {
            setStatus(getStates().get(2));
            setTarget(entityPoint.x, entityPoint.y);
        } else if ((resourcePoint != null) & (!getStatus().equals(getStates().get(3)))) {
            collect(resourcePoint, sweetSwarm);
        } else if (beePoint != null) {
            for (Bee bee : sweetSwarm.bees) {
                if (bee != this) {
                    if (new Point(bee.getCell()[0], bee.getCell()[1]).equals(beePoint)) {
                        if (bee.getStatus().equals(bee.getStates().get(5))) {
                            System.out.println("⬢\t\uD83D\uDC1D  BEE " + sweetSwarm.bees.indexOf(this) + " IS " + getStatus().toUpperCase() + " X \uD83D\uDC1D BEE " + sweetSwarm.bees.indexOf(bee) + " IS RUNNING");
                            setTarget(bee.getTarget().x, bee.getTarget().y);
                            setStatus(getStates().get(2));
                            setReacting(true);
                            controller(sweetSwarm);
                        }
                        if (bee.getStatus().equals(bee.getStates().get(2))) {
                            System.out.println("⬢\t\uD83D\uDC1D  BEE " + sweetSwarm.bees.indexOf(this) + " IS " + getStatus().toUpperCase() + " X \uD83D\uDC1D BEE " + sweetSwarm.bees.indexOf(bee) + " IS ATTACKING");
                            setTarget(bee.getTarget().x, bee.getTarget().y);
                            setStatus(getStates().get(2));
                            setReacting(true);
                            controller(sweetSwarm);
                            break;
                        }
                        if ((getStatus().equals(getStates().get(1))) & (bee.getStatus().equals(bee.getStates().get(1)))) {
                            System.out.println("⬢\t\uD83D\uDC1D BEE " + sweetSwarm.bees.indexOf(this) + " IS LOOKING X \uD83D\uDC1D BEE " + sweetSwarm.bees.indexOf(bee) + " IS LOOKING");
                            setStatus(getStates().get(4));
                            break;
                        }
                        if ((getStatus().equals(getStates().get(3))) & (bee.getStatus().equals(bee.getStates().get(3)))) {
                            System.out.println("⬢\t\uD83D\uDC1D BEE " + sweetSwarm.bees.indexOf(this) + " IS COLLECTING X \uD83D\uDC1D BEE " + sweetSwarm.bees.indexOf(bee) + " IS COLLECTING");
                            setStatus(getStates().get(4));
                            break;
                        }
                        if ((getStatus().equals(getStates().get(1))) & (bee.getStatus().equals(bee.getStates().get(3)))) {
                            if (!resourceTransferred) {
                                System.out.println("⬢\t\uD83D\uDC1D BEE " + sweetSwarm.bees.indexOf(this) + " IS LOOKING X \uD83D\uDC1D BEE " + sweetSwarm.bees.indexOf(bee) + " IS COLLECTING");
                                setStatus(getStates().get(3));
                                setCollecting(true);
                                bee.setStatus(getStates().get(1));
                                bee.setCollecting(false);
                                resourceTransferred = true;
                                break;
                            }
                            break;
                        }
                    }
                }
            }
            if (collecting) {
                moveToCenter(sweetSwarm);
            } else {
                moveToNextCell(sweetSwarm);
            }
        } else {
            if (collecting) {
                moveToCenter(sweetSwarm);
            } else {
                moveToNextCell(sweetSwarm);
            }
        }
    }

    protected Point detectNeighborEntity(SweetSwarm sweetSwarm, String entity) {//Returns first found position of object
        Point[] neighborList = sweetSwarm.honeycomb.getNeighbors(new Point(getCell()[0], getCell()[1]));
        Point nearestEntity = null;
        for (Point neighbor : neighborList) {
            if (sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].getEntity().equals(entity)) {
                nearestEntity = neighbor;
            }
        }
        return nearestEntity;

    }

    protected Resource detectNeighborResource(SweetSwarm sweetSwarm) { // returns first found Resource near the origin
        Point[] neighborList = sweetSwarm.honeycomb.getNeighbors(new Point(getCell()[0], getCell()[1]));
        Resource nearestResource = null;
        for (Point neighbor : neighborList) {
            if (sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].getEntity().equals("Resource")) {
                for (Resource resource : sweetSwarm.resources) {
                    if (new Point(resource.getCell()[0], resource.getCell()[1]).equals(neighbor)) {
                        nearestResource = resource;
                        break;
                    }
                }

            }
        }
        return nearestResource;
    }

    protected void moveToNextCell(SweetSwarm sweetSwarm) {  // default Movement
        Point[] listOfNeighbors = sweetSwarm.honeycomb.getNeighbors(new Point(getCell()[0], getCell()[1]));
        int randomInt = sweetSwarm.getRandomInteger(0, listOfNeighbors.length);
        if (sweetSwarm.honeycomb.getMap()[listOfNeighbors[randomInt].x][listOfNeighbors[randomInt].y].isAvailable()) { //Random position
            sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].setEntity("Cell");
            setCell(listOfNeighbors[randomInt].x, listOfNeighbors[randomInt].y);
            sweetSwarm.honeycomb.getMap()[listOfNeighbors[randomInt].x][listOfNeighbors[randomInt].y].setEntity("Bee");
            setLocation(sweetSwarm.honeycomb.getMap()[listOfNeighbors[randomInt].x][listOfNeighbors[randomInt].y].getX(), sweetSwarm.honeycomb.getMap()[listOfNeighbors[randomInt].x][listOfNeighbors[randomInt].y].getY());
        } else {                                                                                                       //First available position
            for (Point neighbor : listOfNeighbors) {
                if (sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].isAvailable()) {
                    sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].setEntity("Cell");
                    setCell(neighbor.x, neighbor.y);
                    sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].setEntity("Bee");
                    setLocation(sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].getX(), sweetSwarm.honeycomb.getMap()[neighbor.x][neighbor.y].getY());
                    break;
                }
            }
        }
        System.out.println("⬢\tMOVE TO R[" + getCell()[0] + "] C[" + getCell()[1] + "]");
    }

    protected void moveToCenter(SweetSwarm sweetSwarm) { // after resource was collected
        int row = getCell()[0] - sweetSwarm.center.x;
        int column = getCell()[1] - sweetSwarm.center.y;
        if ((1 - Math.abs(row) == 0 & 1 - Math.abs(column) == 0) | (1 - Math.abs(row) == 0 & column == 0) | (row == 0 & 1 - Math.abs(column) == 0)) { //Found Center
            setStatus(getStates().get(1));
            setCollecting(false);
            setReacting(false);
            resourceTransferred = false;
            sweetSwarm.score += sweetSwarm.resources.get(0).getPoints();
        } else {  // calc next position (cell)
            Point nextCell = getRoute(row, column, sweetSwarm.honeycomb);
            sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].setEntity("Cell");
            setCell(nextCell.x, nextCell.y);
            sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].setEntity("Bee");
        }
        setLocation(sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].getX(), sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].getY());
        System.out.println("⬢\tMOVE TO R[" + getCell()[0] + "] C[" + getCell()[1] + "]");
    }

    public void controller(SweetSwarm sweetSwarm) { // controls bee behaviour
        switch (getStatus()) {
            case "looking" -> lookForNearElements(sweetSwarm, false); // looking for resources
            case "collecting" -> lookForNearElements(sweetSwarm, true); // resource collected, moving to base
            case "reacting" -> attackResponse(sweetSwarm); // general interaction
            case "running" -> { // Bee Collector avoiding Threats
                if (isCollecting()) {
                    setStatus(getStates().get(3));
                } else {
                    setStatus(getStates().get(1));
                }
                controller(sweetSwarm);
            }
        }
    }

    public abstract void attackResponse(SweetSwarm sweetSwarm);
}