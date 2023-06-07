package Score;

import HighScores.Serializer;
import Map.MapGenerator;

import javax.swing.*;

public class ScoreModel {

    private final JLabel score;

    private final JLabel highScore;

    private final int highScorePoints;

    private int points;

    public int getPoints() {
        return points;
    }

    public JLabel getScore() {
        return score;
    }

    public JLabel getHighScore() {
        return highScore;
    }

    public void start() {
        Thread thread = new Thread(() -> {
            MapGenerator.lock();
            score.setText("Ready!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            score.setText("Set!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            score.setText("Go!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            score.setText("Score: " + points);
            MapGenerator.unlock();
        });
        thread.start();
    }

    public void setScore(int a) {
        points += a;
        if(points >= highScorePoints) highScore.setText("High Score: " + points);
        score.setText("Score: " + points);
    }

    public ScoreModel() {
        points = 0;
        highScorePoints = Serializer.deserialize().get(0).getScore();
        score = new JLabel("0");
        highScore = new JLabel("High Score: " + highScorePoints);
        start();
    }

}
