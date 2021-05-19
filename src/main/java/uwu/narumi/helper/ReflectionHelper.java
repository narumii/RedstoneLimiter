package uwu.narumi.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class ReflectionHelper {

  private static String VERSION;
  private static String BUKKIT;
  private static String NMS;

  private static Field tpsField;
  private static Method instance;

  static {
    try {
      BUKKIT = Bukkit.getServer().getClass().getName().replace(".CraftServer", "");
      NMS = BUKKIT.replace("org.bukkit.craftbukkit", "net.minecraft.server");
      VERSION = (BUKKIT.split("\\.")[BUKKIT.split("\\.").length - 1]).substring(1)
          .replace("_", "."); //Yes i know boiler plate

      Class<?> serverClass = Class.forName(NMS + ".MinecraftServer");

      tpsField = serverClass.getDeclaredField("recentTps");
      instance = serverClass.getDeclaredMethod("getServer");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static double[] getTPS() {
    try {
      return (double[]) tpsField.get(instance.invoke(null));
    } catch (Exception e) {
      Bukkit.getLogger().severe("Can't get server tps: " + e);
    }

    return new double[3];
  }

  public static String getBukkitPackage() {
    return BUKKIT;
  }

  public static String getServerPackage() {
    return NMS;
  }

  public static String getVersion() {
    return VERSION;
  }
}