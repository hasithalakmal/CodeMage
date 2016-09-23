/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.controller;

import com.codemage.sql.model.Databases;
import com.codemage.sql.service.DatabasesService;
import java.util.List;
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
public class DatabaseManagementRestController {
    
    @Autowired
    DatabasesService databasesService;
    
    @RequestMapping(value = "user-databases/{userid}", method = RequestMethod.GET, produces = "application/json")
    public List<Databases> getUserDatabases(@PathVariable int userid) {
        List<Databases> x = databasesService.findAllDatabasess(userid);
        return x;
    }

    

}
