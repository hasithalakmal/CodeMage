/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.util;

/**
 *
 * @author Hasitha Lakmal
 */
import java.sql.DatabaseMetaData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import org.springframework.stereotype.Service;

/**
 * This class connects to a database and dumps all the tables and contents out to stdout in the form of
 * a set of SQL executable statements
 */
@Service("dbExport")
public class SqlExport {

    /** Dump the whole database to an SQL string
     * @param dbName */
    public static String dumpDB(String dbName) {
        //props=null;
        //System.out.println("driver.class:- "+props.getProperty("driver.class"));
        String driverClassName = "com.mysql.jdbc.Driver";
        //System.out.println("driver.url:- "+props.getProperty("driver.url"));
        String driverURL = "jdbc:mysql://localhost/"+dbName;//props.getProperty("driver.url");
        // Default to not having a quote character
        
        //System.out.println("columnName.quoteChar:- "+props.getProperty("columnName.quoteChar"));
        String columnNameQuote = null;//props.getProperty("columnName.quoteChar", "");
        DatabaseMetaData dbMetaData = null;
        Connection dbConn = null;
        try {
            Class.forName(driverClassName);
            
            dbConn = DriverManager.getConnection(driverURL, "root","");
            dbMetaData = dbConn.getMetaData();
        }
        catch( Exception e ) {
            System.err.println("Unable to connect to database: "+e);
            return null;
        }

        try {
            StringBuffer result = new StringBuffer();
            String catalog = null;//props.getProperty("catalog");
            
            String schema = null;//props.getProperty("schemaPattern");
            
            String tables = null;//props.getProperty("tableName");
            
            ResultSet rs = dbMetaData.getTables(catalog, schema, tables, null);
            if (! rs.next()) {
                System.err.println("Unable to find any tables matching: catalog="+catalog+" schema="+schema+" tables="+tables);
                rs.close();
            } else {
                // Right, we have some tables, so we can go to work.
                // the details we have are
                // TABLE_CAT String => table catalog (may be null)
                // TABLE_SCHEM String => table schema (may be null)
                // TABLE_NAME String => table name
                // TABLE_TYPE String => table type. Typical types are "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
                // REMARKS String => explanatory comment on the table
                // TYPE_CAT String => the types catalog (may be null)
                // TYPE_SCHEM String => the types schema (may be null)
                // TYPE_NAME String => type name (may be null)
                // SELF_REFERENCING_COL_NAME String => name of the designated "identifier" column of a typed table (may be null)
                // REF_GENERATION String => specifies how values in SELF_REFERENCING_COL_NAME are created. Values are "SYSTEM", "USER", "DERIVED". (may be null)
                // We will ignore the schema and stuff, because people might want to import it somewhere else
                // We will also ignore any tables that aren't of type TABLE for now.
                // We use a do-while because we've already caled rs.next to see if there are any rows
                do {
                    String tableName = rs.getString("TABLE_NAME");
                    String tableType = rs.getString("TABLE_TYPE");
                    if ("TABLE".equalsIgnoreCase(tableType)) {
                        result.append("\n\n-- "+tableName);
                        result.append("\nCREATE TABLE "+tableName+" (\n");
                        ResultSet tableMetaData = dbMetaData.getColumns(null, null, tableName, "%");
                        boolean firstLine = true;
                        while (tableMetaData.next()) {
                            if (firstLine) {
                                firstLine = false;
                            } else {
                                // If we're not the first line, then finish the previous line with a comma
                                result.append(",\n");
                            }
                            String columnName = tableMetaData.getString("COLUMN_NAME");
                            String columnType = tableMetaData.getString("TYPE_NAME");
                            // WARNING: this may give daft answers for some types on some databases (eg JDBC-ODBC link)
                            int columnSize = tableMetaData.getInt("COLUMN_SIZE");
                            String nullable = tableMetaData.getString("IS_NULLABLE");
                            String nullString = "NULL";
                            if ("NO".equalsIgnoreCase(nullable)) {
                                nullString = "NOT NULL";
                            }
                            result.append("    "+columnName+" "+columnType+" ("+columnSize+")"+" "+nullString);
                        }
                        tableMetaData.close();

                        // Now we need to put the primary key constraint
                        try {
                            ResultSet primaryKeys = dbMetaData.getPrimaryKeys(catalog, schema, tableName);
                            // What we might get:
                            // TABLE_CAT String => table catalog (may be null)
                            // TABLE_SCHEM String => table schema (may be null)
                            // TABLE_NAME String => table name
                            // COLUMN_NAME String => column name
                            // KEY_SEQ short => sequence number within primary key
                            // PK_NAME String => primary key name (may be null)
                            String primaryKeyName = null;
                            StringBuffer primaryKeyColumns = new StringBuffer();
                            while (primaryKeys.next()) {
                                String thisKeyName = primaryKeys.getString("PK_NAME");
                                if ((thisKeyName != null && primaryKeyName == null)
                                        || (thisKeyName == null && primaryKeyName != null)
                                        || (thisKeyName != null && ! thisKeyName.equals(primaryKeyName))
                                        || (primaryKeyName != null && ! primaryKeyName.equals(thisKeyName))) {
                                    // the keynames aren't the same, so output all that we have so far (if anything)
                                    // and start a new primary key entry
                                    if (primaryKeyColumns.length() > 0) {
                                        // There's something to output
                                        result.append(",\n    PRIMARY KEY ");
                                        //if (primaryKeyName != null) { result.append(primaryKeyName); }
                                        result.append("("+primaryKeyColumns.toString()+")");
                                    }
                                    // Start again with the new name
                                    primaryKeyColumns = new StringBuffer();
                                    primaryKeyName = "";
                                }
                                // Now append the column
                                if (primaryKeyColumns.length() > 0) {
                                    primaryKeyColumns.append(", ");
                                }
                                primaryKeyColumns.append(primaryKeys.getString("COLUMN_NAME"));
                                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                                System.out.println(primaryKeyName);
                                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                            }
                            if (primaryKeyColumns.length() > 0) {
                                // There's something to output
                                result.append(",\n    PRIMARY KEY ");
                                if (primaryKeyName != null) { result.append(primaryKeyName); }
                                result.append(" ("+primaryKeyColumns.toString()+")");
                            }
                            /*String fk = getForeignKey (dbMetaData,catalog,tableName);
                            if(fk != ""){
                                result.append(",\n    ");
                                result.append(fk);
                            }*/
                            
                        } catch (SQLException e) {
                            // NB you will get this exception with the JDBC-ODBC link because it says
                            // [Microsoft][ODBC Driver Manager] Driver does not support this function
                            System.err.println("Unable to get primary keys for table "+tableName+" because "+e);
                        }

                        result.append("\n);\n");

                        // Right, we have a table, so we can go and dump it
                        dumpTable(dbConn, result, tableName);
                    }
                } while (rs.next());
                rs.close();
            }
            dbConn.close();
            return result.toString();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return null;
    }

    /** dump this particular table to the string buffer */
    private static void dumpTable(Connection dbConn, StringBuffer result, String tableName) {
        try {
            // First we output the create table stuff
            PreparedStatement stmt = dbConn.prepareStatement("SELECT * FROM "+tableName);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Now we can output the actual data
            result.append("\n\n-- Data for "+tableName+"\n");
            while (rs.next()) {
                result.append("INSERT INTO "+tableName+" VALUES (");
                for (int i=0; i<columnCount; i++) {
                    if (i > 0) {
                        result.append(", ");
                    }
                    Object value = rs.getObject(i+1);
                    if (value == null) {
                        result.append("NULL");
                    } else {
                        String outputValue = value.toString();
                        outputValue = outputValue.replaceAll("'","\\'");
                        result.append("'"+outputValue+"'");
                    }
                }
                result.append(");\n");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Unable to dump table "+tableName+" because: "+e);
        }
    }
    
    
    private static String getForeignKey (DatabaseMetaData dbMetaData,String  catalog,String tableName) throws SQLException{
        ResultSet foreignKeys = dbMetaData.getImportedKeys(catalog, null, tableName);
        String result = "";
        while (foreignKeys.next()) {
            //String fkTableName = foreignKeys.getString("FKTABLE_NAME");
            String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");
            String pkTableName = foreignKeys.getString("PKTABLE_NAME");
            String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
            result = "FOREIGN KEY ("+fkColumnName+") REFERENCES "+ pkTableName+"("+pkColumnName+")";
        } 
        return result; 
    }

    
}
