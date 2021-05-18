package uwu.narumi;

import java.lang.reflect.Field;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import uwu.narumi.command.MaxChangesCommand;
import uwu.narumi.listener.RedstoneListener;

public class Main extends JavaPlugin {

  private static Main instance;

  private SimpleCommandMap commandMap;
  private int maxChanges;

  @Override
  public void onEnable() {
    getLogger().info("Fuck yooniks skid lol");
    instance = this;

    if (commandMap == null) {
      trySetCommandMap();
      commandMap.register("redstonelimiter", new MaxChangesCommand());
    }

    saveDefaultConfig();
    maxChanges = getConfig().getInt("max-redstone-changes", 600);

    getServer().getPluginManager().registerEvents(new RedstoneListener(), this);
  }

  @Override
  public void onDisable() {
    getLogger().info("Fuck yooniks skid lol");

    getConfig().set("max-redstone-changes", maxChanges);
    saveConfig();
  }

  private void trySetCommandMap() {
    try {
      Field field = getServer().getClass().getDeclaredField("commandMap");
      field.setAccessible(true);

      commandMap = (SimpleCommandMap) field.get(getServer());
    } catch (Exception e) {
      getLogger().severe("Can't set CommandMap");
    }
  }

  public int getMaxChanges() {
    return maxChanges;
  }

  public void setMaxChanges(int maxChanges) {
    this.maxChanges = maxChanges;
  }

  public static Main getInstance() {
    return instance;
  }
}
