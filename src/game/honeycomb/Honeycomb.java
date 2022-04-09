package game.honeycomb;

import java.awt.Point;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Honeycomb {
    private Cell[][] map; // honeycomb matrix
    private final Cell cell = new Cell(true); // honeycomb null cell
    private final Cell empty = new Cell(false); // honeycomb common cell
    private final Point position;

    public Honeycomb(Point position) {
        this.position = position;
        init();
    }

    public Cell[][] getMap() {
        return map;
    }

    private void init() {
        this.map = new Cell[][]{
                {empty, empty, empty, empty, empty, empty, cell, cell, empty, empty, empty, empty, empty}, // Row 0
                {empty, empty, empty, empty, cell, cell, cell, cell, cell, empty, empty, empty, empty}, // Row 1
                {empty, cell, cell, cell, cell, cell, cell, cell, cell, cell, empty, empty, empty}, // Row 2
                {empty, cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, empty, empty}, // Row 3
                {empty, cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, empty}, // Row 4
                {empty, cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, cell}, // Row 5
                {cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, empty}, // Row 6
                {empty, cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, empty}, // Row 7
                {cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, empty}, // Row 8
                {cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, empty}, // Row 9
                {cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, empty, empty}, // Row 10
                {empty, empty, cell, cell, cell, cell, cell, cell, cell, cell, cell, cell, empty}, // Row 11
                {empty, empty, cell, cell, cell, cell, cell, cell, cell, cell, cell, empty, empty}, // Row 12
                {empty, empty, empty, empty, cell, cell, cell, cell, cell, empty, empty, empty, empty}, // Row 13
                {empty, empty, empty, empty, cell, cell, empty, empty, empty, empty, empty, empty, empty} // Row 14
        };
        cellPositioning();
    }

    private void cellPositioning() {
        int cellWidth = this.cell.getIcon().getIconWidth(); // honeycomb cell width
        int cellHeight = this.cell.getIcon().getIconHeight(); // honeycomb cell height
        int halfCell = cellWidth / 2; // honeycomb half cell width
        int cellXPos, cellYPos; // honeycomb cell position
        for (int row = 0; row < this.map.length; row++) {
            System.out.println("\n");
            for (int col = 0; col < this.map[row].length; col++) {
                if (row % 2 == 0) {
                    cellXPos = this.position.x + (halfCell + (col * cellWidth));
                } else {
                    cellXPos = this.position.x + (col * cellWidth);
                }
                if (row != 0) {
                    cellYPos = this.position.y + ((row * cellHeight) - 10);
                } else {
                    cellYPos = this.position.y;
                }
                this.map[row][col].setPosition(cellXPos, cellYPos);
                System.out.print("\t\t  MAP [" + row + "," + col + "] | POS (" + this.map[row][col].getPosition().x + "," + this.map[row][col].getPosition().y + ")");
            }
        }
    }
}