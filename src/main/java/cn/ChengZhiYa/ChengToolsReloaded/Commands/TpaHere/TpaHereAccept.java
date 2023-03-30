package cn.ChengZhiYa.ChengToolsReloaded.Commands.TpaHere;

import cn.ChengZhiYa.ChengToolsReloaded.HashMap.BooleanHashMap;
import cn.ChengZhiYa.ChengToolsReloaded.HashMap.IntHashMap;
import cn.ChengZhiYa.ChengToolsReloaded.HashMap.StringHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static cn.ChengZhiYa.ChengToolsReloaded.Ultis.multi.*;

public class TpaHereAccept implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                String AcceptPlayerName = args[0];
                Player player = (Player) sender;
                if (StringHashMap.Get(AcceptPlayerName + "_Temp_TpaherePlayerName") == null || !Objects.equals(StringHashMap.Get(AcceptPlayerName + "_Temp_TpaherePlayerName"), player.getName())) {
                    sender.sendMessage(getLang("TpaHere.NotSendTeleport"));
                    return false;
                }
                if (Bukkit.getPlayer(AcceptPlayerName) == null) {
                    StringHashMap.Set(AcceptPlayerName + "_Temp_TpaHerePlayerName", null);
                    BooleanHashMap.Set(AcceptPlayerName + "_Temp_TpaHere", false);
                    IntHashMap.Set(AcceptPlayerName + "_Temp_TpaHereTime", 0);
                    sender.sendMessage(getLang("TpaHere.Offline"));
                    return false;
                }
                Player AcceptPlayer = Bukkit.getPlayer(AcceptPlayerName);
                Location AcceptPlayerLocation = Objects.requireNonNull(AcceptPlayer).getLocation();
                player.teleport(AcceptPlayerLocation);
                AcceptPlayer.sendMessage(getLang("TpaHere.TeleportDone"));
                player.sendMessage(getLang("TpaHere.AcceptDone"));
                StringHashMap.Set(AcceptPlayerName + "_Temp_TpaPlayerName", null);
                BooleanHashMap.Set(AcceptPlayerName + "_Temp_Tpa", false);
                IntHashMap.Set(AcceptPlayerName + "_Temp_TpaTime", 0);
            } else {
                sender.sendMessage(getLang("Usage.TpaHere"));
            }
        } else {
            sender.sendMessage(getLang("OnlyPlayer"));
        }
        return false;
    }
}
