package cn.ChengZhiYa.ChengToolsReloaded.Commands.Login;

import cn.ChengZhiYa.ChengToolsReloaded.ChengToolsReloaded;
import cn.ChengZhiYa.ChengToolsReloaded.HashMap.StringHasMap;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static cn.ChengZhiYa.ChengToolsReloaded.Utils.Util.*;

public final class Register implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Bukkit.getScheduler().runTaskAsynchronously(ChengToolsReloaded.instance, () -> {
                if (args.length == 1) {
                    File Login_File = new File(ChengToolsReloaded.instance.getDataFolder(), "LoginData.yml");
                    YamlConfiguration PasswordData = YamlConfiguration.loadConfiguration(Login_File);
                    String Password = args[0];
                    Player player = (Player) sender;
                    if (getLogin(player)) {
                        sender.sendMessage(getLang("Login.AlreadyLogin"));
                        return;
                    }
                    int MinPasswordLength = ChengToolsReloaded.instance.getConfig().getInt("LoginSystemSettings.MinPaswordLength");
                    int MaxPasswordLength = ChengToolsReloaded.instance.getConfig().getInt("LoginSystemSettings.MaxPaswordLength");
                    List<String> EasyPasswords = ChengToolsReloaded.instance.getConfig().getStringList("LoginSystemSettings.EasyPasswords");
                    if (Password.length() < MinPasswordLength) {
                        sender.sendMessage(getLang("Login.LengthShort", String.valueOf(MinPasswordLength)));
                        return;
                    }
                    if (Password.length() > MaxPasswordLength) {
                        sender.sendMessage(getLang("Login.LengthLong", String.valueOf(MaxPasswordLength)));
                        return;
                    }
                    for (String EasyPassword : EasyPasswords) {
                        if (Password.equals(EasyPassword)) {
                            sender.sendMessage(getLang("Login.EasyPassword"));
                            return;
                        }
                    }
                    if (PasswordData.getString(player.getName() + "_Password") != null) {
                        sender.sendMessage(getLang("Login.AlreadyRegister"));
                        return;
                    }
                    StringHasMap.getHasMap().put(player.getName() + "_Login", "t");
                    PasswordData.set(player.getName() + "_Password", Sha256(Password));
                    try {
                        PasswordData.save(Login_File);
                    } catch (IOException ignored) {}
                    if (ChengToolsReloaded.instance.getConfig().getBoolean("LoginSystemSettings.AutoLogin")) {
                        StringHasMap.getHasMap().put(player.getName() + "_LoginIP", Objects.requireNonNull(player.getAddress()).getHostName());
                    }
                    sender.sendMessage(getLang("Login.RegisterDone"));
                } else {
                    sender.sendMessage(getLang("Usage.Register", label));
                }
            });
        } else {
            sender.sendMessage(getLang("OnlyPlayer"));
        }
        return false;
    }
}
