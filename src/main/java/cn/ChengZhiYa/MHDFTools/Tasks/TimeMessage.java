package cn.ChengZhiYa.MHDFTools.Tasks;

import cn.ChengZhiYa.MHDFTools.MHDFTools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static cn.ChengZhiYa.MHDFTools.Utils.Util.ChatColor;

public final class TimeMessage extends BukkitRunnable {

    private int i = 0;

    private static int order(int i) {
        i++;
        return i;
    }

    public void run() {
        if (MHDFTools.instance.getConfig().getBoolean("TimeMessageSettings.Enable")) {
            if (MHDFTools.instance.getConfig().getBoolean("TimeMessageSettings.ConsoleDisplayed")) {
                Bukkit.broadcastMessage(ChatColor(null, MHDFTools.instance.getConfig().getStringList("TimeMessageSettings.Message").get(this.i)));
            } else {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(ChatColor(null, MHDFTools.instance.getConfig().getStringList("TimeMessageSettings.Message").get(this.i)));
                }
            }
            this.i = order(this.i);
            if (this.i >= MHDFTools.instance.getConfig().getStringList("TimeMessageSettings.Message").size()) {
                this.i = 0;
            }
        }
    }
}
