package uwu.narumi.config;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

  /*
  WTF IS THAT????? XD
   */

  private final FileConfiguration configuration;

  public int limit = 600;
  public int tps;

  public boolean disableRedstone;
  public boolean autoDisable = true;

  public boolean canDisableRedstone;

  public Config(FileConfiguration configuration) {
    this.configuration = configuration;
  }

  public void load() {
    limit = configuration.getInt("max-redstone-changes", 600);
    tps = configuration.getInt("disable-tps-trigger", 15);
    disableRedstone = configuration.getBoolean("disable-redstone", false);
    autoDisable = configuration.getBoolean("auto-disable-redstone", true);
  }

  public void save() {
    configuration.set("max-redstone-changes", limit);
    configuration.set("disable-tps-trigger", tps);
    configuration.set("disable-redstone", disableRedstone);
    configuration.set("auto-disable-redstone", autoDisable);
  }
}
