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
public interface DQLQueries {

    String selectAllData(String tableName);

    String selectQueryData(String tableName, String condition);

    String getFeildData(String FeildName, String data_type, String input, String operator, String value);

    String getOperator(String operator);

}
