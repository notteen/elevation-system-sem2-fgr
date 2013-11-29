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
public class ElevatorTable extends AbstractTableModel{
    
    public List<Elevator> listElevator;

    public ElevatorTable(List<Elevator> listElevator) {
        this.listElevator = listElevator;
    }
    
    public Elevator getElevator(int index) {
        return this.listElevator.get(index);
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
        Elevator el = this.listElevator.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return el.getName();
            case 1:
                return el.getType();
            case 2:
                return getWarranty(el.getWarranty());
            default:
                return null;
        }
    }
    @Override
    public String getColumnName(int column){
        switch(column){
            case 0: return "Name";
            case 1: return "Elavator System Type";
            case 2: return "Warranty Period";
            default: return null;
        }
    }
    
    private String getWarranty(int i){
        if(i < 2){
            return i + " year";
        }
        else{
            return i + " years";
        }
    }
}
