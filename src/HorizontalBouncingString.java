import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class HorizontalBouncingString {
    public static final int PANEL_WIDTH = 400;
    public static final int PANEL_HEIGHT = 300;
    public static final int DELAY_MS = 40;
    public static final int STEP = 5;
    public static final String[] COLORS = {"Red", "Green", "Blue"};
    public static final Color[] COLOR_VALUES = {Color.RED, Color.GREEN, Color.BLUE};
    public final String str = "Hello, world!";
    public int x = 0;
    public int y = PANEL_HEIGHT / 2;
    public int dx = STEP;
    public Color color = COLOR_VALUES[0];

    public JPanel panel;

     public  HorizontalBouncingString() {
        JFrame frame = new JFrame("Horizontal Bouncing String");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(color);
                g.drawString(str, x, y);
            }
        };
        panel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        JComboBox<String> colorComboBox = new JComboBox<>(COLORS);
        colorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = colorComboBox.getSelectedIndex();
                color = COLOR_VALUES[index];
            }
        });
        panel.add(colorComboBox);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        Timer timer = new Timer(DELAY_MS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x += dx;
                if (x + str.length() * STEP >= panel.getWidth() || x == 0) {
                    dx = -dx;
                    int index_col = (int) (Math.random() * COLOR_VALUES.length);
                    color = COLOR_VALUES[index_col];
                }

                panel.repaint();
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        new HorizontalBouncingString();
    }
}

