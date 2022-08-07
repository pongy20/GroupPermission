package net.playlegend.grouppermission.config;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Config {

    private String dir;
    private String filename;

    public Config(String dir, String filename) {
        this.dir = dir;
        this.filename = filename;
        if (!filename.endsWith(".yml"))
            filename += ".yml";
    }

    /**
     * Used to get easy access to the File, the config is saved
     * @return the File, the Config is saved
     */
    public File getFile() {
        return new File(dir, filename);
    }

    /**
     * The YAMLConfiguration to use Bukkit/Spigot predefined methods
     * @return a YAMLConfiguration Object using the given File
     */
    public YamlConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    /**
     * Can be used to save Defaults Data for example in onEnable sequence
     */
    public abstract void setDefaults();

    /**
     * Can be used to load Default data, usually used in onEnable sequence
     */
    public abstract void loadDefaults();

    /**
     * Saves the object in the given node in Config file
     * @param node where the object should be saved in config
     * @param object the object which will be saved in given node
     */
    public void set(String node, Object object) {
        YamlConfiguration cfg = getConfig();
        cfg.options().copyDefaults(true);

        cfg.set(node, object);

        save(cfg);
    }

    /**
     * Deletes a node from config file
     * @param node Node which should be deleted
     */
    public void delete(String node) {
        YamlConfiguration cfg = getConfig();
        cfg.set(node, null);

        save(cfg);
    }
    /**
     * Loads an object in given node
     * @param node the node of the object which should be loaded
     * @return the loaded Object
     */
    @Nullable
    public Object load(String node) {
        if (doesNodeExsist(node)) {
            YamlConfiguration cfg = getConfig();
            return cfg.get(node);
        }
        return null;
    }

    /**
     * Looks if a given node exists
     * @param node the node which should be checked
     * @return true if the node exists, false if not
     */
    public boolean doesNodeExsist(String node) {
        YamlConfiguration cfg = getConfig();
        try {
            return cfg.contains(node);
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    /**
     * Searching for all available nodes in config file
     * @return a List of nodes, saved in config
     */
    public List<String> getAvailableNodes(boolean selectChildrenNodes) {
        YamlConfiguration cfg = getConfig();
        List<String> nodes = new ArrayList<>();
        nodes.addAll(cfg.getKeys(selectChildrenNodes));
        return nodes;
    }
    /**
     * Smooth saves a location to given node in config file
     * @param node the node, where the location should be saved
     * @param location the location which should be saved
     */
    public void saveLocation(String node, Location location) {
        YamlConfiguration cfg = getConfig();

        cfg.set(node, location);

        save(cfg);
    }
    /**
     *  Loads a location
     * @param node the source of the location
     * @return null if there is no location or the saved location object as itself
     */
    @Nullable
    public Location loadLocation(String node) {
        YamlConfiguration cfg  = getConfig();
        if (cfg.isLocation(node)) {
            return cfg.getLocation(node);
        }
        return null;
    }
    /**
     * Saves the Config to given YAMLConfiguration object
     * @param cfg YAMLConfiguration instance which should be saved
     */
    public void save(YamlConfiguration cfg) {
        try {
            cfg.save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getDir() {
        return dir;
    }

    public String getFilename() {
        return filename;
    }

}
