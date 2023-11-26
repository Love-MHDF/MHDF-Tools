package cn.ChengZhiYa.ChengToolsReloaded.Commands.Home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static cn.ChengZhiYa.ChengToolsReloaded.Utils.Database.HomeUtil.*;
import static cn.ChengZhiYa.ChengToolsReloaded.Utils.Util.getLang;

public final class DelHome implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) sender;
                String HomeName = args[0];
                if (HomeExists(player.getName(),HomeName)) {
                    RemoveHome(player.getName(),HomeName);
                    player.sendMessage(getLang("Home.RemoveDone"));
                }else {
                    player.sendMessage(getLang("Home.NotFound", HomeName));
                }
            } else {
                sender.sendMessage(getLang("Usage.Home", label));
            }
        } else {
            sender.sendMessage(getLang("OnlyPlayer"));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) sender;
                return getPlayerHomeList(player.getName());
            }
        }
        return null;
    }
}
