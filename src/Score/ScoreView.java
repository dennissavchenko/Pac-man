package Score;

import Customs.CustomFont;

import javax.swing.*;
import java.awt.*;

public class ScoreView extends JPanel {

    public ScoreView(ScoreModel model) {

        setLayout(new BorderLayout());

        model.getScore().setForeground(Color.white);
        //model.getLevelLabel().setForeground(Color.white);
        model.getHighScore().setForeground(Color.white);

        Font semiBold = new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.BOLD, 14);
        Font regular = new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.PLAIN, 14);

        model.getScore().setFont(semiBold);
        //model.getLevelLabel().setFont(regular);
        model.getHighScore().setFont(regular);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(model.getScore(), BorderLayout.WEST);
        add(model.getHighScore(), BorderLayout.EAST);

    }
}
