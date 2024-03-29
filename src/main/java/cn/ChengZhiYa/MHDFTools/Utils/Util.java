package cn.ChengZhiYa.MHDFTools.Utils;

import cn.ChengZhiYa.MHDFTools.HashMap.StringHasMap;
import cn.ChengZhiYa.MHDFTools.MHDFTools;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

import static cn.ChengZhiYa.MHDFTools.Utils.BCUtil.PlayerList;
import static cn.chengzhiya.mhdfpluginapi.Util.ChatColor;

public final class Util {
    public static final Class<?> pluginClassLoader;
    public static final Field pluginClassLoaderPlugin;
    public static final List<String> CommandLinkList = new ArrayList<>();
    public static List<String> VanishList = new ArrayList<>();
    public static BossBar VanishBossBar;
    public static YamlConfiguration LangFileData;
    public static YamlConfiguration SoundFileData;

    static {
        try {
            pluginClassLoader = Class.forName("org.bukkit.plugin.java.PluginClassLoader");
            pluginClassLoaderPlugin = pluginClassLoader.getDeclaredField("plugin");
            pluginClassLoaderPlugin.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getIpLocation(String Ip) {
        try {
            URL url = new URL("https://opendata.baidu.com/api.php?query=" + Ip + "&co=&resource_id=6006&t=1433920989928&ie=utf8&oe=utf-8&format=json");
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            JSONObject Json = JSONObject.parseObject(reader.readLine());
            JSONObject DataJson = JSONObject.parseObject(JSON.parseArray(Json.getString("data"), String.class).get(0));
            return DataJson.getString("location");
        } catch (IOException e) {
            return "获取失败";
        }
    }

    public static boolean ifSupportGetTps() {
        try {
            Bukkit.class.getMethod("getTPS");
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public static String PAPIChatColor(OfflinePlayer Player, String Message) {
        if (MHDFTools.PAPI) {
            Message = PlaceholderAPI.setPlaceholders(Player, Message);
        }
        return ChatColor(Message);
    }

    public static String Sha256(String Message) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(Message.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuffer = new StringBuilder();
            for (final byte aByte : messageDigest.digest()) {
                final String temp = Integer.toHexString(aByte & 0xFF);
                if (temp.length() == 1) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(temp);
            }
            return stringBuffer.toString().toUpperCase();
        } catch (Exception ignored) {
            return "";
        }
    }

    public static boolean ifLogin(Player player) {
        return StringHasMap.getHasMap().get(player.getName() + "_Login") != null;
    }

    public static String getNMSVersion() {
        try {
            return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public static void OpSendMessage(String Message, String PlayerName) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.isOp()) {
                if (!player.getName().equals(PlayerName)) {
                    player.sendMessage(ChatColor(Message));
                }
            }
        }
    }

    public static GameMode GetGamemode(int GameModeID) {
        if (GameModeID == 0) {
            return GameMode.SURVIVAL;
        }
        if (GameModeID == 1) {
            return GameMode.CREATIVE;
        }
        if (GameModeID == 2) {
            return GameMode.ADVENTURE;
        }
        if (GameModeID == 3) {
            return GameMode.SPECTATOR;
        }
        return null;
    }

    public static String GetGamemodeString(int GameModeID) {
        if (GameModeID == 0) {
            return i18n("Gamemode.Survival");
        }
        if (GameModeID == 1) {
            return i18n("Gamemode.Creative");
        }
        if (GameModeID == 2) {
            return i18n("Gamemode.Adventure");
        }
        if (GameModeID == 3) {
            return i18n("Gamemode.Spectator");
        }
        return null;
    }

    public static void registerCommand(Plugin plugin, CommandExecutor commandExecutor, String description, String... aliases) {
        PluginCommand command = getCommand(aliases[0], plugin);
        command.setAliases(Arrays.asList(aliases));
        command.setDescription(description);
        getCommandMap().register(plugin.getDescription().getName(), command);
        command.setExecutor(commandExecutor);
    }

    public static void registerCommand(Plugin plugin, CommandExecutor commandExecutor, TabExecutor tabExecutor, String description, String... aliases) {
        PluginCommand command = getCommand(aliases[0], plugin);
        command.setAliases(Arrays.asList(aliases));
        command.setDescription(description);
        getCommandMap().register(plugin.getDescription().getName(), command);
        command.setExecutor(commandExecutor);
        command.setTabCompleter(tabExecutor);
    }

    private static PluginCommand getCommand(String name, Plugin plugin) {
        PluginCommand command = null;
        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);
            command = c.newInstance(name, plugin);
        } catch (Exception ignored) {
        }
        return command;
    }

