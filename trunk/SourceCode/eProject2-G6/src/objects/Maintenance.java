/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

import java.io.Serializable;

/**
 *
 * @author notte_000
 */
public class Maintenance implements Serializable{
    private int id;
    private int billid;
    private int clientid;
    private int orderid;
    private int employeeid;
    private int elevatorid;
    private String orderdate;
    private String deliverydate;
    private String firstyear;
    private String secondyear;
    private String thirdyear;

    public Maintenance(int billid, int orderid, String orderdate, String deliverydate, String firstyear, String secondyear, String thirdyear) {
        this.billid = billid;
        this.orderid = orderid;
        this.orderdate = orderdate;
        this.deliverydate = deliverydate;
        this.firstyear = firstyear;
        this.secondyear = secondyear;
        this.thirdyear = thirdyear;
    }

    public Maintenance(int billid, int clientid, int orderid, int elevatorid, String orderdate, String deliverydate, String firstyear, String secondyear, String thirdyear) {
        this.billid = billid;
        this.clientid = clientid;
        this.orderid = orderid;
        this.elevatorid = elevatorid;
        this.orderdate = orderdate;
        this.deliverydate = deliverydate;
        this.firstyear = firstyear;
        this.secondyear = secondyear;
        this.thirdyear = thirdyear;
    }

    public Maintenance(int billid, int orderid, int elevatorid, String orderdate, String deliverydate, String firstyear, String secondyear, String thirdyear) {
        this.billid = billid;
        this.orderid = orderid;
        this.elevatorid = elevatorid;
        this.orderdate = orderdate;
        this.deliverydate = deliverydate;
        this.firstyear = firstyear;
        this.secondyear = secondyear;
        this.thirdyear = thirdyear;
    }

    public int getBillid() {
        return billid;
    }

    public void setBillid(int billid) {
        this.billid = billid;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public int getElevatorid() {
        return elevatorid;
    }

    public void setElevatorid(int elevatorid) {
        this.elevatorid = elevatorid;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }

    public String getFirstyear() {
        return firstyear;
    }

    public void setFirstyear(String firstyear) {
        this.firstyear = firstyear;
    }

    public String getSecondyear() {
        return secondyear;
    }

    public void setSecondyear(String secondyear) {
        this.secondyear = secondyear;
    }

    public String getThirdyear() {
        return thirdyear;
    }

    public void setThirdyear(String thirdyear) {
        this.thirdyear = thirdyear;
    }

    @Override
    public String toString() {
        return "Maintenance{" + "id=" + id + ", billid=" + billid + ", clientid=" + clientid + ", orderid=" + orderid + ", employeeid=" + employeeid + ", elevatorid=" + elevatorid + ", orderdate=" + orderdate + ", deliverydate=" + deliverydate + ", firstyear=" + firstyear + ", secondyear=" + secondyear + ", thirdyear=" + thirdyear + '}';
    }
   
}
