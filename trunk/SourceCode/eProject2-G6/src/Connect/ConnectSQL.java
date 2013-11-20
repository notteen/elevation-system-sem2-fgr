/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ToanLM
 */
public class ConnectSQL {
    
    //Toan
    public static Connection getConnection(){
        File file = new File("lib/config.txt");
        String[] strArr = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            while ((text = reader.readLine()) != null)
            {
               strArr=text.split(";");
            }
            String url = "jdbc:sqlserver://"+strArr[0]+":"+strArr[1]+";databaseName="+strArr[2];
            String user = strArr[3];
            String password = strArr[4];
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (Exception ex) {
            //Logger.getLogger(ConnectSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    //Phuong
    public boolean checkConnection(){
        try {
            File file = new File("lib/config.txt");
            boolean check = true;
            if(file.length() <= 0){
                check = false;
            }
            try {
                getConnection();
            } catch (Exception ex) {
            }
           if(getConnection() != null){
               check = true;
           }else{
             check = false;
           }
           return check;
        }catch(Exception e){
            return false;
        }
    }
}
