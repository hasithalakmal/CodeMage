/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.query;

import org.json.JSONArray;
import org.json.JSONObject;
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
    
    @Override
    public String getFeildData(String FeildName, String data_type, String input,String operator,String value) {
        String feild_query = "";
        feild_query = FeildName;
        feild_query = feild_query + "  " + this.getOperator(operator)+" ";
        if(data_type.equals("string")){
            feild_query = feild_query + "  " +"'"+ value+"'"+" ";
        } else{
            feild_query = feild_query + "  " + value+" ";
        }
        
        return feild_query;
    }
    
    @Override
    public String getOperator(String operator){
        switch (operator) {
            case "equal":  operator = "=";
                     break;
            case "less":  operator = "<";
                     break;
            case "greater":  operator = ">";
                     break;
            case "less than or equal":  operator = "<=";
                     break;
            case "greater than or equal": operator = ">=";
                      break;
            case "not_equal": operator = "!=";
                      break;
        }
        
        return operator;
    }
    
    @Override
    public String selectQueryData(String table_JSON, String condition) {

        JSONObject jason = new JSONObject(table_JSON);
        String table_name = jason.get("table_name").toString();
        
        JSONArray tableColume = jason.getJSONArray("table_colume");
        String tbl_query = "SELECT";
        String a =this.tableCol(tableColume);
        tbl_query = tbl_query+ " "+ a +" "+"FROM " + table_name + " where ";

        System.out.println(tbl_query);
        JSONObject whereLength = jason.getJSONObject("where");
        

        tbl_query = tbl_query + " " + condition + " ; ";
     /*   if (whereLength.length()== 0) {
            tbl_query = tbl_query + ";";
            return tbl_query;
        } else {
            String condition = (String) whereLength.get("condition");
            JSONArray paramArray = whereLength.getJSONArray("rules");
            tbl_query = tbl_query + " where ";

            if (paramArray.length()==1) {
                JSONObject fld = paramArray.getJSONObject(0);
                String fild = this.getFeildData(fld.getString("field"), fld.getString("type"),fld.getString("input"), fld.getString("operator"),fld.getString("value")); 
                tbl_query = tbl_query + " " + fild;
                tbl_query = tbl_query + ";";
                return tbl_query; 
            } else {
                tbl_query = this.creatGroupParams(paramArray,tbl_query,condition);
            }
        }
        tbl_query = tbl_query + ";";*/
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!" + tbl_query);
        return tbl_query;
    }
     
    public String creatGroupParams(JSONArray paramArray ,String tbl_query,String condition){
        for (int i = 0; i < paramArray.length(); i++) {
            JSONObject fld = paramArray.getJSONObject(i);
            
            if (fld.has("condition")) {
                condition      = (String) fld.get("condition");
                JSONArray para = fld.getJSONArray("rules");
                tbl_query      = this.creatGroupParams(para,tbl_query,condition);
            } else{
                String fild = this.getFeildData(fld.getString("field"), fld.getString("type"),fld.getString("input"), fld.getString("operator"),fld.getString("value")); 
                tbl_query   = tbl_query + " " + fild;
                if (i== 0){
                    tbl_query = tbl_query +" "+condition+" ";
                }
            }
        }
        return tbl_query;
    }
    
    public String tableCol(JSONArray tableColume){
        String fieldList = " ";
        for ( int i= 0 ; i < tableColume.length();i++){
            String col = (String) tableColume.get(i);
            fieldList  = fieldList + col;
            if (tableColume.length() - 1 > i){
               fieldList = fieldList + ","; 
            }    
        }
 
        return fieldList;
    }
    
}
