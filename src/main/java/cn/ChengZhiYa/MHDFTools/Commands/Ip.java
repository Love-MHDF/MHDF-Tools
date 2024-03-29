package cn.ChengZhiYa.MHDFTools.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static cn.ChengZhiYa.MHDFTools.Utils.Util.getIpLocation;
import static cn.ChengZhiYa.MHDFTools.Utils.Util.i18n;

public final class Ip implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender.hasPermission("MHDFTools.Command.Ip")) {
            if (args.length == 1) {
                String PlayerName = args[0];
                if (Bukkit.getPlayer(PlayerName) == null) {
                    sender.sendMessage(i18n("PlayerNotOnline"));
                    return false;
                }
                Player player = Bukkit.getPlayer(PlayerName);
                String PlayerIp = Objects.requireNonNull(Objects.requireNonNull(player).getAddress()).getHostString();
                String PlayerLocation = getIpLocation(PlayerIp);
                sender.sendMessage(i18n("IPMessage", player.getName(), PlayerIp, PlayerLocation));
            } else {
                sender.sendMessage(i18n("Usage.Ip"));
            }
        } else {
            sender.sendMessage(i18n("NoPermission"));
        }
        return false;
    }
}
