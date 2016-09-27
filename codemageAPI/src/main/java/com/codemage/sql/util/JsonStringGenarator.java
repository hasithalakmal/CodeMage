/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.util;

import org.springframework.stereotype.Service;

/**
 *
 * @author Hasitha Lakmal
 */
@Service("jsonStringGenarator")
public class JsonStringGenarator {

    public String chanageToJSON(String x) {
        String y = x.replace("\n", "\\n");
        System.out.println(y);
        return y;
    }
    
    public String javaToJSON(String x) {
      //  String y = x.replace("\n", "\\n");
      String y = x.replace("\n", "\\n");
        String z= y.replace("\"", "\\\"");
        return z;
    }

    
}
