package Game;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    public GameView(GameModel model) {

        Thread checkResize = new Thread(() -> {
            while (true) {
                int h = getHeight();
                int w = getWidth();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
                if (h - getHeight() == 0 && w - getWidth() == 0 && model.getResized()) setSize(model.getWidth(), model.getHeight() + model.getLivesView().getHeight() + 55);
            }
        });

        checkResize.start();

        model.getMapView().setBackground(Color.BLACK);

        setSize(model.getWidth(), model.getHeight() + (85 + model.getLivesView().getHeight()));

        model.getLayeredPane().setBounds(0, 0, getWidth(), getHeight());
        model.getLayeredPane().setBackground(Color.BLACK);

        model.getScoreView().setBackground(Color.black);

        for (int i = 0; i < model.getGhostModels().length; i++) {
            model.getLayeredPane().add(model.getGhostView()[i], JLayeredPane.DRAG_LAYER);
            model.getGhostView()[i].setBounds(0, 0, getWidth(), getHeight() - (85 + model.getLivesView().getHeight()));
            model.getGhostView()[i].setOpaque(false);
        }

        model.getLayeredPane().add(model.getPacmanView(), JLayeredPane.POPUP_LAYER);
        model.getLayeredPane().add(model.getCoinsView(), JLayeredPane.MODAL_LAYER);
        model.getLayeredPane().add(model.getMapView(), JLayeredPane.DEFAULT_LAYER);

        model.getMapView().setBounds(0, 0, getWidth(), getHeight());
        model.getPacmanView().setBounds(0, 0, getWidth(), getHeight() - (85 + model.getLivesView().getHeight()));
        model.getCoinsView().setBounds(0, 0, getWidth(), getHeight() - (85 + model.getLivesView().getHeight()));

        model.getPacmanView().setOpaque(false);
        model.getCoinsView().setOpaque(false);

        setLayout(new BorderLayout());

        add(model.getScoreView(), BorderLayout.NORTH);
        add(model.getLayeredPane(), BorderLayout.CENTER);
        add(model.getLivesView(), BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setTitle("Game — Level 1");
        getContentPane().setBackground(Color.BLACK);
        setBackground(Color.black);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}

