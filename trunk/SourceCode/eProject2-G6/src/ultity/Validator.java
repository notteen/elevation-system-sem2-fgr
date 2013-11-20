/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ultity;

/**
 *
 * @author ToanLM
 */
public class Validator {
    public String ValidateString(String s){
        while (s.indexOf("  ") != -1) {
            s = s.replaceAll("  ", " ");
        }
        return s.trim();
    }
    
    public Float ValidateFloat(String s){
        while (s.indexOf(" ") != -1) {
            s = s.replaceAll(" ", "");
        }
        return Float.parseFloat(s);
    }
    
    
    public int ValidateInt(String i){
        while (i.indexOf(" ") != -1) {
            i = i.replaceAll(" ", "");
        }
        return Integer.parseInt(i);
    }
}
