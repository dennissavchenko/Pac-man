package HighScores;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Serializer {

    public static void serialize(HighScore highScore) {

        List<HighScore> highScores = deserialize();

        highScores.add(highScore);

        try {
            FileOutputStream fileOut = new FileOutputStream("HighScores.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(highScores);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Object was not written!");
        }

    }

    public static List<HighScore> deserialize() {

        List<HighScore> highScores = new ArrayList<>();

        try {

            FileInputStream fileIn = new FileInputStream("HighScores.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            highScores = (List<HighScore>) in.readObject();
            in.close();
            fileIn.close();

        } catch (IOException e) {
            System.out.println("Object was not read!");
        } catch (ClassNotFoundException e) {
            System.out.println("Object was not accessed!");
        }

        Collections.sort(highScores);

        return highScores;

    }
}
