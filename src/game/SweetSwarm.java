package game;

import game.honeycomb.Cell;
import game.honeycomb.Honeycomb;
import game.bee.Bee;
import game.bee.Collector;
import game.bee.Guard;
import game.object.Object;
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
    public final JLabel[] base = new JLabel[7];
    public final List<Bee> bees = new ArrayList<>();
    public final List<Object> objects = new ArrayList<>();

    /**
     * SweetSwarm class constructor.
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

    private int getRandomInteger(int origin, int bound) {
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
            if (honeycomb.getMap()[cell.x][cell.y].isAvailable()) {
                honeycomb.getMap()[cell.x][cell.y].setEntity(entity);
                available = false;
            }
        }
        return cell;
    }

    private void gameSetup() {
        addBase(new Point(7, 6)); // base
        addObjects(); // objects
        addBees(); // bees
        addHoneycomb(); // honeycomb
    }

    private void addBase(Point origin) {
        // calc cell positions based on origin point (main cell)
        Point[] cells;
        if (origin.x % 2 != 0) {
            cells = new Point[]{
                    origin, // [R][C]
                    new Point((origin.x - 1), (origin.y - 1)), // [R-1][C-1] *
                    new Point((origin.x - 1), origin.y), // [R-1][C]
                    new Point(origin.x, (origin.y - 1)), // [R][C-1]
                    new Point(origin.x, (origin.y + 1)), // [R][C+1]
                    new Point((origin.x + 1), (origin.y - 1)), // [R+1][C-1] *
                    new Point((origin.x + 1), origin.y) // [R+1][C]
            };
        } else {
            cells = new Point[]{
                    origin, // [R][C]
                    new Point((origin.x - 1), (origin.y + 1)), // [R-1][C+1] *
                    new Point((origin.x - 1), origin.y), // [R-1][C]
                    new Point(origin.x, (origin.y - 1)), // [R][C-1]
                    new Point(origin.x, (origin.y + 1)), // [R][C+1]
                    new Point((origin.x + 1), (origin.y + 1)), // [R+1][C+1] *
                    new Point((origin.x + 1), origin.y) // [R+1][C]
            };
        }
        // setup base titles
        for (int i = 0; i < base.length; i++) {
            base[i] = labelSetup(baseImg, honeycomb.getMap()[cells[i].x][cells[i].y].getX(), honeycomb.getMap()[cells[i].x][cells[i].y].getY(), true); // base titles JLabel setup
            honeycomb.getMap()[cells[i].x][cells[i].y].setEntity("Base");
            getContentPane().add(base[i]); // display base
        }
    }

    private void addObjects() {
        objectGenerator(); // create objects
        for (Object obj : objects) {
            getContentPane().add(obj); // display object
        }
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
        Point[] cells;
        if (origin.x % 2 != 0) {
            cells = new Point[]{
                    origin, // [R][C]
                    new Point((origin.x - 1), (origin.y - 1)), // [R-1][C-1] *
                    new Point((origin.x - 1), origin.y), // [R-1][C]
                    new Point((origin.x + 1), (origin.y - 1)), // [R+1][C-1] *
                    new Point((origin.x + 1), origin.y) // [R+1][C]
            };
        } else {
            cells = new Point[]{
                    origin, // [R][C]
                    new Point((origin.x - 1), (origin.y + 1)), // [R-1][C+1] *
                    new Point((origin.x - 1), origin.y), // [R-1][C]
                    new Point((origin.x + 1), (origin.y + 1)), // [R+1][C+1] *
                    new Point((origin.x + 1), origin.y) // [R+1][C]
            };
        }
        // setup resource titles
        for (Point cell : cells) {
            objects.add(new Resource(honeycomb.getMap()[cell.x][cell.y].getX(), honeycomb.getMap()[cell.x][cell.y].getY(), cell.x, cell.y));
            honeycomb.getMap()[cell.x][cell.y].setEntity("Resource");
        }
    }

    private void blockGenerator(Point cell) {
        objects.add(new Block(honeycomb.getMap()[cell.x][cell.y].getX(), honeycomb.getMap()[cell.x][cell.y].getY(), cell.x, cell.y));
        honeycomb.getMap()[cell.x][cell.y].setEntity("Block");
    }

    private void threatGenerator(Point cell) {
        objects.add(new Threat(honeycomb.getMap()[cell.x][cell.y].getX(), honeycomb.getMap()[cell.x][cell.y].getY(), cell.x, cell.y));
        honeycomb.getMap()[cell.x][cell.y].setEntity("Threat");
    }

    private void objectGenerator() {
        // resources init
        int resourcesAmount = getRandomInteger(4, 9); // 3 < x < 9
        for (int i = 0; i < resourcesAmount; i++) {
            resourceGenerator(positioning(new Point(3, 9), new Point(3, 9), "Resource"));
        }
        // blocks init
        int blocksAmount = getRandomInteger(10, 16); // 9 < x < 16
        for (int i = 0; i < blocksAmount; i++) {
            blockGenerator(positioning("Block"));
        }
        // threats init
        int threatsAmount = getRandomInteger(10, 16); // 9 < x < 16
        for (int i = 0; i < threatsAmount; i++) {
            threatGenerator(positioning("Threat"));
        }
    }

    private void beeGenerator() {
        int beesAmount = getRandomInteger(30, 50); // 29 < x < 50
        for (int i = 0; i < beesAmount; i++) {
            Point cell;
            switch (getRandomInteger(1, 3)) {  // 0 < x < 3
                case 1 -> {
                    cell = positioning("Collector");
                    bees.add(i, new Collector(honeycomb.getMap()[cell.x][cell.y].getX(), honeycomb.getMap()[cell.x][cell.y].getY(), cell.x, cell.y));
                }
                case 2 -> {
                    cell = positioning("Guard");
                    bees.add(i, new Guard(honeycomb.getMap()[cell.x][cell.y].getX(), honeycomb.getMap()[cell.x][cell.y].getY(), cell.x, cell.y));
                }
            }
        }
    }

    private void scoreManager(Object object) {
        score += object.getPoints();
        scoreLbl.setText(String.valueOf(score));
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
            if (bees.isEmpty()) { // gameOver condition
                gameOver();
                break;
            }
            System.out.println("⬢\t⏰ TICK");
            timer.setDelay(speed); // timer speed

            // simulation [game loop] by Mariana tkm

            try { // thread delay
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}