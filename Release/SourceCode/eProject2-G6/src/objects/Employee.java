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
public class Employee implements Serializable{
    int Id;
    int UserId;
    String Username;
    String Password;
    String Fullname;
    int Gender;
    String Email;
    String Contact;

    public Employee(int Id, int UserId, String Username, String Fullname, int Gender, String Email, String Contact) {
        this.Id = Id;
        this.UserId = UserId;
        this.Username = Username;
        this.Fullname = Fullname;
        this.Gender = Gender;
        this.Email = Email;
        this.Contact = Contact;
    }

    public Employee() {
    }

    public Employee(int UserId, String Username, String Password, String Fullname, int Gender, String Email, String Contact) {
        this.UserId = UserId;
        this.Username = Username;
        this.Password = Password;
        this.Fullname = Fullname;
        this.Gender = Gender;
        this.Email = Email;
        this.Contact = Contact;
    }

    public Employee(String Username, String Password, String Fullname, int Gender, String Email, String Contact) {
        this.Username = Username;
        this.Password = Password;
        this.Fullname = Fullname;
        this.Gender = Gender;
        this.Email = Email;
        this.Contact = Contact;
    }

    public Employee(int UserId) {
        this.UserId = UserId;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
    
    public String getPassword() {
        return Password;
    }
    
    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String Fullname) {
        this.Fullname = Fullname;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int Gender) {
        this.Gender = Gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }
}
