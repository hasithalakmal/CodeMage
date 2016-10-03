/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.runner;

import java.util.ArrayList;

/**
 *
 * @author Hasitha Lakmal
 */
public interface DMLQueryRunner {

    String insertData(String dbName, String query);
    
    void updateData(String dbName, String query);
    
    void deleteData(String dbName, String query);
    
    String getDataFromTable(String dbName, String query);
          
}
