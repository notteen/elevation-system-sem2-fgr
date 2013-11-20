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
import objects.Client;
import objects.Elevator;
import objects.Order;
import ultity.SQLTools;

/**
 *
 * @author ToanLM
 */
public class ObjectsData {

    public List<Elevator> getAllElevator(int limit) {
        try {
            SQLTools t = new SQLTools();
            ResultSet rs = t.ListElevator();
            List<Elevator> list = new ArrayList<>();
            int i = 0;
            while (rs.next()) {
                i++;
                // If limit = 0 then view all Elevator. If limit is value > 0 then view limit new Elevator
                if (limit == 0) {
                    list.add(new Elevator(rs.getInt("id"), rs.getString("name"), rs.getString("image"), rs.getString("type"), rs.getFloat("price"), rs.getFloat("weight"), rs.getFloat("speed"), rs.getString("madein"), rs.getInt("warranty")));
                } else {
                    if (i <= limit) {
                        list.add(new Elevator(rs.getInt("id"), rs.getString("name"), rs.getString("image"), rs.getString("type"), rs.getFloat("price"), rs.getFloat("weight"), rs.getFloat("speed"), rs.getString("madein"), rs.getInt("warranty")));
                    } else {
                        break;
                    }
                }
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param pageNumber
     * @param rowsPerPage
     * @param s
     * @param i
     * @return List of Elevator contain 's'
     */
    //phuongLH
    public List<Elevator> getAllElevator(int pageNumber, int rowsPerPage, String s, String i) {
        int from, to;
        from = ((pageNumber - 1) * rowsPerPage) + 1;
        to = (pageNumber * rowsPerPage);
        try {
            SQLTools st = new SQLTools();
            String var = "";
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
            ResultSet rs = st.selectSQL("WITH Elevator AS (SELECT *, ROW_NUMBER() OVER (ORDER BY id) AS 'RowNumber' FROM dbo.Elevator" + var + ") SELECT * FROM Elevator WHERE RowNumber BETWEEN " + from + " AND " + to);
            List<Elevator> list = new LinkedList<Elevator>();
            while (rs.next()) {
                list.add(new Elevator(rs.getInt("id"), rs.getString("name"), rs.getString("image"), rs.getString("type"), rs.getFloat("price"), rs.getFloat("weight"), rs.getFloat("speed"), rs.getString("madein"), rs.getInt("warranty")));
            }
            return list;
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getTotalElevator(String s, String i) {
        try {
            SQLTools st = new SQLTools();
            String var = "";
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
            ResultSet rs = st.resultSQL("SELECT COUNT(*) FROM Elevator" + var);
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
            SQLTools st = new SQLTools();
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
            ResultSet rs = st.selectSQL("WITH Orders (row, id, name, orderdate, employeeid, price, status) AS (SELECT ROW_NUMBER() OVER (ORDER BY od.id DESC), id, (SELECT cl.name FROM dbo.Clients AS cl WHERE cl.id = od.clientid), od.orderdate, od.employeeid, od.price, od.status FROM dbo.[Order] AS od) SELECT * FROM Orders as os WHERE "+var+"(os.row BETWEEN "+from+" AND "+to+")");
            List<Order> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Order(rs.getInt("id"), rs.getString("name"), rs.getLong("orderdate"), rs.getInt("employeeid"), rs.getFloat("price"), rs.getInt("status")));
            }
            return list;
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getTotalOrder(String s, String i) {
        try {
            SQLTools st = new SQLTools();
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
            ResultSet rs = st.resultSQL("WITH Orders (name, price) AS (SELECT (SELECT cl.name FROM dbo.Clients AS cl WHERE cl.id = od.clientid), od.price FROM dbo.[Order] AS od) SELECT COUNT(*) FROM Orders as os"+var);
            return rs.getInt(1);
        } catch (Exception ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /* ToanLM */
    public List<Client> getAllClient(int limit) {
        try {
            SQLTools t = new SQLTools();
            ResultSet rs = t.ListClient();
            List<Client> list = new ArrayList<>();
            int i = 0;
            while (rs.next()) {
                i++;
                // If limit = 0 then view all Client. If limit is value > 0 then view limit new Client
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
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Client getLastClient() {
        try {
            SQLTools t = new SQLTools();
            ResultSet rs = t.ListLastClient();
            List<Elevator> list = new ArrayList<>();
            Client c = null;
            while (rs.next()) {
                c = new Client(rs.getInt("id"), rs.getString("name"), rs.getString("company"), rs.getString("address"), rs.getString("email"), rs.getString("mobile"));
                break;
            }
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Order getLastOrder() {
        try {
            SQLTools t = new SQLTools();
            ResultSet rs = t.LastOrder();
            List<Order> list = new ArrayList<>();
            Order o = null;
            while (rs.next()) {
                o = new Order(rs.getInt("id"), rs.getInt("clientid"), rs.getLong("orderdate"), rs.getInt("employeeid"), rs.getFloat("price"), rs.getInt("status"));
                break;
            }
            return o;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Client getClientById(int id) {
        try {
            SQLTools t = new SQLTools();
            ResultSet rs = t.ClientbyID(id);
            Client c = null;
            while (rs.next()) {
                c = new Client(rs.getInt("id"), rs.getString("name"), rs.getString("company"), rs.getString("address"), rs.getString("email"), rs.getString("mobile"));
                break;
            }
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(ObjectsData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Elevator> getOrderDetail(int id){
        try {
            SQLTools st = new SQLTools();
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
}
