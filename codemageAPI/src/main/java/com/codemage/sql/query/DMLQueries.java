/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.query;

/**
 *
 * @author Hasitha Lakmal
 */
public interface DMLQueries {

    String InsertData(String InsertJSON);

    String UpdateData(String UpdateJSON);

    String DeleteData(String DeleteJSON);
    
}
