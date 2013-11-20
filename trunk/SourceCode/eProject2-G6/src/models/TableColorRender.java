package models;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TableColorRender extends JLabel implements TableCellRenderer{
    public static int rowcolumn = -1;
    public static int rows[] = {};
    public static boolean clear;
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(clear == true){
            this.removeAll();
        }
        if(row%2 != 0){
            this.setBackground(new Color(255, 255, 255));
        }else{
            this.setBackground(new Color(220, 220, 220));
        }
        if(row == rowcolumn){
            this.setBackground(new Color(240, 240, 140));
        }
        for (int i : rows) {
            if(row == i){
                this.setBackground(new Color(51, 153, 255));
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
}