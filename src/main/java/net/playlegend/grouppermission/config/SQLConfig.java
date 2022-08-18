package net.playlegend.grouppermission.config;

import org.bukkit.configuration.file.YamlConfiguration;

public class SQLConfig extends Config {

    private static SQLConfig instance;
    
    public static SQLConfig getInstance() {
        if (instance == null) {
            instance = new SQLConfig();
        }
        return instance;
    }

    public String host, port, database, user, password;
    
    private SQLConfig() {
        super("plugins/GroupPermission", "mysql-config.yml");
    }

    @Override
    public void setDefaults() {
        YamlConfiguration cfg = getConfig();
        cfg.options().copyDefaults(true);

        cfg.addDefault("host","playlegend.net");
        cfg.addDefault("port", "3306");
        cfg.addDefault("database", "");
        cfg.addDefault("username", "");
        cfg.addDefault("password", "");

        save(cfg);
    }

    @Override
    public void loadDefaults() {
        YamlConfiguration cfg = getConfig();

        host = cfg.getString("host");
        port = cfg.getString("port");
        database = cfg.getString("database");
        user = cfg.getString("username");
        password = cfg.getString("password");
    }
}
