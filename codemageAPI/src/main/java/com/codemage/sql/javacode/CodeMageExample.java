/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.javacode;

/*STEP 1. Import required packages*/
import java.sql.*;

public class CodeMageExample {

    /* JDBC driver name and database URL */
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/STUDENTS";

    /*  Change Your Database credentials */
    static final String USER = "username";
    static final String PASS = "password";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            /*STEP 2: Register JDBC driver */
            Class.forName("com.mysql.jdbc.Driver");

            /* STEP 3: Open a connection */
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            /* STEP 4: Execute a query */
            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();

            String sql = "Query";
            ResultSet rs = stmt.executeQuery(sql);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            /* Iterate through the data in the result set and display it. */
            while (rs.next()) {
                /*Print one row*/          
                for (int i = 1; i <= columnsNumber; i++) {
                    /*Print one element of a row*/
                    System.out.print(rs.getString(i) + " "); 
                }
                /*Move to the next line to print the next row.  */
                System.out.println();
            }

        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            /* finally block used to close resources */
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
        System.out.println("Goodbye!");
    }
}
