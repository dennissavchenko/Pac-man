package Map;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MapRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

        if (value instanceof String val) {
            if (val.equals("W")) {
                cellComponent.setBackground(Color.BLUE);
                cellComponent.setForeground(Color.blue);
            } else if(val.equals("G")) {
                cellComponent.setBackground(Color.DARK_GRAY);
                cellComponent.setForeground(Color.DARK_GRAY);
            } else {
                cellComponent.setBackground(Color.BLACK);
                cellComponent.setForeground(Color.BLACK);
            }
        }

        return cellComponent;

    }
}
