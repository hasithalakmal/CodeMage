/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.runner;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Hasitha Lakmal
 */
@Service("DQLQueryRunner")
public class DQLQueryRunnerImpl implements DQLQueryRunner {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    @Override
    public String selectData(String dbName, String query) {
        String result = "";

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
            ResultSet rs = stmt.executeQuery(sql);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            // Iterate through the data in the result set and display it. 
            while (rs.next()) {
                result = result + "<tr>";
                //Print one row          
                for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rs.getString(i) + " "); //Print one element of a row
                    result = result + "<td>" + rs.getString(i) + "</td>";
                }
                result = result + "</tr>";
                System.out.println();//Move to the next line to print the next row.  
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
        return result;
    }

    @Override
    public String selectDataForEditableTable(String dbName, String tblName, String query) {
        String result = "<table class=\"table table-bordered table-hover table-condensed\"><tr style=\"font-weight: bold\">";
        ArrayList colomns = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = (Connection) DriverManager.getConnection(DB_URL + dbName, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating database...");
            stmt = (Statement) conn.createStatement();

            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getColumns(null, null, tblName, null);

            String tb = "";
            int i = 0;
            while (rs.next()) {
                String col = rs.getString("COLUMN_NAME");
                result = result + "<td>" + col + "</td>";
                tb = tb + "<td><span editable-text=\"user.feild" + i + "\" e-form=\"rowform\" >{{ user.feild" + i + " || 'empty' }}</span></td>";
                colomns.add(col);
                i = i + 1;
            }

            result = result + "<td>Options</td></tr><tr ng-repeat=\"user in users\">" + tb + "<td style=\"white-space: nowrap\">\n"
                    + "<form editable-form name=\"rowform\" ng-show=\"rowform.$visible\" class=\"form-buttons form-inline\" shown=\"inserted == user\">\n"
                    + "<button type=\"submit\" ng-disabled=\"rowform.$waiting\" class=\"btn btn-primary\" ng-click=\"saveUser($index);\">save</button>\n"
                    + "<button type=\"button\" ng-disabled=\"rowform.$waiting\" ng-click=\"rowform.$cancel()\" class=\"btn btn-default\">cancel</button>\n"
                    + "</form>\n"
                    + "<div class=\"buttons\" ng-show=\"!rowform.$visible\">\n"
                    + "<button class=\"btn btn-primary\" ng-click=\"rowform.$show()\">edit</button>\n"
                    + "<button class=\"btn btn-danger\" ng-click=\"removeUser($index)\">del</button>\n"
                    + "</div>  \n"
                    + "</td></tr></table><button  ng-click=\"insertData()\" class=\"btn btn-default\">Insert Data</button>";

            System.out.println(result);
            System.out.println("Database created successfully...");
        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }//end finally try
        }//end try

        System.out.println(colomns);
        return result;
    }

    @Override
    public String selectDataForEditableTableUpdate(String dbName, String tblName, String query) {
        String result = "<table class=\"table table-bordered table-hover table-condensed\"><tr style=\"font-weight: bold\">";
        ArrayList colomns = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = (Connection) DriverManager.getConnection(DB_URL + dbName, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating database...");
            stmt = (Statement) conn.createStatement();

            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getColumns(null, null, tblName, null);

            String tb = "";
            int i = 0;
            while (rs.next()) {
                String col = rs.getString("COLUMN_NAME");
                result = result + "<td>" + col + "</td>";
                tb = tb + "<td><span editable-text=\"user.feild" + i + "\" e-form=\"rowform\" >{{ user.feild" + i + " || 'empty' }}</span></td>";
                colomns.add(col);
                i = i + 1;
            }

            result = result + "<td>Options</td></tr><tr ng-repeat=\"user in users\">" + tb + "<td style=\"white-space: nowrap\">\n"
                    + "<form editable-form name=\"rowform\" ng-show=\"rowform.$visible\" class=\"form-buttons form-inline\" shown=\"inserted == user\">\n"
                    + "<button type=\"submit\" ng-disabled=\"rowform.$waiting\" class=\"btn btn-primary\" ng-click=\"saveUser($index)\">save</button>\n"
                    + "<button type=\"button\" ng-disabled=\"rowform.$waiting\" ng-click=\"rowform.$cancel()\" class=\"btn btn-default\">cancel</button>\n"
                    + "</form>\n"
                    + "<div class=\"buttons\" ng-show=\"!rowform.$visible\">\n"
                    + "<button class=\"btn btn-primary\" ng-click=\"rowform.$show()\">edit</button>\n"
                    + "</div>  \n"
                    + "</td></tr></table><button  ng-click=\"insertData()\" class=\"btn btn-default\">Update Data</button>";

            System.out.println(result);
            System.out.println("Database created successfully...");
        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }//end finally try
        }//end try

        System.out.println(colomns);
        return result;
    }

    @Override
    public String selectDataForEditableTableDelete(String dbName, String tblName, String query) {
        String result = "<table class=\"table table-bordered table-hover table-condensed\"><tr style=\"font-weight: bold\">";
        ArrayList colomns = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = (Connection) DriverManager.getConnection(DB_URL + dbName, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating database...");
            stmt = (Statement) conn.createStatement();

            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getColumns(null, null, tblName, null);

            String tb = "";
            int i = 0;
            while (rs.next()) {
                String col = rs.getString("COLUMN_NAME");
                result = result + "<td>" + col + "</td>";
                tb = tb + "<td><span editable-text=\"user.feild" + i + "\" e-form=\"rowform\" >{{ user.feild" + i + " || 'empty' }}</span></td>";
                colomns.add(col);
                i = i + 1;
            }

            result = result + "<td>Options</td></tr><tr ng-repeat=\"user in users\">" + tb + "<td style=\"white-space: nowrap\">\n"
                    + "<form editable-form name=\"rowform\" ng-show=\"rowform.$visible\" class=\"form-buttons form-inline\" shown=\"inserted == user\">\n"
                    + "<button type=\"submit\" ng-disabled=\"rowform.$waiting\" class=\"btn btn-primary\" ng-click=\"saveUser($index)\">save</button>\n"
                    + "<button type=\"button\" ng-disabled=\"rowform.$waiting\" ng-click=\"rowform.$cancel()\" class=\"btn btn-default\">cancel</button>\n"
                    + "</form>\n"
                    + "<div class=\"buttons\" ng-show=\"!rowform.$visible\">\n"
                    + "<button class=\"btn btn-danger\" ng-click=\"removeUser($index)\">Select</button>\n"
                    + "</div>  \n"
                    + "</td></tr></table><button  ng-click=\"insertData()\" class=\"btn btn-default\">Delete Data</button>";

            System.out.println(result);
            System.out.println("Database created successfully...");
        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }//end finally try
        }//end try

        System.out.println(colomns);
        return result;
    }

    @Override
    public String selectDataForQueryTest(String dbName,  String query) {
        String msg = "ok";
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = (Connection) DriverManager.getConnection(DB_URL + dbName, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating database...");
            stmt = (Statement) conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Database created successfully...");
        } catch (SQLException se) {

            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

            int count = 1;
            while (se != null) {
                int ecode = se.getErrorCode();
                String sqlState = se.getSQLState();
                String sqlMsg = se.getMessage();
                System.out.println("SQLException " + count);
                System.out.println("Code: " + ecode);
                System.out.println("SqlState: " + sqlState);
                System.out.println("Error Message: " + sqlMsg);
                msg = msg + "SQLException " + count + "\nCode: " + ecode + "\nSqlState: " + sqlState + "\nError Message: " + sqlMsg + "\n\n\n";
                se = se.getNextException();
                count++;
            }
            System.out.println("___________________________________________________");

            //msg = se.getLocalizedMessage();
            //System.err.println("Error #####" + se.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DMLQueryRunnerImpl.class.getName()).log(Level.SEVERE, null, ex);
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

}
