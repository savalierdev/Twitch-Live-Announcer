package it.ax3lt.Handlers;

import it.ax3lt.Main.StreamAnnouncer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommandHandler implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                StreamAnnouncer.getInstance().reloadConfig();
                sender.sendMessage(Objects.requireNonNull(StreamAnnouncer.getInstance().getConfig().getString("reload_config"))
                        .replace("%prefix", Objects.requireNonNull(StreamAnnouncer.getInstance().getConfig().getString("prefix"))));
                return true;
            }
            return true;
        } else if (sender.hasPermission("streamannouncer.reload") && args.length == 1 &&
                args[0].equalsIgnoreCase("reload")) {
            StreamAnnouncer.getInstance().reloadConfig();
            sender.sendMessage(StreamAnnouncer.getInstance().getConfig().getString("prefix") + " Config reloaded!");
            return true;
        }
        return false;
    }
}