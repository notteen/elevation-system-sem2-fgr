/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ultity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Maintenance;

/**
 *
 * @author ToanLM
 */
public class Tools {
    public String CoppyFile(String fpath) {
        File file = new File("Images");
        if(!file.exists()){
            file.mkdir();
        }
        Long time = System.currentTimeMillis();
        //String path = urlFile(getClass().getClassLoader().getResource(".").getPath());
        File fdir = new File(System.getProperty("java.class.path"));
        File dir = fdir.getAbsoluteFile().getParentFile();
        String path = dir.toString();
        File f = new File(fpath);
        String extension = "";
        int i = f.getName().lastIndexOf('.');
        if (i >= 0) {
            extension = f.getName().substring(i + 1);
        }
        File fe = new File(path + "/Images/" + time + "." + extension);
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(f);
            output = new FileOutputStream(fe);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
            input.close();
            output.close();
        } catch (Exception ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fe.getName();
    }
    
    public String urlFile(String s){
        while (s.indexOf("%20") != -1) {
            s = s.replaceAll("%20", " ");
        }
        return s;
    }
    
    public static String nextYear(String date){//M/d/Y
        String sdate[] = date.split("/");
        int nextY = Integer.parseInt(sdate[2]) + 1;
        return sdate[0]+"/"+sdate[1]+"/"+nextY;
    }
    
    public static String isExpired(int i, Maintenance m){
        String word = "";
        switch(i){
            case 1: word = m.getFirstyear(); break;
            case 2: word = m.getSecondyear(); break;
            case 3: word = m.getThirdyear(); break;
            default: word = ""; break;
        }
        if(!word.equals("")){
            try {
                String a[] = word.split("/");
                String b = a[2]+"-"+a[0]+"-"+a[1]+" 01:00:00";
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date ndate = sdf.parse(b);
                long time = ndate.getTime();
                if(time < System.currentTimeMillis()){
                    switch(i){
                        case 1: {
                            if(!m.getSecondyear().equals("")){
                                word = "Passed";
                            }
                            else{
                                word = "Expired";
                            }
                            break;
                        }
                        case 2: {
                            if(!m.getThirdyear().equals("")){
                                word = "Passed";
                            }
                            else{
                                word = "Expired";
                            }
                            break;
                        }
                        case 3: {
                            word = "Expired";
                            break;
                        }
                        default: word = ""; break;
                    }
                }
            } catch (ParseException ex) {
                Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return word;
    }
    
    
}
