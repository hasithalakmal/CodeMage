/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.javacode;

/*STEP 1. Import required packages*/
import java.sql.*;

public class CodeMageExample1 {

    public static void main(String[] args) {
        String x = "";
        for(int i=1; i<16;i++){
            x = x+"case "+i+":\n";
            for(int j=0; j<i;j++){
                x = x+"\tvaluesofData["+j+"] = $scope.users[0].feild"+j+";\n";
            }
            x=x+"\tbreak;\n";
        }
        
        System.out.println(x);
    }
}
