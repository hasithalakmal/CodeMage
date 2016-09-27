/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.javacode;

/**
 *
 * @author Hasitha Lakmal
 */
public interface DMLJava {

    String SelectData(String Query, String dbName);
    
    String InsertData(String Query, String dbName);

    String UpdateData(String Query, String dbName);

    String DeleteData(String Query, String dbName);
    
}
