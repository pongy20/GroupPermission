package net.playlegend.grouppermission.repository;

public class Repository {

    /**
     * Table name this repository is used to
     */
    public String tableName;
    /**
     * SQL-Connection this repository will use
     */
    public SQL sql;

    public Repository(String tableName, SQL sql) {
        this.tableName = tableName;
        this.sql = sql;
    }
}
