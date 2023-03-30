package cn.ChengZhiYa.ChengToolsReloaded.HashMap;

import org.bukkit.Location;

import java.util.HashMap;

public class LocationHashMap {
    static final HashMap<String, Location> Temp = new HashMap<>();

    public static void Set(String HashMapName, Location Value) {
        Temp.put(HashMapName, Value);
    }

    public static Location Get(String HashMapName) {
        return Temp.get(HashMapName);
    }

    public static void Clear() {
        try {
            Temp.clear();
        } catch (Exception ignored) {
        }
    }
}
