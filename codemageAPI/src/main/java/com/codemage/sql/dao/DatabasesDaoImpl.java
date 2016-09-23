package com.codemage.sql.dao;

import com.codemage.sql.model.Databases;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("databasesDao")
public class DatabasesDaoImpl extends AbstractDao<Integer, Databases> implements DatabasesDao {

    @Override
    public void saveDatabases(Databases databases) {
        persist(databases);
    }

    @Override
    public void deleteDatabasesBySsn(String user_dbname) {
        Query query = getSession().createSQLQuery("delete from user_databases where user_dbname = :user_dbname");
        query.setString("user_dbname", user_dbname);
        query.executeUpdate();
    }

    //@SuppressWarnings("unchecked")
    @Override
    public List<Databases> findAllDatabasess(int userid) {
        Criteria crit = getSession().createCriteria(Databases.class);
        crit.add(Restrictions.eq("userid", userid));
        List<Databases> results = crit.list();
        System.out.println(results);
        return results;

    }

}
