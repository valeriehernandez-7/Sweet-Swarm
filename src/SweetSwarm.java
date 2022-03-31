import java.awt.*;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
 */
public class SweetSwarm extends JFrame {
    // ui components
    private JButton playBtn, pauseBtn, slowerBtn, fasterBtn;
    private JLabel scoreLbl, windowBackgroundLbl;
    private Font fontSource, font;
    // game features
    private boolean gameOver = false;
    private boolean gamePaused = false;
    public int speed = 1000;
    private int score = 0;

    /**
     * SweetSwarm class constructor.
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
     * @author <a href="https://github.com/valeriehernandez-7">Valerie M. Hernández Fernández</a>
     */
    private void getUIComponents() {
        try {
            // --- fonts ---
            fontSource = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/fonts/HoneyBear.ttf"));
            font = fontSource.deriveFont(Font.PLAIN, 24);
            // --- buttons ---
            // play button
            playBtn = new JButton();
            playBtn.setIcon(new ImageIcon("src/resources/img/__btn-play-1.png"));
            playBtn.setDisabledIcon(new ImageIcon("src/resources/img/__btn-play-0.png"));
            playBtn.setBounds(187, 333, playBtn.getIcon().getIconWidth(), playBtn.getIcon().getIconHeight());
            playBtn.setBorder(BorderFactory.createEmptyBorder());
            playBtn.setContentAreaFilled(false);
            playBtn.setVisible(true);
            playBtn.addActionListener(Event -> {
                System.out.println("⬢   ▶️PLAY");
                gamePaused = false;
                playBtn.setVisible(false);
                playBtn.setEnabled(false);
                pauseBtn.setVisible(true);
                pauseBtn.setEnabled(true);
                fasterBtn.setEnabled(score != 250);
                slowerBtn.setEnabled(score != 2000);
            });
            getContentPane().add(playBtn);
            // pause button
            pauseBtn = new JButton();
            pauseBtn.setIcon(new ImageIcon("src/resources/img/__btn-pause-1.png"));
            pauseBtn.setDisabledIcon(new ImageIcon("src/resources/img/__btn-pause-0.png"));
            pauseBtn.setBounds(187, 333, pauseBtn.getIcon().getIconWidth(), pauseBtn.getIcon().getIconHeight());
            pauseBtn.setBorder(BorderFactory.createEmptyBorder());
            pauseBtn.setContentAreaFilled(false);
            pauseBtn.setVisible(false);
            pauseBtn.addActionListener(Event -> {
                System.out.println("⬢   ⏸️PAUSE");
                gamePaused = true;
                playBtn.setVisible(true);
                playBtn.setEnabled(true);
                pauseBtn.setVisible(false);
                pauseBtn.setEnabled(false);
                slowerBtn.setEnabled(false);
                fasterBtn.setEnabled(false);
            });
            getContentPane().add(pauseBtn);
            // slower button
            slowerBtn = new JButton();
            slowerBtn.setIcon(new ImageIcon("src/resources/img/__btn-slower-1.png"));
            slowerBtn.setDisabledIcon(new ImageIcon("src/resources/img/__btn-slower-0.png"));
            slowerBtn.setBounds(136, 333, slowerBtn.getIcon().getIconWidth(), slowerBtn.getIcon().getIconHeight());
            slowerBtn.setBorder(BorderFactory.createEmptyBorder());
            slowerBtn.setContentAreaFilled(false);
            slowerBtn.setEnabled(false);
            slowerBtn.setVisible(true);
            slowerBtn.addActionListener(Event -> {
                if (speed < 2000) {
                    speed += 250;
                    System.out.println("⬢   ⏪️SLOWER ⏰ " + speed + " ms");
                    if (speed == 2000) {
                        slowerBtn.setEnabled(false);
                    }
                }
                fasterBtn.setEnabled(true);
            });
            getContentPane().add(slowerBtn);
            // faster button
            fasterBtn = new JButton();
            fasterBtn.setIcon(new ImageIcon("src/resources/img/__btn-faster-1.png"));
            fasterBtn.setDisabledIcon(new ImageIcon("src/resources/img/__btn-faster-0.png"));
            fasterBtn.setBounds(247, 333, fasterBtn.getIcon().getIconWidth(), fasterBtn.getIcon().getIconHeight());
            fasterBtn.setBorder(BorderFactory.createEmptyBorder());
            fasterBtn.setContentAreaFilled(false);
            fasterBtn.setEnabled(false);
            fasterBtn.setVisible(true);
            fasterBtn.addActionListener(Event -> {
                if (250 < speed) {
                    speed -= 250;
                    System.out.println("⬢   ⏩️FASTER ⏰ " + speed + " ms");
                    if (speed == 250) {
                        fasterBtn.setEnabled(false);
                    }
                }
                slowerBtn.setEnabled(true);
            });
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
            // background label
            windowBackgroundLbl = new JLabel(new ImageIcon("src/resources/img/__background.png"));
            windowBackgroundLbl.setBounds(0, 0, windowBackgroundLbl.getIcon().getIconWidth(), windowBackgroundLbl.getIcon().getIconHeight());
            getContentPane().add(windowBackgroundLbl);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}