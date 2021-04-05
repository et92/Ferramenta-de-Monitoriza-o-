/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package writeFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author et007
 */
public class ReadFromFile2 {

    public BufferedReader arquivoLido(){
        BufferedReader bb = null;
        try {
            
            FileReader arquivoDiretorio = new FileReader("/home/et007/NetBeansProjects/Snmp_tp/log.txt"); 
	BufferedReader arquivoLido = new BufferedReader(arquivoDiretorio);
        if(arquivoLido != null){
                bb = arquivoLido;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bb;
    }
}
    

