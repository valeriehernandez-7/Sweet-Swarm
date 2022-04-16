package game.honeycomb;

import java.awt.Point;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Honeycomb {
    private Cell[][] map; // honeycomb matrix
    private final Point position;

    public Honeycomb(int xPosition, int yPosition) {
        position = new Point(xPosition, yPosition);
        init();
    }

    public Cell[][] getMap() {
        return map;
    }

    private void init() {
        map = new Cell[][]{
                {new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(true), new Cell(true), new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false)}, // Row 0
                {new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(false), new Cell(false), new Cell(false), new Cell(false)}, // Row 1
                {new Cell(false), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(false), new Cell(false), new Cell(false)}, // Row 2
                {new Cell(false), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(false), new Cell(false)}, // Row 3
                {new Cell(false), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(false)}, // Row 4
                {new Cell(false), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true)}, // Row 5
                {new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(false)}, // Row 6
                {new Cell(false), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(false)}, // Row 7
                {new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(false)}, // Row 8
                {new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(false)}, // Row 9
                {new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(false), new Cell(false)}, // Row 10
                {new Cell(false), new Cell(false), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(false)}, // Row 11
                {new Cell(false), new Cell(false), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(false), new Cell(false)}, // Row 12
                {new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(true), new Cell(false), new Cell(false), new Cell(false), new Cell(false)}, // Row 13
                {new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(true), new Cell(true), new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false)} // Row 14
        };
        cellPositioning();
    }

    private void cellPositioning() {
        Cell cell = new Cell(true); // honeycomb cell
        int cellWidth = cell.getIcon().getIconWidth(); // honeycomb cell width
        int cellHeight = cell.getIcon().getIconHeight(); // honeycomb cell height
        int halfCell = cellWidth / 2; // honeycomb half cell width
        int cellXPos, cellYPos; // honeycomb cell position
        for (int row = 0; row < map.length; row++) {
//            System.out.println("\n");
            for (int col = 0; col < map[row].length; col++) {
                if (row % 2 == 0) {
                    cellXPos = position.x + (halfCell + (col * cellWidth));
                } else {
                    cellXPos = position.x + (col * cellWidth);
                }
                if (row != 0) {
                    cellYPos = position.y + ((row * cellHeight) - (row * 10));
                } else {
                    cellYPos = position.y;
                }
                map[row][col].setLocation(cellXPos, cellYPos);
//                System.out.print("\t\t  MAP [" + row + "," + col + "] | POS (" + map[row][col].getX() + "," + map[row][col].getY() + ")");
            }
        }
    }
}