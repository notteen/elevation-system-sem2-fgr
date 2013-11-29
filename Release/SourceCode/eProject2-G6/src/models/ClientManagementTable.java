/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import objects.Client;

/**
 *
 * @author ToanLM
 */
public class ClientManagementTable extends AbstractTableModel{
    public List<Client> listClient;
    
    public ClientManagementTable(List<Client> listClient) {
        this.listClient = listClient;
    }
    
    public Client getClient(int index) {
        return this.listClient.get(index);
    }
    
    @Override
    public int getRowCount() {
        return this.listClient.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Client currentClient = this.listClient.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return currentClient.getName();
            case 1:
                return currentClient.getEmail();
            
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int column){
        switch(column){
            case 0: return "Name";
            case 1: return "Email";
            default: return null;
        }
    }
    
}
