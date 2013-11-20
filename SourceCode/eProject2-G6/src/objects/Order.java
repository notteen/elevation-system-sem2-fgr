/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author ToanLM
 */
public class Order {
    int id;
    int clientid;
    long orderdate;
    int employeeid;
    float price;
    int status;
    //phuonglh
    String clientname; 
    int quantity;
    //end
    
    public Order() {
    }

    public Order(int clientid, long orderdate, int employeeid, float price, int status) {
        this.clientid = clientid;
        this.orderdate = orderdate;
        this.employeeid = employeeid;
        this.price = price;
        this.status = status;
    }

    public Order(int id, int clientid, long orderdate, int employeeid, float price, int status) {
        this.id = id;
        this.clientid = clientid;
        this.orderdate = orderdate;
        this.employeeid = employeeid;
        this.price = price;
        this.status = status;
    }

    //phuonglh
    public Order(int id, String clientname, long orderdate, int employeeid, float price, int status) {
        this.id = id;
        this.clientname = clientname;
        this.orderdate = orderdate;
        this.employeeid = employeeid;
        this.price = price;
        this.status = status;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }
    //end phuonglh
    
    public int getId() {
        return id;
    }

    public int getClientid() {
        return clientid;
    }

    public long getOrderdate() {
        return orderdate;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public float getPrice() {
        return price;
    }

    public int getStatus() {
        return status;
    }
    
}
