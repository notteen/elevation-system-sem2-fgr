/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import objects.Order;
import ultity.SQLTools;

/**
 *
 * @author notte_000
 */
public class OrderTable extends AbstractTableModel{
    public List<Order> list;
    SQLTools st = new SQLTools();
    
    public OrderTable(List<Order> list) {
        this.list = list;
    }
        
    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order o = list.get(rowIndex);
        switch(columnIndex){
            case 0: return o.getId();
            case 1: return getDate(o.getOrderdate());
            case 2: return o.getClientname();
            case 3: return o.getPrice() + " USD";
            case 4: return getStatus(o.getStatus());
            default: return null;
        }
    }
    
    @Override
    public String getColumnName(int column){
        switch(column){
            case 0: return "Order Number";
            case 1: return "Order Date";
            case 2: return "Customer Name";
            case 3: return "Total Amount";
            case 4: return "Status";
            default: return null;
        }
    }
    
    public String getDate(long l){
        Date date = new Date(l);
        return date.toString();
    }
    
    public String getStatus(int i){
        switch(i){
            case 0: return "Cancelled";
            case 1: return "Ordered";
            case 2: return "Processed";
            case 3: return "Delivered";
            default: return null;
        }
    }
}