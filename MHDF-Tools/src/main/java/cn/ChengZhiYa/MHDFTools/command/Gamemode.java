package cn.ChengZhiYa.MHDFTools.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cn.ChengZhiYa.MHDFTools.util.Util.*;

public final class Gamemode implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        String RunSender = i18n("Gamemode.Server");
        String GameModeName = null;
        Player player = null;

        if (args.length == 1) {
            GameModeName = args[0];
            player = (Player) sender;
        }
        if (args.length == 2) {
            GameModeName = args[0];
            if (Bukkit.getPlayer(args[1]) == null) {
                sender.sendMessage(i18n("PlayerNotOnline"));
                return false;
            }
            player = Bukkit.getPlayer(args[1]);

        }
        if (player != null) {
            if (GameModeName.equals("生存") || GameModeName.equals("0") || GameModeName.equalsIgnoreCase("survival")) {
                player.setGameMode(Objects.requireNonNull(GetGamemode(0)));
                player.sendMessage(i18n("Gamemode.Done", GetGamemodeString(0)));
                OpSendMessage(i18n("Gamemode.OtherOpDone", RunSender, player.getName(), GetGamemodeString(0)), sender.getName());
            }
            if (GameModeName.equals("冒险") || GameModeName.equals("2") || GameModeName.equalsIgnoreCase("adventure")) {
                player.setGameMode(Objects.requireNonNull(GetGamemode(2)));
                player.sendMessage(i18n("Gamemode.Done", GetGamemodeString(2)));
                OpSendMessage(i18n("Gamemode.OtherOpDone", RunSender, player.getName(), GetGamemodeString(2)), sender.getName());
            }
            if (GameModeName.equals("创造") || GameModeName.equals("1") || GameModeName.equalsIgnoreCase("creative")) {
                player.setGameMode(Objects.requireNonNull(GetGamemode(1)));
                player.sendMessage(i18n("Gamemode.Done", GetGamemodeString(1)));
                OpSendMessage(i18n("Gamemode.OtherOpDone", RunSender, player.getName(), GetGamemodeString(1)), sender.getName());
            }
            if (GameModeName.equals("旁观") || GameModeName.equals("3") || GameModeName.equalsIgnoreCase("spectator")) {
                player.setGameMode(Objects.requireNonNull(GetGamemode(3)));
                player.sendMessage(i18n("Gamemode.Done", GetGamemodeString(3)));
                OpSendMessage(i18n("Gamemode.OtherOpDone", RunSender, player.getName(), GetGamemodeString(3)), sender.getName());
            }
            return true;
        }
        sender.sendMessage(i18n("Usage.Gamemode"), label);
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 1) {
            ArrayList<String> tablist = new ArrayList<>();
            tablist.add("冒险");
            tablist.add("创造");
            tablist.add("旁观");
            tablist.add("生存");
            return tablist;
        }
        return null;
    }
}
