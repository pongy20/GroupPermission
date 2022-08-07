package net.playlegend.grouppermission;

import net.playlegend.grouppermission.service.MessageService;
import org.bukkit.plugin.java.JavaPlugin;

public final class GroupPermission extends JavaPlugin {

    @Override
    public void onEnable() {
        MessageService.getInstance().initDefaultMessages();
    }

    @Override
    public void onDisable() {

    }
}
