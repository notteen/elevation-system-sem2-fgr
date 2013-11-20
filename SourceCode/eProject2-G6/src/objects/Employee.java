/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author ToanLM
 */
public class Employee {
    int Id;
    String UserId;
    String Fullname;
    int Gender;
    String Email;
    String Contact;
    String Department;
    int Active;

    public Employee(int Id, String UserId, String Fullname, int Gender, String Email, String Contact, String Department, int Active) {
        this.Id = Id;
        this.UserId = UserId;
        this.Fullname = Fullname;
        this.Gender = Gender;
        this.Email = Email;
        this.Contact = Contact;
        this.Department = Department;
        this.Active = Active;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
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

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int Active) {
        this.Active = Active;
    }
}
