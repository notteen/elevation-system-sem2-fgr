/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import objects.Print;
import ultity.Validator;
/**
 *
 * @author notte_000
 */
public class PrintTable extends AbstractTableModel{
    List<Print> list;
    Validator validate = new Validator();
    
    public PrintTable(List list) {
        this.list = list;
    }
    
    @Override
    public int getRowCount() {
        if(list != null){
            return list.size();
        }
        else{
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Print p = list.get(rowIndex);
        switch(columnIndex){
            case 0: return rowIndex + 1;
            case 1: return p.getDescription();
            case 2: return p.getUnit();
            case 3: return p.getQuantity();
            case 4: return validate.Money(p.getPrice()) + "$";
            case 5: return validate.Money(p.getAmount()) + "$";
            default: return null;
        }
    }
    
    @Override
    public String getColumnName(int column){
        switch(column){
            case 0: return "No.";
            case 1: return "Description";
            case 2: return "Unit";
            case 3: return "Quantity";
            case 4: return "Unit price";
            case 5: return "Amount";
            default: return null;
        }
    }
}
