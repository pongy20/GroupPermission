package net.playlegend.grouppermission.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Commands:
 *
 * group - shows the group the player currently is
 * group <name> - shows the group the given player is
 * group create <name> - creates a new group
 * group <group> prefix <prefix> - will change the prefix of the given group
 * group <group> add <player> - permanently add a player to a group
 * group <group> add <player> <time> - will temporary add a player to a group
 * group remove <player> - removes a player from a group
 * group delete <name> - deletes a group
 * group help - see all help information about group command
 */
public class GroupCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("grouppermission.admin") || args.length == 0) {
            //TODO: send info for current group
            return true;
        }

        if (args.length == 2) {

        } else if (args.length == 3) {

        } else if (args.length == 4) {

        } else {

        }
        return true;
    }

    /**
     * Sends help information to command sender
     */
    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GRAY + "Group Command Help-Page:");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GRAY + "/group - shows the current group");
        sender.sendMessage(ChatColor.GRAY + "/group create <name> - creates a new group with given name");
        sender.sendMessage(ChatColor.GRAY + "/group <group> prefix <prefix> - change the prefix of the group");
        sender.sendMessage(ChatColor.GRAY + "/group <group> add <player> - adds the player to given group");
        sender.sendMessage(ChatColor.GRAY + "/group <group> add <player> <time> - adds the player to given group for given time");
        sender.sendMessage(ChatColor.GRAY + "Valid time format: 1d5m10s - for 1 day, 5 minutes and 10 seconds");
        sender.sendMessage(ChatColor.GRAY + "/group remove <player> - removes the player from current group");
        sender.sendMessage(ChatColor.GRAY + "/group delete <name> - deletes the given group");
    }

}
