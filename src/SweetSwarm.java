import game.bee.Bee;
import game.honeycomb.Cell;
import game.honeycomb.Honeycomb;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 * @author <a href="https://github.com/Mariana612">Mariana Navarro Jiménez</a>
 */
public class SweetSwarm extends JFrame implements ActionListener {
    // ui components
    private JButton playBtn, pauseBtn, slowerBtn, fasterBtn;
    private JLabel scoreLbl, backgroundLbl;
    private Font fontSource, font;
    // resources
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
    private boolean gameOver = false;
    private boolean gamePaused = false;
    public int speed = 1000;
    private int score = 0;
    // game components
    private final Honeycomb honeycomb = new Honeycomb(307, 98);
    private final JLabel[] base = new JLabel[7];
    private final Bee[] bees = new Bee[getRandomInteger(20, 50)];
    private final Object[] objects = new Object[getRandomInteger(25, 30)];

    /**
     * SweetSwarm class constructor.
     *
     * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
     */
    public SweetSwarm() {
        setIconImage(new ImageIcon("src/resources/img/__icon.png").getImage());
        setTitle("Sweet Swarm");
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getUIComponents();
        setVisible(true);
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
            // base
            addBase();
            // honeycomb
            addHoneycomb();
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
        Object source = e.getSource();
        if (source == playBtn) {
            System.out.println("⬢\t▶️PLAY");
            gamePaused = false;
            playBtn.setVisible(false);
            playBtn.setEnabled(false);
            pauseBtn.setVisible(true);
            pauseBtn.setEnabled(true);
            fasterBtn.setEnabled(score != 250);
            slowerBtn.setEnabled(score != 2000);
        } else if (source == pauseBtn) {
            System.out.println("⬢\t⏸️PAUSE");
            gamePaused = true;
            playBtn.setVisible(true);
            playBtn.setEnabled(true);
            pauseBtn.setVisible(false);
            pauseBtn.setEnabled(false);
            slowerBtn.setEnabled(false);
            fasterBtn.setEnabled(false);
        } else if (source == slowerBtn) {
            if (speed < 2000) {
                speed += 250;
                System.out.println("⬢\t⏪️SLOWER ⏰ " + speed + " ms");
                if (speed == 2000) {
                    slowerBtn.setEnabled(false);
                }
            }
            fasterBtn.setEnabled(true);
        } else if (source == fasterBtn) {
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

    private int getRandomInteger(int origin, int bound) {
        return (int) ((Math.random() * (bound - origin)) + origin);
    }

    private void addBase() {
        // calc positions based on origin point
        Point origin = new Point(7, 6);
        Point[] positions = {
                origin, // [R][C]
                new Point((origin.x - 1), (origin.y - 1)), // [R-1][C-1]
                new Point((origin.x - 1), origin.y), // [R-1][C]
                new Point(origin.x, (origin.y - 1)), // [R][C-1]
                new Point(origin.x, (origin.y + 1)), // [R][C+1]
                new Point((origin.x + 1), (origin.y - 1)), // [R+1][C-1]
                new Point((origin.x + 1), origin.y) // [R+1][C]
        };
        // setup base titles
        for (int i = 0; i < base.length; i++) {
            base[i] = labelSetup(baseImg, honeycomb.getMap()[positions[i].x][positions[i].y].getX(), honeycomb.getMap()[positions[i].x][positions[i].y].getY(), true); // base titles JLabel setup
            honeycomb.getMap()[positions[i].x][positions[i].y].setEntity("Base"); // set entity
            getContentPane().add(base[i]); // display
        }
    }

    private void addHoneycomb() {
        for (Cell[] container : honeycomb.getMap()) {
            for (Cell cell : container) {
                getContentPane().add(cell);
            }
        }
    }
}