package net.playlegend.grouppermission.service;

import net.playlegend.grouppermission.api.Group;
import net.playlegend.grouppermission.repository.GroupPlayerRepository;
import net.playlegend.grouppermission.repository.GroupRepository;
import net.playlegend.grouppermission.utils.MessageId;
import net.playlegend.grouppermission.utils.TimeConverter;
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
        if (!hasPermissionSendMessage(sender, "grouppermission.create")) return;
        if (groupRepository.doesGroupExists(name)) {
            sender.sendMessage(MessageService.getInstance().getMessage(MessageId.GROUP_ALREADY_EXISTS));
            return;
        }
        groupRepository.insertGroup(name);
        sender.sendMessage(MessageService.getInstance().getMessage(MessageId.GROUP_CREATED));
    }

    /**
     * Deletes a existing group
     */
    public void deleteGroup(CommandSender sender, String name) {
        if (!hasPermissionSendMessage(sender, "grouppermission.delete")) return;
        if (!groupRepository.doesGroupExists(name)) {
            sender.sendMessage(MessageService.getInstance().getMessage(MessageId.GROUP_NOT_EXISTS));
            return;
        }
        groupRepository.deleteGroup(name);
        sender.sendMessage(MessageService.getInstance().getMessage(MessageId.GROUP_DELETED));
    }

    /**
     * Adds a member to a group permanently
     */
    public boolean addMember(CommandSender sender, String targetGroup, String targetPlayer) {
        if (!hasPermissionSendMessage(sender, "grouppermission.add")) return false;
        if (!groupRepository.doesGroupExists(targetGroup)) {
            sender.sendMessage(MessageService.getInstance().getMessage(MessageId.GROUP_NOT_EXISTS));
            return false;
        }

        Group group = groupRepository.getGroupByName(targetGroup);
        String targetUUID = UUIDFetcher.getUUID(targetPlayer).toString();
        // always deleted the player from group table - also if he's not in any group
        playerRepository.removePlayer(targetUUID);
        playerRepository.insertPlayer(targetUUID, group.groupId);
        String msg = MessageService.getInstance().getMessage(MessageId.PLAYER_ADDED);
        msg = MessageService.getInstance().replacePlaceholder(msg, "player", targetPlayer);
        msg = MessageService.getInstance().replacePlaceholder(msg, "group", group.name);
        sender.sendMessage(msg);
        return true;
    }

    /**
     * Adds a member to a group temporary
     */
    public void addMember(CommandSender sender, String targetGroup, String targetPlayer, String timeInput) {
        if (!addMember(sender, targetGroup, targetPlayer)) return;

        String uuid = UUIDFetcher.getUUID(targetPlayer).toString();
        long time = TimeConverter.getInstance().convertTime(timeInput);
        long until = System.currentTimeMillis() + time;

        playerRepository.updateUntil(uuid, until);

    }

    /**
     * Removes a groupmember
     */
    public void removeMember(CommandSender sender, String targetPlayer) {
        if (!hasPermissionSendMessage(sender, "grouppermission.remove")) return;

        String uuid = UUIDFetcher.getUUID(targetPlayer).toString();
        playerRepository.removePlayer(uuid);
        sender.sendMessage(MessageService.getInstance().getMessage(MessageId.PLAYER_REMOVED));
    }

    /**
     * Changes the prefix of a group
     */
    public void changePrefix(CommandSender sender, String targetGroup, String prefix) {
        if (!hasPermissionSendMessage(sender, "grouppermission.changeprefix")) return;

        Group group = groupRepository.getGroupByName(targetGroup);
        if (group == null) {
            sender.sendMessage(MessageService.getInstance().getMessage(MessageId.GROUP_NOT_EXISTS));
            return;
        }
        groupRepository.updatePrefix(group.groupId, prefix);
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
