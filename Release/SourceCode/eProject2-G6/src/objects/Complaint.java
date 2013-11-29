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
public class Complaint implements Serializable{
    private int id;
    private int clientid;
    private String clientname;
    private int employeeid;
    private String complaint;
    private String resolve;
    private String cdate;
    private String rdate;
    private int status;

    public Complaint(int id, int clientid, String clientname, int employeeid, String complaint, String resolve, int status) {
        this.id = id;
        this.clientid = clientid;
        this.clientname = clientname;
        this.employeeid = employeeid;
        this.complaint = complaint;
        this.resolve = resolve;
        this.status = status;
    }

    public Complaint(int clientid, String clientname, int employeeid, String complaint, String resolve, String cdate, String rdate, int status) {
        this.clientid = clientid;
        this.clientname = clientname;
        this.employeeid = employeeid;
        this.complaint = complaint;
        this.resolve = resolve;
        this.status = status;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
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

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getResolve() {
        return resolve;
    }

    public void setResolve(String resolve) {
        this.resolve = resolve;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
