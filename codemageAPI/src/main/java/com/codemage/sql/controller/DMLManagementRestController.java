/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.controller;

import com.codemage.sql.query.DMLQueries;
import com.codemage.sql.runner.DMLQueryRunner;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
public class DMLManagementRestController {
    
    @Autowired
    DMLQueries DMLQueries;
    
    @Autowired
    DMLQueryRunner DMLQueryRunner;
    
    @RequestMapping(value = "insert", method = RequestMethod.POST, consumes ="application/json", produces = "application/json")
    public String insertData(@RequestBody String JSON) {
        JSONObject jsonObj = new JSONObject(JSON);
        String dbName = jsonObj.getString("db_name");
        String inser_data = jsonObj.getJSONObject("inser_data").toString();
        String query = DMLQueries.InsertData(inser_data);
        DMLQueryRunner.insertData(dbName, query);
        return query;
    }

    
    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes ="application/json", produces = "application/json")
    public String updatedata(@RequestBody String JSON) {
        JSONObject jsonObj = new JSONObject(JSON);
        String dbName = jsonObj.getString("db_name");
        String update_data = jsonObj.getJSONObject("update_data").toString();
        String query = DMLQueries.UpdateData(update_data);
        DMLQueryRunner.updateData(dbName, query);
        return query;
    }
    

}
