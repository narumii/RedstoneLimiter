package uwu.narumi;

import java.lang.reflect.Field;
import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import uwu.narumi.command.RedstoneCommand;
import uwu.narumi.config.Config;
import uwu.narumi.config.ConfigManager;
import uwu.narumi.listener.RedstoneListener;
import uwu.narumi.task.RedstoneDisableTask;

public class Main extends JavaPlugin {

  private static Main instance;

  private SimpleCommandMap commandMap;
  private ConfigManager configManager;
  private Config config;

  public static Main getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    getLogger().info("Fuck yooniks skid lol");
    instance = this;

    configManager = new ConfigManager(getDataFolder());
    config = configManager.loadConfig();

    if (commandMap == null) {
      trySetCommandMap();
      commandMap.register("redstonelimiter", new RedstoneCommand());
    }

    getServer().getPluginManager().registerEvents(new RedstoneListener(), this);

    if (config.AUTO.enabled) //We can run this in async
    {
      Bukkit.getScheduler().runTaskTimerAsynchronously(this, new RedstoneDisableTask(), 20, 20);
    }
  }

  @Override
  public void onDisable() {
    getLogger().info("Fuck yooniks skid lol");
    configManager.saveConfig(config);
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

  public Config getCustomConfig() {
    return config;
  }
}
