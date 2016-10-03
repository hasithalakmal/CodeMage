/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.runner;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Hasitha Lakmal
 */
@Service("DMLQueryRunner")
public class DMLQueryRunnerImpl implements DMLQueryRunner {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    @Override
    public String insertData(String dbName, String query) {
        String msg = "";
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = (Connection) DriverManager.getConnection(DB_URL + dbName, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");
            stmt = (Statement) conn.createStatement();

            String sql = query;
            int x = stmt.executeUpdate(sql);

            if (x != 1) {
                msg = stmt.getExceptionInterceptor().toString();
                System.err.println("Error !!!!!!!!!!!!!!!!!!!" + msg);
            } else {
                msg = "ok";
            }
            System.out.println("Inserted records into the table...");
        } catch (SQLException | ClassNotFoundException se) {
            msg = se.toString();
            System.err.println("Error !!!!!!!!!!!!!!!!!!!" + se);
        } finally {
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }
        }
        return msg;
    }

    @Override
    public void updateData(String dbName, String query) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = (Connection) DriverManager.getConnection(DB_URL + dbName, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = (Statement) conn.createStatement();
            String sql = query;
            stmt.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    @Override
    public void deleteData(String dbName, String query) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = (Connection) DriverManager.getConnection(DB_URL + dbName, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = (Statement) conn.createStatement();
            String sql = query;
            stmt.executeUpdate(sql);

        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    @Override
    public String getDataFromTable(String dbName, String tableName) {
        String result = "";
        ArrayList bigobj = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = (Connection) DriverManager.getConnection(DB_URL + dbName, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");
            stmt = (Statement) conn.createStatement();

            String sql = "SELECT * FROM " + tableName + ";";
            ResultSet rs = stmt.executeQuery(sql);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            // Iterate through the data in the result set and display it. 
            int itt = 1;
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", itt);
                //Print one row          
                for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rs.getString(i) + " "); //Print one element of a row
                    String key = "feild" + (i - 1);
                    obj.put(key, rs.getString(i));
                }
                System.out.println();//Move to the next line to print the next row.  
                bigobj.add(obj);
                itt = itt + 1;
            }

            System.out.println("Inserted records into the table...");

        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        result = bigobj.toString();
        System.out.println(result);
        return result;
    }

}
