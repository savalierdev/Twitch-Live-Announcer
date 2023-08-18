package it.ax3lt.Commands.Link;

import it.ax3lt.Main.TLA;
import it.ax3lt.Utils.Configs.ConfigUtils;
import it.ax3lt.Utils.Configs.MessagesConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class AddLinkCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("twitchliveannouncer.link.add"))
            return true;


        if (args.length < 4) {
            sender.sendMessage(Objects.requireNonNull(MessagesConfigUtils.getString("add_link_usage"))
                    .replace("%prefix%", Objects.requireNonNull(ConfigUtils.getConfigString("prefix"))));
            return true;
        }


        String mcName = args[2];
        String twitchName = args[3];
        // Check if channel is in the list, if it isn't, add it
        if (!TLA.getInstance().getConfig().getStringList("channels").contains(twitchName)) {
            List<String> channels = TLA.getInstance().getConfig().getStringList("channels");
            channels.add(twitchName);
            TLA.getInstance().getConfig().set("channels", channels);
            TLA.getInstance().saveConfig();
            TLA.getInstance().reloadConfig();
        }

        // Check if mcName and twitchName are already in the config
        List<String> linkedUsers = TLA.getInstance().getConfig().getStringList("linked_users." + mcName);

        if (linkedUsers != null && !linkedUsers.isEmpty()) {
            for (String s : linkedUsers) {
                if (s.equalsIgnoreCase(twitchName)) {
                    sender.sendMessage(Objects.requireNonNull(MessagesConfigUtils.getString("link-already-made")).replace(
                            "%channel%", twitchName
                    ));
                    return true;
                }
            }
        }


        linkedUsers.add(twitchName);
        TLA.getInstance().getConfig().set("linked_users." + mcName, linkedUsers);
        TLA.getInstance().saveConfig();
        TLA.getInstance().reloadConfig();
        sender.sendMessage(Objects.requireNonNull(MessagesConfigUtils.getString("link-made"))
                .replace("%channel%", twitchName)
                .replace("%player%", mcName));
        return true;
    }
}
