/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.swing.table.AbstractTableModel;
import objects.*;
import ultity.Validator;

/**
 *
 * @author notte_000
 */
public class ElevatorDetailTable extends AbstractTableModel{
    Elevator el;
    Validator validate = new Validator();

    public ElevatorDetailTable(Elevator el) {
        this.el = el;
    }
    
    @Override
    public int getRowCount() {
        return 8;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:{
                switch(rowIndex){
                    case 0: return "Machine Number";
                    case 1: return "Name";
                    case 2: return "Type";
                    case 3: return "Price";
                    case 4: return "Weight";
                    case 5: return "Speed";
                    case 6: return "Made in";
                    case 7: return "Warranty";
                    default: return "";
                }
            }
            case 1:{
                switch(rowIndex){
                    case 0: return el.getId();
                    case 1: return el.getName();
                    case 2: return el.getType();
                    case 3: return validate.Money(el.getPrice()) + " USD";
                    case 4: return el.getWeight() + " kg";
                    case 5: return el.getSpeed() + " m/s";
                    case 6: return el.getMadein();
                    case 7: return el.getWarranty() + " year(s)";
                    default: return "";
                }
            }
            default: return null;
        }
    }
    
    @Override
    public String getColumnName(int column){
        switch(column){
            case 0: return "Property";
            case 1: return "Value";
            default: return null;
        }
    
    }
}
