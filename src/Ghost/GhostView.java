package Ghost;

import java.awt.*;
import javax.swing.*;

public class GhostView extends JPanel {
    public GhostView(GhostModel model) {

        setLayout(null);

        ImageIcon icon = new ImageIcon("./Images/" + model.getColour() + "/" + model.getColour() + "_up_move.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(model.getLabel().getWidth(), model.getLabel().getHeight(), Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);

        model.getLabel().setIcon(icon);
        model.getLabel().setLocation(model.getX(), model.getY());

        add(model.getLabel());

    }


}

