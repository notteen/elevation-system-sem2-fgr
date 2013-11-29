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
public class Print implements Serializable{
    String description;
    String unit;
    int quantity;
    float price;
    float amount;

    public Print(String description, String unit, int quantity, float price, float amount) {
        this.description = description;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
    
}
