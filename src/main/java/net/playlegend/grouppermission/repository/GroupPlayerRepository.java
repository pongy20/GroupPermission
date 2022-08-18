package net.playlegend.grouppermission.repository;

import net.playlegend.grouppermission.api.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Table syntax:
 *
 * uuid VARCHAR(36) NOTNULL -> PRIMARY KEY
 * groupId INTEGER NOTNULL -> FOREIGN KEY
 * until BIGINT NULLABLE
 */
public class GroupPlayerRepository extends Repository {

    private static GroupPlayerRepository instance;

    public static GroupPlayerRepository getInstance() {
        if (instance == null) {
            instance = new GroupPlayerRepository();
        }
        return instance;
    }

    private GroupRepository groupRepository;

    private GroupPlayerRepository() {
        super("groupPlayer", SQL.getInstance());
        groupRepository = GroupRepository.getInstance();
    }

    /**
     * Creates the sql-table using described syntax
     */
    public void createTable() {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + tableName +
                    " (uuid VARCHAR(36)," +
                    "groupId INTEGER NOT NULL," +
                    "until BIGINT," +
                    "CONSTRAINT pk_uuid PRIMARY KEY(uuid)," +
                    "CONSTRAINT fk_group FOREIGN KEY(groupId) REFERENCES group(groupId) ON DELETE CASCADE)");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a new entry using uuid and groupId
     */
    public void insertPlayer(String uuid, int groupId) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("INSERT INTO " + tableName + " " +
                    "(uuid,groupId) VALUES (?,?)");
            ps.setString(1, uuid);
            ps.setInt(2, groupId);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the current until status of an entry using uuid
     */
    public void updateUntil(String uuid, long until) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("UPDATE " + tableName +
                    " SET until = ? WHERE uuid = ?");
            ps.setLong(1, until);
            ps.setString(2, uuid);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return gets the group of a player by using uuid
     */
    public Group getGroupByUuid(String uuid) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT * FROM " + tableName + " WHERE uuid = ?");
            ps.setString(1, uuid);
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
     * Removes a player from a group using uuid
     */
    public void removePlayer(String uuid) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("DELETE FROM " + tableName + " WHERE uuid = ?");
            ps.setString(1, uuid);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Turns a result set entry into a group using groupId
     */
    private Group getGroupByResultSetEntry(ResultSet rs) throws SQLException {
        int groupId = rs.getInt("groupId");
        return groupRepository.getGroupById(groupId);
    }

}
