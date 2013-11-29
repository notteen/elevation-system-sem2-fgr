/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import objects.Complaint;

/**
 *
 * @author notte_000
 */
public class ComplaintTable extends AbstractTableModel{
    List<Complaint> list;

    public ComplaintTable(List<Complaint> list) {
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
        Complaint cp = list.get(rowIndex);
        switch(columnIndex){
            case 0: return cp.getClientid();
            case 1: return cp.getClientname();
            case 2: return shotMessage(cp.getComplaint());
            case 3: return shotMessage(cp.getResolve());
            case 4: return Status(cp.getStatus());
            default: return null;
        }
    }

    @Override
    public String getColumnName(int col){
        switch(col){
            case 0: return "Client Id";
            case 1: return "Client Name";
            case 2: return "Complaint";
            case 3: return "Resolve";
            case 4: return "Status";
            default: return null;
        }
    }
    
    private String shotMessage(String text){
        if(!text.equals("")){
            String str[] = text.split(" ");
            String result = "";
            for (int i = 0; i < 3; i++) {
                result += " " + str[i];
            }
            return result.trim() + "...";
        }
        else{
            return "";
        }
    }

    private String Status(int status){
        switch(status){
            case 0: return "Can't resolve";
            case 1: return "Pending";
            case 2: return "Resolved";
            default: return null;
        }
    }
}
