package cn.ChengZhiYa.ChengToolsReloaded.Tasks;

import cn.ChengZhiYa.ChengToolsReloaded.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static cn.ChengZhiYa.ChengToolsReloaded.Ultis.multi.*;

public class VanillaOpWhitelist_Task extends BukkitRunnable {
    public void run() {
        if (main.main.getConfig().getBoolean("VanillaOpWhitelist.Enable")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.isOp()) {
                    for (String OpWhitelits : main.main.getConfig().getStringList("VanillaOpWhitelist.Whitelist")) {
                        if (!player.getName().equals(OpWhitelits)) {
                            player.setOp(false);
                            OpSendMessage(getLang("OpWhietList.Message",player.getName()), player.getName());
                            player.kickPlayer("OpWhietList.KickMessage");
                        }
                    }
                }
            }
        }
    }
}