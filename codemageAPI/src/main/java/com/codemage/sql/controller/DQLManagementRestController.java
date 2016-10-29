/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.controller;

import com.codemage.sql.javacode.DMLJava;
import com.codemage.sql.query.DQLQueries;
import com.codemage.sql.runner.DMLQueryRunner;
import com.codemage.sql.runner.DQLQueryRunner;
import com.codemage.sql.util.JsonStringGenarator;
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
public class DQLManagementRestController {

    @Autowired
    DQLQueries DQLQueries;

    @Autowired
    DMLJava DMLJava;

    @Autowired
    DQLQueryRunner DQLQueryRunner;

    @Autowired
    DMLQueryRunner DMLQueryRunner;

    @Autowired
    JsonStringGenarator jsonStringGenarator;

    @RequestMapping(value = "data/{dbName}/{tableName}", method = RequestMethod.GET, produces = "application/json")
    public String getTableData(@PathVariable String dbName, @PathVariable String tableName) {
        String query = DQLQueries.selectAllData(tableName);
        String res = DQLQueryRunner.selectData(dbName, query);
        String table = DQLQueryRunner.selectDataForEditableTable(dbName, tableName, query);
        String javacode = DMLJava.SelectData(query, dbName);
        javacode = jsonStringGenarator.javaToJSON(javacode);
        table = jsonStringGenarator.javaToJSON(table);

        String err = "false";
        String msg = "success";
        String runningState = DQLQueryRunner.selectDataForQueryTest(dbName, query);
        runningState = jsonStringGenarator.javaToJSON(runningState);
        if (!"ok".equals(runningState)) {
            err = "true";
            msg = runningState;
        }

        query = "{\"msg\":\"" + msg + "\",\"err\":\"" + err + "\",\"result\":\"" + res + "\",\"table\":\"" + table + "\",\"java\":\"" + javacode + "\",\"query\":\"" + query + "\"}";
        return query;
    }

    @RequestMapping(value = "data-update/{dbName}/{tableName}", method = RequestMethod.GET, produces = "application/json")
    public String getTableDataUpdate(@PathVariable String dbName, @PathVariable String tableName) {
        String query = DQLQueries.selectAllData(tableName);
        String res = DQLQueryRunner.selectData(dbName, query);
        String table = DQLQueryRunner.selectDataForEditableTableUpdate(dbName, tableName, query);
        String javacode = DMLJava.SelectData(query, dbName);
        javacode = jsonStringGenarator.javaToJSON(javacode);
        table = jsonStringGenarator.javaToJSON(table);

        String err = "false";
        String msg = "success";
        String runningState = DQLQueryRunner.selectDataForQueryTest(dbName, query);
        runningState = jsonStringGenarator.javaToJSON(runningState);
        if (!"ok".equals(runningState)) {
            err = "true";
            msg = runningState;
        }

        query = "{\"msg\":\"" + msg + "\",\"err\":\"" + err + "\",\"result\":\"" + res + "\",\"table\":\"" + table + "\",\"java\":\"" + javacode + "\",\"query\":\"" + query + "\"}";
        return query;
    }

    @RequestMapping(value = "data-delete/{dbName}/{tableName}", method = RequestMethod.GET, produces = "application/json")
    public String getTableDataDelete(@PathVariable String dbName, @PathVariable String tableName) {
        String query = DQLQueries.selectAllData(tableName);
        String res = DQLQueryRunner.selectData(dbName, query);
        String table = DQLQueryRunner.selectDataForEditableTableDelete(dbName, tableName, query);
        String javacode = DMLJava.SelectData(query, dbName);
        javacode = jsonStringGenarator.javaToJSON(javacode);
        table = jsonStringGenarator.javaToJSON(table);

        String err = "false";
        String msg = "success";
        String runningState = DQLQueryRunner.selectDataForQueryTest(dbName, query);
        runningState = jsonStringGenarator.javaToJSON(runningState);
        if (!"ok".equals(runningState)) {
            err = "true";
            msg = runningState;
        }

        query = "{\"msg\":\"" + msg + "\",\"err\":\"" + err + "\",\"result\":\"" + res + "\",\"table\":\"" + table + "\",\"java\":\"" + javacode + "\",\"query\":\"" + query + "\"}";
        return query;
    }

    @RequestMapping(value = "data-json/{dbName}/{tableName}", method = RequestMethod.GET, produces = "application/json")
    public String getTableDataJSON(@PathVariable String dbName, @PathVariable String tableName) {
        String query = DMLQueryRunner.getDataFromTable(dbName, tableName);
        query = jsonStringGenarator.javaToJSON(query);
        query = "{\"msg\":\"success\",\"err\":\"false\",\"query\":\"" + query + "\"}";
        return query;
    }

    @RequestMapping(value = "qurey-builder", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public String getQueryData(@RequestBody String tableJSON) {
        JSONObject jsonObj = new JSONObject(tableJSON);
        System.out.println(jsonObj.toString());
        String dbName = jsonObj.getString("db_name");
        String table = jsonObj.getJSONObject("table_detail").toString();
        String logic = jsonObj.getString("logical_condition");
        System.out.println(">>>>>>>>>>>>>>>>>>>>> " + logic);
        String query = DQLQueries.selectQueryData(table, logic);
        String res   = DQLQueryRunner.selectData(dbName, query);
        query = jsonStringGenarator.chanageToJSON(query);
        String javaCode = DMLJava.SelectData(query, dbName);
        javaCode = jsonStringGenarator.javaToJSON(javaCode);
        query = "{\"msg\":\"success\",\"err\":\"false\",\"result\":\"" + res + "\",\"query\":\"" + query + "\", \"java_code\":\"" + javaCode + "\"}";
        System.out.println( query);
        return query;
        
    }
}
