package net.playlegend.grouppermission.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Table syntax:
 *
 * groupid INTEGER AUTO_INCREMENT --> PRIMARY KEY
 * name VARCHAR(32) NOT NULL --> Unique
 * prefix VARCHAR(32) NULLABLE
 */
public class GroupRepository extends Repository {
    
    private static GroupRepository instance;
    
    public static GroupRepository getInstance() {
        if (instance == null) {
            instance = new GroupRepository();
        }
        return instance;
    }
    
    private GroupRepository() {
        super("group", SQL.getInstance());
    }

    public void createTable() {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + tableName +
                    "groupId INTEGER AUTO_INCREMENT," +
                    "name VARCHAR(32) NOT NULL," +
                    "prefix VARCHAR(32)," +
                    "CONSTRAINT pk_group PRIMARY KEY(groupId)," +
                    "CONSTRAINT uk_name UNIQUE(name)");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
