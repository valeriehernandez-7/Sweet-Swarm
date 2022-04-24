package game.bee;

import game.SweetSwarm;
import game.object.Resource;
import game.object.Threat;

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
    protected List<String> states = List.of("dead", "looking", "attacking", "collecting","following"); // bee states

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
                // create new resource
            }
        }
    }

    public void nearestResource(SweetSwarm sweetSwarm) {
        if (sweetSwarm.resources.isEmpty()) {
            System.out.println("NO RESOURCES AVAILABLE");
        } else {
            Resource nearestResource = sweetSwarm.resources.get(0);
            Point resultCell = new Point(getCell()[0] - sweetSwarm.resources.get(0).getCell()[0], getCell()[1] - sweetSwarm.resources.get(0).getCell()[1]);
            for (Resource resource : sweetSwarm.resources) {
                int resultRow = getCell()[0] - resource.getCell()[0];
                int resultColumn = getCell()[1] - resource.getCell()[1];
                if ((resultRow <= resultCell.x & resultColumn <= resultCell.y) | (resultRow == resultCell.x & resultColumn < resultCell.y) | (resultRow < resultCell.x & resultColumn == resultCell.y)) {
                    nearestResource = resource;
                    resultCell.x = resultRow;
                    resultCell.y = resultColumn;
                }
            }
            moveToCollect(nearestResource, sweetSwarm);
        }
    }

    private Point getRoute(int row, int column) {
        int cellRow = 0;
        int cellColumn = 0;
        if (row > 0) {
            cellRow = -1;
        }
        if (row < 0) {
            cellRow = 1;
        }
        if (column < 0) {
            cellColumn = 1;
        }
        if (column > 0) {
            cellColumn = -1;
        }
        return new Point(getCell()[0] + cellRow, cell[1] + cellColumn);

        //honeycomb.getNeighbors(bestPath);
        //llamar a check escarabajo
        //devuelba un punto
        //si el punto no esta available. vecinos del punto, si comparte vecinos con el punto original pfijese si alguno esta disponible y si lo esta vayase al primero. reiterativo fijese
    }

    public void moveToCollect(Resource resource, SweetSwarm sweetSwarm) {
        int row = getCell()[0] - resource.getCell()[0];
        int column = getCell()[1] - resource.getCell()[1];

        if ((1 - Math.abs(row) == 0 & 1 - Math.abs(column) == 0) | (1 - Math.abs(row) == 0 & column == 0) | (row == 0 & 1 - Math.abs(column) == 0)) {
            collect(resource, sweetSwarm);
        } else {
            setCell(getRoute(row, column).x, getRoute(row, column).y);
        }
        setLocation(sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].getX(), sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].getY()); // calc next position (cell)
    }

    public void moveToCenter(SweetSwarm sweetSwarm) {
        System.out.println(sweetSwarm.base[0].getX());
        System.out.println(getCell()[0]);

        int row = Math.abs(getCell()[0] - sweetSwarm.base[0].getX());
        int column = Math.abs(getCell()[1] - sweetSwarm.base[0].getY());

        if ((1 - row == 0 & 1 - column == 0) | (1 - row == 0 & column == 0) | (row == 0 & 1 - column == 0)) {
            setStatus(getStates().get(1));
            sweetSwarm.score += sweetSwarm.resources.get(0).getPoints();
        } else {
            setCell(getRoute(row, column).x, getRoute(row, column).y);
        }
        setLocation(sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].getX(), sweetSwarm.honeycomb.getMap()[getCell()[0]][getCell()[1]].getY());
    }

    public abstract void attackResponse(SweetSwarm sweetSwarm);

    public abstract void controller(SweetSwarm sweetSwarm);
}
