package Map;

import javax.swing.table.AbstractTableModel;

public class MapTableModel extends AbstractTableModel {

    Object[][] data;
    String[] columns;

    public MapTableModel(Object[][] data, String[] columns) {
        this.data = data;
        this.columns = columns;
    }


    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() { return columns.length; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
