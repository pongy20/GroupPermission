package net.playlegend.grouppermission.utils;

import org.bukkit.ChatColor;

/**
 * Represents Messages including id and default message
 */
public enum MessageId {

    NO_PERM("no_permission", "&cYou don't have enough permissions to do that!"),
    MUST_PLAYER("must_player",  "&cOnly players can do that!");

    public String id;
    public String defaultMessage;

    MessageId(String id, String defaultMessage) {
        this.id = id;
        this.defaultMessage = defaultMessage;
    }

}
