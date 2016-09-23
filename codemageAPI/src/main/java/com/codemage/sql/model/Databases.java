package com.codemage.sql.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_databases")
public class Databases implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_dbname")
    private String user_dbname;

    @Column(name = "userid")
    private int userid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUser_dbname() {
        return user_dbname;
    }

    public void setUser_dbname(String user_dbname) {
        this.user_dbname = user_dbname;
    }

    @Override
    public String toString() {
        return "Databases{" + "id=" + id + ", user_dbname=" + user_dbname + ", userid=" + userid + '}';
    }

}
