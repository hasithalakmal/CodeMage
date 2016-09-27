/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.controller;

import com.codemage.sql.javacode.DMLJava;
import com.codemage.sql.query.DQLQueries;
import com.codemage.sql.runner.DQLQueryRunner;
import com.codemage.sql.util.JsonStringGenarator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
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
    JsonStringGenarator jsonStringGenarator;

    @RequestMapping(value = "data/{dbName}/{tableName}", method = RequestMethod.GET, produces = "application/json")
    public String getTableData(@PathVariable String dbName, @PathVariable String tableName) {
        String query = DQLQueries.selectAllData(tableName);
        String res = DQLQueryRunner.selectData(dbName, query);
        String table = DQLQueryRunner.selectDataForEditableTable(dbName, tableName, query);
        String javacode = DMLJava.SelectData(query, dbName);
        javacode = jsonStringGenarator.javaToJSON(javacode);
        table = jsonStringGenarator.javaToJSON(table);
        query = "{\"msg\":\"success\",\"err\":\"false\",\"result\":\"" + res + "\",\"table\":\"" + table + "\",\"java\":\"" + javacode + "\",\"query\":\"" + query + "\"}";
        return query;
    }

}
