package Coins;

import Customs.CustomFont;
import Map.MapGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CoinsModel {

    private int rows;

    private int columns;

    private int side;

    private JLabel[][] coins;

    private int[][] coinsType;

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public JLabel[][] getCoins() {
        return coins;
    }

    public int[][] getCoinsType() {
        return coinsType;
    }

    public int getSide() {
        return side;
    }

    public void setCoinsType(int r, int c, int val) {
        coinsType[r][c] = val;
    }

    public void setSide(int side) {
        this.side = side;
        for (int i = 0; i < coinsType.length; i++) {
            for(int j = 0; j < coinsType[i].length; j++) {
                if(coinsType[i][j] == 2) coins[i][j].setFont(new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.PLAIN, side));
                else if(coinsType[i][j] == 1) coins[i][j].setFont(new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.PLAIN, side / 2));
                else if(coinsType[i][j] > 2) {
                    coins[i][j].setSize(side * 2 / 3, side * 2 / 3);
                    ImageIcon icon = null;
                    switch (coinsType[i][j]) {
                        case 3 -> icon = new ImageIcon("./Images/super/speed_up.png");
                        case 4 -> icon = new ImageIcon("./Images/super/2x.png");
                        case 5 -> icon = new ImageIcon("./Images/super/slow_down.png");
                        case 6 -> icon = new ImageIcon("./Images/super/cave.png");
                        case 7 -> icon = new ImageIcon("./Images/super/life.png");
                    }
                    if(icon != null) {
                        Image image = icon.getImage();
                        Image scaledImage = image.getScaledInstance(coins[i][j].getWidth(), coins[i][j].getHeight(), Image.SCALE_SMOOTH);
                        icon = new ImageIcon(scaledImage);
                        coins[i][j].setText("");
                        coins[i][j].setIcon(icon);
                    }
                }
            }
        }
    }

    public void resetCoins() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                coins[i][j].setIcon(null);
                if ((MapGenerator.getData()[i][j].equals("•") || MapGenerator.getData()[i][j].equals("?")) && (int) (Math.random() * 50) == 0) {
                    coins[i][j].setText("•");
                    coins[i][j].setFont(new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.BOLD, side));
                    coinsType[i][j] = 2;
                } else if ((MapGenerator.getData()[i][j].equals("•") || MapGenerator.getData()[i][j].equals("?"))) {
                    coins[i][j].setText("•");
                    coins[i][j].setFont(new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.BOLD, side / 2));
                    coinsType[i][j] = 1;
                } else {
                    coins[i][j].setText(" ");
                    coinsType[i][j] = 0;
                }
            }
        }
    }


    public CoinsModel(int side) {

        this.side = side;

        rows = MapGenerator.getData().length;
        columns = MapGenerator.getData()[0].length;

        coins = new JLabel[rows][columns];
        coinsType = new int[rows][columns];

    }

}
