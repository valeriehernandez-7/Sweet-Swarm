package game.bee;

import game.object.Resource;

import java.awt.Point;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public abstract class Bee {
    protected JLabel sprite = new JLabel(); // bee image
    protected int health; // bee health
    protected int power; // bee power
    protected Point position = new Point(); // bee position
    protected Point target = new Point(); // bee destination
    protected String status; // bee status
    protected List<String> states = List.of("dead", "looking", "attacking", "collecting"); // bee states

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

    public Point getPosition() {
        return position;
    }

    public void setPosition(int xPosition, int yPosition) {
        this.position.x = xPosition;
        this.position.y = yPosition;
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
        sprite.setIcon(new ImageIcon(source));
        status = states.get(stateIndex);
    }

    public List<String> getStates() {
        return states;
    }

    public void move(int xPosition, int yPosition) {
        setTarget(xPosition, yPosition);
    }

    public void collect(Resource resource) {
        if (resource.getResistance() > 0) {
            resource.setResistance(resource.getResistance() - 1);
        } else {
            resource.setStatus(false);
        }
        // move() to honeycomb base
    }

    public abstract void controller();
}