package net.playlegend.grouppermission.service;

import net.playlegend.grouppermission.api.Group;
import net.playlegend.grouppermission.repository.GroupPlayerRepository;
import net.playlegend.grouppermission.repository.GroupRepository;
import net.playlegend.grouppermission.utils.MessageId;
import net.playlegend.grouppermission.utils.UUIDFetcher;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Service Class to manage all group things
 */
public class GroupService {

    private static GroupService instance;

    public static GroupService getInstance() {
        if (instance == null) {
            instance = new GroupService();
        }
        return instance;
    }

    private GroupRepository groupRepository;
    private GroupPlayerRepository playerRepository;

    private GroupService() {
        groupRepository = GroupRepository.getInstance();
        playerRepository = GroupPlayerRepository.getInstance();
    }

    /**
     * Replies the sender with group of target
     */
    public void infoGroup(CommandSender sender, String target) {
        if (!(sender instanceof Player) && sender.getName().equals(target)) {
            sender.sendMessage(MessageService.getInstance().getMessage(MessageId.MUST_PLAYER));
            return;
        }
        String uuid = UUIDFetcher.getUUID(target).toString();
        Group currentGroup = playerRepository.getGroupByUuid(uuid);

        String toSend;
        // sender and target is the same
        if (sender.getName().equals(target)) {
            toSend = MessageService.getInstance().getMessage(MessageId.INFO_GROUP_SELF);
        } else {
            toSend = MessageService.getInstance().getMessage(MessageId.INFO_GROUP_OTHER);
            toSend = MessageService.getInstance().replacePlaceholder(toSend, "target", target);
        }
        toSend = MessageService.getInstance().replacePlaceholder(toSend, "group", currentGroup.name);
        sender.sendMessage(toSend);
    }

    /**
     * Creates a new group
     */
    public void createGroup(CommandSender sender, String name) {
        if (!hasPermissionSendMessage(sender, "grouppermission.create")) {
            return;
        }
        if (groupRepository.doesGroupExists(name)) {
            sender.sendMessage(MessageService.getInstance().getMessage(MessageId.GROUP_ALREADY_EXISTS));
            return;
        }

    }

    /**
     * Deletes a existing group
     */
    public void deleteGroup() {

    }

    /**
     * Adds a member to a group permanently
     */
    public void addMember() {

    }

    /**
     * Adds a member to a group temporary
     */
    public void addMember(String timeInput) {

    }

    /**
     * Removes a groupmember
     */
    public void removeMember() {

    }

    /**
     * Changes the prefix of a group
     */
    public void changePrefix() {

    }

    /**
     * Checks if the CommandSender has the given permission and messages him if not
     */
    private boolean hasPermissionSendMessage(CommandSender sender, String permission) {
        if (sender.hasPermission(permission)) {
            return true;
        }
        sender.sendMessage(MessageService.getInstance().getMessage(MessageId.NO_PERM));
        return false;
    }


}