    private static CommandMap getCommandMap() {
        CommandMap commandMap = null;
        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);
                commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
            }
        } catch (Exception ignored) {
        }
        return commandMap;
    }

    public static String getTps(int Time) {
        double TPS1 = BigDecimal.valueOf(getTpsList()[0]).setScale(1, RoundingMode.HALF_UP).doubleValue();
        double TPS5 = BigDecimal.valueOf(getTpsList()[1]).setScale(1, RoundingMode.HALF_UP).doubleValue();
        double TPS15 = BigDecimal.valueOf(getTpsList()[2]).setScale(1, RoundingMode.HALF_UP).doubleValue();
        if (TPS1 > 20.0D) {
            TPS1 = 20.0D;
        }
        if (TPS5 > 20.0D) {
            TPS5 = 20.0D;
        }
        if (TPS15 > 20.0D) {
            TPS15 = 20.0D;
        }
        if (Time == 1) {
            if (TPS1 > 18.0D) {
                return "&a" + TPS1;
            }
            if (TPS1 > 16.0D) {
                return "&6" + TPS1;
            }
            if (TPS1 < 16.0D) {
                return "&c" + TPS1;
            }
        }
        if (Time == 5) {
            if (TPS5 > 18.0D) {
                return "&a" + TPS5;
            }
            if (TPS5 > 16.0D) {
                return "&6" + TPS5;
            }
            if (TPS5 < 16.0D) {
                return "&c" + TPS5;
            }
        }
        if (Time == 15) {
            if (TPS15 > 18.0D) {
                return "&a" + TPS15;
            }
            if (TPS15 > 16.0D) {
                return "&6" + TPS15;
            }
            if (TPS15 < 16.0D) {
                return "&c" + TPS15;
            }
        }
        return "获取失败";
    }

    public static double[] getTpsList() {
        return Bukkit.getTPS();
    }

    public static String sound(String SoundKey) {
        return SoundFileData.getString(SoundKey);
    }

    public static String i18n(String LangKey) {
        return ChatColor(Objects.requireNonNull(LangFileData.getString(LangKey)));
    }

    public static String i18n(String LangKey, String Vaule1) {
        return ChatColor(Objects.requireNonNull(LangFileData.getString(LangKey))
                .replaceAll("%1", Vaule1));
    }

    public static String i18n(String LangKey, String Vaule1, String Vaule2) {
        return ChatColor(Objects.requireNonNull(LangFileData.getString(LangKey))
                .replaceAll("%1", Vaule1)
                .replaceAll("%2", Vaule2));
    }

    public static String i18n(String LangKey, String Vaule1, String Vaule2, String Vaule3) {
        return ChatColor(Objects.requireNonNull(LangFileData.getString(LangKey))
                .replaceAll("%1", Vaule1)
                .replaceAll("%2", Vaule2)
                .replaceAll("%3", Vaule3));
    }

    public static String i18n(String LangKey, String Vaule1, String Vaule2, String Vaule3, String Vaule4) {
        return ChatColor(Objects.requireNonNull(LangFileData.getString(LangKey))
                .replaceAll("%1", Vaule1)
                .replaceAll("%2", Vaule2)
                .replaceAll("%3", Vaule3)
                .replaceAll("%4", Vaule4));
    }

    public static String getJoinMessage(Player player) {
        HashMap<Integer, String> MessageList = new HashMap<>();
        List<Integer> WeghitList = new ArrayList<>();
        for (PermissionAttachmentInfo permInfo : player.getEffectivePermissions()) {
            String perm = permInfo.getPermission();
            if (perm.startsWith("mhdftools.joinmessage.")) {
                String Group = perm.substring("mhdftools.joinmessage.".length());
                int Weight = MHDFTools.instance.getConfig().getInt("CustomJoinServerMessageSettings." + Group + ".Weight");
                MessageList.put(Weight, Group);
                WeghitList.add(Weight);
            }
        }
        if (!MessageList.isEmpty()) {
            WeghitList.sort(Collections.reverseOrder());
            return PAPIChatColor(player, MHDFTools.instance.getConfig().getString("CustomJoinServerMessageSettings." + MessageList.get(WeghitList.get(0)) + ".JoinMessage")).replaceAll("%PlayerName%", player.getName());
        }
        return PAPIChatColor(player, MHDFTools.instance.getConfig().getString("CustomJoinServerMessageSettings.Default.JoinMessage"))
                .replaceAll("%PlayerName%", player.getName());
    }

    public static String getQuitMessage(Player player) {
        Map<Integer, String> MessageList = new HashMap<>();
        List<Integer> WeghitList = new ArrayList<>();
        for (PermissionAttachmentInfo permInfo : player.getEffectivePermissions()) {
            String perm = permInfo.getPermission();
            if (perm.startsWith("mhdftools.quitmessage.")) {
                String Group = perm.substring("mhdftools.quitmessage.".length());
                int Weight = MHDFTools.instance.getConfig().getInt("CustomQuitServerMessageSettings." + Group + ".Weight");
                MessageList.put(Weight, Group);
                WeghitList.add(Weight);
            }
        }
        if (!MessageList.isEmpty()) {
            WeghitList.sort(Collections.reverseOrder());
            return PAPIChatColor(player, MHDFTools.instance.getConfig().getString("CustomQuitServerMessageSettings." + MessageList.get(WeghitList.get(0)) + ".QuitMessage")).replaceAll("%PlayerName%", player.getName());
        }
        return PAPIChatColor(player, MHDFTools.instance.getConfig().getString("CustomQuitServerMessageSettings.Default.QuitMessage"))
                .replaceAll("%PlayerName%", player.getName());
    }

    public static BossBar getVanishBossBar() {
        if (VanishBossBar == null) {
            VanishBossBar = BossBar.bossBar(Component.text(i18n("Vanish.Bossbar")), 1f, BossBar.Color.WHITE, BossBar.Overlay.PROGRESS);
        }
        return VanishBossBar;
    }

    public static List<String> getPlayerList(boolean BCAPI) {
        List<String> OnlinePlayerList = new ArrayList<>();
        if (BCAPI && MHDFTools.instance.getConfig().getBoolean("BungeecordSettings.Enable")) {
            OnlinePlayerList.addAll(Arrays.asList(PlayerList));
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                OnlinePlayerList.add(player.getName());
            }
        }
        OnlinePlayerList.removeAll(VanishList);
        return OnlinePlayerList;
    }

    public static void SendTitle(Player player, String TitleString) {
        String[] Title = TitleString.split("\\|");
        player.sendTitle(PAPIChatColor(player, Title[0]), PAPIChatColor(player, Title[1]), Integer.parseInt(Title[2]), Integer.parseInt(Title[3]), Integer.parseInt(Title[4]));
    }

    public static void PlaySound(Player player, String SoundString) {
        String[] Sound = SoundString.split("\\|");
        player.playSound(player, org.bukkit.Sound.valueOf(Sound[0]), Float.parseFloat(Sound[1]), Float.parseFloat(Sound[2]));
    }

    public static String getTimeString(int Time) {
        int seconds = Time % 60;

        int totalMinutes = Time / 60;
        int minutes = totalMinutes % 60;

        int totalHours = totalMinutes / 60;
        int hours = totalHours % 24;

        int totalDays = totalHours / 24;
        int days = totalDays % 30;

        int totalMonths = totalDays / 30;
        int months = totalMonths % 12;

        int years = totalMonths / 12;

        StringBuilder sb = new StringBuilder();
        if (years > 0) {
            sb.append(years).append("年");
        }
        if (months > 0) {
            sb.append(months).append("月");
        }
        if (days > 0) {
            sb.append(days).append("日");
        }
        if (hours > 0) {
            sb.append(hours).append("时");
        }
        if (minutes > 0) {
            sb.append(minutes).append("分");
        }
        if (seconds > 0) {
            sb.append(seconds).append("秒");
        }
        return sb.toString();
    }
}
