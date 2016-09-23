package com.codemage.sql.dao;

import com.codemage.sql.model.Databases;
import java.util.List;



public interface DatabasesDao {

	void saveDatabases(Databases database);
	
	void deleteDatabasesBySsn(String user_dbname);
	
	List<Databases> findAllDatabasess(int ssn);

}
