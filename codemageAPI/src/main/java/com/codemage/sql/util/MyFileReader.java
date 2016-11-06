/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hasitha Lakmal
 */
public class MyFileReader {

    public String test() throws FileNotFoundException, IOException {
        String code = "";
        FileReader fr = new FileReader("E:\\ANL.java");
        BufferedReader br = new BufferedReader(fr);
        String s;
        while ((s = br.readLine()) != null) {
            code = code+"\n"+s;
        }
        fr.close();
        
        return code;
    }
    
    public static void main(String[] args) {
        try {
            MyFileReader mf = new MyFileReader();
            String x = mf.test();
            System.out.println(x);
        } catch (IOException ex) {
            Logger.getLogger(MyFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
}
