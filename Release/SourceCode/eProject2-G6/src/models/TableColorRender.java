package models;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TableColorRender extends JLabel implements TableCellRenderer{
    public static int rowcolumn = -1;
    public static int rows[] = null;
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(row%2 != 0){
            this.setBackground(new Color(255, 255, 255));
        }else{
            this.setBackground(new Color(235, 235, 235));
        }
        if(row == rowcolumn){
            this.setBackground(new Color(240, 240, 140));
        }
        if(rows != null){
            for (int i : rows) {
                if(row == i){
                    this.setBackground(new Color(51, 153, 255));
                }
            }
        }
        this.setOpaque(true);
        if(value != null){
            this.setText(value.toString());
        }
        else{
            this.setText("");
        }
        return this;
    }
    
    public static void clear(){
        rowcolumn = -1;
        rows = null;
    }
}