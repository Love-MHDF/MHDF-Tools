package cn.ChengZhiYa.MHDFTools.Commands;

import cn.ChengZhiYa.MHDFTools.HashMap.StringHasMap;
import cn.ChengZhiYa.MHDFTools.MHDFTools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static cn.ChengZhiYa.MHDFTools.Utils.Database.LoginUtil.CheckPassword;
import static cn.ChengZhiYa.MHDFTools.Utils.Database.LoginUtil.LoginExists;
import static cn.ChengZhiYa.MHDFTools.Utils.Util.*;

public final class Login implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Bukkit.getScheduler().runTaskAsynchronously(MHDFTools.instance, () -> {
                if (args.length == 1) {
                    String Password = args[0];
                    Player player = (Player) sender;
                    if (ifLogin(player)) {
                        sender.sendMessage(i18n("Login.AlreadyLogin"));
                        return;
                    }
                    int MinPasswordLength = MHDFTools.instance.getConfig().getInt("LoginSystemSettings.MinPaswordLength");
                    int MaxPasswordLength = MHDFTools.instance.getConfig().getInt("LoginSystemSettings.MaxPaswordLength");
                    List<String> EasyPasswords = MHDFTools.instance.getConfig().getStringList("LoginSystemSettings.EasyPasswords");
                    if (Password.length() < MinPasswordLength) {
                        sender.sendMessage(i18n("Login.LengthShort", String.valueOf(MinPasswordLength)));
                        return;
                    }
                    if (Password.length() > MaxPasswordLength) {
                        sender.sendMessage(i18n("Login.LengthLong", String.valueOf(MaxPasswordLength)));
                        return;
                    }
                    if (EasyPasswords.contains(Password)) {
                        sender.sendMessage(i18n("Login.EasyPassword"));
                        return;
                    }
                    if (!LoginExists(player.getName())) {
                        sender.sendMessage(i18n("Login.NoRegister"));
                        return;
                    }
                    if (CheckPassword(player.getName(), Sha256(Password))) {
                        StringHasMap.getHasMap().put(player.getName() + "_Login", "t");
                        if (MHDFTools.instance.getConfig().getBoolean("LoginSystemSettings.AutoLogin")) {
                            StringHasMap.getHasMap().put(player.getName() + "_LoginIP", Objects.requireNonNull(player.getAddress()).getHostName());
                        }
                        sender.sendMessage(i18n("Login.PasswordRight"));
                    } else {
                        sender.sendMessage(i18n("Login.PasswordError"));
                    }
                } else {
                    sender.sendMessage(i18n("Usage.Login", label));
                }
            });
        } else {
            sender.sendMessage(i18n("OnlyPlayer"));
        }
        return false;
    }
}
