/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import objects.Elevator;

/**
 *
 * @author ToanLM
 */
public class ElevatorOrderTable extends AbstractTableModel{
    public List<Elevator> listElevator;
    
    public ElevatorOrderTable(List<Elevator> listElevator) {
        this.listElevator = listElevator;
    }
    
    @Override
    public int getRowCount() {
        return this.listElevator.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Elevator currentElevator = this.listElevator.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return currentElevator.getName();
            case 1:
                return getPrice(currentElevator.getPrice());
            case 2: 
                return currentElevator.getQuantity();
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int column){
        switch(column){
            case 0: return "Name";
            case 1: return "Price";
            case 2: return "Qty";
            default: return null;
        }
    }
    
    @Override
    public Class getColumnClass(int columnIndex){
        return getValueAt(0, columnIndex).getClass();
    }
    
    private String getPrice(float p){
        return p + " USD";
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        return col >= 2;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        Elevator el = listElevator.get(row);
        if (col == 2){
            el.setQuantity(Integer.parseInt(value.toString()));
        }
        fireTableCellUpdated(row, col);
    }
}
