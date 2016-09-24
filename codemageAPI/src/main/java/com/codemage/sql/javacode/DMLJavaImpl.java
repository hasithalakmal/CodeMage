/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.javacode;

import com.codemage.sql.query.*;
import static javax.management.Query.value;
import static javax.management.Query.value;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Hasitha Lakmal
 */
@Service("DMLJava")
public class DMLJavaImpl implements DMLJava {

    @Override
    public String InsertData(String Query, String dbName) {
        String javaCode = "/*STEP 1. Import required packages*/\n"
                + "import java.sql.*;\n"
                + "\n"
                + "public class CodeMageExample {\n"
                + "\n"
                + "    /* JDBC driver name and database URL */\n"
                + "    static final String JDBC_DRIVER = \"com.mysql.jdbc.Driver\";\n"
                + "    static final String DB_URL = \"jdbc:mysql://localhost/" + dbName + "\";\n"
                + "\n"
                + "    /*  Change Your Database credentials */\n"
                + "    static final String USER = \"username\";\n"
                + "    static final String PASS = \"password\";\n"
                + "\n"
                + "    public static void main(String[] args) {\n"
                + "        Connection conn = null;\n"
                + "        Statement stmt = null;\n"
                + "        try {\n"
                + "            /*STEP 2: Register JDBC driver */\n"
                + "            Class.forName(\"com.mysql.jdbc.Driver\");\n"
                + "\n"
                + "            /* STEP 3: Open a connection */\n"
                + "            System.out.println(\"Connecting to a selected database...\");\n"
                + "            conn = DriverManager.getConnection(DB_URL, USER, PASS);\n"
                + "            System.out.println(\"Connected database successfully...\");\n"
                + "\n"
                + "            /* STEP 4: Execute a query */\n"
                + "            System.out.println(\"Inserting records into the table...\");\n"
                + "            stmt = conn.createStatement();\n"
                + "\n"
                + "            String sql = \"" + Query + "\";\n"
                + "            stmt.executeUpdate(sql);\n"
                + "            System.out.println(\"Inserted records into the table...\");\n"
                + "\n"
                + "        } catch (SQLException | ClassNotFoundException se) {\n"
                + "        } finally {\n"
                + "            /* finally block used to close resources */\n"
                + "            try {\n"
                + "                if (stmt != null) {\n"
                + "                    conn.close();\n"
                + "                }\n"
                + "            } catch (SQLException se) {\n"
                + "            }\n"
                + "            try {\n"
                + "                if (conn != null) {\n"
                + "                    conn.close();\n"
                + "                }\n"
                + "            } catch (SQLException se) {\n"
                + "            }\n"
                + "        }\n"
                + "        System.out.println(\"Goodbye!\");\n"
                + "    }\n"
                + "}";

        return javaCode;
    }

    @Override
    public String UpdateData(String Query, String dbName) {
        String javaCode = "/*STEP 1. Import required packages*/\n"
                + "import java.sql.*;\n"
                + "\n"
                + "public class CodeMageExample {\n"
                + "\n"
                + "    /* JDBC driver name and database URL */\n"
                + "    static final String JDBC_DRIVER = \"com.mysql.jdbc.Driver\";\n"
                + "    static final String DB_URL = \"jdbc:mysql://localhost/" + dbName + "\";\n"
                + "\n"
                + "    /*  Change Your Database credentials */\n"
                + "    static final String USER = \"username\";\n"
                + "    static final String PASS = \"password\";\n"
                + "\n"
                + "    public static void main(String[] args) {\n"
                + "        Connection conn = null;\n"
                + "        Statement stmt = null;\n"
                + "        try {\n"
                + "            /*STEP 2: Register JDBC driver */\n"
                + "            Class.forName(\"com.mysql.jdbc.Driver\");\n"
                + "\n"
                + "            /* STEP 3: Open a connection */\n"
                + "            System.out.println(\"Connecting to a selected database...\");\n"
                + "            conn = DriverManager.getConnection(DB_URL, USER, PASS);\n"
                + "            System.out.println(\"Connected database successfully...\");\n"
                + "\n"
                + "            /* STEP 4: Execute a query */\n"
                + "            System.out.println(\"Updating records in the table...\");\n"
                + "            stmt = conn.createStatement();\n"
                + "\n"
                + "            String sql = \"" + Query + "\";\n"
                + "            stmt.executeUpdate(sql);\n"
                + "            System.out.println(\"Updated records in the table...\");\n"
                + "\n"
                + "        } catch (SQLException | ClassNotFoundException se) {\n"
                + "        } finally {\n"
                + "            /* finally block used to close resources */\n"
                + "            try {\n"
                + "                if (stmt != null) {\n"
                + "                    conn.close();\n"
                + "                }\n"
                + "            } catch (SQLException se) {\n"
                + "            }\n"
                + "            try {\n"
                + "                if (conn != null) {\n"
                + "                    conn.close();\n"
                + "                }\n"
                + "            } catch (SQLException se) {\n"
                + "            }\n"
                + "        }\n"
                + "        System.out.println(\"Goodbye!\");\n"
                + "    }\n"
                + "}";

        return javaCode;
    }

    @Override
    public String DeleteData(String Query, String dbName) {
        String javaCode = "/*STEP 1. Import required packages*/\n"
                + "import java.sql.*;\n"
                + "\n"
                + "public class CodeMageExample {\n"
                + "\n"
                + "    /* JDBC driver name and database URL */\n"
                + "    static final String JDBC_DRIVER = \"com.mysql.jdbc.Driver\";\n"
                + "    static final String DB_URL = \"jdbc:mysql://localhost/" + dbName + "\";\n"
                + "\n"
                + "    /*  Change Your Database credentials */\n"
                + "    static final String USER = \"username\";\n"
                + "    static final String PASS = \"password\";\n"
                + "\n"
                + "    public static void main(String[] args) {\n"
                + "        Connection conn = null;\n"
                + "        Statement stmt = null;\n"
                + "        try {\n"
                + "            /*STEP 2: Register JDBC driver */\n"
                + "            Class.forName(\"com.mysql.jdbc.Driver\");\n"
                + "\n"
                + "            /* STEP 3: Open a connection */\n"
                + "            System.out.println(\"Connecting to a selected database...\");\n"
                + "            conn = DriverManager.getConnection(DB_URL, USER, PASS);\n"
                + "            System.out.println(\"Connected database successfully...\");\n"
                + "\n"
                + "            /* STEP 4: Execute a query */\n"
                + "            System.out.println(\"Deleteing records in the table...\");\n"
                + "            stmt = conn.createStatement();\n"
                + "\n"
                + "            String sql = \"" + Query + "\";\n"
                + "            stmt.executeUpdate(sql);\n"
                + "            System.out.println(\"Deleted records in the table...\");\n"
                + "\n"
                + "        } catch (SQLException | ClassNotFoundException se) {\n"
                + "        } finally {\n"
                + "            /* finally block used to close resources */\n"
                + "            try {\n"
                + "                if (stmt != null) {\n"
                + "                    conn.close();\n"
                + "                }\n"
                + "            } catch (SQLException se) {\n"
                + "            }\n"
                + "            try {\n"
                + "                if (conn != null) {\n"
                + "                    conn.close();\n"
                + "                }\n"
                + "            } catch (SQLException se) {\n"
                + "            }\n"
                + "        }\n"
                + "        System.out.println(\"Goodbye!\");\n"
                + "    }\n"
                + "}";

        return javaCode;
    }

}
