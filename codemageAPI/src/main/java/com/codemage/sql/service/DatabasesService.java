package com.codemage.sql.service;

import com.codemage.sql.model.Databases;

import java.util.List;



public interface DatabasesService {

	void saveDatabases(Databases databases);
	
	
	void deleteDatabasesBySsn(String user_dbname);

	List<Databases> findAllDatabasess(int ssn); 
	
}
