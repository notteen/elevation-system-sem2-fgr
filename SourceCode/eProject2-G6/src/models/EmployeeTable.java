/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import objects.Employee;

/**
 *
 * @author notte_000
 */
public class EmployeeTable extends AbstractTableModel{
    List<Employee> list;

    public EmployeeTable(List<Employee> list) {
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
        Employee e = list.get(rowIndex);
        switch(columnIndex){
            case 0: return e.getUserId();
            case 1: return e.getUsername();
            case 2: return e.getFullname();
            case 3: return e.getEmail();
            case 4: return e.getContact();
            case 5: return Gender(e.getGender());
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0: return "User Id";
            case 1: return "Username";
            case 2: return "Fullname";
            case 3: return "Email";
            case 4: return "Contact";
            case 5: return "Gender";
            default: return null;
        }
    }
    
    private String Gender(int gender){
        if(gender == 1){
            return "Male";
        }
        else{
            return "Female";
        }
    }
    
}
