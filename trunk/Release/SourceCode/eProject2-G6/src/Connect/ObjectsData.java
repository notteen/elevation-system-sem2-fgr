/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.*;
import ultity.SQLTools;

/**
 *
 * @author ToanLM
 */
public class ObjectsData {
    SQLTools st = new SQLTools();

    public List<Elevator> getAllElevator(int limit) {
        try {
            ResultSet rs = st.ListElevator(limit);
            List<Elevator> list = new ArrayList<>();
            while (rs.next()) {
               list.add(new Elevator(rs.getInt("id"), rs.getString("name"), rs.getString("image"), rs.getString("type"), rs.getFloat("price"), rs.getFloat("weight"), rs.getFloat("speed"), rs.getString("madein"), rs.getInt("warranty")));
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Order> getOrder(int limit) {
        try {
            ResultSet rs = st.ListOrder(limit);
            List<Order> list = new ArrayList<>();
            while (rs.next()) {
               list.add(new Order(rs.getInt("id"), rs.getInt("clientid"), rs.getLong("orderdate"), rs.getInt("employeeid"), rs.getFloat("price"), rs.getInt("status")));
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Elevator> getAllElevator(int pageNumber, int rowsPerPage, String s, String i, List<Elevator> listNotIn) {
        int from, to;
        from = ((pageNumber - 1) * rowsPerPage) + 1;
        to = (pageNumber * rowsPerPage);
        try {
            String var = "";
            String notin = "";
            switch (i) {
                case "ALL":
                    var = " WHERE name LIKE '%"+s+"%' OR type LIKE '%"+s+"%' OR warranty LIKE '%"+s+"%' OR type LIKE '%"+s+"%' OR price LIKE '%"+s+"%' OR speed LIKE '%"+s+"%' OR weight LIKE '%"+s+"%' OR madein LIKE '%"+s+"%'";
                    break;
                case "":
                    var = "";
                    break;
                default:
                    var = " WHERE " + i + " LIKE '%"+s+"%'";
                    break;
            }
            if(listNotIn != null){
                for (Elevator elevator : listNotIn) {
                    notin += "," + elevator.getId();
                }
                notin = notin.substring(1);
                if(i.equals("")){
                    var += " WHERE id NOT IN ("+notin+")";
                }
                else{
                    var += " AND id NOT IN ("+notin+")";
                }
            }
            ResultSet rs = st.selectSQL("WITH Elevator AS (SELECT *, ROW_NUMBER() OVER (ORDER BY id DESC) AS 'RowNumber' FROM dbo.Elevator" + var + ") SELECT * FROM Elevator WHERE RowNumber BETWEEN " + from + " AND " + to);
            List<Elevator> newList = new LinkedList<Elevator>();
            while (rs.next()) {
                newList.add(new Elevator(rs.getInt("id"), rs.getString("name"), rs.getString("image"), rs.getString("type"), rs.getFloat("price"), rs.getFloat("weight"), rs.getFloat("speed"), rs.getString("madein"), rs.getInt("warranty")));
            }
            return newList;
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getTotalElevator(String s, String i, List<Elevator> listNotIn) {
        try {
            String var = "";
            String notin = "";
            switch (i) {
                case "ALL":
                    var = " WHERE name LIKE '%"+s+"%' OR type LIKE '%"+s+"%' OR warranty LIKE '%"+s+"%' OR type LIKE '%"+s+"%' OR price LIKE '%"+s+"%' OR speed LIKE '%"+s+"%' OR weight LIKE '%"+s+"%' OR madein LIKE '%"+s+"%'";
                    break;
                case "":
                    var = "";
                    break;
                default:
                    var = " WHERE " + i + " LIKE '%"+s+"%'";
                    break;
            }
            if(listNotIn != null){
                for (Elevator elevator : listNotIn) {
                    notin += "," + elevator.getId();
                }
                notin = notin.substring(1);
                if(i.equals("")){
                    var += " WHERE id NOT IN ("+notin+")";
                }
                else{
                    var += " AND id NOT IN ("+notin+")";
                }
            }
            ResultSet rs = st.resultSQL("SELECT COUNT(*) FROM Elevator" + var);
            return rs.getInt(1);
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    //phuongLH
    public List<Complaint> getAllComplaint(int pageNumber, int rowsPerPage, String s, String i, String o) {
        int from, to;
        from = ((pageNumber - 1) * rowsPerPage) + 1;
        to = (pageNumber * rowsPerPage);
        try {
            String var = "";
            switch (i) {
                case "ALL":
                    var = " WHERE complaint LIKE '%"+s+"%' OR resolve LIKE '%"+s+"%'";
                    break;
                case "":
                    var = "";
                    break;
                default:
                    var = " WHERE " + i + " LIKE '%"+s+"%'";
                    break;
            }
            ResultSet rs = st.selectSQL("WITH Complaints AS (SELECT *, ROW_NUMBER() OVER (ORDER BY id) AS 'RowNumber' FROM dbo.Complaints" + var + ") SELECT * FROM Complaints WHERE RowNumber BETWEEN " + from + " AND " + to + "ORDER BY status "+o);
            List<Complaint> newList = new LinkedList<Complaint>();
            while (rs.next()) {
                Client c = getClientById(rs.getInt("clientid"));
                newList.add(new Complaint(rs.getInt("id"), rs.getInt("clientid"), c.getName(), rs.getInt("employeeid"), rs.getString("complaint"), rs.getString("resolve"), rs.getInt("status")));
            }
            return newList;
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getTotalComplaint(String s, String i) {
        try {
            String var = "";
            switch (i) {
                case "ALL":
                    var = " WHERE complaint LIKE '%"+s+"%' OR resolve LIKE '%"+s+"%'";
                    break;
                case "":
                    var = "";
                    break;
                default:
                    var = " WHERE " + i + " LIKE '%"+s+"%'";
                    break;
            }
            ResultSet rs = st.resultSQL("SELECT COUNT(*) FROM Complaints" + var);
            return rs.getInt(1);
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    //phuongLH
    public List<Order> getAllOrder(int pageNumber, int rowsPerPage, String s, String i) {
        int from, to;
        from = ((pageNumber - 1) * rowsPerPage) + 1;
        to = (pageNumber * rowsPerPage);
        try {
            String var = "";
            switch (i) {
                case "ALL":
                    var = "(os.name LIKE '%"+s+"%' OR convert(INT,os.price) LIKE '%"+s+"%') AND ";
                    break;
                case "":
                    var = " ";
                    break;
                case "name":
                    var = "(os.name LIKE '%"+s+"%') AND ";
                    break;
                case "price":
                    var = "convert(INT,os.price) LIKE '%"+s+"%') AND ";
                    break;
            }
            ResultSet rs = st.selectSQL("WITH Orders (row, id, clientid, clientname, orderdate, employeeid, price, status) AS (SELECT ROW_NUMBER() OVER (ORDER BY od.id DESC), id, od.clientid, (SELECT cl.name FROM dbo.Clients AS cl WHERE cl.id = od.clientid), od.orderdate, od.employeeid, od.price, od.status FROM dbo.[Order] AS od) SELECT * FROM Orders as os WHERE "+var+"(os.row BETWEEN "+from+" AND "+to+")");
            List<Order> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Order(rs.getInt("id"), rs.getInt("clientid"), rs.getString("clientname"), rs.getLong("orderdate"), rs.getInt("employeeid"), rs.getFloat("price"), rs.getInt("status")));
            }
            return list;
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Maintenance> getAllMaintenance(int pageNumber, int rowsPerPage, String s, String i) {
        int from, to;
        from = ((pageNumber - 1) * rowsPerPage) + 1;
        to = (pageNumber * rowsPerPage);
        try {
            String var = "";
            ResultSet rs = st.selectSQL("with Maintenances (row, orderid, elevatorid, id, billid, orderdate, deliverydate, firstyear, secondyear, thirdyear) as (select ROW_NUMBER() over (Order by m.id asc), (select orderid from dbo.Bill where id = m.billid), (select elevatorid from dbo.Bill where id = m.billid), * from dbo.Maintenance as m) select * from Maintenances where "+var+"(row between "+from+" and "+to+")");
            List<Maintenance> list = new ArrayList<>();
            while (rs.next()) {
                ResultSet c = st.resultSQL("SELECT clientid FROM dbo.[Order] WHERE id = "+rs.getInt("orderid"));
                ResultSet e = st.resultSQL("SELECT elevatorid FROM Bill WHERE id = "+rs.getInt("billid"));
                if(c != null && e != null){
                    list.add(new Maintenance(rs.getInt("billid"), c.getInt(1), rs.getInt("orderid"), e.getInt(1), rs.getString("orderdate"), rs.getString("deliverydate"), rs.getString("firstyear"), rs.getString("secondyear"), rs.getString("thirdyear")));
                }
            }
            return list;
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getTotalMaintenance(String s, String i) {
        try {
            String var = "";
            ResultSet rs = st.resultSQL("SELECT COUNT(*) FROM Maintenance"+var);
            return rs.getInt(1);
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int getTotalOrder(String s, String i) {
        try {
            String var = "";
            switch (i) {
                case "ALL":
                    var = " WHERE (os.name LIKE '%"+s+"%' OR convert(INT,os.price) LIKE '%"+s+"%')";
                    break;
                case "":
                    var = "";
                    break;
                case "name":
                    var = " WHERE (os.name LIKE '%"+s+"%')";
                    break;
                case "price":
                    var = " WHERE convert(INT,os.price) LIKE '%"+s+"%')";
                    break;
            }
            ResultSet rs = st.resultSQL("SELECT COUNT(*) FROM dbo.[Order]"+var);
            return rs.getInt(1);
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /* ToanLM */
    public int getTotalProducts() {
        try {
            ResultSet rs = st.resultSQL("SELECT COUNT(*) FROM Elevator");
            return rs.getInt(1);
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public int getTotalClient() {
        try {
            ResultSet rs = st.resultSQL("SELECT COUNT(*) FROM Clients");
            return rs.getInt(1);
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public int getSoldProducts() {
        try {
            ResultSet rs = st.resultSQL("SELECT SUM(quantity) FROM Bill");
            return rs.getInt(1);
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public List<Client> getAllClient(int limit, String search) {
        try {
            ResultSet rs = st.ListClient();
            List<Client> list = new ArrayList<>();
            int i = 0;
            while (rs.next()) {
                i++;
                if(!search.equals("")){
                    if(rs.getString("name").contains(search) || rs.getString("company").contains(search) || rs.getString("address").contains(search) || rs.getString("email").contains(search) || rs.getString("mobile").contains(search)){
                        if (i <= limit) {
                            list.add(new Client(rs.getInt("id"), rs.getString("name"), rs.getString("company"), rs.getString("address"), rs.getString("email"), rs.getString("mobile")));
                        } else {
                            break;
                        }
                    }
                }
                else{
                    if (limit == 0) {
                        list.add(new Client(rs.getInt("id"), rs.getString("name"), rs.getString("company"), rs.getString("address"), rs.getString("email"), rs.getString("mobile")));
                    } else {
                        if (i <= limit) {
                            list.add(new Client(rs.getInt("id"), rs.getString("name"), rs.getString("company"), rs.getString("address"), rs.getString("email"), rs.getString("mobile")));
                        } else {
                            break;
                        }
                    }

                }
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Client getLastClient() {
        try {
            ResultSet rs = st.ListLastClient();
            Client c = null;
            if (rs.next()) {
                c = new Client(rs.getInt("id"), rs.getString("name"), rs.getString("company"), rs.getString("address"), rs.getString("email"), rs.getString("mobile"));
            }
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Order getLastOrder() {
        try {
            ResultSet rs = st.LastOrder();
            Order o = null;
            if (rs.next()) {
                o = new Order(rs.getInt("id"), rs.getInt("clientid"), rs.getLong("orderdate"), rs.getInt("employeeid"), rs.getFloat("price"), rs.getInt("status"));
            }
            return o;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Employee getLastEmployee() {
        try {
            ResultSet rs = st.LastUser();
            Employee e = null;
            if (rs.next()) {
                e = new Employee(rs.getInt("id"));
            }
            return e;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Client getClientById(int id) {
        try {
            ResultSet rs = st.ClientbyID(id);
            Client c = null;
            if (rs.next()) {
                c = new Client(rs.getInt("id"), rs.getString("name"), rs.getString("company"), rs.getString("address"), rs.getString("email"), rs.getString("mobile"));
            }
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Elevator> getOrderDetail(int id){
        try {
            ResultSet rs = st.selectSQL("SELECT * FROM Bill WHERE orderid = " + id);
            List<Elevator> list = new ArrayList<Elevator>();
            while (rs.next()) {
                ResultSet rs1 = st.selectSQL("SELECT * FROM Elevator WHERE id = " + rs.getInt("elevatorid"));
                if(rs1.next()){
                    list.add(new Elevator(rs1.getInt("id"), rs1.getString("name"), rs1.getFloat("price"), rs1.getInt("warranty"), rs.getInt("quantity")));
                }
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Bill> getBillByOrderId(int orderid){
        try {
            List<Bill> list = new ArrayList<>();
            ResultSet rs = st.selectSQL("SELECT * FROM Bill WHERE orderid = "+orderid);
            while (rs.next()) {
                list.add(new Bill(rs.getInt("id"), rs.getInt("orderid"), rs.getInt("elevatorid"), rs.getInt("quantity")));
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Print> getListPrintbyOrderId(int orderid){
        try {
            ResultSet rs = st.selectSQL("SELECT * FROM Bill WHERE orderid = " + orderid);
            List<Print> list = new ArrayList<Print>();
            while (rs.next()) {
                ResultSet rs1 = st.selectSQL("SELECT * FROM Elevator WHERE id = " + rs.getInt("elevatorid"));
                if(rs1.next()){
                    float amount = rs.getInt("quantity") * rs1.getFloat("price");
                    list.add(new Print(rs1.getString("name"), "PCS", rs.getInt("quantity"), rs1.getFloat("price"), amount));
                }
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Employee getEmployee(int id){
        try {
            ResultSet rs = st.selectSQL("WITH Employee (userid, username, fullname, gender, email, contact) AS (SELECT userid, (SELECT username FROM [Users] WHERE id = ep.userid), fullname, gender, email, contact FROM dbo.Employee AS ep WHERE id = " + id + ") SELECT * FROM Employee");
            Employee ep = null;
            if (rs.next()) {
                ep = new Employee(id, rs.getInt("userid"), rs.getString("username"), rs.getString("fullname"), rs.getInt("gender"), rs.getString("email"), rs.getString("contact"));
            }
            return ep;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
        
    public Elevator getElevator(int id){
        try {
            ResultSet rs = st.selectSQL("SELECT * FROM Elevator WHERE id = "+id);
            Elevator el = null;
            if (rs.next()) {
                el = new Elevator(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getInt("warranty"));
            }
            return el;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Employee> getAllEmployee(int pageNumber, int rowsPerPage, String s, String i) {
        int from, to;
        from = ((pageNumber - 1) * rowsPerPage) + 1;
        to = (pageNumber * rowsPerPage);
        try {
            String var = "";
            switch (i) {
                case "ALL":
                    var = " es.username+es.fullname+es.email+es.contact LIKE '%"+s+"%' AND ";
                    break;
                case "":
                    var = "";
                    break;
                default:
                    var = " es."+i+" LIKE '%"+s+"%' AND ";
                    break;
            }
            ResultSet rs = st.selectSQL("WITH Employees (row, username, password, id, userid, fullname, gender, email, contact, active) AS (SELECT row_number() over (order by em.id), (SELECT username FROM dbo.Users WHERE id = em.userid), (SELECT password FROM dbo.Users WHERE id = em.userid), * FROM Employee as em) select * from Employees as es where"+var+"(es.row between "+from+" and "+to+")");
            List<Employee> list = new ArrayList<>();
            while (rs.next()) {
                    list.add(new Employee(rs.getInt("userid"), rs.getString("username"), rs.getString("password"), rs.getString("fullname"), rs.getInt("gender"), rs.getString("email"), rs.getString("contact")));
            }
            return list;
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getTotalEmployee(String s, String i) {
        try {
            String var = "";
            switch (i) {
                case "ALL":
                    var = " es.username+es.fullname+es.email+es.contact LIKE '%"+s+"%' AND ";
                    break;
                case "":
                    var = "";
                    break;
                default:
                    var = " es."+i+" LIKE '%"+s+"%' AND ";
                    break;
            }
            ResultSet rs = st.resultSQL("SELECT COUNT(*) FROM Employee as es"+var);
            return rs.getInt(1);
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public int getTotalClient(String s, String i) {
        try {
            String var = "";
            switch (i) {
                case "ALL":
                    var = " cl.name+cl.company+cl.address+cl.email+cl.mobile LIKE '%"+s+"%' AND ";
                    break;
                case "":
                    var = "";
                    break;
                default:
                    var = " cl."+i+" LIKE '%"+s+"%' AND ";
                    break;
            }
            ResultSet rs = st.resultSQL("SELECT COUNT(*) FROM Clients as cl"+var);
            return rs.getInt(1);
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
   
    public List<Client> getAllClient(int pageNumber, int rowsPerPage, String s, String i) {
        int from, to;
        from = ((pageNumber - 1) * rowsPerPage) + 1;
        to = (pageNumber * rowsPerPage);
        try {
            String var = "";
            switch (i) {
                case "ALL":
                    var = " cl.name+cl.company+cl.address+cl.email+cl.mobile LIKE '%"+s+"%' AND ";
                    break;
                case "":
                    var = " ";
                    break;
                default:
                    var = " cl."+i+" LIKE '%"+s+"%' AND ";
                    break;
            }
            ResultSet rs = st.selectSQL("with nClients (id, name, company, address, email, mobile) as (select ROW_NUMBER() over (Order by id), name, company, address, email, mobile from Clients) select * from nClients where"+var+"(id between "+from+" and "+to+")");
            List<Client> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Client(rs.getString("name"), rs.getString("company"), rs.getString("address"), rs.getString("email"), rs.getString("mobile")));
            }
            return list;
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


}
