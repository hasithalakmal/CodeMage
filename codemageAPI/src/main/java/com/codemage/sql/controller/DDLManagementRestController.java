/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.controller;

import com.codemage.sql.query.DatabaseDDL;
import com.codemage.sql.query.TableDDL;
import com.codemage.sql.model.Databases;
import com.codemage.sql.runner.DatabaseRunner;
import com.codemage.sql.service.DatabasesService;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Hasitha Lakmal
 */
@RestController
@Component
public class DDLManagementRestController {

    @Autowired
    DatabaseDDL databaseDDL;

     @Autowired
    TableDDL tableDDL;
     
    @Autowired
    DatabasesService databasesService;

    @Autowired
    DatabaseRunner databaseRunner;

    @RequestMapping(value = "database", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public String createDatabases(@RequestBody Databases db) {
        databasesService.saveDatabases(db);
        String query = databaseDDL.createDatabase(db.getUser_dbname());
        databaseRunner.createDatabase(db.getUser_dbname());
        query = "{\"msg\":\"success\",\"err\":\"false\",\"query\":\"" + query + "\"}";
        return query;
    }

    @RequestMapping(value = "database/{dbName}", method = RequestMethod.DELETE, produces = "application/json")
    public String deleteDatabases( @PathVariable String dbName) {
        databasesService.deleteDatabasesBySsn(dbName);
        databaseRunner.dropDatabase(dbName);
        String query = databaseDDL.dropDatabase(dbName);
        query = "{\"msg\":\"success\",\"err\":\"false\",\"query\":\"" + query + "\"}";
        return query;
    }

    @RequestMapping(value = "database/{userID}", method = RequestMethod.GET, produces = "application/json")
    public List<Databases> selectDatabases(@PathVariable int userID) {
        List<Databases> dbs = databasesService.findAllDatabasess(userID);
        return dbs;
    }
    
    @RequestMapping(value = "tables/{dbName}", method = RequestMethod.GET, produces = "application/json")
    public ArrayList selectAllTables(@PathVariable String dbName) {
        System.out.println(dbName);
        ArrayList dbs = databaseRunner.getTables(dbName);
        return dbs;
    }
    
    @RequestMapping(value = "export/{dbName}", method = RequestMethod.GET, produces = "application/json")
    public String exportDatabase(@PathVariable String dbName) {
         databaseRunner.exportSQL(dbName);
        return "success";
    }

    
    @RequestMapping(value = "table", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public String createTable(@RequestBody String tableJSON) {
        JSONObject jsonObj = new JSONObject(tableJSON);
        String dbName = jsonObj.getString("db_name");
        String table = jsonObj.getJSONObject("table_detail").toString();
        String query = tableDDL.createTable(table);
        databaseRunner.createTable(query, dbName);
        query = "{\"msg\":\"success\",\"err\":\"false\",\"query\":\"" + query + "\"}";
        return query;
    }
    
    @RequestMapping(value = "table-fk", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public String addFK(@RequestBody String tableJSON) {
        JSONObject jsonObj = new JSONObject(tableJSON);
        String dbName = jsonObj.getString("db_name");
        String fk_details = jsonObj.getJSONObject("fk_details").toString();
        String query = tableDDL.createFK(fk_details);
        databaseRunner.createTable(query, dbName);
        query = "{\"msg\":\"success\",\"err\":\"false\",\"query\":\"" + query + "\"}";
        return query;
    }
    
    @RequestMapping(value = "table/{dbName}/{tableName}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public String deleteTable(@PathVariable String dbName, @PathVariable String tableName) {
        System.out.println(dbName);
        System.out.println(tableName);
        String query = tableDDL.dropTable(tableName);
        System.out.println(query);
        databaseRunner.dropTable(tableName, dbName);
        query = "{\"msg\":\"success\",\"err\":\"false\",\"query\":\"" + query + "\"}";
        return query;
    }
}
