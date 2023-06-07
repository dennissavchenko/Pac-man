package Pacman;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PacmanView extends JPanel {

    private final PacmanModel model;

    public PacmanView(PacmanModel model) {
        this.model = model;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.YELLOW);
        int x = model.getX();
        int y = model.getY();
        int side = model.getSide();
        int angle = model.getAngle();
        int arc = model.getArc();
        g.fillArc(x, y, side * 2 / 3, side * 2 / 3, angle, arc);
    }


}

