package cn.ChengZhiYa.MHDFTools.command.subCommand.misc.money;

import cn.ChengZhiYa.MHDFTools.MHDFTools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static cn.ChengZhiYa.MHDFTools.utils.Util.i18n;
import static cn.ChengZhiYa.MHDFTools.utils.database.EconomyUtil.getMoney;

public final class Money implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        switch (args.length) {
            case 0:
                if (sender instanceof Player) {
                    sender.sendMessage(i18n("Vault.Money", String.valueOf(getMoney(sender.getName())), MHDFTools.instance.getConfig().getString("EconomySettings.MoneyName")));
                } else {
                    sender.sendMessage(i18n("Usage.Money"));
                }
                break;
            case 1:
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    sender.sendMessage(i18n("Vault.GetMoney", target.getName(), String.valueOf(getMoney(target.getName())), MHDFTools.instance.getConfig().getString("EconomySettings.MoneyName")));
                } else {
                    sender.sendMessage(i18n("PlayerNotFound", args[0]));
                }
                break;
            default:
                sender.sendMessage(i18n("Usage.Money"));
                break;
        }
        return false;
    }
}