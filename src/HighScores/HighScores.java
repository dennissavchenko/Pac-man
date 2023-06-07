package HighScores;

import Customs.CustomFont;
import Listeners.QuitListener;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextHitInfo;
import java.util.List;

public class HighScores extends JFrame {

    private void listSettings(JList list) {

        list.setCellRenderer(new HighScoresCellRenderer());

        list.setFont(new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.PLAIN, 14));
        list.setForeground(Color.white);
        list.setBackground(Color.black);

        list.setSelectionModel(new NoneSelectionModel());

    }

    public HighScores() {

        super("High Scores");

        List<HighScore> highScores = Serializer.deserialize();

        String[] names = new String[highScores.size() + 1];
        String[] score = new String[highScores.size() + 1];
        String[] date = new String[highScores.size() + 1];

        names[0] = "Name";
        score[0] = "Score";
        date[0] = "Date";

        for (int i = 1; i < names.length; i++) {
            names[i] = highScores.get(i - 1).getName();
            score[i] = String.valueOf(highScores.get(i - 1).getScore());
            date[i] = highScores.get(i - 1).getDate();
        }

        JList<String> namesList = new JList<>(names);
        JList<String> scoreList = new JList<>(score);
        JList<String> dateList = new JList<>(date);

        listSettings(namesList);
        listSettings(scoreList);
        listSettings(dateList);

        JPanel panel = new JPanel();
        panel.setBackground(Color.black);

        panel.setLayout(new FlowLayout());

        panel.add(namesList);
        panel.add(scoreList);
        panel.add(dateList);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBackground(Color.BLACK);

        add(scrollPane);

        setFocusable(true);

        addKeyListener(new QuitListener(this));

        pack();
        setVisible(true);
        setTitle("Pacman — High Scores");
        setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
}
