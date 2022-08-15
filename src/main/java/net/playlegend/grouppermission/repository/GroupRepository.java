package net.playlegend.grouppermission.repository;

import net.playlegend.grouppermission.api.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                    " (groupId INTEGER AUTO_INCREMENT," +
                    "name VARCHAR(32) NOT NULL," +
                    "prefix VARCHAR(32)," +
                    "CONSTRAINT pk_group PRIMARY KEY(groupId)," +
                    "CONSTRAINT uk_name UNIQUE(name))");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return true if the given group exists - otherwise false
     */
    public boolean doesGroupExists(String name) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT name FROM " + tableName + " WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Inserts a new group with given name.
     * If the group already exists... nothing will happen
     */
    public void insertGroup(String name) {
        if (doesGroupExists(name)) return;
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("INSERT INTO " + tableName +
                    " (name) VALUES (?)");
            ps.setString(1, name);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the group object - null if there is no group with this name
     */
    public Group getGroupByName(String name) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT * FROM " + tableName + " WHERE name = ?");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getGroupByResultSetEntry(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return the group object - null if there is no group with that id
     */
    public Group getGroupById(int id) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT * FROM " + tableName + " WHERE groupId = ?");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getGroupByResultSetEntry(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT * FROM " + tableName);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                groups.add(getGroupByResultSetEntry(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    /**
     * Updates the name of a given groupId
     */
    public void updateName(int id, String newName) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("UPDATE " + tableName +
                    " SET name = ? WHERE id = ?");
            ps.setString(1, newName);
            ps.setInt(2, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the prefix of a given groupId
     */
    public void updatePrefix(int id, String newPrefix) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("UPDATE " + tableName +
                    " SET prefix = ? WHERE id = ?");
            ps.setString(1, newPrefix);
            ps.setInt(2, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a group with given name
     */
    public void deleteGroup(String name) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("DELETE FROM " + tableName + " WHERE name = ?");
            ps.setString(1, name);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a group object out of result set entry.
     * Used to avoid doubled code in getter methods.
     */
    private Group getGroupByResultSetEntry(ResultSet rs) throws SQLException {
        if (rs == null) return null;
        int groupId = rs.getInt("groupId");
        String name = rs.getString("name");
        String prefix = rs.getString("prefix");
        return new Group(groupId, name, prefix);
    }

}
