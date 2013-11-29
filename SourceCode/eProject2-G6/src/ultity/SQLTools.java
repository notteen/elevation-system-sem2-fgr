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
import objects.*;

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
    public ResultSet ListElevator(int limit) {
        try {
            String query = null;
            if (limit == 0) {
                query = "SELECT * FROM Elevator order by id DESC";
            } else {
                query = "SELECT TOP " + limit + " * FROM Elevator order by id DESC";
            }
            Connection conn = ConnectSQL.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            return res;
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet ListOrder(int limit) {
        try {
            String query = null;
            if (limit == 0) {
                query = "SELECT * FROM [Order] order by id DESC";
            } else {
                query = "SELECT TOP " + limit + " * FROM [Order] order by id DESC";
            }
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
            String query = "INSERT INTO [Order](clientid, orderdate, deliverydate, employeeid, price, status) VALUES(" + o.getClientid() + ", " + o.getOrderdate() + ", '" + o.getDeliverydate() + "', " + o.getEmployeeid() + ", " + o.getPrice() + ", " + o.getStatus() + ")";
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

    public int EditOrder(Order o) {
        String query = "UPDATE [Order] SET deliverydate = '" + o.getDeliverydate() + "', status = '" + o.getStatus() + "', price = " + o.getPrice() + ", employeeid = '" + o.getEmployeeid() + "' WHERE id = " + o.getId();
        int i = insertSQL(query);
        return i;
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

    public int removeBill(int orderid) {
        try {
            String query = "DELETE FROM Bill WHERE orderid = " + orderid;
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
            String query = "select * from Clients where id = " + id;
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
        }
        return null;
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
        }
        return 0;
    }

    //PhuongLH
    public ResultSet resultSQL(String sql) {
        try {
            Connection conn = ConnectSQL.getConnection();
            Statement st = conn.createStatement();
            st.execute(sql);
            ResultSet rs = st.getResultSet();
            if (rs.next()) {
                return rs;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getStringSQL(String sql) {
        try {
            Connection conn = ConnectSQL.getConnection();
            Statement st = conn.createStatement();
            st.execute(sql);
            ResultSet rs = st.getResultSet();
            if (rs.next()) {
                return rs.getString(1);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet getUser(String username) {
        try {
            ResultSet rs = resultSQL("Select * FROM Users WHERE username = '" + username + "'");
            return rs;
        } catch (Exception ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean existUser(String username) {
        boolean check = false;
        try {
            ResultSet rs = selectSQL("Select * FROM Users WHERE username = '" + username + "'");
            if(rs.next()){
                check = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    public int changePassword(int userid, String password) {
        String query = "UPDATE [Users] SET password = '" + password + "' WHERE id = " + userid;
        int i = insertSQL(query);
        return i;
    }

    public int editUser(Employee e) {
        int i = 0;
        if(!e.getPassword().equals("")){
            String query = "UPDATE [Users] SET password = '" + e.getPassword() + "' WHERE id = " + e.getUserId();
            insertSQL(query);
        }
        String query1 = "UPDATE [Employee] SET fullname = '"+e.getFullname()+"', email = '"+e.getEmail()+"', contact = '"+e.getContact()+"', gender = "+e.getGender()+" WHERE userid = "+e.getUserId();
        i = insertSQL(query1);
        return i;
    }

    public ResultSet getEmployee(int id) {
        try {
            ResultSet rs = resultSQL("SELECT * FROM Employee WHERE userid = " + id);
            return rs;
        } catch (Exception ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int editEmplyoee(Employee e) {
        String query = "UPDATE [Employee] SET fullname = '" + e.getFullname() + "', gender = " + e.getGender() + ", email = '" + e.getEmail() + "', contact = '" + e.getContact() + "' WHERE id = " + e.getId();
        int i = insertSQL(query);
        return i;
    }

    public int addComplaint(Complaint c) {
        String query = "INSERT INTO Complaints (clientid, employeeid, complaint, resolve, cdate, rdate, status) VALUES (" + c.getClientid() + ", " + c.getEmployeeid() + ", '" + c.getComplaint() + "', '" + c.getResolve() + "', '" + c.getCdate() + "', '" + c.getRdate() + "', " + c.getStatus() + ")";
        int i = insertSQL(query);
        return i;
    }

    public int editComplaint(Complaint c) {
        String query = "UPDATE Complaints SET employeeid = " + c.getEmployeeid() + ", resolve = '" + c.getResolve() + "', rdate = '" + c.getRdate() + "', status = " + c.getStatus() + " WHERE id = " + c.getId();
        int i = insertSQL(query);
        return i;
    }

    public int addMaintenance(Maintenance m) {
        String query = "INSERT INTO Maintenance (billid, orderdate, deliverydate, firstyear, secondyear, thirdyear) VALUES (" + m.getBillid() + ", '" + m.getOrderdate() + "', '" + m.getDeliverydate() + "', '" + m.getFirstyear() + "', '" + m.getSecondyear() + "', '" + m.getThirdyear() + "')";
        int i = insertSQL(query);
        return i;
    }
    //ToanLM

    public int AddUser(Employee e) {
        try {
            String query = "INSERT INTO Users(username, password, accounttype, status) VALUES('" + e.getUsername() + "', '" + e.getPassword() + "', 2, 1)";
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

    public ResultSet LastUser() {
        try {
            String query = "select * from Users where id = (select MAX(id) from Users)";
            Connection conn = ConnectSQL.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            return res;
        } catch (SQLException ex) {
            Logger.getLogger(SQLTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int AddEmployee(Employee e) {
        try {
            String query = "INSERT INTO Employee(userid, fullname, gender, email, contact) VALUES('" + e.getUserId() + "', '" + e.getFullname() + "', '" + e.getGender() + "', '" + e.getEmail() + "', '" + e.getContact() + "')";
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
    
    public int removeEmployee(Employee e) {
        int i = 0;
        String query = "DELETE FROM Employee WHERE userid = "+e.getUserId();
        i = insertSQL(query);
        if(i == 1){
            String query1 = "DELETE FROM Users WHERE id = "+e.getUserId();
            i = insertSQL(query1);
        }
        return i;
    }

    public int removeElevator(Elevator e) {
        int i = 0;
        String query = "DELETE FROM Elevator WHERE id = "+e.getId();
        i = insertSQL(query);
        return i;
    }
    
    public int editClient(Client c) {
        String query = "UPDATE Clients SET name = '"+c.getName()+"', address = '"+c.getAddress()+"', company = '"+c.getCompany()+"', email = '"+c.getEmail()+"', mobile = '"+c.getMobile()+"' WHERE id = "+c.getId();
        int i = insertSQL(query);
        return i;
    }

    public int removeClient(Client c) {
        int i = 0;
        String query = "DELETE FROM Clients WHERE id = "+c.getId();
        i = insertSQL(query);
        return i;
    }
    
    public int removeComplaint(Complaint c) {
        int i = 0;
        String query = "DELETE FROM Complaints WHERE id = "+c.getId();
        i = insertSQL(query);
        return i;
    }
    
    public int removeMaintenance(Maintenance c) {
        int i = 0;
        String query = "DELETE FROM Maintenance WHERE id = "+c.getId();
        i = insertSQL(query);
        return i;
    }
    

}