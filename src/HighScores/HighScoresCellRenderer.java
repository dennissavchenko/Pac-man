package HighScores;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class HighScoresCellRenderer extends DefaultListCellRenderer {
    private final Border border = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY);

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        int padding = 5;
        renderer.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(padding, padding, padding, padding)));

        return renderer;
    }
}
