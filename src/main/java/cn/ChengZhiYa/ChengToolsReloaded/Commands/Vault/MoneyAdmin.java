package cn.ChengZhiYa.ChengToolsReloaded.Commands.Vault;

import cn.ChengZhiYa.ChengToolsReloaded.Ultis.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static cn.ChengZhiYa.ChengToolsReloaded.Ultis.multi.*;

public class MoneyAdmin implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 3) {
            if (args[0].equals("add")) {
                if (Bukkit.getPlayer(args[1]) == null) {
                    sender.sendMessage(getLang("PlayerNotOnline"));
                    return false;
                }
                new EconomyAPI().addTo(args[1], Double.valueOf(args[2]));
            }
            if (args[0].equals("take")) {
                if (Bukkit.getPlayer(args[1]) == null) {
                    sender.sendMessage(getLang("PlayerNotOnline"));
                    return false;
                }
                new EconomyAPI().takeFrom(args[1], Double.valueOf(args[2]));
            }
            if (args[0].equals("set")) {
                if (Bukkit.getPlayer(args[1]) == null) {
                    sender.sendMessage(getLang("PlayerNotOnline"));
                    return false;
                }
                new EconomyAPI().setMoney(args[1], Double.valueOf(args[2]));
            }
        }
        Help(sender,s);
        return false;
    }

    public void Help(CommandSender sender, String Command) {
        sender.sendMessage(getLang("Vault.AdminHelpMessage", Command));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> TabList = new ArrayList<>();
        if (args.length == 1) {
            TabList.add("help");
            TabList.add("add");
            TabList.add("take");
            TabList.add("set");
            return TabList;
        }
        return null;
    }
}