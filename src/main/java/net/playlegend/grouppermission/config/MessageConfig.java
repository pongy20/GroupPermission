package net.playlegend.grouppermission.config;

import org.bukkit.configuration.file.YamlConfiguration;

public class MessageConfig extends Config {

    public MessageConfig() {
        super("plugins/GroupPermission", "messages.yml");
    }

    /**
     * Inserts a node as default value
     */
    public void setDefault(String node, String message) {
        YamlConfiguration cfg = getConfig();
        cfg.options().copyDefaults(true);
        cfg.addDefault(node, message);
        save(cfg);
    }

    /**
     * Inserts or updates (in case node already exists) a node
     */
    public void setMessage(String node, String message) {
        set(node, message);
    }

    /**
     * @return the message saved in config for given node - null in case node doesn't exist
     */
    public String getMessage(String node) {
        Object rawObject = load(node);
        try {
            return rawObject.toString();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setDefaults() {
        // no need
    }

    @Override
    public void loadDefaults() {
        // no need
    }
}
