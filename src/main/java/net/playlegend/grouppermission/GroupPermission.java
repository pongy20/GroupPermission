package net.playlegend.grouppermission;

import net.playlegend.grouppermission.command.GroupCommand;
import net.playlegend.grouppermission.config.SQLConfig;
import net.playlegend.grouppermission.repository.GroupRepository;
import net.playlegend.grouppermission.repository.SQL;
import net.playlegend.grouppermission.service.MessageService;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class GroupPermission extends JavaPlugin {

    @Override
    public void onEnable() {
        MessageService.getInstance().initDefaultMessages();
        SQLConfig.getInstance().setDefaults();
        SQLConfig.getInstance().loadDefaults();

        // try sql connection
        try {
            SQL.getInstance().connect();
        } catch (SQLException e) {
            getLogger().warning(" ");
            getLogger().warning(" ");
            getLogger().warning("No SQL-Connection established. Connect to SQL-Database and reload.");
            getLogger().warning(" ");
            getLogger().warning(" ");
            return;
        }

        // create database tables
        GroupRepository.getInstance().createTable();

        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable() {
        if (SQL.getInstance().isConnected()) {
            SQL.getInstance().close();
        }
    }

    private void registerEvents() {

    }

    private void registerCommands() {
        this.getCommand("group").setExecutor(new GroupCommand());
    }

}
