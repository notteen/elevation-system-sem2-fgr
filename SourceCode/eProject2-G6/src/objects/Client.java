/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author ToanLM
 */
public class Client {
    int id;
    String name;
    String company;
    String address;
    String email;
    String mobile;

    public Client() {
    }

    public Client(int id, String name, String company, String address, String email, String mobile) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
    }

    public Client(String name, String company, String address, String email, String mobile) {
        this.name = name;
        this.company = company;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }
    
}
