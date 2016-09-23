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
import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

/**
 *
 * @author Hasitha Lakmal
 */
@Service("databaseRunner")
public class DatabaseRunnerImpl implements DatabaseRunner {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    @Override
    public String createDatabase(String dbName) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating database...");
            stmt = (Statement) conn.createStatement();

            String sql = "CREATE DATABASE " + dbName;
            stmt.executeUpdate(sql);
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
        System.out.println("Goodbye!");

        return "success";
    }

    @Override
    public String dropDatabase(String dbName) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating database...");
            stmt = (Statement) conn.createStatement();

            String sql = "DROP DATABASE " + dbName;
            stmt.executeUpdate(sql);
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
        System.out.println("Goodbye!");

        return "success";
    }

    @Override
    public String useDatabase(String dbName) {
        String query = "USE DATABASE " + dbName + " ;";

        return query;
    }

    @Override
    public ArrayList getTables(String dbName) {
        ArrayList tables = new ArrayList();

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
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                tables.add(rs.getString(3));
            }

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
        System.out.println("Goodbye!");

        return tables;
    }

    @Override
    public void exportSQL(String dbName) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>");
        // String dumpCommand = "mysqldump " + dbName + " -h " + ip + " -u " + user + " -p" + pass;
        String dumpCommand = "mysqldump -u root -p MYDB > db_backup.sql";
    }

    @Override
    public void createTable(String query, String dbName) {
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
            System.out.println("Creating table in given database...");
            stmt = (Statement) conn.createStatement();
            System.out.println(query);
            String sql = query;

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");
        } catch (SQLException se) {
        } catch (ClassNotFoundException e) {
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
    public void dropTable(String tableName, String dbName) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = (Connection) DriverManager.getConnection(DB_URL+dbName, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Deleting table in given database...");
            stmt = (Statement) conn.createStatement();

            String sql = "DROP TABLE "+tableName;

            stmt.executeUpdate(sql);
            System.out.println("Table  deleted in given database...");
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
    public void addFK(String query, String dbName) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = (Connection) DriverManager.getConnection(DB_URL+dbName, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Deleting table in given database...");
            stmt = (Statement) conn.createStatement();

            String sql = query;

            stmt.executeUpdate(sql);
            System.out.println("Table  deleted in given database...");
        } catch (SQLException se) {
        } catch (ClassNotFoundException e) {
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

}
