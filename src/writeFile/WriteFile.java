/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package writeFile;

import java.io.FileWriter;

/**
 *
 * @author et007
 */
public class WriteFile {
    public void writing(String stingBuffer){
   
        try {           
             FileWriter file = new FileWriter("/home/et007/NetBeansProjects/Snmp_tp/log.txt");
             file.write(stingBuffer);
             file.close();
        } catch (Exception e) {
        }
    }

    public void escrever(String toString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
