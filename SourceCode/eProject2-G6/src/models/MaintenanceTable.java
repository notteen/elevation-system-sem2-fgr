/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import Connect.ObjectsData;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import objects.Maintenance;
import ultity.Tools;

/**
 *
 * @author notte_000
 */
public class MaintenanceTable extends AbstractTableModel{
    private List<Maintenance> list;

    public MaintenanceTable(List<Maintenance> list) {
        this.list = list;
    }
    
    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Maintenance mt = list.get(rowIndex);
        ObjectsData od = new ObjectsData();
        switch(columnIndex){
            case 0: return mt.getOrderid();
            case 1: return mt.getElevatorid();
            case 2: return mt.getOrderdate();
            case 3: return mt.getDeliverydate();
            case 4: return Tools.isExpired(1, mt);
            case 5: return Tools.isExpired(2, mt);
            case 6: return Tools.isExpired(3, mt);
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0: return "Order Number";
            case 1: return "Machine Number";
            case 2: return "Order date";
            case 3: return "Delivery date";
            case 4: return "1st maintenance";
            case 5: return "2nd maintenance";
            case 6: return "3rd maintenance";
            default: return null;
        }
    }
    
    
    
}
