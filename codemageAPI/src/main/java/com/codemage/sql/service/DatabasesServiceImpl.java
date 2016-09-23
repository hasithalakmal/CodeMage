package com.codemage.sql.service;

import com.codemage.sql.dao.DatabasesDao;
import com.codemage.sql.model.Databases;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("databasesService")
@Transactional
public class DatabasesServiceImpl implements DatabasesService {

	@Autowired
	private DatabasesDao dao;


        @Override
	public void saveDatabases(Databases databases) {
		dao.saveDatabases(databases);
	}

        @Override
	public void deleteDatabasesBySsn(String user_dbname) {
		dao.deleteDatabasesBySsn(user_dbname);
	}
	
        @Override
	public List<Databases> findAllDatabasess(int ssn) {
		return dao.findAllDatabasess(ssn);
	}

}
