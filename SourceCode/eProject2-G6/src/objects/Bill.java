/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author ToanLM
 */
public class Bill {
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
    
}
