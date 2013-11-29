/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import objects.Client;

/**
 *
 * @author notte_000
 */
public class ClientTable extends AbstractTableModel{
    public List<Client> lc;
    
    public ClientTable(List<Client> lc) {
        this.lc = lc;
    }
    
    public Client getClient(int index) {
        return this.lc.get(index);
    }
    
    @Override
    public int getRowCount() {
        if(lc != null){
            return this.lc.size();
        }
        else{
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Client c = this.lc.get(rowIndex);
        switch (columnIndex) {
            case 0: return c.getName();
            case 1: return c.getCompany();
            case 2: return c.getAddress();
            case 3: return c.getEmail();
            case 4: return c.getMobile();
            default: return null;
        }
    }
    
    @Override
    public String getColumnName(int column){
        switch(column){
            case 0: return "Name";
            case 1: return "Company";
            case 2: return "Address";
            case 3: return "Email";
            case 4: return "Mobile";
            default: return null;
        }
    }
}
