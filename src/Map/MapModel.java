package Map;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MapModel extends JPanel {

    private final JTable table = new JTable();
    private int side;

    private final int columns;

    public void setSide(int side) {
        this.side = side;
    }

    public void refresh() {
        for (int i = 0; i < columns; i++) {
            table.getColumnModel().getColumn(i).setMinWidth(6);
            table.getColumnModel().getColumn(i).setWidth(side);
        }
        table.setRowHeight(side);
    }

    public int getSide() {
        return side;
    }

    public  JTable getTable() {
        return table;
    }

    public MapModel(int rows, int columns) {

        this.columns = columns;

        if(rows <= 20 && columns <= 60) side = 30;
        else if(rows <= 35 && columns <= 70) side = 24;
        else if(rows <= 45 && columns <= 90) side = 18;
        else if(rows <= 60) side = 12;
        else side = 6;

        MapGenerator.generate(rows, columns);

        String[] columnNames = new String[columns];
        Arrays.fill(columnNames, "");

        MapTableModel model = new MapTableModel(MapGenerator.getData(), columnNames);

        table.setModel(model);

        for (int i = 0; i < columns; i++) table.getColumnModel().getColumn(i).setCellRenderer(new MapRenderer());

        refresh();

        table.setEnabled(false);
        table.setGridColor(Color.black);

        table.setBackground(Color.BLACK);

    }
}
