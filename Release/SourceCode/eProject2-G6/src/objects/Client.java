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
public class Client implements Serializable{
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
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
    
    @Override
    public String toString(){
        return "Name: " + name;
    }
}
