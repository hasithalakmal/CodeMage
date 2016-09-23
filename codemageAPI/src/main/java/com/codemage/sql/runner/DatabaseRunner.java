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
public interface DatabaseRunner {

    String createDatabase(String dbName);

    String dropDatabase(String dbName);

    String useDatabase(String dbName);
    
    ArrayList getTables(String dbName);
    
    void exportSQL(String dbName);
    
    void createTable(String query,String dbName);
    
    void dropTable(String tableName,String dbName);
    
    void addFK(String query,String dbName);
          
}
