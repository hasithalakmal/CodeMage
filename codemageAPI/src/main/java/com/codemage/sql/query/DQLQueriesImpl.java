/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.query;

import org.springframework.stereotype.Service;

/**
 *
 * @author Hasitha Lakmal
 */
@Service("DQLQueries")
public class DQLQueriesImpl implements DQLQueries {

    @Override
    public String selectAllData(String tableName) {
        String selectQuery = "SELECT * FROM "+ tableName+ ";";
        return selectQuery;
    }

  
}
