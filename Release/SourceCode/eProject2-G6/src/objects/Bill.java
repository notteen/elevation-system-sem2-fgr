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
public class Bill implements Serializable{
    int id;
    int orderid;
    int elevatorid;
    int quantity;

    public Bill() {
    }

    public Bill(int orderid, int elevatorid, int quantity) {
        this.orderid = orderid;
        this.elevatorid = elevatorid;
        this.quantity = quantity;
    }

    public Bill(int id, int orderid, int elevatorid, int quantity) {
        this.id = id;
        this.orderid = orderid;
        this.elevatorid = elevatorid;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getOrderid() {
        return orderid;
    }

    public int getElevatorid() {
        return elevatorid;
    }

    public int getQuantity() {
        return quantity;
    }
    
    @Override
    public String toString(){
       return "Id: " + id + ", OrderId: " + orderid + ", ElevatorId: " + elevatorid + ", Quantity: " + quantity;
    }
}
