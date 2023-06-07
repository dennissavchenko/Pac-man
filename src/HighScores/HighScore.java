package HighScores;

import java.io.Serializable;

public class HighScore implements Serializable, Comparable<HighScore> {

    private final String name;

    private final int score;

    private final String date;

    public HighScore(String name, int score, String date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int compareTo(HighScore o) {
        return Integer.compare(o.score, this.score);
    }
}
