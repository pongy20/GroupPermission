package net.playlegend.grouppermission.service;

import net.playlegend.grouppermission.config.MessageConfig;
import net.playlegend.grouppermission.utils.MessageId;
import org.bukkit.ChatColor;

/**
 * single instance (singleton) class to manage game-messages
 */
public class MessageService {

    private static MessageService instance;

    public static MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService();
        }
        return instance;
    }

    private MessageConfig config;

    private MessageService() {
        config = new MessageConfig();
    }

    /**
     * @return the saved message by id
     */
    public String getMessage(String id) {
        String savedMessage = config.getMessage(id);
        return ChatColor.translateAlternateColorCodes('&', savedMessage);
    }

    /**
     * @return the saved message by messageid enum entry
     */
    public String getMessage(MessageId messageId) {
        return getMessage(messageId.id);
    }

    /**
     * Will insert or update an existing entry
     */
    public void updateMessage(String node, String newMessage) {
        config.setMessage(node, newMessage);
    }

    /**
     * Replaces a placeholder of {} in a string
     * @param input the inputstring including given placeholder
     * @param placeholder the placeholder which is in $ format
     * @param replacement the string the placeholder should be replaced with
     * @return replaced string - if no placeholder will be found, nothing will happen
     */
    public String replacePlaceholder(String input, String placeholder, String replacement) {
        String[] splitted = input.split("$");
        if (splitted.length <= 1) {
            return input;
        }
        String output = "";
        for (int i = 0; i < splitted.length; i++) {
            if (splitted[i].equalsIgnoreCase(placeholder)) {
                splitted[i] = replacement;
            }
            output += splitted[i] + " ";
        }
        return output;
    }

    /**
     * Will initialize all default messages.
     * Will not override messages which have been changed manually by user.
     */
    public void initDefaultMessages() {
        for (MessageId message : MessageId.values()) {
            config.setDefault(message.id, message.defaultMessage);
        }
    }

}
