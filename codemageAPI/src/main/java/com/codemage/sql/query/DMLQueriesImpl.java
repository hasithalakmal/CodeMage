/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codemage.sql.query;

import static javax.management.Query.value;
import static javax.management.Query.value;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Hasitha Lakmal
 */
@Service("DMLQueries")
public class DMLQueriesImpl implements DMLQueries {

    @Override
    public String InsertData(String InsertJSON) {
        String insertQuery = "INSERT INTO ";

        JSONObject jsonObj = new JSONObject(InsertJSON);
        String table_name = jsonObj.getString("table_name");

        insertQuery = insertQuery + table_name + " (";

        JSONArray feild_names = jsonObj.getJSONArray("feild_names");
        for (int i = 0; i < feild_names.length(); i++) {
            if (feild_names.length() - 1 == i) {
                insertQuery = insertQuery + feild_names.getString(i) + " ) VALUES ( ";
            } else {
                insertQuery = insertQuery + feild_names.getString(i) + " , ";
            }
        }

        JSONArray data_types = jsonObj.getJSONArray("data_types");
        JSONArray data_value = jsonObj.getJSONArray("data_value");

        for (int i = 0; i < data_types.length(); i++) {
            String val = "";
            if (data_types.getString(i).equals("varchar(45)")||data_types.getString(i).equals("VARCHAR")  || data_types.getString(i).equals("Date")) {
                val = "'" + data_value.getString(i) + "'";
            } else {
                val = data_value.getString(i);
            }

            if (feild_names.length() - 1 == i) {
                insertQuery = insertQuery + val + " ) ";
            } else {
                insertQuery = insertQuery + val + " , ";
            }
        }

        insertQuery = insertQuery + ";";

        return insertQuery;
    }

    @Override
    public String UpdateData(String UpdateJSON) {
        String updateQuery = "UPDATE ";

        JSONObject jsonObj = new JSONObject(UpdateJSON);
        String table_name = jsonObj.getString("table_name");
        updateQuery = updateQuery + table_name + " SET ";

        JSONArray update_feilds = jsonObj.getJSONArray("update_feilds");

        for (int i = 0; i < update_feilds.length(); i++) {
            JSONObject up_fld = update_feilds.getJSONObject(i);
            String val = "";
            if (up_fld.getString("data_type").equals("varchar(45)") ||up_fld.getString("data_type").equals("VARCHAR") || up_fld.getString("data_type").equals("Date")) {
                val = "'" + up_fld.getString("new_val") + "'";
            } else {
                val = up_fld.getString("new_val");
            }

            if (update_feilds.length() - 1 == i) {
                updateQuery = updateQuery + up_fld.getString("feild_name") + " = " + val + " WHERE ";
            } else {
                updateQuery = updateQuery + up_fld.getString("feild_name") + " = " + val + " , ";
            }
        }

        JSONObject condition = jsonObj.getJSONObject("condition");
        String base_feild = condition.getString("base_feild");
        String data_type = condition.getString("data_type");
        if (data_type.equals("varchar(45)") || data_type.equals("Date")) {
            String value = condition.getString("value");
            updateQuery = updateQuery + base_feild + " = " + value + " ; ";
        } else {
            int value = condition.getInt("value");
            updateQuery = updateQuery + base_feild + " = " + value + " ; ";
        }

        return updateQuery;
    }

    @Override
    public String DeleteData(String DeleteJSON) {
        String deleteQuery = "DELETE FROM ";

        JSONObject jsonObj = new JSONObject(DeleteJSON);
        String table_name = jsonObj.getString("table_name");

        deleteQuery = deleteQuery + table_name + " WHERE ";

        JSONObject condition = jsonObj.getJSONObject("condition");
        if (condition.getString("data_type").equals("varchar(45)") || condition.getString("data_type").equals("Date")) {
            String val = "'" + condition.getString("value") + "'";
            deleteQuery = deleteQuery + condition.getString("base_feild") + " = " + val + " ; ";
        } else {
            int val = condition.getInt("value");
            deleteQuery = deleteQuery + condition.getString("base_feild") + " = " + val + " ; ";
        }

        return deleteQuery;
    }

}
