/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import objects.Elevator;

/**
 *
 * @author notte_000
 */
public class OrderDetailTable extends AbstractTableModel{
    private List<Elevator> list;

    public OrderDetailTable(List<Elevator> list) {
        this.list = list;
    }
    
    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Elevator el = list.get(rowIndex);
        int amount = (int) (el.getQuantity() * el.getPrice());
        switch(columnIndex){
            case 0: return el.getName();
            case 1: return el.getQuantity();
            case 2: return el.getPrice() + " USD";
            case 3: return amount + " USD";
            default: return null;
        }
    }
    
    @Override
    public String getColumnName(int column){
        switch(column){
            case 0: return "Product Name";
            case 1: return "Quantity";
            case 2: return "Unit Price";
            case 3: return "Amount";
            default: return null;
        }
    }
}
