package Game;

import Customs.CustomDate;
import Customs.CustomFont;
import HighScores.HighScore;
import HighScores.Serializer;
import Listeners.ButtonMouseListener;

import javax.swing.*;
import java.awt.*;

public class GameOver extends JFrame {

    public GameOver(GameModel gameModel) {

        Font semiBold = new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.BOLD, 12);

        JTextField textField = new JTextField();
        JLabel label = new JLabel("Enter name:");

        label.setForeground(Color.GRAY);

        textField.setFont(new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.PLAIN, 12));
        textField.setCaretColor(Color.WHITE);
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.white);
        textField.setBorder(null);
        textField.setPreferredSize(new Dimension(115, 30));

        label.setFont(new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.BOLD, 12));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBackground(Color.BLACK);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(4, 6, 0, 0));

        inputPanel.add(label);
        inputPanel.add(textField);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.BLACK);

        JLabel info1 = new JLabel("You have gained " + gameModel.getScoreModel().getPoints() + " points. Do you want");
        info1.setFont(new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.PLAIN, 11));
        info1.setBackground(Color.BLACK);
        info1.setForeground(Color.GRAY);
        info1.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        JLabel info2 = new JLabel("to save this game to high scores?");
        info2.setFont(new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.PLAIN, 11));
        info2.setBackground(Color.BLACK);
        info2.setForeground(Color.GRAY);
        info2.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));

        infoPanel.add(Box.createVerticalGlue());
        infoPanel.add(info1);
        infoPanel.add(info2);
        infoPanel.add(Box.createVerticalGlue());

        JPanel panelButtons = new JPanel();

        panelButtons.setBackground(Color.BLACK);

        JButton cancel = new JButton("Cancel");
        JButton confirm = new JButton("Confirm");

        cancel.setFont(semiBold);
        confirm.setFont(semiBold);

        cancel.addActionListener(e -> {
            gameModel.getView().dispose();
            dispose();
        });

        panelButtons.add(cancel);
        panelButtons.add(confirm);

        cancel.setOpaque(true);
        cancel.setBorderPainted(false);
        cancel.setForeground(Color.white);
        cancel.setBackground(Color.BLACK);

        cancel.addMouseListener(new ButtonMouseListener());
        confirm.addMouseListener(new ButtonMouseListener());

        cancel.setFocusPainted(false);
        confirm.setFocusPainted(false);

        confirm.addActionListener(e -> {
            if(textField.getText().length() > 0) {
                boolean unique = true;
                for (HighScore highScore : Serializer.deserialize()) {
                    if(highScore.getName().equals(textField.getText())) {
                        info1.setText("You already have game with this name!");
                        info2.setText("Name has to be unique!");
                        unique = false;
                        break;
                    }
                }
                if (unique) {
                    Serializer.serialize(new HighScore(textField.getText(), gameModel.getScoreModel().getPoints(), CustomDate.getCurrentDate()));
                    gameModel.getView().dispose();
                    dispose();
                }
            }
        });

        panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        pack();
        setBackground(Color.BLACK);
        setTitle("Pacman — Save Game");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

}
