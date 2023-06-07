package Coins;

import Customs.CustomFont;
import Map.MapGenerator;

import javax.swing.*;
import java.awt.*;

public class CoinsView extends JPanel {
    public CoinsView(CoinsModel model) {

        for (int i = 0; i < model.getRows(); i++) {
            for (int j = 0; j < model.getColumns(); j++) {
                JLabel label = new JLabel();
                if ((MapGenerator.getData()[i][j].equals("•") || MapGenerator.getData()[i][j].equals("?")) && (int) (Math.random() * 50) == 0) {
                    label.setText("•");
                    label.setFont(new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.BOLD, model.getSide()));
                    model.getCoinsType()[i][j] = 2;
                } else if ((MapGenerator.getData()[i][j].equals("•") || MapGenerator.getData()[i][j].equals("?"))) {
                    label.setText("•");
                    label.setFont(new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.PLAIN, model.getSide() / 2));
                    model.getCoinsType()[i][j] = 1;
                } else {
                    label.setText(" ");
                    model.getCoinsType()[i][j] = 0;
                }
                label.setForeground(Color.WHITE);
                label.setOpaque(false);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);
                model.getCoins()[i][j] = label;
                add(label);
            }
        }

        setLayout(new GridLayout(MapGenerator.getData().length, MapGenerator.getData()[0].length, 0, 0));
        setOpaque(false);

    }
}
