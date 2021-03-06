package game;

import game.honeycomb.Cell;
import game.honeycomb.Honeycomb;
import game.bee.Bee;
import game.bee.Collector;
import game.bee.Guard;
import game.object.Block;
import game.object.Resource;
import game.object.Threat;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public class SweetSwarm extends JFrame implements ActionListener {
    // ui components
    private JButton playBtn, pauseBtn, slowerBtn, fasterBtn;
    private JLabel scoreLbl, backgroundLbl;
    private Font fontSource, font;
    // resources
    private final ImageIcon iconImg = new ImageIcon("src/resources/img/__icon.png");
    private final ImageIcon backgroundImg = new ImageIcon("src/resources/img/__background.png");
    private final ImageIcon baseImg = new ImageIcon("src/resources/img/__honeycomb-base.png");
    private final ImageIcon playBtn0Img = new ImageIcon("src/resources/img/__btn-play-0.png");
    private final ImageIcon playBtn1Img = new ImageIcon("src/resources/img/__btn-play-1.png");
    private final ImageIcon pauseBtn0Img = new ImageIcon("src/resources/img/__btn-pause-0.png");
    private final ImageIcon pauseBtn1Img = new ImageIcon("src/resources/img/__btn-pause-1.png");
    private final ImageIcon slowerBtn0Img = new ImageIcon("src/resources/img/__btn-slower-0.png");
    private final ImageIcon slowerBtn1Img = new ImageIcon("src/resources/img/__btn-slower-1.png");
    private final ImageIcon fasterBtn0Img = new ImageIcon("src/resources/img/__btn-faster-0.png");
    private final ImageIcon fasterBtn1Img = new ImageIcon("src/resources/img/__btn-faster-1.png");
    // game features
    public boolean gamePaused;
    public int speed = 1000;
    public Timer timer = new Timer(speed, simulation -> new Thread(this::play, "simulation").start());
    public int score = 0;
    // game components
    public final Honeycomb honeycomb = new Honeycomb(307, 98);
    public final Point center = new Point(7, 6);
    public final JLabel[] base = new JLabel[7];
    public final List<Bee> bees = new ArrayList<>();
    public final List<Block> blocks = new ArrayList<>();
    public final List<Resource> resources = new ArrayList<>();
    public final List<Threat> threats = new ArrayList<>();

    /**
     * SweetSwarm class constructor.
     *
     * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
     */
    public SweetSwarm() {
        setIconImage(iconImg.getImage());
        setTitle("Sweet Swarm");
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getUIComponents();
        setVisible(true);
        timer.setRepeats(false);
    }

    /**
     * Instantiate and customize the interface components.
     *
     * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
     */
    private void getUIComponents() {
        try {
            // --- fonts ---
            fontSource = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/fonts/HoneyBear.ttf"));
            font = fontSource.deriveFont(Font.PLAIN, 24);
            // --- buttons ---
            // play button
            playBtn = buttonSetup(playBtn1Img, playBtn0Img, 187, 333, true, true);
            getContentPane().add(playBtn);
            // pause button
            pauseBtn = buttonSetup(pauseBtn1Img, pauseBtn0Img, 187, 333, true, false);
            getContentPane().add(pauseBtn);
            // slower button
            slowerBtn = buttonSetup(slowerBtn1Img, slowerBtn0Img, 136, 333, false, true);
            getContentPane().add(slowerBtn);
            // faster button
            fasterBtn = buttonSetup(fasterBtn1Img, fasterBtn0Img, 247, 333, false, true);
            getContentPane().add(fasterBtn);
            // --- labels ---
            // score label
            scoreLbl = new JLabel();
            scoreLbl.setBounds(474, 29, 188, 43);
            scoreLbl.setHorizontalAlignment(JLabel.CENTER);
            scoreLbl.setVerticalAlignment(JLabel.CENTER);
            scoreLbl.setFont(font);
            scoreLbl.setForeground(new Color(36, 6, 0));
            scoreLbl.setText(String.valueOf(score));
            getContentPane().add(scoreLbl);
            // --- game components ---
            gameSetup();
            // background label
            backgroundLbl = labelSetup(backgroundImg, 0, 0, true);
            getContentPane().add(backgroundLbl);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    private JButton buttonSetup(ImageIcon source, ImageIcon effect, int posX, int posY, boolean enabled, boolean visible) {
        JButton button = new JButton();
        button.setIcon(source);
        button.setDisabledIcon(effect);
        button.setPressedIcon(effect);
        button.setRolloverIcon(effect);
        button.setBounds(posX, posY, button.getIcon().getIconWidth(), button.getIcon().getIconHeight());
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setEnabled(enabled);
        button.setVisible(visible);
        button.addActionListener(this);
        return button;
    }

    private JLabel labelSetup(ImageIcon source, int posX, int posY, boolean visible) {
        JLabel label = new JLabel(source);
        label.setBounds(posX, posY, label.getIcon().getIconWidth(), label.getIcon().getIconHeight());
        label.setVisible(visible);
        return label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        java.lang.Object source = e.getSource();
        if (source == playBtn) {
            System.out.println("⬢\t▶️PLAY");
            gamePaused = false;
            playBtn.setVisible(false);
            playBtn.setEnabled(false);
            pauseBtn.setVisible(true);
            pauseBtn.setEnabled(true);
            fasterBtn.setEnabled(score != 250);
            slowerBtn.setEnabled(score != 2000);
            if (!timer.isRunning()) { // starts the simulation [ play() ]
                timer.start();
            }
        } else if (source == pauseBtn) {
            System.out.println("⬢\t⏸️PAUSE");
            gamePaused = true;
            playBtn.setVisible(true);
            playBtn.setEnabled(true);
            pauseBtn.setVisible(false);
            pauseBtn.setEnabled(false);
            slowerBtn.setEnabled(false);
            fasterBtn.setEnabled(false);
            timer.stop(); // stops the simulation [ play() ]
        } else if (source == slowerBtn) {
            speedManager(-1);
        } else if (source == fasterBtn) {
            speedManager(1);
        }
    }

    public int getRandomInteger(int origin, int bound) {
        return (int) ((Math.random() * (bound - origin)) + origin);
    }

    private Point positioning(String entity) {
        Point cell = new Point();
        boolean available = true;
        while (available) {
            cell.x = getRandomInteger(1, 15);  // 0 < x < 15
            cell.y = getRandomInteger(1, 13); // 0 < x < 13
            if (honeycomb.getMap()[cell.x][cell.y].isAvailable()) {
                honeycomb.getMap()[cell.x][cell.y].setEntity(entity);
                available = false;
            }
        }
        return cell;
    }

    private Point positioning(Point rows, Point cols, String entity) {
        Point cell = new Point();
        boolean available = true;
        while (available) {
            cell.x = getRandomInteger(rows.x, rows.y); // row min < x < row max
            cell.y = getRandomInteger(cols.x, cols.y); // column min < x < column max
            if (honeycomb.getMap()[cell.x][cell.y].isAvailable() && honeycomb.areNeighborsAvailable(new Point(cell.x, cell.y))) {
                honeycomb.getMap()[cell.x][cell.y].setEntity(entity);
                available = false;
            }
        }
        return cell;
    }

    private void gameSetup() {
        setBaseEnabled(false); // while positioning
        addObjects(); // objects
        addBees(); // bees
        addBase(center); // base
        addHoneycomb(); // honeycomb
    }

    private void setBaseEnabled(boolean enabled) {
        Point[] cells = honeycomb.getNeighbors(center);
        for (int i = 0; i < base.length; i++) {
            honeycomb.getMap()[cells[i].x][cells[i].y].setAvailable(enabled);
        }
    }

    private void addBase(Point origin) {
        // calc cell positions based on origin point (main cell)
        Point[] cells = honeycomb.getNeighbors(origin);
        // setup base titles
        for (int i = 0; i < base.length; i++) {
            base[i] = labelSetup(baseImg, honeycomb.getMap()[cells[i].x][cells[i].y].getX(), honeycomb.getMap()[cells[i].x][cells[i].y].getY(), true); // base titles JLabel setup
            honeycomb.getMap()[cells[i].x][cells[i].y].setEntity("Base");
            getContentPane().add(base[i]); // display base
        }
    }

    private void addObjects() {
        resourcePositioning();
        blockPositioning();
        threatPositioning();
    }

    private void addBees() {
        beeGenerator(); // create bees
        for (Bee bee : bees) {
            getContentPane().add(bee); // display bee
        }
    }

    private void addHoneycomb() {
        for (Cell[] container : honeycomb.getMap()) {
            for (Cell cell : container) {
                getContentPane().add(cell); // display cell
            }
        }
    }

    private void resourceGenerator(Point origin) {
        // calc cell positions based on origin point (main cell)
        Point[] cells = honeycomb.getNeighbors(origin);
        // setup resource titles
        for (Point cell : cells) {
            resources.add(new Resource(honeycomb.getMap()[cell.x][cell.y].getX(), honeycomb.getMap()[cell.x][cell.y].getY(), cell.x, cell.y));
            honeycomb.getMap()[cell.x][cell.y].setEntity("Resource");
        }
    }

    private void resourcePositioning() {
        int resourcesAmount = getRandomInteger(1, 4); // 0 < x < 4
        for (int i = 0; i < resourcesAmount; i++) {
            resourceGenerator(positioning(new Point(3, 9), new Point(3, 9), "Resource"));
        }
        for (Resource resource : resources) { // display resources
            getContentPane().add(resource);
        }
    }

    public void respawnResources(SweetSwarm sweetSwarm, Resource resource) { // respawn of new resources ner neighbors
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Resource res : sweetSwarm.resources) {
            Point[] resourceNeighbors = sweetSwarm.honeycomb.getNeighbors(new Point(res.getCell()[0], res.getCell()[1]));
            boolean foundPlace = false;
            for (Point neighbors : resourceNeighbors) {
                if (sweetSwarm.honeycomb.getMap()[neighbors.x][neighbors.y].isAvailable()) {
                    sweetSwarm.honeycomb.getMap()[neighbors.x][neighbors.y].setEntity("Resource");
                    resource.setCell(neighbors.x, neighbors.y);
                    resource.setLocation(sweetSwarm.honeycomb.getMap()[neighbors.x][neighbors.y].getX(), sweetSwarm.honeycomb.getMap()[neighbors.x][neighbors.y].getY());
                    resource.setResistance(2);
                    resource.updateStatus();
                    foundPlace = true;
                    break;
                }
            }
            if (foundPlace) {
                break;
            }
        }
    }

    private void blockGenerator(Point cell) {
        blocks.add(new Block(honeycomb.getMap()[cell.x][cell.y].getX(), honeycomb.getMap()[cell.x][cell.y].getY(), cell.x, cell.y));
        honeycomb.getMap()[cell.x][cell.y].setEntity("Block");
    }

    private void blockPositioning() {
        int blocksAmount = getRandomInteger(4, 9); // 3 < x < 9
        for (int i = 0; i < blocksAmount; i++) {
            blockGenerator(positioning("Block"));
        }
        for (Block block : blocks) {
            getContentPane().add(block); // display block
        }
    }

    private void threatGenerator(Point cell) {
        threats.add(new Threat(honeycomb.getMap()[cell.x][cell.y].getX(), honeycomb.getMap()[cell.x][cell.y].getY(), cell.x, cell.y));
        honeycomb.getMap()[cell.x][cell.y].setEntity("Threat");
    }

    public void respawnThreats(SweetSwarm sweetSwarm, Threat threat) { // respawn of new resources ner neighbors
        try {
            Thread.sleep(speed+50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Point newPosition = positioning("Threat");
        threat.setCell(newPosition.x, newPosition.y);
        threat.setResistance(10);
        threat.setLocation(sweetSwarm.honeycomb.getMap()[newPosition.x][newPosition.y].getX(), sweetSwarm.honeycomb.getMap()[newPosition.x][newPosition.y].getY());
        threat.setBounds(threat.getLocation().x, threat.getLocation().y, threat.getIcon().getIconWidth(), threat.getIcon().getIconHeight());
    }

    private void threatPositioning() {
        int threatsAmount = getRandomInteger(4, 15); // 3 < x < 9
        for (int i = 0; i < threatsAmount; i++) {
            //threatGenerator(new Point(4,4));
            threatGenerator(positioning("Threat"));
        }
        for (Threat threat : threats) {
            getContentPane().add(threat); // display threat
        }
    }

    private void beeGenerator() {
        int beesAmount = getRandomInteger(15, 31); // 14 < x < 31
        //int beesAmount = 30;
        for (int i = 0; i < beesAmount; i++) {
            Point cell = positioning("Bee");
            switch (getRandomInteger(1, 3)) {  // 0 < x < 3
                case 1 -> {
                    bees.add(i, new Collector(honeycomb.getMap()[cell.x][cell.y].getX(), honeycomb.getMap()[cell.x][cell.y].getY(), cell.x, cell.y));
                }
                case 2 -> {
                    bees.add(i, new Guard(honeycomb.getMap()[cell.x][cell.y].getX(), honeycomb.getMap()[cell.x][cell.y].getY(), cell.x, cell.y));
                }
            }
        }
    }

    private boolean findGuards() {
        boolean exists = false;
        for (Bee bee : bees) {
            if (bee.getClass().getSimpleName().equals("Guard")) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    private void speedManager(int controller) {
        switch (controller) {
            case -1 -> { // slowerBtn
                if (speed < 2000) {
                    speed += 250;
                    System.out.println("⬢\t⏪️SLOWER ⏰ " + speed + " ms");
                    if (speed == 2000) {
                        slowerBtn.setEnabled(false);
                    }
                }
                fasterBtn.setEnabled(true);
            }
            case 1 -> { // fasterBtn
                if (250 < speed) {
                    speed -= 250;
                    System.out.println("⬢\t⏩️FASTER ⏰ " + speed + " ms");
                    if (speed == 250) {
                        fasterBtn.setEnabled(false);
                    }
                }
                slowerBtn.setEnabled(true);
            }
        }
    }

    private void gameOver() {
        System.out.println("⬢\tGAME OVER");
        int input = JOptionPane.showConfirmDialog(this, "Score: " + score + "\nDo you want to play another Sweet Swarm?",
                "SweetSwarm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, iconImg);
        dispose();
        if (input == JOptionPane.YES_OPTION) {
            System.out.println("\n⬢ \uD83D\uDC1D Sweet Swarm \uD83D\uDC1D ⬢");
            new SweetSwarm();
        }
    }

    private void play() {
        while (!gamePaused) {
            if (!findGuards()) { // gameOver condition
                gameOver();
                break;
            }
            System.out.println("\n⬢\t⏰ TICK");
            // start simulation
            for (Bee bee : bees) {
                timer.setDelay(speed); // timer speed
                System.out.println("\n⬢⬢⬢\t\uD83D\uDC1D " + bee.getClass().getSimpleName().toUpperCase() + " BEE " + bees.indexOf(bee) + "\t⬢⬢⬢");
                System.out.println("⬢\tPOSITION R[" + bee.getCell()[0] + "] C[" + bee.getCell()[1] + "]");
                bee.controller(this);
                if (bee.getStatus().equals(bee.getStates().get(4))) {
                    if (bee.isCollecting()) {
                        bee.setStatus(bee.getStates().get(3));
                    } else {
                        bee.setStatus(bee.getStates().get(1));
                    }
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    bee.controller(this);
                }
                scoreLbl.setText(String.valueOf(score));
                repaint();
                if (bees.isEmpty()) {
                    break;
                }
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (Threat threat : threats) {
                threat.attackBee(this);
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // end simulation
            bees.removeIf(bee -> bee.getStatus().equals(bee.getStates().get(0))); // remove dead bees
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}