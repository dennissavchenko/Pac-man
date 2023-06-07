package Lives;

import Customs.CustomFont;

import javax.swing.*;
import java.awt.*;

public class LivesView extends JPanel{
    public static class DrawPacman extends JLabel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.YELLOW);
            g.fillArc(0, 0, 16, 16, 45, 270);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(16, 16);
        }

    }

    public LivesView(LivesModel model) {

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        model.getLifePanel().setLayout(new FlowLayout());
        model.getLifePanel().setBackground(Color.black);
        model.getLabel().setFont(new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.PLAIN, 14));
        model.getLabel().setForeground(Color.white);
        model.getLabel().setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));

        add(model.getLifePanel(), BorderLayout.LINE_START);
        add(model.getLabel(), BorderLayout.LINE_END);

    }
}
