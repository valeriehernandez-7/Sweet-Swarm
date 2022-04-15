package game.honeycomb;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class Honeycomb extends JPanel {
    private Cell[][] map; // honeycomb matrix
    private final Cell cell = new Cell(true); // honeycomb null cell
    private final Cell empty = new Cell(false); // honeycomb common cell

    public Honeycomb(int xPosition, int yPosition) {
        init();
        setBounds(xPosition, yPosition, cell.getIcon().getIconWidth() * map[0].length, map.length * (cell.getIcon().getIconHeight() - 10));
        setOpaque(false);
        cellPositioning();
        // temp line 22
        setBorder(BorderFactory.createLineBorder(Color.white));
    }

    public Cell[][] getMap() {
        return map;
    }

    private void init() {
        map = new Cell[][]{
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
    }

    private void cellPositioning() {
        int cellWidth = cell.getIcon().getIconWidth(); // honeycomb cell width
        int cellHeight = cell.getIcon().getIconHeight(); // honeycomb cell height
        int halfCell = cellWidth / 2; // honeycomb half cell width
        int cellXPos, cellYPos; // honeycomb cell position
        for (int row = 0; row < map.length; row++) {
            System.out.println("\n");
            for (int col = 0; col < map[row].length; col++) {
                if (row % 2 == 0) {
                    cellXPos = getX() + (halfCell + (col * cellWidth));
                } else {
                    cellXPos = getX() + (col * cellWidth);
                }
                if (row != 0) {
                    cellYPos = getY() + ((row * cellHeight) - 10);
                } else {
                    cellYPos = getY();
                }
                map[row][col].setLocation(cellXPos, cellYPos);
                add(map[row][col]);
                System.out.print("\t\t  MAP [" + row + "," + col + "] | POS (" + map[row][col].getX() + "," + map[row][col].getY() + ")");
            }
        }
    }
}