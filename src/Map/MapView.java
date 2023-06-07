package Map;

import javax.swing.*;
import java.awt.*;

public class MapView extends JPanel {
    public MapView(MapModel model) {
        setLayout(new BorderLayout());
        add(model.getTable(), BorderLayout.CENTER);
    }
}
