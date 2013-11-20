/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author ToanLM
 */
public class Elevator {
    int Id;
    String Name;
    String Image;
    String Type;
    float Price;
    float Weight;
    float Speed;
    String Madein;
    int Warranty;
    int quantity = 1;
    
    public Elevator() {
    }

    public Elevator(int Id, String Name, String Image, String Type, float Price, float Weight, float Speed, String Madein, int Warranty) {
        this.Id = Id;
        this.Name = Name;
        this.Image = Image;
        this.Type = Type;
        this.Price = Price;
        this.Weight = Weight;
        this.Speed = Speed;
        this.Madein = Madein;
        this.Warranty = Warranty;
    }

    public Elevator(String Name, String Image, String Type, float Price, float Weight, float Speed, String Madein, int Warranty) {
        this.Name = Name;
        this.Image = Image;
        this.Type = Type;
        this.Price = Price;
        this.Weight = Weight;
        this.Speed = Speed;
        this.Madein = Madein;
        this.Warranty = Warranty;
    }

    public Elevator(int Id, String Name, String Type, int Warranty) {
        this.Id = Id;
        this.Name = Name;
        this.Type = Type;
        this.Warranty = Warranty;
    }

    public Elevator(int Id, String Name, float Price) {
        this.Id = Id;
        this.Name = Name;
        this.Price = Price;
    }

    public Elevator(int Id, String Name, float Price, int Warranty, int quantity) {
        this.Id = Id;
        this.Name = Name;
        this.Price = Price;
        this.Warranty = Warranty;
        this.quantity = quantity;
    }

    public Elevator(int Id, String Name, float Price, int quantity) {
        this.Id = Id;
        this.Name = Name;
        this.Price = Price;
        this.quantity = quantity;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public String getType() {
        return Type;
    }

    public float getPrice() {
        return Price;
    }

    public float getWeight() {
        return Weight;
    }

    public float getSpeed() {
        return Speed;
    }

    public String getMadein() {
        return Madein;
    }

    public int getWarranty() {
        return Warranty;
    }
    
    @Override
    public String toString(){
        return Id +", "+Name +", "+ Price +", "+ quantity+"\n";
    }
}
