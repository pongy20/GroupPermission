package net.playlegend.grouppermission.utils;

import org.bukkit.ChatColor;

/**
 * Represents Messages including id and default message
 */
public enum MessageId {

    NO_PERM("no_permission", "&cYou don't have enough permissions to do that!"),
    MUST_PLAYER("must_player",  "&cOnly players can do that!"),
    INFO_GROUP_SELF("info_group_self", "&4You are currently a member of group $group$."),
    INFO_GROUP_OTHER("info_group_other", "&4$target$ is currently a member of group $group$."),
    GROUP_ALREADY_EXISTS("group_already_exists", "&cThere is already a group with that name!"),
    GROUP_CREATED("group_created", "&2The new group have been created!"),
    GROUP_NOT_EXISTS("group_not_exists", "&2There is no group with that name!"),
    GROUP_DELETED("group_deleted", "&2The group have been deleted!"),
    PLAYER_ADDED("player_added", "&2The player $player$ have been added to group $group$!"),
    PLAYER_REMOVED("player_removed", "&2The player have been removed from his group.");

    public String id;
    public String defaultMessage;

    MessageId(String id, String defaultMessage) {
        this.id = id;
        this.defaultMessage = defaultMessage;
    }

}
