/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;

/**
 *
 * @author ToanLM
 */
public class Order implements Serializable{
    int id;
    int clientid;
    long orderdate;
    String deliverydate;
    int employeeid;
    float price;
    int status;
    //phuonglh
    String clientname; 
    int quantity;
    //end
    
    public Order() {
    }

    public Order(int clientid, long orderdate, String deliverydate, int employeeid, float price, int status) {
        this.clientid = clientid;
        this.orderdate = orderdate;
        this.deliverydate = deliverydate;
        this.employeeid = employeeid;
        this.price = price;
        this.status = status;
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
    public Order(int id, int clientid, String clientname, long orderdate, int employeeid, float price, int status) {
        this.id = id;
        this.clientid = clientid;
        this.clientname = clientname;
        this.orderdate = orderdate;
        this.employeeid = employeeid;
        this.price = price;
        this.status = status;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public String getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public void setOrderdate(long orderdate) {
        this.orderdate = orderdate;
    }

    public void setStatus(int status) {
        this.status = status;
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
    
    @Override
    public String toString(){
        return id+","+clientid+","+clientname+","+orderdate+","+employeeid+","+price+","+status;
    }
}
