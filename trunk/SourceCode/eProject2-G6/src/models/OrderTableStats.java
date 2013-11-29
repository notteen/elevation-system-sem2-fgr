/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import Connect.ObjectsData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import objects.Client;
import objects.Order;
import ultity.SQLTools;
import ultity.Validator;

/**
 *
 * @author notte_000
 */
public class OrderTableStats extends AbstractTableModel{
    public List<Order> list;
    SQLTools st = new SQLTools();
    Validator validate = new Validator();
    
    public OrderTableStats(List<Order> list) {
        this.list = list;
    }
    
    public Order getOrder(int i){
        return list.get(i);
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
        ObjectsData od = new ObjectsData();
        Client c = od.getClientById(o.getClientid());
        switch(columnIndex){
            case 0: return o.getId();
            case 1: return getDate(o.getOrderdate());
            case 2: return c.getName();
            case 3: return validate.Money(o.getPrice()) + " USD";
            case 4: return getStatus(o.getStatus());
            default: return null;
        }
    }
    
    @Override
    public String getColumnName(int column){
        switch(column){
            case 0: return "ID";
            case 1: return "Order Date";
            case 2: return "Customer Name";
            case 3: return "Total Amount";
            case 4: return "Status";
            default: return null;
        }
    }
    
    public String getDate(long l){
            Date date = new Date(l);
            SimpleDateFormat ft = new SimpleDateFormat("M/d/Y");
            return ft.format(date);
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