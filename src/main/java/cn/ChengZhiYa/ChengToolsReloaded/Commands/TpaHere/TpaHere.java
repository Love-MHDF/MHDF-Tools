package cn.ChengZhiYa.ChengToolsReloaded.Commands.TpaHere;

import cn.ChengZhiYa.ChengToolsReloaded.HashMap.BooleanHashMap;
import cn.ChengZhiYa.ChengToolsReloaded.HashMap.IntHashMap;
import cn.ChengZhiYa.ChengToolsReloaded.HashMap.StringHashMap;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static cn.ChengZhiYa.ChengToolsReloaded.Ultis.multi.*;

public class TpaHere implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) sender;
                String TpaHerePlayerName = args[0];
                String PlayerName = player.getName();
                if (Bukkit.getPlayer(TpaHerePlayerName) == null) {
                    sender.sendMessage(getLang("PlayerNotOnline"));
                    return false;
                }
                Player TpaHerePlayer = Bukkit.getPlayer(TpaHerePlayerName);
                if (StringHashMap.Get(PlayerName + "_TempTpaherePlayerName") != null) {
                    player.sendMessage(getLang("TpaHere.RepectSend"));
                    return false;
                }
                player.sendMessage(getLang("TpaHere.SendDone"));
                TextComponent Message = new TextComponent();
                TextComponent AccentMessage = new TextComponent(getLang("TpaHere.AccentMessage"));
                AccentMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor("   "))));
                AccentMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpahereaccept " + PlayerName));
                TextComponent RefuseMessage = new TextComponent(getLang("TpaHere.DefuseMessage"));
                RefuseMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor("   "))));
                RefuseMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaheredefuse " + PlayerName));
                TextComponent Null = new TextComponent(getLang("TpaHere.NullMessage"));
                Message.addExtra(AccentMessage);
                Message.addExtra(Null);
                Message.addExtra(RefuseMessage);
                Objects.requireNonNull(TpaHerePlayer).sendMessage(getLang("TpaHere.MessageUp",player.getName()));
                Objects.requireNonNull(TpaHerePlayer).spigot().sendMessage(Message);
                TpaHerePlayer.sendMessage(getLang("TpaHere.MessageDown"));
                StringHashMap.Set(PlayerName + "_Temp_TpaherePlayerName", TpaHerePlayer.getName());
                BooleanHashMap.Set(PlayerName + "_Temp_Tpahere", false);
                IntHashMap.Set(PlayerName + "_Temp_TpahereTime", 0);
            } else {
                sender.sendMessage(getLang("Usage.TpaHere"));
            }
        } else {
            sender.sendMessage(getLang("OnlyPlayer"));
        }
        return false;
    }
}