package Lives;

import javax.swing.*;

public class LivesModel {
    private int life = 5;

    private int seconds = 0;

    private final LivesView.DrawPacman[] pacman;

    private final JPanel lifePanel;

    private JLabel label;

    public JPanel getLifePanel() {
        return lifePanel;
    }

    public JLabel getLabel() {
        return label;
    }

    private final Thread timer = new Thread(() -> {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            seconds++;
            String secS;
            String minS;
            int sec = seconds % 60;
            int min = seconds / 60;
            if(sec < 10) secS = "0" + sec;
            else secS = String.valueOf(sec);
            if(min < 10) minS = "0" + min;
            else minS = String.valueOf(min);
            label.setText(minS + ":" + secS);
        }
    });

    public void interruptTimer() {
        timer.interrupt();
    }

    public LivesModel() {
        label = new JLabel("00:00");
        lifePanel = new JPanel();
        pacman = new LivesView.DrawPacman[life];
        for(int i = 0; i < life; i++) {
            pacman[i] = new LivesView.DrawPacman();
            lifePanel.add(pacman[i]);
        }
        timer.start();
    }

    public void death() {
        pacman[life - 1].setVisible(false);
        life--;
    }

    public int getLife() {
        return life;
    }

    public void newLife() {
        if(life < 5) {
            pacman[life].setVisible(true);
            life++;
        }
    }
}
