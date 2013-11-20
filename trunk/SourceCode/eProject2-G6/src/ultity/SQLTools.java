/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ultity;

import Connect.ConnectSQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Bill;
import objects.Client;
import objects.Elevator;
import objects.Order;

/**
 *
 * @author ToanLM
 */
public class SQLTools {

    //ToanLM
    public int AddElevator(Elevator e) {
        try {
            String query = "INSERT INTO Elevator(name, image, type, price, weight, speed, madein, warranty) VALUES('" + e.getName() + "', '" + e.getImage() + "', '" + e.getType() + "', '" + e.getPrice() + "', '" + e.getWeight() + "', '" + e.getSpeed() + "', '" + e.getMadein() + "', '" + e.getWarranty() + "')";
            Connection conn = ConnectSQL.getConnection();
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            st.close();
            conn.close();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int UpdateElevator(Elevator e) {
        try {
            String query = "UPDATE Elevator SET name = '" + e.getName() + "', image = '" + e.getImage() + "', type = '" + e.getType() + "', price = '" + e.getPrice() + "', weight = '" + e.getWeight() + "', speed = '" + e.getSpeed() + "', madein = '" + e.getMadein() + "', warranty = '" + e.getWarranty() + "' WHERE id = '" + e.getId() + "'";
            Connection conn = ConnectSQL.getConnection();
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            st.close();
            conn.close();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int AddClient(Client c) {
        try {
            String query = "INSERT INTO Clients(name, company, address, email, mobile) VALUES('" + c.getName() + "', '" + c.getCompany() + "', '" + c.getAddress() + "', '" + c.getEmail() + "', '" + c.getMobile() + "')";
            Connection conn = ConnectSQL.getConnection();
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            st.close();
            conn.close();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    //ToanLM
    public ResultSet ListElevator() {
        try {
            String query = "SELECT * FROM Elevator order by id DESC";
            Connection conn = ConnectSQL.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            return res;
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet ListLastClient() {
        try {
            String query = "select * from dbo.Clients where id = (select MAX(id) from dbo.Clients)";
            Connection conn = ConnectSQL.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            return res;
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet ListClient() {
        try {
            String query = "SELECT * FROM Clients order by id DESC";
            Connection conn = ConnectSQL.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            return res;
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int AddOrder(Order o) {
        try {
            String query = "INSERT INTO [Order](clientid, orderdate, employeeid, price, status) VALUES(" + o.getClientid() + ", " + o.getOrderdate() + ", " + o.getEmployeeid() + ", " + o.getPrice() + ", " + o.getStatus() + ")";
            Connection conn = ConnectSQL.getConnection();
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            st.close();
            conn.close();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ResultSet LastOrder() {
        try {
            String query = "select * from dbo.[Order] where id = (select MAX(id) from dbo.[Order])";
            Connection conn = ConnectSQL.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            return res;
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int AddBill(Bill b) {
        try {
            String query = "INSERT INTO Bill(orderid, elevatorid, quantity) VALUES('" + b.getOrderid() + "', '" + b.getElevatorid() + "', '" + b.getQuantity() + "')";
            Connection conn = ConnectSQL.getConnection();
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            st.close();
            conn.close();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ResultSet ClientbyID(int id) {
        try {
            String query = "select * from Clients where id = " + id + "";
            Connection conn = ConnectSQL.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            return res;
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //PhuongLH
    public ResultSet selectSQL(String sql) {
        try {
            Connection conn = ConnectSQL.getConnection();
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    //PhuongLH
    public int insertSQL(String sql) {
        try {
            Connection conn = ConnectSQL.getConnection();
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.executeUpdate(sql);
            st.close();
            conn.close();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    //PhuongLH
    public ResultSet resultSQL(String sql) {
        try {
            Connection conn = ConnectSQL.getConnection();
            Statement st = conn.createStatement();
            st.execute(sql);
            ResultSet rs = st.getResultSet();
            if(rs.next()){
                return rs;
            }
            else{
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public String getStringSQL(String sql){
       try {
            Connection conn = ConnectSQL.getConnection();
            Statement st = conn.createStatement();
            st.execute(sql);
            ResultSet rs = st.getResultSet();
            if(rs.next()){
                return rs.getString(1);
            }
            else{
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }
}